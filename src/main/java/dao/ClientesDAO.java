/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {
    // Método para agregar un cliente
    public String agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO Clientes (nombres, apellidos, telefono, correo, cedula) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombres());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getCorreo());
            stmt.setString(5, cliente.getCedula());
            stmt.executeUpdate();
            return "Cliente agregado con éxito.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al agregar cliente: " + e.getMessage();
        }
    }

    // Método para listar todos los clientes
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("cedula")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    // Método para eliminar un cliente
    public String eliminarCliente(int idCliente) {
        String sql = "DELETE FROM Clientes WHERE id_cliente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Cliente eliminado con éxito.";
            } else {
                return "No se encontró un cliente con el ID especificado.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar cliente: " + e.getMessage();
        }
    }

    // Método para actualizar un cliente
    public String actualizarCliente(Cliente cliente) {
        String sql = "UPDATE Clientes SET nombres = ?, apellidos = ?, telefono = ?, correo = ?, cedula = ? WHERE id_cliente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombres());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getCorreo());
            stmt.setString(5, cliente.getCedula());
            stmt.setInt(6, cliente.getIdCliente());
            stmt.executeUpdate();
            return "Cliente actualizado con éxito.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar cliente: " + e.getMessage();
        }
    }
    
    public Cliente obtenerClientePorId(int idCliente) {
    String sql = "SELECT * FROM Clientes WHERE id_cliente = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idCliente);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("cedula")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
}

