package com.example.annel.arqui2;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class sensores {
    @SerializedName("m_fecha")
    private Date fecha;
    @SerializedName("m_temperatura")
    private double temperatura;
    @SerializedName("m_humedad")
    private double humedad;
    @SerializedName("m_iluminacion")
    private double luz;
    @SerializedName("m_peso")
    private double peso;
    @SerializedName("estado")
    private double estado;

    public  sensores(Date fecha, double temperatura, double humedad, double iluminacion, double peso){
        this.setFecha(fecha);
        this.setTemperatura(temperatura);
        this.setTemperatura(humedad);
        this.setIluminacion(iluminacion);
        this.setPeso(peso);
    }
    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getIluminacion() {
        return luz;
    }

    public void setIluminacion(double iluminacion) {
        this.luz = iluminacion;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public double getEstado() {
        return estado;
    }

    public void setEstado(double estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
