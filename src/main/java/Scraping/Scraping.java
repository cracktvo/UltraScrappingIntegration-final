package Scraping;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import modelo.entidades.Cliente;
import net.sourceforge.htmlunit.corejs.javascript.WrappedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.util.StringUtil;
import uploader.Oc;
import uploader.Uploader;

import javax.swing.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.SocketTimeoutException;
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
    private String cuotaPasada = null;
    int contador =0;
    HtmlPage pageresultadoLimpia;

    public Scraping() {
    }

    private WebClient obtenerWC() {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
        WebClient webClient = new WebClient(BrowserVersion.EDGE);
        webClient.setJavaScriptTimeout(40000);
        webClient.waitForBackgroundJavaScript(2000);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setPrintContentOnFailingStatusCode(true);
        webClient.getOptions().setAppletEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(20000);
        return webClient;
    }

    private HtmlSubmitInput logSmart(WebClient webClient) throws IOException {
        try {
            HtmlPage page1 = webClient.getPage("https://www.region9.telcel.com/smartdistribuidores");

            HtmlTextInput textFieldUsuario = page1.getHtmlElementById("txtUsuario");
            HtmlPasswordInput passFieldContrasenia = page1.getHtmlElementById("txtContrasenia");
            HtmlSubmitInput button = page1.getHtmlElementById("btnEntrar");

            textFieldUsuario.setValueAttribute(Oc.getSmartUser());
            passFieldContrasenia.setValueAttribute(Oc.getSmartPass());

            return button;
        } catch (IOException | FailingHttpStatusCodeException e) {
            LOG.log(Level.SEVERE, "logSmart : {0}", e.getMessage());
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
        HtmlTextInput textFieldRFC;
        HtmlSubmitInput buttonPuntos;

        int num = Oc.getNumCotiCC();
        try {
            LOG.log(Level.INFO, "obtenerCamposSmart: {0}", "Obtiene el submenu");
            pageResultado = obtenerPaginaMenu(button,num, "Menu1_" + (num+1),webClient);
            webClient.waitForBackgroundJavaScript(1000);
            LOG.log(Level.INFO, "obtenerCamposSmart: {0}", pageResultado.asXml());
            LOG.log(Level.INFO, "obtenerCamposSmart: {0}", "Termina de obtener el submenu");
            textFieldCelular = pageResultado.getHtmlElementById("celular");
            LOG.log(Level.INFO, "obtenerCamposSmart: {0}", "Obtuvo el campo celular");
            textFieldCuenta = pageResultado.getHtmlElementById("cuenta");
            LOG.log(Level.INFO, "obtenerCamposSmart: {0}", "Obtuvo el campo cuenta");
            textFieldRFC = pageResultado.getHtmlElementById("rfc");
            LOG.log(Level.INFO, "obtenerCamposSmart: {0}", "Obtuvo el campo RFC");
            buttonPuntos = pageResultado.getHtmlElementById("buscarPuntos");
            LOG.log(Level.INFO, "obtenerCamposSmart: {0}", "Obtuvo el boton buscarPuntos");
            elements.add(textFieldCelular);
            elements.add(textFieldCuenta);
            elements.add(textFieldRFC);
            elements.add(buttonPuntos);
            repetir = false;
            this.elements = elements;
            if(StringUtils.isEmpty(obtenerClienteSinSubir(new Cliente("92096786")).getNombre())){
                LOG.log(Level.INFO, "obtenerCamposSmart: {0}", "Se manda a repetir por no encontrar al cliente.");
                repetir = true;
                webClient.close();
                return elements;
            }
            LOG.log(Level.INFO, "obtenerCamposSmart: {0}", "Si obtuvo el primer muestreo");
        } catch (ElementNotFoundException |NullPointerException e) {
            repetir = true;
            webClient.close();
            LOG.log(Level.SEVERE, "Sesion expiro: {0}", Arrays.toString(e.getStackTrace()));
            return elements;
        }
        return elements;
    }

    private Cliente cargarDatosSmart(HtmlTextInput textFieldCelular, HtmlTextInput textFieldCuenta, HtmlTextInput textFieldRFC, HtmlSubmitInput buttonPuntos, WebClient webClient, Cliente cliente,boolean numSearch) throws IOException {



        if(numSearch){
            LOG.log(Level.INFO, "Cliente: {0} {1} ", new Object[]{new Date(), cliente});
            textFieldCelular.setValueAttribute(cliente.getNumeroTelefonico());
        }else{
            LOG.log(Level.INFO, "Cliente: {0} {1} ", new Object[]{new Date(), cliente});
            if(cliente.getCuenta()!=null&&cliente.getCuenta().equals("0000000000")){
                textFieldCelular.setValueAttribute("0000000000");
                textFieldCuenta.setValueAttribute("");
            }else if(cliente.getCuenta()==null){
                textFieldCuenta.setValueAttribute("0");
            }else{
                textFieldCuenta.setValueAttribute(cliente.getCuenta());
                textFieldCelular.setValueAttribute("");
            }

        }
        textFieldRFC.setValueAttribute("0");
        HtmlPage pageResultado = buttonPuntos.click();
        webClient.waitForBackgroundJavaScript(5000);
        repetir = false;
        String error = "";
        if(pageResultado.asXml().contains("table")&&(!pageResultado.asXml().contains("encontraron"))){
            int dias;
            String modalidad;

            try {

                HtmlDivision divConsultaPuntos = pageResultado.getHtmlElementById("consultaPuntos");
                HtmlTable table1 = (HtmlTable) divConsultaPuntos.getElementsByTagName("table").get(0);
                HtmlTable table2 = (HtmlTable) table1.getRow(0).getCell(0).getElementsByTagName("table").get(0);
                HtmlTable table3 = (HtmlTable) table1.getRow(0).getCell(2).getElementsByTagName("table").get(0);


                cliente.setNombre(table2.getRow(0).getCell(1).getTextContent());
                cliente.setNumeroTelefonico(table2.getRow(1).getCell(1).getTextContent());
                cliente.setCuenta(table2.getRow(3).getCell(1).getTextContent());
                cliente.setFechaFacturacion(table3.getRow(3).getCell(1).getTextContent());
                System.out.println("Buscando diasRenovacion...");
                error="diasRenovacion";
                HtmlHiddenInput diasRenovacion = pageResultado.getHtmlElementById("diasRenovacion");
                HtmlHiddenInput plazoForzosoHdn = pageResultado.getHtmlElementById("plazoForzosoHdn");
                System.out.println("Buscando planActual...");
                error="planActual";
                HtmlHiddenInput planActualInput = pageResultado.getHtmlElementById("planActual");

                dias = Integer.parseInt(diasRenovacion.getValueAttribute());
                dias = dias>=0?dias:0;


                cliente.setMesesFaltantes(Integer.toString(dias));
                cliente.setPlazo(Long.parseLong(plazoForzosoHdn.getValueAttribute()));
                cliente.setMensaje("S");

                System.out.println("Buscando puntos...");
                HtmlHiddenInput puntosCA = pageResultado.getHtmlElementById("puntosDisponibles");
                cliente.setPuntosCA(StringUtils.isEmpty(puntosCA.getValueAttribute())?0:new Long(puntosCA.getValueAttribute()));
                error="puntosDisponibles";

                for(int i=0;i<5&&cliente.getCuota()==null;i++) {
                    System.out.println("Buscando tramite...");
                    error = "buscarTramite";
                    HtmlSelect selectTramites = pageResultado.getHtmlElementById("tramite");
                    pageResultado = selectTramites.setSelectedAttribute(selectTramites.getOption(i), true);

                    webClient.waitForBackgroundJavaScript(2000);
                    webClient.waitForBackgroundJavaScript(2000);

                    System.out.println("Buscando planes...");
                    error = "buscarPlanes";
                    pageResultado = pageResultado.getHtmlElementById("buscarPlanes").click();
                    webClient.waitForBackgroundJavaScript(2000);
                    webClient.waitForBackgroundJavaScript(2000);

                    System.out.println("Buscando planSeleccionado...");
                    error = "planSeleccionado";
                    HtmlSelect selectPlan = pageResultado.getHtmlElementById("planSeleccionado");

                    HtmlOption plan = null;
                    cliente.setPlan(planActualInput.getValueAttribute());
                    for (HtmlOption option : selectPlan.getOptions()) {
                        if (option.getText().contains("TELCEL")) {
                            plan = option;
                            break;
                        }
                    }
                    pageResultado = selectPlan.setSelectedAttribute(plan, true);
                    webClient.waitForBackgroundJavaScript(2000);
                    System.out.println("Buscando cuotaInput...");
                    error="cuotaInput";
                    HtmlTextInput cuota = pageResultado.getHtmlElementById("cuotaInput");
                    if(cuota.getText().equals(cuotaPasada)){
                        throw new ElementNotFoundException("Cuenta","Cuenta","Repitiendo");
                    }
                    cuotaPasada = cuota.getText();
                    System.out.println("Buscando labelPuntosDisponibles...");
                    error="labelPuntosDisponibles";
                    cliente.setCuota(cuota!=null?cuota.getText():"0");
                }

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



    private HtmlPage obtenerPaginaMenu(HtmlSubmitInput button, int m1, String m2,WebClient wc){
        HtmlPage pageResultado = null;
        HtmlPage resultado = null;
        try {
            pageResultado = button.click();
            wc.waitForBackgroundJavaScript(5000);
            LOG.log(Level.INFO, "obtenerCamposSmart: {0}", (pageResultado.asXml()));
            HtmlAnchor link = (HtmlAnchor) pageResultado.getElementById("lMenuSmart:submenu:"+m1).getElementsByTagName("a").get(0);
            resultado = link.click();
            wc.waitForBackgroundJavaScript(1000);
            resultado = wc.getPage(resultado.getBaseURL());
            wc.waitForBackgroundJavaScript(1000);
            while(!((HtmlPage)resultado.getFrames().get(0).getEnclosedPage()).asXml().contains("celular")){
                LOG.log(Level.INFO, "obtenerCamposSmart: {0}", ((HtmlPage)pageResultado.getFrames().get(0).getEnclosedPage()).asXml());
                pageResultado = button.click();
                wc.waitForBackgroundJavaScript(5000);
                resultado = pageResultado.getElementById("lMenuSmart:submenu:"+m1).getElementsByTagName("a").get(0).click();
                wc.waitForBackgroundJavaScript(6000);
            }
            repetir = false;
        } catch (RuntimeException e) {
            repetir = true;
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Scrapping.obtenerPaginaMenu: {0}", e.getMessage());
        }
        return resultado;
    }


    private void waitOutLoading(WebClient webClient,HtmlPage pageResultado) {
        while(!((HtmlPage)pageResultado.getFrames().get(0).getEnclosedPage()).asXml().contains("celular")){
            LOG.log(Level.INFO, "obtenerCamposSmart: {0}", ((HtmlPage)pageResultado.getFrames().get(0).getEnclosedPage()).asXml());
            webClient.waitForBackgroundJavaScript(2000);
        }
    }

    public void inicializar() throws IOException {
        repetir = true;
        int intentos=0;
        while (repetir) {
            webClientSmart = obtenerWC();
            pageSmart = logSmart(webClientSmart);
            elements = obtenerCamposSmart(pageSmart, webClientSmart);
            intentos++;
            if(intentos>4){
                System.exit(0);
            }
        }

    }
    public Cliente  obtenerCliente(Cliente entity,boolean numSearch) throws IOException{
        contador++;
        repetir = true;
            entity = cargarDatosSmart((HtmlTextInput) elements.get(0), (HtmlTextInput) elements.get(1), (HtmlTextInput) elements.get(2), (HtmlSubmitInput) elements.get(3), webClientSmart, entity,numSearch);
            if((!repetir)&&entity.getNombre()!=null){
                LOG.log(Level.INFO, "Cliente Final: {0} #: {1} ", new Object[]{entity.toString(), contador});
            }
            else{
                entity.setMensaje("N");
                LOG.log(Level.INFO, "Error en el cliente:{0} #: {1} ", new Object[]{entity.toString(), contador});
            }
            return entity;
    }

    public Cliente obtenerClienteSinSubir(Cliente entity) throws IOException{
        if(elements.isEmpty()){
            return new Cliente("");
        }
        contador++;
        repetir = true;
        if (validarCampo(entity)) {
            entity = cargarDatosSmart((HtmlTextInput) elements.get(0), (HtmlTextInput) elements.get(1), (HtmlTextInput) elements.get(2), (HtmlSubmitInput) elements.get(3), webClientSmart, entity,false);
            if((!repetir)&&entity.getNombre()!=null){
                LOG.log(Level.INFO, "Cliente Final: {0} #: {1} ", new Object[]{entity.toString(), contador});
            }
            else{
                entity.setMensaje("N");
                LOG.log(Level.INFO, "Error en el cliente:{0} #: {1} ", new Object[]{entity.toString(), contador});
            }
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
        if (entity.getCuenta() == null) {
            return false;
        }
        try {
            parseLong(entity.getCuenta());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void makeWebClientWaitThroughJavaScriptLoadings(WebClient webClient) {
        webClient.setAjaxController(new AjaxController(){
            @Override
            public boolean processSynchron(HtmlPage page, WebRequest request, boolean async)
            {
                return true;
            }
        });
    }

}
