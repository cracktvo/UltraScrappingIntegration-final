package jentities;

import modelo.entidades.Cliente;

import java.util.Date;
import java.sql.Timestamp;

/**
 * Created by oscar on 04/10/2016.
 */
public class ClienteEntity {

    private String cNumeroTelefonico;
    private String fMesesFaltantes;
    private String dCuota;
    private String cMensaje;
    private String cNombre;
    private String cPlan;
    private String cCuenta;
    private String fFechaFacturacion;
    private Long iPlazo;
    private Long iPuntosCa;
    private String cDelegacion;
    private String cDireccion;
    private Timestamp fUltimoContacto;
    private Long iContesto;
    private Long iStatus;
    private Date dCuando;
    private Long iPorqueNoGemobile;
    private Long iPorqueCambio;
    private Double dCuotaFin;

    public ClienteEntity(String cNumeroTelefonico) {
        this.cNumeroTelefonico = cNumeroTelefonico;
    }

    public ClienteEntity(String cNumeroTelefonico, String fMesesFaltantes, String dCuota, String cMensaje, String cNombre, String cPlan, String cCuenta, String fFechaFacturacion, Long iPlazo, Long iPuntosCa, String cDelegacion, String cDireccion, Timestamp fUltimoContacto, Long iContesto, Long iStatus, Date dCuando, Long iPorqueNoGemobile, Long iPorqueCambio, Double dCuotaFin) {
        this.cNumeroTelefonico = cNumeroTelefonico;
        this.fMesesFaltantes = fMesesFaltantes;
        this.dCuota = dCuota;
        this.cMensaje = cMensaje;
        this.cNombre = cNombre;
        this.cPlan = cPlan;
        this.cCuenta = cCuenta;
        this.fFechaFacturacion = fFechaFacturacion;
        this.iPlazo = iPlazo;
        this.iPuntosCa = iPuntosCa;
        this.cDelegacion = cDelegacion;
        this.cDireccion = cDireccion;
        this.fUltimoContacto = fUltimoContacto;
        this.iContesto = iContesto;
        this.iStatus = iStatus;
        this.dCuando = dCuando;
        this.iPorqueNoGemobile = iPorqueNoGemobile;
        this.iPorqueCambio = iPorqueCambio;
        this.dCuotaFin = dCuotaFin;
    }

    public String getcNumeroTelefonico() {
        return cNumeroTelefonico;
    }

    public void setcNumeroTelefonico(String cNumeroTelefonico) {
        this.cNumeroTelefonico = cNumeroTelefonico;
    }

    public String getfMesesFaltantes() {
        return fMesesFaltantes;
    }

    public void setfMesesFaltantes(String fMesesFaltantes) {
        this.fMesesFaltantes = fMesesFaltantes;
    }

    public String getdCuota() {
        return dCuota;
    }

    public void setdCuota(String dCuota) {
        this.dCuota = dCuota;
    }

    public String getcMensaje() {
        return cMensaje;
    }

    public void setcMensaje(String cMensaje) {
        this.cMensaje = cMensaje;
    }

    public String getcNombre() {
        return cNombre;
    }

    public void setcNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getcPlan() {
        return cPlan;
    }

    public void setcPlan(String cPlan) {
        this.cPlan = cPlan;
    }

    public String getcCuenta() {
        return cCuenta;
    }

    public void setcCuenta(String cCuenta) {
        this.cCuenta = cCuenta;
    }

    public String getfFechaFacturacion() {
        return fFechaFacturacion;
    }

    public void setfFechaFacturacion(String fFechaFacturacion) {
        this.fFechaFacturacion = fFechaFacturacion;
    }

    public Long getiPlazo() {
        return iPlazo;
    }

    public void setiPlazo(Long iPlazo) {
        this.iPlazo = iPlazo;
    }

    public Long getiPuntosCa() {
        return iPuntosCa;
    }

    public void setiPuntosCa(Long iPuntosCa) {
        this.iPuntosCa = iPuntosCa;
    }

    public String getcDelegacion() {
        return cDelegacion;
    }

    public void setcDelegacion(String cDelegacion) {
        this.cDelegacion = cDelegacion;
    }

    public String getcDireccion() {
        return cDireccion;
    }

    public void setcDireccion(String cDireccion) {
        this.cDireccion = cDireccion;
    }

    public Timestamp getfUltimoContacto() {
        return fUltimoContacto;
    }

    public void setfUltimoContacto(Timestamp fUltimoContacto) {
        this.fUltimoContacto = fUltimoContacto;
    }

    public Long getiContesto() {
        return iContesto;
    }

    public void setiContesto(Long iContesto) {
        this.iContesto = iContesto;
    }

    public Long getiStatus() {
        return iStatus;
    }

    public void setiStatus(Long iStatus) {
        this.iStatus = iStatus;
    }

    public Date getdCuando() {
        return dCuando;
    }

    public void setdCuando(Date dCuando) {
        this.dCuando = dCuando;
    }

    public Long getiPorqueNoGemobile() {
        return iPorqueNoGemobile;
    }

    public void setiPorqueNoGemobile(Long iPorqueNoGemobile) {
        this.iPorqueNoGemobile = iPorqueNoGemobile;
    }

    public Long getiPorqueCambio() {
        return iPorqueCambio;
    }

    public void setiPorqueCambio(Long iPorqueCambio) {
        this.iPorqueCambio = iPorqueCambio;
    }

    public Double getdCuotaFin() {
        return dCuotaFin;
    }

    public void setdCuotaFin(Double dCuotaFin) {
        this.dCuotaFin = dCuotaFin;
    }

    @Override
    public String toString() {
        return "ClienteEntity{" +
                "cNumeroTelefonico='" + cNumeroTelefonico + '\'' +
                ", fMesesFaltantes='" + fMesesFaltantes + '\'' +
                ", dCuota='" + dCuota + '\'' +
                ", cMensaje='" + cMensaje + '\'' +
                ", cNombre='" + cNombre + '\'' +
                ", cPlan='" + cPlan + '\'' +
                ", cCuenta='" + cCuenta + '\'' +
                ", fFechaFacturacion='" + fFechaFacturacion + '\'' +
                ", iPlazo=" + iPlazo +
                ", iPuntosCa=" + iPuntosCa +
                ", cDelegacion='" + cDelegacion + '\'' +
                ", cDireccion='" + cDireccion + '\'' +
                ", fUltimoContacto=" + fUltimoContacto +
                ", iContesto=" + iContesto +
                ", iStatus=" + iStatus +
                ", dCuando=" + dCuando +
                ", iPorqueNoGemobile=" + iPorqueNoGemobile +
                ", iPorqueCambio=" + iPorqueCambio +
                ", dCuotaFin=" + dCuotaFin +
                '}';
    }
}
