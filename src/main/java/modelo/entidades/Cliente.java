/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entidades;

import jentities.ClienteEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import vista.rows.ClienteRow;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 *
 * @author oscar
 */
public class Cliente extends Entity{
    private String numeroTelefonico;
    private String mesesFaltantes;
    private String cuota;
    private String mensaje;
    private String nombre;
    private String plan;
    private String cuenta;
    private String fechaFacturacion;
    private Long plazo;
    private Long puntosCA;
    private String delegacion;
    private String direccion;
    private Long contesto;
    private Long status;
    private Date cuando;
    private Long porqueNoGemobile;
    private Long porqueCambio;
    private Double cuotaFin;


    public Cliente() {
    }

    public Cliente(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public Cliente(String numeroTelefonico, String mesesFaltantes, String cuota, String mensaje, String nombre, String plan, String cuenta, String fechaFacturacion, Long plazo, Long puntosCA, Double cuotaFin) {
        this.numeroTelefonico = numeroTelefonico;
        this.mesesFaltantes = mesesFaltantes;
        this.cuota = cuota;
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.plan = plan;
        this.cuenta = cuenta;
        this.fechaFacturacion = fechaFacturacion;
        this.plazo = plazo;
        this.puntosCA = puntosCA;
        this.cuotaFin = cuotaFin;
    }

    public Cliente(String numeroTelefonico, String mesesFaltantes, String cuota, String mensaje, String nombre, String plan, String cuenta, String fechaFacturacion, Long plazo, Long puntosCA, String delegacion, String direccion, Long contesto, Long status, Date cuando, Long porqueNoGemobile, Long porqueCambio, Double cuotaFin) {
        this.numeroTelefonico = numeroTelefonico;
        this.mesesFaltantes = mesesFaltantes;
        this.cuota = cuota;
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.plan = plan;
        this.cuenta = cuenta;
        this.fechaFacturacion = fechaFacturacion;
        this.plazo = plazo;
        this.puntosCA = puntosCA;
        this.delegacion = delegacion;
        this.direccion = direccion;
        this.contesto = contesto;
        this.status = status;
        this.cuando = cuando;
        this.porqueNoGemobile = porqueNoGemobile;
        this.porqueCambio = porqueCambio;
        this.cuotaFin = cuotaFin;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getMesesFaltantes() {
        return mesesFaltantes;
    }

    public void setMesesFaltantes(String mesesFaltantes) {
        this.mesesFaltantes = mesesFaltantes;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(String fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public Long getPlazo() {
        return plazo;
    }

    public void setPlazo(Long plazo) {
        this.plazo = plazo;
    }

    public Long getPuntosCA() {
        return puntosCA;
    }

    public void setPuntosCA(Long puntosCA) {
        this.puntosCA = puntosCA;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getContesto() {
        return contesto;
    }

    public void setContesto(Long contesto) {
        this.contesto = contesto;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getCuando() {
        return cuando;
    }

    public void setCuando(Date cuando) {
        this.cuando = cuando;
    }

    public Long getPorqueNoGemobile() {
        return porqueNoGemobile;
    }

    public void setPorqueNoGemobile(Long porqueNoGemobile) {
        this.porqueNoGemobile = porqueNoGemobile;
    }

    public Long getPorqueCambio() {
        return porqueCambio;
    }

    public void setPorqueCambio(Long porqueCambio) {
        this.porqueCambio = porqueCambio;
    }

    public Double getCuotaFin() {
        return cuotaFin;
    }

    public void setCuotaFin(Double cuotaFin) {
        this.cuotaFin = cuotaFin;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "numeroTelefonico='" + numeroTelefonico + '\'' +
                ", mesesFaltantes='" + mesesFaltantes + '\'' +
                ", cuota='" + cuota + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", nombre='" + nombre + '\'' +
                ", plan='" + plan + '\'' +
                ", cuenta='" + cuenta + '\'' +
                ", fechaFacturacion='" + fechaFacturacion + '\'' +
                ", plazo=" + plazo +
                ", puntosCA=" + puntosCA +
                ", delegacion='" + delegacion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", contesto=" + contesto +
                ", status=" + status +
                ", cuando=" + cuando +
                ", porqueNoGemobile=" + porqueNoGemobile +
                ", porqueCambio=" + porqueCambio +
                ", cuotaFin=" + cuotaFin +
                '}';
    }

    @Override
    public ClienteRow getRow() {
        return new ClienteRow(numeroTelefonico
                , mesesFaltantes
                , cuota
                , mensaje
                , nombre
                , plan
                , cuenta
                , fechaFacturacion
                , plazo
                , puntosCA
                , delegacion
                , direccion
                , contesto
                , status
                , cuando
                , porqueNoGemobile
                , porqueCambio
                ,cuotaFin);
    }

    public ClienteEntity getSerial(){
        return new ClienteEntity(numeroTelefonico
                , mesesFaltantes
                , cuota
                , mensaje
                , nombre
                , plan
                , cuenta
                , fechaFacturacion
                , plazo
                , puntosCA
                , delegacion
                , direccion
                , null
                , contesto
                , status
                , cuando
                , porqueNoGemobile
                , porqueCambio
                ,cuotaFin);
    }

    public StringEntity getJsonString() throws UnsupportedEncodingException {

        JSONObject json = new JSONObject();
        json.put("cNumeroTelefonico", numeroTelefonico);
        json.put("fMesesFaltantes", mesesFaltantes);
        json.put("dCuota", cuota);
        json.put("cMensaje", mensaje);
        json.put("cNombre", nombre!=null?new String(nombre.getBytes("ISO-8859-1"),"UTF-8"):"NO EXISTE");
        json.put("cPlan", plan);
        json.put("cCuenta", cuenta);
        json.put("fFechaFacturacion", fechaFacturacion);
        json.put("iPlazo", plazo);
        json.put("iPuntosCa", puntosCA);
        json.put("dCuotaFin", cuotaFin);
        StringEntity se = new StringEntity( json.toString());
        se.setContentType("application/json");
        System.out.println(json.toString());
        return se;
    }
    
}
