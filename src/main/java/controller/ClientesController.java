/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ClientesDAO;
import model.Cliente;

import java.util.List;

public class ClientesController {
    private final ClientesDAO clientesDAO;

    public ClientesController() {
        this.clientesDAO = new ClientesDAO();
    }

    // Método para agregar un cliente
    public String agregarCliente(Cliente cliente) {
        return clientesDAO.agregarCliente(cliente);
    }

    // Método para listar clientes
    public List<Cliente> listarClientes() {
        return clientesDAO.listarClientes();
    }

    // Método para eliminar un cliente
    public String eliminarCliente(int idCliente) {
        return clientesDAO.eliminarCliente(idCliente);
    }

    // Método para actualizar un cliente
    public String actualizarCliente(Cliente cliente) {
        return clientesDAO.actualizarCliente(cliente);
    }
}
