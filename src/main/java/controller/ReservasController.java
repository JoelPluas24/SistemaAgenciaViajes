/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ReservasDAO;
import model.Reserva;

import java.util.List;

public class ReservasController {
    private final ReservasDAO reservasDAO;

    public ReservasController() {
        this.reservasDAO = new ReservasDAO();
    }

    public String agregarReserva(Reserva reserva) {
        return reservasDAO.agregarReserva(reserva);
    }

    public List<Reserva> listarReservas() {
        return reservasDAO.listarReservas();
    }
    
    public String eliminarReserva(int idReserva) {
        return reservasDAO.eliminarReserva(idReserva);
    }

    public String actualizarReserva(Reserva reserva) {
        return reservasDAO.actualizarReserva(reserva);
    }
    
    public String procesarReserva(boolean clienteRegistrado, boolean destinoDisponible, boolean pagoConfirmado, Reserva reserva) {
    if (clienteRegistrado && destinoDisponible && pagoConfirmado) {
        return agregarReserva(reserva);  // A1: Agregar reserva
    } else {
        return "Reserva cancelada debido a condiciones no cumplidas.";  // A2: Cancelar reserva
    }
}
    
    
}
