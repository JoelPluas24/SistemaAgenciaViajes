/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PasajesDAO;
import model.Pasaje;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PasajesController {
    private final PasajesDAO pasajesDAO;

    public PasajesController() {
        this.pasajesDAO = new PasajesDAO();
    }

    public String agregarPasaje(int idRuta, String tipoTransporte, LocalDate fechaSalida, LocalTime horaSalida, double precio, int disponibilidad) {
        Pasaje nuevoPasaje = new Pasaje(0, idRuta, tipoTransporte, fechaSalida, horaSalida, precio, disponibilidad);
        return pasajesDAO.agregarPasaje(nuevoPasaje);
    }

    public List<Pasaje> listarPasajesPorRuta(int idRuta) {
        return pasajesDAO.listarPasajesPorRuta(idRuta);
    }
}

