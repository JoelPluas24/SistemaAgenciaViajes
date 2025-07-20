/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import model.Pago;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagosDAO {
public String agregarPago(Pago pago) {
    String sql = "INSERT INTO Pagos (id_reserva, monto, metodo_pago, estado, fecha_pago) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, pago.getIdReserva());
        stmt.setDouble(2, pago.getMonto());
        stmt.setString(3, pago.getMetodoPago());
        stmt.setString(4, pago.getEstado());
        stmt.setDate(5, Date.valueOf(pago.getFechaPago()));
        stmt.executeUpdate();
        return "Pago registrado con éxito.";
    } catch (SQLException e) {
        e.printStackTrace();
        return "Error al registrar el pago: " + e.getMessage();
    }
}

public String anularPago(int idPago) {
    String sql = "UPDATE Pagos SET estado = 'Anulado' WHERE id_pago = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idPago);
        int filasActualizadas = stmt.executeUpdate();
        return (filasActualizadas > 0) ? "Pago anulado con éxito." : "No se encontró el pago.";
    } catch (SQLException e) {
        e.printStackTrace();
        return "Error al anular el pago: " + e.getMessage();
    }
}


    public List<Pago> listarPagos() {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT * FROM Pagos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pagos.add(new Pago(
                        rs.getInt("id_pago"),
                        rs.getInt("id_reserva"),
                        rs.getDouble("monto"),
                        rs.getString("metodo_pago"),
                        rs.getString("estado"),
                        rs.getDate("fecha_pago").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagos;
    }
    
    public Map<String, String> obtenerPagoPorIdConDetalles(int idPago) {
    String sql = "SELECT p.id_pago, p.monto, p.metodo_pago, p.estado, p.fecha_pago, " +
                 "c.nombres AS cliente_nombre, c.apellidos AS cliente_apellido, " +
                 "pa.tipo_transporte, r.origen, r.destino " +
                 "FROM Pagos p " +
                 "JOIN Reservas res ON p.id_reserva = res.id_reserva " +
                 "JOIN Clientes c ON res.id_cliente = c.id_cliente " +
                 "JOIN Pasajes pa ON res.id_pasaje = pa.id_pasaje " +
                 "JOIN Rutas r ON pa.id_ruta = r.id_ruta " +
                 "WHERE p.id_pago = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idPago);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            Map<String, String> detallesPago = new HashMap<>();
            detallesPago.put("id_pago", String.valueOf(rs.getInt("id_pago")));
            detallesPago.put("monto", String.format("$ %.2f", rs.getDouble("monto")));
            detallesPago.put("metodo_pago", rs.getString("metodo_pago"));
            detallesPago.put("estado", rs.getString("estado"));
            detallesPago.put("fecha_pago", rs.getDate("fecha_pago").toString());
            detallesPago.put("cliente_nombre", rs.getString("cliente_nombre") + " " + rs.getString("cliente_apellido"));
            detallesPago.put("tipo_transporte", rs.getString("tipo_transporte"));
            detallesPago.put("origen", rs.getString("origen"));
            detallesPago.put("destino", rs.getString("destino"));
            return detallesPago;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    
}

