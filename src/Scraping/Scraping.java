package Scraping;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import modelo.entidades.Cliente;
import net.sourceforge.htmlunit.corejs.javascript.WrappedException;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import uploader.Oc;
import uploader.Uploader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Long.parseLong;

/**
 *
 * @author Oscar Rodriguez
 */
public class Scraping {

    private static final Logger LOG = Logger.getLogger(Scraping.class.getName());

    private Uploader uploader = new Uploader();
    private boolean repetir = false;
    private WebClient webClientSmart;
    private HtmlSubmitInput pageSmart;
    List<HtmlElement> elements = new ArrayList<>();
    private String cuentaAnterior = "";
    int contador =0;
    HtmlPage pageresultadoLimpia;


    public Scraping() {
    }

    private WebClient obtenerWC() {
        WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setPrintContentOnFailingStatusCode(true);
        webClient.getOptions().setAppletEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setTimeout(999999999);
        return webClient;
    }

    private HtmlSubmitInput logSmart(WebClient webClient) throws IOException {
        try {
            HtmlPage page1 = webClient.getPage("https://www.region9.telcel.com/smartdistribuidores/login_dep.aspx");

            HtmlForm form = page1.getFormByName("seguridad");
            HtmlTextInput textFieldUsuario = form.getInputByName("LoginTB");
            HtmlPasswordInput passFieldContrasenia = form.getInputByName("PassTB");
            HtmlSubmitInput button = form.getInputByName("btnEntrar");

            textFieldUsuario.setValueAttribute("09VIDA68093000688");
            passFieldContrasenia.setValueAttribute("KAZA90304");

            return button;
        } catch (IOException | FailingHttpStatusCodeException e) {
            LOG.log(Level.SEVERE, "logSmart: {0}", e.getMessage());
        } catch (ElementNotFoundException e) {
            this.repetir = true;
            LOG.log(Level.SEVERE, "logSmart: {0}", e.getMessage());
        }
        return null;
    }

    private List<HtmlElement> obtenerCamposSmart(HtmlSubmitInput button, WebClient webClient) throws IOException {
        List<HtmlElement> elements = new ArrayList<>();
        HtmlPage pageResultado;
        HtmlTextInput textFieldCelular;
        HtmlTextInput textFieldCuenta;
        HtmlSubmitInput buttonPuntos;

        int num = Oc.obtenerCamposN();
        try {
            pageResultado = obtenerPaginaMenu(button, "Menu1_" + num, "Menu1_" + (num+1));
            webClient.waitForBackgroundJavaScript(1000);
            textFieldCelular = pageResultado.getHtmlElementById("celular");
            textFieldCuenta = pageResultado.getHtmlElementById("cuenta");
            buttonPuntos = pageResultado.getHtmlElementById("buscarPuntos");
            elements.add(textFieldCelular);
            elements.add(textFieldCuenta);
            elements.add(buttonPuntos);
            if (textFieldCelular == null || textFieldCuenta == null || buttonPuntos == null) {
                repetir = true;
            }
            repetir = false;
        } catch (ElementNotFoundException |NullPointerException e) {
            repetir = true;
            LOG.log(Level.SEVERE, "Sesion expiro: {0}", Arrays.toString(e.getStackTrace()));
            return elements;
        }
        return elements;
    }

