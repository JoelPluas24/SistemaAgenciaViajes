/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.RutasDAO;
import java.time.LocalTime;
import model.Rutas;

import java.util.List;

public class RutasController {
    private final RutasDAO rutasDAO;

    public RutasController() {
        this.rutasDAO = new RutasDAO();
    }

    public String agregarRuta(String origen, String destino, LocalTime duracion) {
        Rutas nuevaRuta = new Rutas(0, origen, destino, duracion);
        return rutasDAO.agregarRuta(nuevaRuta);
    }

    public List<Rutas> listarRutas() {
        return rutasDAO.listarRutas();
    }

    public String eliminarRuta(int idRuta) {
        return rutasDAO.eliminarRuta(idRuta);
    }
}

