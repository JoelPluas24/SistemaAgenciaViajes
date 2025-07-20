/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class BotonRenderer extends JPanel implements TableCellRenderer {

    public BotonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JButton btnAnular = new JButton("Anular");
        JButton btnConsultar = new JButton("Consultar");

        panel.add(btnAnular);
        panel.add(btnConsultar);

        // 🔹 Aplicar color amarillo si la fila fue anulada
        if (BotonEditor.esFilaAnulada(row)) {
            panel.setBackground(Color.YELLOW);
        } else {
            panel.setBackground(table.getBackground());
        }

        return panel;
    }
}





