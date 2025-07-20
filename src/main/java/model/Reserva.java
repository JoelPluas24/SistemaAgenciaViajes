/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class Reserva {
    private int idReserva;
    private int idCliente; 
    private int idPasaje;
    private String estado;
    private LocalDate fechaReserva;

    public Reserva(int idReserva, int idCliente, int idPasaje, String estado, LocalDate fechaReserva) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.idPasaje = idPasaje;
        this.estado = estado;
        this.fechaReserva = fechaReserva;
    }

    public Reserva() {}

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdCliente() { // Antes era getIdUsuario
        return idCliente;
    }

    public void setIdCliente(int idCliente) { // Antes era setIdUsuario
        this.idCliente = idCliente;
    }

    public int getIdPasaje() {
        return idPasaje;
    }

    public void setIdPasaje(int idPasaje) {
        this.idPasaje = idPasaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
}
