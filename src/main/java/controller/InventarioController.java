/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.InventarioDAO;
import model.Inventario;

import java.util.List;

public class InventarioController {
    private final InventarioDAO inventarioDAO;

    public InventarioController() {
        this.inventarioDAO = new InventarioDAO();
    }

    public String agregarInventario(int idRuta, int idPasaje, int cantidadDisponible) {
        Inventario nuevoInventario = new Inventario(0, idRuta, idPasaje, cantidadDisponible);
        return inventarioDAO.agregarInventario(nuevoInventario);
    }

    public List<Inventario> listarInventarios() {
        return inventarioDAO.listarInventarios();
    }

    public String actualizarInventario(int idInventario, int nuevaCantidad) {
        return inventarioDAO.actualizarInventario(idInventario, nuevaCantidad);
    }
}

