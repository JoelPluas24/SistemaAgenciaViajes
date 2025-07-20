/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import model.Pasaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasajesDAO {
    public String agregarPasaje(Pasaje pasaje) {
        String sql = "INSERT INTO Pasajes (id_ruta, tipo_transporte, fecha_salida, hora_salida, precio, disponibilidad) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pasaje.getIdRuta());
            stmt.setString(2, pasaje.getTipoTransporte());
            stmt.setDate(3, Date.valueOf(pasaje.getFechaSalida()));
            stmt.setTime(4, Time.valueOf(pasaje.getHoraSalida()));
            stmt.setDouble(5, pasaje.getPrecio());
            stmt.setInt(6, pasaje.getDisponibilidad());
            stmt.executeUpdate();
            return "Pasaje agregado con éxito.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al agregar pasaje: " + e.getMessage();
        }
    }

    public List<Pasaje> listarPasajesPorRuta(int idRuta) {
        List<Pasaje> pasajes = new ArrayList<>();
        String sql = "SELECT * FROM Pasajes WHERE id_ruta = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRuta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pasajes.add(new Pasaje(
                        rs.getInt("id_pasaje"),
                        rs.getInt("id_ruta"),
                        rs.getString("tipo_transporte"),
                        rs.getDate("fecha_salida").toLocalDate(),
                        rs.getTime("hora_salida").toLocalTime(),
                        rs.getDouble("precio"),
                        rs.getInt("disponibilidad")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pasajes;
    }
    
    public List<Pasaje> listarPasajes() {
    List<Pasaje> pasajes = new ArrayList<>();
    String sql = "SELECT * FROM Pasajes"; // Consulta sin filtro por ruta
    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            pasajes.add(new Pasaje(
                    rs.getInt("id_pasaje"),
                    rs.getInt("id_ruta"),
                    rs.getString("tipo_transporte"),
                    rs.getDate("fecha_salida").toLocalDate(),
                    rs.getTime("hora_salida").toLocalTime(),
                    rs.getDouble("precio"),
                    rs.getInt("disponibilidad")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return pasajes;
    }
    
    public Pasaje obtenerPasajePorId(int idPasaje) {
    String sql = "SELECT * FROM Pasajes WHERE id_pasaje = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idPasaje);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Pasaje(
                    rs.getInt("id_pasaje"),
                    rs.getInt("id_ruta"),
                    rs.getString("tipo_transporte"),
                    rs.getDate("fecha_salida").toLocalDate(),
                    rs.getTime("hora_salida").toLocalTime(),
                    rs.getDouble("precio"),
                    rs.getInt("disponibilidad")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    
    
}

