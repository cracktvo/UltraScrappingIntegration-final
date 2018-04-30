/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.rows;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import modelo.entidades.Cliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oscar
 */
public class ClienteRow  extends Row{

    private static final Logger LOG = Logger.getLogger(ClienteRow.class.getName());

    private final SimpleStringProperty numeroTelefonico;
    private final SimpleStringProperty mesesFaltantes;
    private final SimpleStringProperty cuota;
    private final SimpleStringProperty mensaje;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty plan;
    private final SimpleStringProperty cuenta;
    private final SimpleStringProperty fechaFacturacion;
    private final SimpleLongProperty plazo;
    private final SimpleLongProperty puntosCA;
    private final SimpleStringProperty delegacion;
    private final SimpleStringProperty direccion;
    private final SimpleLongProperty contesto;
    private final SimpleLongProperty status;
    private final SimpleStringProperty cuando;
    private final SimpleLongProperty porqueNoGemobile;
    private final SimpleLongProperty porqueCambio;
    private final SimpleDoubleProperty cuotaFin;

    SimpleDateFormat dateFormatOut = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat dateFormatIn = new SimpleDateFormat("dd/MM/yyyy");

    public ClienteRow(String numeroTelefonico
            , String mesesFaltantes
            , String cuota
            , String mensaje
            , String nombre
            , String plan
            , String cuenta
            , String fechaFacturacion
            , Long plazo
            , Long puntosCA
            , String delegacion
            , String direccion
            , Long contesto
            , Long status
            , Date cuando
            , Long porqueNoGemobile
            , Long porqueCambio
            ,Double cuotaFin) {
        this.numeroTelefonico = new SimpleStringProperty(numeroTelefonico);
        this.mesesFaltantes = new SimpleStringProperty(mesesFaltantes);
        this.cuota = new SimpleStringProperty(cuota);
        this.mensaje = new SimpleStringProperty(mensaje);
        this.nombre = new SimpleStringProperty(nombre);
        this.plan = new SimpleStringProperty(plan);
        this.cuenta = new SimpleStringProperty(cuenta);
        this.fechaFacturacion = new SimpleStringProperty(fechaFacturacion);
        this.plazo = new SimpleLongProperty(plazo!=null?plazo:new Long(0));
        this.puntosCA = new SimpleLongProperty(puntosCA!=null?puntosCA:new Long(0));
        this.delegacion = new SimpleStringProperty(delegacion);
        this.direccion = new SimpleStringProperty(direccion);
        this.contesto = new SimpleLongProperty(contesto!=null?contesto:new Long(0));
        this.status = new SimpleLongProperty(status!=null?status:new Long(0));
        this.cuando = new SimpleStringProperty(cuando!=null?dateFormatOut.format(cuando):null);
        this.porqueNoGemobile = new SimpleLongProperty(porqueNoGemobile!=null?porqueNoGemobile:new Long(0));
        this.porqueCambio = new SimpleLongProperty(porqueCambio!=null?porqueCambio:new Long(0));
        this.cuotaFin = new SimpleDoubleProperty(cuotaFin!=null?cuotaFin:new Long(0));
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico.get();
    }

    public String getMesesFaltantes() {
        return mesesFaltantes.get();
    }

    public String getCuota() {
        return cuota.get();
    }

    public String getMensaje() {
        return mensaje.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getPlan() {
        return plan.get();
    }

    public String getCuenta() {
        return cuenta.get();
    }

    public String getFechaFacturacion() {
        return fechaFacturacion.get();
    }

    public Long getPlazo() {
        return plazo.get();
    }

    public Long getPuntosCA() {
        return puntosCA.get();
    }

    public String getDelegacion() {
        return delegacion.get();
    }

    public String getDireccion() {
        return direccion.get();
    }

    public Long getContesto() {
        return contesto.get();
    }


    public Long getStatus() {
        return status.get();
    }


    public String getCuando() {
        return cuando.get();
    }


    public Long getPorqueNoGemobile() {
        return porqueNoGemobile.get();
    }


    public Long getPorqueCambio() {
        return porqueCambio.get();
    }

    public double getCuotaFin() {
        return cuotaFin.get();
    }


    @Override
    public String toString() {
        return "ClienteRow{" +
                "numeroTelefonico=" + numeroTelefonico +
                ", mesesFaltantes=" + mesesFaltantes +
                ", cuota=" + cuota +
                ", mensaje=" + mensaje +
                ", nombre=" + nombre +
                ", plan=" + plan +
                ", cuenta=" + cuenta +
                ", fechaFacturacion=" + fechaFacturacion +
                ", plazo=" + plazo +
                ", puntosCA=" + puntosCA +
                ", delegacion=" + delegacion +
                ", direccion=" + direccion +
                ", contesto=" + contesto +
                ", status=" + status +
                ", cuando=" + cuando +
                ", porqueNoGemobile=" + porqueNoGemobile +
                ", porqueCambio=" + porqueCambio +
                ", dateFormatOut=" + dateFormatOut +
                ", dateFormatIn=" + dateFormatIn +
                '}';
    }

    @Override
    public Cliente getEntity() {
        Date nCuando = null;
        try {
            nCuando = cuando!=null?dateFormatIn.parse(getCuando()):null;
        } catch (ParseException e) {
            LOG.log(Level.SEVERE, "ClienteRow.getEntity: {0}", e.getMessage());
        }
        return new Cliente(getNumeroTelefonico()
                , getMesesFaltantes()
                , getCuota()
                , getMensaje()
                , getNombre()
                , getPlan()
                , getCuenta()
                , getFechaFacturacion()
                , getPlazo()
                , getPuntosCA()
                , getDelegacion()
                , getDireccion()
                , getContesto()
                , getStatus()
                , nCuando
                , getPorqueNoGemobile()
                ,getPorqueCambio()
                ,getCuotaFin());
    }

}
