/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.InventarioDAO;
import model.Inventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventariosView extends JFrame {
    private JTextField campoIdRuta, campoIdPasaje, campoCantidadDisponible;
    private JTable tablaInventarios;
    private DefaultTableModel modeloTabla;
    private InventarioDAO inventarioDAO;
    private String nombreUsuario;

    public InventariosView(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        
        setTitle("Gestión de Inventarios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inventarioDAO = new InventarioDAO();

        // Panel superior para agregar o actualizar inventario
        JPanel panelSuperior = new JPanel(new GridLayout(2, 4, 10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelSuperior.add(new JLabel("ID Ruta:"));
        campoIdRuta = new JTextField();
        panelSuperior.add(campoIdRuta);

        panelSuperior.add(new JLabel("ID Pasaje:"));
        campoIdPasaje = new JTextField();
        panelSuperior.add(campoIdPasaje);

        panelSuperior.add(new JLabel("Cantidad Disponible:"));
        campoCantidadDisponible = new JTextField();
        panelSuperior.add(campoCantidadDisponible);

        JButton btnAgregar = new JButton("Agregar Inventario");
        panelSuperior.add(btnAgregar);

        JButton btnActualizar = new JButton("Actualizar Inventario");
        panelSuperior.add(btnActualizar);

        JButton btnVolver = new JButton("Volver");
        panelSuperior.add(btnVolver);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla para listar inventarios
        modeloTabla = new DefaultTableModel(new String[]{"ID Inventario", "ID Ruta", "ID Pasaje", "Cantidad Disponible"}, 0);
        tablaInventarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaInventarios);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar inventarios al abrir
        cargarInventarios();

        // Acción para agregar inventario
        btnAgregar.addActionListener(e -> agregarInventario());

        // Acción para actualizar inventario
        btnActualizar.addActionListener(e -> actualizarInventario());

        // Acción para volver al menú principal
        btnVolver.addActionListener(e -> {
            new MainMenu(nombreUsuario); // Regresa al menú principal
            dispose(); // Cierra la ventana actual
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarInventarios() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        List<Inventario> inventarios = inventarioDAO.listarInventarios(); // Obtener todos los inventarios
        for (Inventario inventario : inventarios) {
            modeloTabla.addRow(new Object[]{
                inventario.getIdInventario(),
                inventario.getIdRuta(),
                inventario.getIdPasaje(),
                inventario.getCantidadDisponible()
            });
        }
    }

    private void agregarInventario() {
        try {
            // Obtener los datos del formulario
            int idRuta = Integer.parseInt(campoIdRuta.getText());
            int idPasaje = Integer.parseInt(campoIdPasaje.getText());
            int cantidadDisponible = Integer.parseInt(campoCantidadDisponible.getText());

            // Crear una nueva instancia de Inventario
            Inventario nuevoInventario = new Inventario(0, idRuta, idPasaje, cantidadDisponible);

            // Guardar el inventario en la base de datos
            String resultado = inventarioDAO.agregarInventario(nuevoInventario);

            // Mostrar mensaje y recargar la tabla
            JOptionPane.showMessageDialog(this, resultado);
            cargarInventarios();
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void actualizarInventario() {
        try {
            // Obtener los datos del formulario
            int idRuta = Integer.parseInt(campoIdRuta.getText());
            int idPasaje = Integer.parseInt(campoIdPasaje.getText());
            int nuevaCantidad = Integer.parseInt(campoCantidadDisponible.getText());

            // Actualizar la cantidad disponible
            String resultado = inventarioDAO.actualizarInventarioPorRutaYPasaje(idRuta, idPasaje, nuevaCantidad);

            // Mostrar mensaje y recargar la tabla
            JOptionPane.showMessageDialog(this, resultado);
            cargarInventarios();
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        campoIdRuta.setText("");
        campoIdPasaje.setText("");
        campoCantidadDisponible.setText("");
    }
}

