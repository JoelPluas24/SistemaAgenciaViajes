/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class Pago {
    private int idPago;
    private int idReserva;
    private double monto;
    private String metodoPago;
    private String estado;
    private LocalDate fechaPago;

    public Pago(int idPago, int idReserva, double monto, String metodoPago, String estado, LocalDate fechaPago) {
        this.idPago = idPago;
        this.idReserva = idReserva;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fechaPago = fechaPago;
    }

    public Pago() {}

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
}