    private Cliente cargarDatosSmart(HtmlTextInput textFieldCelular, HtmlTextInput textFieldCuenta, HtmlSubmitInput buttonPuntos, WebClient webClient, Cliente cliente) throws IOException {


        LOG.log(Level.INFO, "Cliente: {0} {1} ", new Object[]{new Date(), cliente.getNumeroTelefonico()});
        textFieldCelular.setValueAttribute(cliente.getNumeroTelefonico());
        textFieldCuenta.setValueAttribute("12345678");
        HtmlPage pageResultado = buttonPuntos.click();
        webClient.waitForBackgroundJavaScript(4000);
        if(pageresultadoLimpia!=null){
            pageResultado = pageresultadoLimpia;
            System.out.println("**Entra en pagina limpia**");
        }
        repetir = false;
        String error = "";
        if(!pageResultado.asXml().contains("encontraron")){
            int dias;
            String modalidad;

            try {

                HtmlDivision divConsultaPuntos = pageResultado.getHtmlElementById("consultaPuntos");
                HtmlTable table1 = (HtmlTable) divConsultaPuntos.getElementsByTagName("table").get(0);
                HtmlTable table2 = (HtmlTable) table1.getRow(0).getCell(0).getElementsByTagName("table").get(0);
                HtmlTable table3 = (HtmlTable) table1.getRow(0).getCell(2).getElementsByTagName("table").get(0);


                if(cuentaAnterior.equals(table2.getRow(3).getCell(1).getTextContent())){
                    throw new ElementNotFoundException("Cuenta","Cuenta","Repitiendo");
                }

                cuentaAnterior = table2.getRow(3).getCell(1).getTextContent();

                cliente.setNombre(table2.getRow(0).getCell(1).getTextContent());
                cliente.setCuenta(table2.getRow(3).getCell(1).getTextContent());
                cliente.setFechaFacturacion(table3.getRow(3).getCell(1).getTextContent());
                System.out.println("Buscando diasRenovacion...");
                error="diasRenovacion";
                HtmlHiddenInput diasRenovacion = pageResultado.getHtmlElementById("diasRenovacion");
                System.out.println("Buscando plazoForzosoHdn...");
                error="plazoForzosoHdn";
                HtmlHiddenInput plazoForzosoHdn = pageResultado.getHtmlElementById("plazoForzosoHdn");
                System.out.println("Buscando planActual...");
                error="planActual";
                HtmlHiddenInput planActualInput = pageResultado.getHtmlElementById("planActual");

                dias = Integer.parseInt(diasRenovacion.getValueAttribute());
                dias = dias>=0?dias:0;


                cliente.setMesesFaltantes(Integer.toString(dias));
                cliente.setPlazo(Long.parseLong(plazoForzosoHdn.getValueAttribute()));
                cliente.setMensaje("S");

                for(int i=0;i<7&&cliente.getPlan()==null;i++) {
                    System.out.println("Buscando tramite...");
                    error = "buscarTramite";
                    HtmlSelect selectTramites = pageResultado.getHtmlElementById("tramite");
                    pageResultado = selectTramites.setSelectedAttribute(selectTramites.getOption(i), true);
                    System.out.println("Buscando planes...");
                    error = "buscarPlanes";

                    HtmlRadioButtonInput input;
                    System.out.println("Buscando tipoplanCADIV...");
                    input = (HtmlRadioButtonInput) pageResultado.getElementById("tipoplanCADIV").getElementsByTagName("input").get(0);
                    modalidad = "true".equalsIgnoreCase(input.getAttribute("checked")) ? "Normal-" : "Mixto-";
                    cliente.setPlan(modalidad);
                    System.out.println("Buscando planes...");
                    error = "buscarPlanes";
                    pageResultado = pageResultado.getHtmlElementById("buscarPlanes").click();
                    webClient.waitForBackgroundJavaScript(2000);
                    webClient.waitForBackgroundJavaScript(2000);

                    System.out.println("Buscando planSeleccionado...");
                    error = "planSeleccionado";
                    HtmlSelect selectPlan = pageResultado.getHtmlElementById("planSeleccionado");

                    HtmlOption plan = null;
                    for (HtmlOption option : selectPlan.getOptions()) {
                        if (option.getText().contains(planActualInput.getValueAttribute())) {
                            cliente.setPlan(modalidad + option.getText());
                            plan = option;
                            break;
                        }
                    }
                    if(cliente.getPlan()==null){
                        for (HtmlOption option : selectPlan.getOptions()) {
                            if (option.getText().contains("MAX SIN LIMITE 1500 I") || option.getText().contains("MAX SIN LIMITE MIXTO 1500 I")) {
                                plan = option;
                                break;
                            }
                        }
                    }
                    pageResultado = selectPlan.setSelectedAttribute(plan, true);
                }
                webClient.waitForBackgroundJavaScript(2000);
                webClient.waitForBackgroundJavaScript(2000);
                webClient.waitForBackgroundJavaScript(2000);
                webClient.waitForBackgroundJavaScript(2000);
                webClient.waitForBackgroundJavaScript(2000);

                System.out.println("Buscando cuotaInput...");
                error="cuotaInput";
                HtmlTextInput cuota = pageResultado.getHtmlElementById("cuotaInput");
                System.out.println("Buscando labelPuntosDisponibles...");
                error="labelPuntosDisponibles";
                HtmlTextInput puntosCA = pageResultado.getHtmlElementById("labelPuntosDisponibles");
                cliente.setCuota(cuota!=null?cuota.getText():"0");
                cliente.setPuntosCA(StringUtils.isEmpty(puntosCA.getText())?0:new Long(puntosCA.getText()));
                System.out.println("Buscando financiamientoDiv...");
                error="financiamientoDiv";
                HtmlDivision financiamientoDiv = pageResultado.getHtmlElementById("financiamientoDiv");
                if(!financiamientoDiv.getAttribute("style").toString().contains("none")|| StringUtil.isBlank(financiamientoDiv.getAttribute("style").toString())) {
                    System.out.println("Buscando totalLbl...");
                    error = "totalLbl";
                    HtmlLabel cuotaFin = pageResultado.getHtmlElementById("totalLbl");
                    if (cuotaFin.hasChildNodes()) {
                        cliente.setCuotaFin(new Double(cuotaFin.getTextContent() != null ? cuotaFin.getTextContent() : "0.0"));
                    }
                }
                cuentaAnterior = cliente.getCuenta();
                if (dias>90) {
                    cliente.setMensaje("N");
                }
                webClient.waitForBackgroundJavaScript(3000);
            } catch (IOException | NullPointerException | IndexOutOfBoundsException e) {
                repetir = true;
                LOG.log(Level.SEVERE, "Log Smart: Error: {0} en: " + error, e.getMessage());
            } catch (ElementNotFoundException e) {
                repetir = true;
                LOG.log(Level.SEVERE, "Log Smart: Error: {0} en: " + error, e.getMessage());
            }finally {
                HtmlSubmitInput limpiar = pageResultado.getHtmlElementById("limpiarPantalla");
                pageresultadoLimpia = limpiar.click();
                webClient.waitForBackgroundJavaScript(3000);
            }
        }else {
            cliente.setMensaje("I");
        }
        return cliente;

    }



