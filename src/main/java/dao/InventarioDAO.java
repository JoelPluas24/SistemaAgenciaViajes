/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import model.Inventario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {
    public String agregarInventario(Inventario inventario) {
        String sql = "INSERT INTO Inventario (id_ruta, id_pasaje, cantidad_disponible) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, inventario.getIdRuta());
            stmt.setInt(2, inventario.getIdPasaje());
            stmt.setInt(3, inventario.getCantidadDisponible());
            stmt.executeUpdate();
            return "Inventario agregado con éxito.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al agregar el inventario: " + e.getMessage();
        }
    }

    public List<Inventario> listarInventarios() {
        List<Inventario> inventarios = new ArrayList<>();
        String sql = "SELECT * FROM Inventario";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                inventarios.add(new Inventario(
                        rs.getInt("id_inventario"),
                        rs.getInt("id_ruta"),
                        rs.getInt("id_pasaje"),
                        rs.getInt("cantidad_disponible")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventarios;
    }

    public String actualizarInventario(int idInventario, int nuevaCantidad) {
        String sql = "UPDATE Inventario SET cantidad_disponible = ? WHERE id_inventario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nuevaCantidad);
            stmt.setInt(2, idInventario);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Inventario actualizado con éxito.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error al actualizar el inventario.";
    }
    
    public String actualizarInventarioPorRutaYPasaje(int idRuta, int idPasaje, int nuevaCantidad) {
    String sql = "UPDATE Inventario SET cantidad_disponible = ? WHERE id_ruta = ? AND id_pasaje = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, nuevaCantidad);
        stmt.setInt(2, idRuta);
        stmt.setInt(3, idPasaje);
        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            return "Inventario actualizado con éxito.";
        } else {
            return "No se encontró un inventario para la ruta y pasaje especificados.";
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return "Error al actualizar inventario: " + e.getMessage();
    }
}

    
}

