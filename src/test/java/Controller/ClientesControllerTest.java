/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*package Controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.ClientesController;
import model.Cliente;
import java.util.List;
import java.util.ArrayList;

class ClientesControllerTest {
    private ClientesController clientesController;
    private List<Cliente> clientes;

    @BeforeEach
    void setUp() {
        
        clientesController = new ClientesController();
        clientes = new ArrayList<>();
    }

    @Test
    void testAgregarCliente() {
        Cliente cliente = new Cliente(5, "Luisa","Paredes","092274581", "par@gmail.com","0943678345");
        String resultado = clientesController.agregarCliente(cliente);
        assertEquals("Cliente agregado con éxito.", resultado);
    }
    
    @Test
    void testListarClientes() {
        Cliente cliente = new Cliente(2, "Juanito","Perez","094326573", "juanito@gmail.com","0944599898");
        clientesController.agregarCliente(cliente);
        List<Cliente> lista = clientesController.listarClientes();
        assertFalse(lista.isEmpty());
    }

    @Test
    void testEliminarCliente() {
        String resultado = clientesController.eliminarCliente(4);
        assertEquals("Cliente eliminado con éxito.", resultado);
    }

    @Test
    void testActualizarCliente() {
        Cliente cliente = new Cliente(1, "Christian","Albia","0968341612", "atonx@gmail.com","1317625257");
        clientesController.agregarCliente(cliente);
        cliente.setNombres("Christian");
        String resultado = clientesController.actualizarCliente(cliente);
        assertEquals("Cliente actualizado con éxito.", resultado);
    }
    
}*/
