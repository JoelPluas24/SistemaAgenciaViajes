/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import config.DatabaseConnection;
import controller.InventarioController;
import controller.PagosController;
import controller.PasajesController;
import controller.ReservasController;
import controller.RutasController;
import dao.PasajesDAO;
import dao.RutasDAO;
import dao.UsuarioDAO;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import model.Pasaje;
import model.Rutas;
import model.Usuario;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("Conexión exitosa con la base de datos.");
        } else {
            System.out.println("Error al conectar con la base de datos.");
        }
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Agregar un usuario
        //Usuario nuevoUsuario = new Usuario(0, "Carlos Martínez", "carlos@example.com", "hashed_password", LocalDate.now());
        //System.out.println(usuarioDAO.agregarUsuario(nuevoUsuario));

        // Listar usuarios
        //usuarioDAO.listarUsuarios().forEach(usuario -> 
        //    System.out.println(usuario.getIdUsuario() + " - " + usuario.getNombre())
        //);
        
        RutasDAO rutasDAO = new RutasDAO();

        // Probar agregar una ruta
        //Rutas nuevaRuta = new Rutas(0, "Quito", "Guayaquil", LocalTime.of(6, 00)); // Duración de 6 horas y 30 minutos
        //System.out.println(rutasDAO.agregarRuta(nuevaRuta));

        // Probar listar rutas
        rutasDAO.listarRutas().forEach(ruta -> 
            System.out.println("ID Ruta: " + ruta.getIdRuta() + 
                               ", Origen: " + ruta.getOrigen() + 
                               ", Destino: " + ruta.getDestino() + 
                               ", Duración: " + ruta.getDuracion()) // LocalTime se muestra como HH:MM:SS
        );
        
        InventarioController inventarioController = new InventarioController();
        //System.out.println(inventarioController.agregarInventario(1, 1, 20));
        inventarioController.listarInventarios().forEach(inventario -> 
        System.out.println(inventario.getIdInventario() + " - Ruta: " + inventario.getIdRuta() + " - Pasaje: " + inventario.getIdPasaje() + " - Cantidad: " + inventario.getCantidadDisponible())
        );
        
        
        PasajesDAO pasajesDAO = new PasajesDAO();
        // Probar agregar un pasaje
        //Pasaje nuevoPasaje = new Pasaje(0, 1, "avion", LocalDate.now().plusDays(5), LocalTime.of(10, 30), 120.00, 50);
        //System.out.println(pasajesDAO.agregarPasaje(nuevoPasaje));

        // Probar listar pasajes por ruta
        pasajesDAO.listarPasajesPorRuta(1).forEach(pasaje ->
            System.out.println("ID: " + pasaje.getIdPasaje() + 
                               ", Transporte: " + pasaje.getTipoTransporte() + 
                               ", Precio: " + pasaje.getPrecio())
        );
        
        
        PagosController pagosController = new PagosController();
        //System.out.println(pagosController.registrarPago(1, 100.50, "paypal", "exitoso", LocalDate.now()));
        pagosController.listarPagos().forEach(pago ->
        System.out.println("ID Pago: " + pago.getIdPago() + ", Monto: " + pago.getMonto() + ", Método: " + pago.getMetodoPago())
        );
        
        
        ReservasController reservasController = new ReservasController();
        //System.out.println(reservasController.crearReserva(1, 2, "confirmada", LocalDate.now()));
        //reservasController.listarReservasPorUsuario(1).forEach(reserva ->
        //System.out.println("ID Reserva: " + reserva.getIdReserva() + ", Estado: " + reserva.getEstado())
        //);
        
        
        PasajesController pasajesController = new PasajesController();
        //System.out.println(pasajesController.agregarPasaje(1, "avion", LocalDate.now().plusDays(3), LocalTime.of(8, 45), 150.00, 30));
        pasajesController.listarPasajesPorRuta(1).forEach(pasaje ->
        System.out.println("ID Pasaje: " + pasaje.getIdPasaje() + ", Precio: " + pasaje.getPrecio() + ", Transporte: " + pasaje.getTipoTransporte())
        );


        
        
       



        


        
        
        
        
        
    }
    
    
}

