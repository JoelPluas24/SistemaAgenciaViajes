/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import model.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservasDAO {
    //Metodo para agregar una reserva
    public String agregarReserva(Reserva reserva) {
        String sql = "INSERT INTO Reservas (id_cliente, id_pasaje, estado, fecha_reserva) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getIdCliente()); // Antes era getIdUsuario()
            stmt.setInt(2, reserva.getIdPasaje());
            stmt.setString(3, reserva.getEstado());
            stmt.setDate(4, Date.valueOf(reserva.getFechaReserva()));
            stmt.executeUpdate();
            return "Reserva agregada con éxito.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al agregar reserva: " + e.getMessage();
        }
    }
    
    
    //Metodo para listar Reservas
    public List<Reserva> listarReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reservas";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservas.add(new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_cliente"), // Antes era id_usuario
                        rs.getInt("id_pasaje"),
                        rs.getString("estado"),
                        rs.getDate("fecha_reserva").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }
    
     // Método para eliminar una reserva
    public String eliminarReserva(int idReserva) {
        String sql = "DELETE FROM Reservas WHERE id_reserva = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Reserva eliminada con éxito.";
            } else {
                return "No se encontró la reserva con el ID especificado.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar reserva: " + e.getMessage();
        }
    }

    // Método para actualizar una reserva
    public String actualizarReserva(Reserva reserva) {
        String sql = "UPDATE Reservas SET id_cliente = ?, id_pasaje = ?, estado = ?, fecha_reserva = ? WHERE id_reserva = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getIdCliente());
            stmt.setInt(2, reserva.getIdPasaje());
            stmt.setString(3, reserva.getEstado());
            stmt.setDate(4, Date.valueOf(reserva.getFechaReserva()));
            stmt.setInt(5, reserva.getIdReserva());
            stmt.executeUpdate();
            return "Reserva actualizada con éxito.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar reserva: " + e.getMessage();
        }
    }
    
    public Reserva obtenerReservaPorId(int idReserva) {
    String sql = "SELECT * FROM Reservas WHERE id_reserva = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idReserva);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return new Reserva(
                rs.getInt("id_reserva"),
                rs.getInt("id_cliente"),
                rs.getInt("id_pasaje"),
                rs.getString("estado"),
                rs.getDate("fecha_reserva").toLocalDate()
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
    
    
    
}
