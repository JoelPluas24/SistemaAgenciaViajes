/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Rutas;

public class RutasDAO {
    public String agregarRuta(Rutas rutas) {
        String sql = "INSERT INTO Rutas (origen, destino, duracion) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rutas.getOrigen());
            stmt.setString(2, rutas.getDestino());
            stmt.setTime(3, Time.valueOf(rutas.getDuracion()));
            stmt.executeUpdate();
            return "Ruta agregada con éxito.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al agregar la ruta: " + e.getMessage();
        }
    }

    public List<Rutas> listarRutas() {
        List<Rutas> rutas = new ArrayList<>();
        String sql = "SELECT * FROM Rutas";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rutas.add(new Rutas(
                        rs.getInt("id_ruta"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getTime("duracion").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rutas;
    }

    public String eliminarRuta(int idRuta) {
        String sql = "DELETE FROM Rutas WHERE id_ruta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRuta);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Ruta eliminada con éxito.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error al eliminar la ruta.";
    }
    
    // Método para obtener una ruta por su ID
public Rutas obtenerRutaPorId(int idRuta) {
    String sql = "SELECT * FROM Rutas WHERE id_ruta = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idRuta);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Rutas(
                rs.getInt("id_ruta"),
                rs.getString("origen"),
                rs.getString("destino"),
                rs.getTime("duracion").toLocalTime()
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
    
}

