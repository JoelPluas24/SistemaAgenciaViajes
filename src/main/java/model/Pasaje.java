/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Pasaje {
    private int idPasaje;
    private int idRuta;
    private String tipoTransporte;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private double precio;
    private int disponibilidad;

    public Pasaje(int idPasaje, int idRuta, String tipoTransporte, LocalDate fechaSalida, LocalTime horaSalida, double precio, int disponibilidad) {
        this.idPasaje = idPasaje;
        this.idRuta = idRuta;
        this.tipoTransporte = tipoTransporte;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.precio = precio;
        this.disponibilidad = disponibilidad;
    }

    public Pasaje() {}

    public int getIdPasaje() {
        return idPasaje;
    }

    public void setIdPasaje(int idPasaje) {
        this.idPasaje = idPasaje;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getTipoTransporte() {
        return tipoTransporte;
    }

    public void setTipoTransporte(String tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}

