/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import vista.rows.ClienteRow;

/**
 *
 * @author oscar
 */
public class ReporteDataSource implements JRDataSource{
    private final List<ClienteRow> clientes = new ArrayList<>();
    private int indiceClientesActual = -1;

    @Override
    public Object getFieldValue(JRField jrf) throws JRException
    {
        Object valor = null;

        if (null != jrf.getName())
        switch (jrf.getName()) {
            case "numeroTelefonico":
                valor = clientes.get(indiceClientesActual).getNumeroTelefonico();
                break;
            case "mesesFaltantes":
                valor = clientes.get(indiceClientesActual).getMesesFaltantes();
                break;
            case "cuota":
                valor = clientes.get(indiceClientesActual).getCuota();
                break;
            case "mensaje":
                valor = clientes.get(indiceClientesActual).getMensaje();
                break;
            case "nombre":
                valor = clientes.get(indiceClientesActual).getNombre();
                break;
            case "plan":
                valor = clientes.get(indiceClientesActual).getPlan();
                break;
            case "cuenta":
                valor = clientes.get(indiceClientesActual).getCuenta();
                break;
            case "fechaDeFacturacion":
                valor = clientes.get(indiceClientesActual).getFechaFacturacion();
                break;
            case "plazo":
                valor = clientes.get(indiceClientesActual).getPlazo().toString();
                break;
            case "puntosCA":
                valor = clientes.get(indiceClientesActual).getPuntosCA().toString();
                break;
            case "delegacion":
                valor = clientes.get(indiceClientesActual).getDelegacion();
                break;
            case "direccion":
                valor = clientes.get(indiceClientesActual).getDireccion();
                break;
            default:
                break;
        }

        return valor;
    }

    @Override
    public boolean next() throws JRException
    {
        return ++indiceClientesActual < clientes.size();
    }

    public void add(ClienteRow cliente)
    {
        this.clientes.add(cliente);
    }
}

