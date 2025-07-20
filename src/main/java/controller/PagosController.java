/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PagosDAO;
import model.Pago;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PagosController {
    private final PagosDAO pagosDAO;

    public PagosController() {
        this.pagosDAO = new PagosDAO();
    }

    public String agregarPago(Pago pago) {
        return pagosDAO.agregarPago(pago);
    }

    public List<Pago> listarPagos() {
        return pagosDAO.listarPagos();
    }
    
        public String anularPago(int idPago) {
        return pagosDAO.anularPago(idPago);
    }
        
    public Map<String, String> obtenerPagoPorIdConDetalles(int idPago) {
        return pagosDAO.obtenerPagoPorIdConDetalles(idPago);
    }



}