    private HtmlPage obtenerPaginaMenu(HtmlSubmitInput button, String m1, String m2){
        HtmlPage pageResultado = null;
        try {
            pageResultado = button.click();
            pageResultado = (HtmlPage) pageResultado.getElementById(m1).mouseOver();
            pageResultado = pageResultado.getElementById(m2).click();
            repetir = false;
        } catch (NullPointerException | WrappedException | ClassCastException e) {
            repetir = true;
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Scrapping.obtenerPaginaMenu: {0}", e.getMessage());
        }
        return pageResultado;
    }

    public void inicializar() throws IOException {
        repetir = true;
        while (repetir) {
            webClientSmart = obtenerWC();
            pageSmart = logSmart(webClientSmart);
            elements = obtenerCamposSmart(pageSmart, webClientSmart);
        }
    }
    public Cliente obtenerCliente(Cliente entity) throws IOException{
        contador++;
        repetir = true;
        if (validarCampo(entity)) {
            entity = cargarDatosSmart((HtmlTextInput) elements.get(0), (HtmlTextInput) elements.get(1), (HtmlSubmitInput) elements.get(2), webClientSmart, entity);
            if((!repetir)&&entity.getNombre()!=null){
                LOG.log(Level.INFO, "Cliente Final: {0} #: {1} ", new Object[]{entity.toString(), contador});
            }
            else{
                entity.setMensaje("N");
                LOG.log(Level.INFO, "Error en el cliente:{0} #: {1} ", new Object[]{entity.toString(), contador});
            }
            Uploader.insertarCliente(entity);
            return entity;
        } else {
            entity.setMensaje("Número telefónico debe tener 10 dígitos numéricos");
            return entity;
        }
    }


    public void finalizar(){
        webClientSmart.close();
    }

    private boolean validarCampo(Cliente entity) {
        if (entity.getNumeroTelefonico().length() != 10 && entity.getCuenta() != null) {
            return false;
        }
        try {
            parseLong(entity.getNumeroTelefonico());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
