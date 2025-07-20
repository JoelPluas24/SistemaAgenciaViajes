/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package view;

import controller.PagosController;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BotonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton btnAnular, btnConsultar;
    private JTable table;
    private int row;
    private PagosController pagosController;
    private static final Set<Integer> filasAnuladas = new HashSet<>();

    public BotonEditor(JTable table, PagosController pagosController) {
        this.table = table;
        this.pagosController = pagosController;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

        btnAnular = new JButton("Anular");
        btnConsultar = new JButton("Consultar");

        btnAnular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idPago = Integer.parseInt(table.getValueAt(row, 0).toString());
                pagosController.anularPago(idPago);
                filasAnuladas.add(row);
                table.repaint(); // 🔹 Refresca la tabla para aplicar el color
            }
        });

        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idPago = Integer.parseInt(table.getValueAt(row, 0).toString());
                mostrarFactura(idPago);
            }
        });

        panel.add(btnAnular);
        panel.add(btnConsultar);
    }

    private void mostrarFactura(int idPago) {
        Map<String, String> pagoDetalles = pagosController.obtenerPagoPorIdConDetalles(idPago);
        if (pagoDetalles == null) {
            JOptionPane.showMessageDialog(null, "No se encontró la información del pago.");
            return;
        }

        JFrame facturaFrame = new JFrame("Factura de Pago");
        facturaFrame.setSize(400, 400);
        facturaFrame.setLayout(new GridLayout(10, 2, 10, 10));

        facturaFrame.add(new JLabel("ID Pago:"));
        facturaFrame.add(new JLabel(pagoDetalles.get("id_pago")));

        facturaFrame.add(new JLabel("Cliente:"));
        facturaFrame.add(new JLabel(pagoDetalles.get("cliente_nombre")));

        facturaFrame.add(new JLabel("Transporte:"));
        facturaFrame.add(new JLabel(pagoDetalles.get("tipo_transporte")));

        facturaFrame.add(new JLabel("Origen - Destino:"));
        facturaFrame.add(new JLabel(pagoDetalles.get("origen") + " - " + pagoDetalles.get("destino")));

        facturaFrame.add(new JLabel("Método de Pago:"));
        facturaFrame.add(new JLabel(pagoDetalles.get("metodo_pago")));

        facturaFrame.add(new JLabel("Estado:"));
        facturaFrame.add(new JLabel(pagoDetalles.get("estado")));

        facturaFrame.add(new JLabel("Monto:"));
        facturaFrame.add(new JLabel(pagoDetalles.get("monto")));

        facturaFrame.add(new JLabel("Fecha de Pago:"));
        facturaFrame.add(new JLabel(pagoDetalles.get("fecha_pago")));

        JButton btnAceptar = new JButton("Aceptar");
        facturaFrame.add(btnAceptar);
        btnAceptar.addActionListener(e -> facturaFrame.dispose());

        facturaFrame.setLocationRelativeTo(null);
        facturaFrame.setVisible(true);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return panel;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    public static boolean esFilaAnulada(int row) {
        return filasAnuladas.contains(row);
    }
}





