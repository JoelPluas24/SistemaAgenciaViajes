/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ClientesController;
import model.Cliente;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientesView extends JFrame {
    private JTextField campoNombres, campoApellidos, campoTelefono, campoCorreo, campoCedula, campoBuscar;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ClientesController clientesController;

    public ClientesView(String nombreUsuario) {
        setTitle("Gestión de Clientes");
        setResizable(false);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        clientesController = new ClientesController();

        // 📌 Panel principal con GridBagLayout
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // 📌 1️⃣ Panel de Búsqueda Dinámica
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("🔎 Buscar:"));
        campoBuscar = new JTextField(30);
        panelBusqueda.add(campoBuscar);

        // Posición en GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelPrincipal.add(panelBusqueda, gbc);

        // 📌 2️⃣ Panel de Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Información del Cliente"));

        campoNombres = new JTextField(15);
        campoApellidos = new JTextField(15);
        campoTelefono = new JTextField(15);
        campoCorreo = new JTextField(15);
        campoCedula = new JTextField(15);

        panelFormulario.add(new JLabel("Nombres:"));
        panelFormulario.add(campoNombres);
        panelFormulario.add(new JLabel("Apellidos:"));
        panelFormulario.add(campoApellidos);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(campoTelefono);
        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(campoCorreo);
        panelFormulario.add(new JLabel("Cédula:"));
        panelFormulario.add(campoCedula);

        // Posición en GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelPrincipal.add(panelFormulario, gbc);

        // 📌 3️⃣ Panel de Botones con diseño vertical
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Acciones"));

        JButton btnAgregar = crearBoton("Agregar Cliente", new ImageIcon("src/main/resources/icons/mas_cliente.png"));
        JButton btnActualizar = crearBoton("Editar Cliente", new ImageIcon("src/main/resources/icons/editar_cliente.png"));
        JButton btnEliminar = crearBoton("️Eliminar Cliente", new ImageIcon("src/main/resources/icons/eliminar_cliente.png"));
        JButton btnVolver = crearBoton("Volver", new ImageIcon("src/main/resources/icons/retornar.png"));


        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        // Posición en GridBagLayout
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelPrincipal.add(panelBotones, gbc);

        //Tabla de clientes
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombres", "Apellidos", "Teléfono", "Correo", "Cédula"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setPreferredSize(new Dimension(700, 200));

        // Posición en GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelPrincipal.add(scrollPane, gbc);

        add(panelPrincipal);

        // 📌 Listeners y Acciones
        campoBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filtrarClientes(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filtrarClientes(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filtrarClientes(); }
        });

        btnAgregar.addActionListener(e -> agregarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnVolver.addActionListener(e -> {
            new MainMenu(nombreUsuario);
            dispose();
        });

        cargarClientes();
        setVisible(true);
    }

    // 📌 Cargar Clientes en la Tabla
    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        List<Cliente> clientes = clientesController.listarClientes();
        for (Cliente cliente : clientes) {
            modeloTabla.addRow(new Object[]{
                cliente.getIdCliente(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getTelefono(),
                cliente.getCorreo(),
                cliente.getCedula()
            });
        }
    }
    
        private JButton crearBoton(String texto, ImageIcon icono) {
        JButton boton = new JButton(texto, icono);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(52, 152, 219)); // Azul
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Ajustar el tamaño del icono (opcional)
        if (icono != null) {
            Image imagenEscalada = icono.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
            boton.setHorizontalTextPosition(SwingConstants.RIGHT); // Texto a la derecha del icono
            boton.setIconTextGap(10); // Espacio entre el icono y el texto
        }
        
        return boton;
    }
    

    // 📌 Búsqueda Dinámica Mejorada
    private void filtrarClientes() {
        String criterio = campoBuscar.getText().trim().toLowerCase();
        modeloTabla.setRowCount(0);

        List<Cliente> clientes = clientesController.listarClientes();
        for (Cliente cliente : clientes) {
            if (cliente.getNombres().toLowerCase().contains(criterio) ||
                cliente.getApellidos().toLowerCase().contains(criterio) ||
                cliente.getCedula().toLowerCase().contains(criterio)) {
                modeloTabla.addRow(new Object[]{
                    cliente.getIdCliente(),
                    cliente.getNombres(),
                    cliente.getApellidos(),
                    cliente.getTelefono(),
                    cliente.getCorreo(),
                    cliente.getCedula()
                });
            }
        }
    }

    // 📌 Agregar Cliente con Validación
    private void agregarCliente() {
        if (!validarCampos()) return;

        try {
            Cliente nuevoCliente = new Cliente(
                0,
                campoNombres.getText(),
                campoApellidos.getText(),
                campoTelefono.getText(),
                campoCorreo.getText(),
                campoCedula.getText()
            );

            JOptionPane.showMessageDialog(this, clientesController.agregarCliente(nuevoCliente));
            cargarClientes();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // 📌 Método para validar campos vacíos
    // 📌 Método para validar campos vacíos
    private boolean validarCampos() {
        return !campoNombres.getText().trim().isEmpty() && !campoApellidos.getText().trim().isEmpty();
    }

    // 📌 Método para eliminar un cliente
    private void eliminarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.");
            return;
        }

        int idCliente = (int) modeloTabla.getValueAt(fila, 0);
        JOptionPane.showMessageDialog(this, clientesController.eliminarCliente(idCliente));
        cargarClientes();
    }

    private void limpiarCampos() {
        campoNombres.setText("");
        campoApellidos.setText("");
        campoTelefono.setText("");
        campoCorreo.setText("");
        campoCedula.setText("");
    }



    // 📌 FUNCIÓN PARA ACTUALIZAR CLIENTE (ABRE LA VENTANA EMERGENTE)
    private void actualizarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar.");
            return;
        }

        int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombres = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String apellidos = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        String telefono = (String) modeloTabla.getValueAt(filaSeleccionada, 3);
        String correo = (String) modeloTabla.getValueAt(filaSeleccionada, 4);
        String cedula = (String) modeloTabla.getValueAt(filaSeleccionada, 5);

        Cliente clienteSeleccionado = new Cliente(idCliente, nombres, apellidos, telefono, correo, cedula);
        new EditarClienteView(this, clientesController, clienteSeleccionado);
        cargarClientes();
    }

}
