/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.PagosController;
import dao.ClientesDAO;
import dao.ReservasDAO;
import model.Pago;
import model.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import model.Cliente;

public class PagosView extends JFrame {
    private JComboBox<String> comboReserva, comboMetodo, comboEstado;
    private JTextField campoMonto;
    private JTable tablaPagos;
    private DefaultTableModel modeloTabla;
    private PagosController pagosController;
    private ReservasDAO reservasDAO;
    private String nombreUsuario;
    private ClientesDAO clientesDAO;


    public PagosView(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;

        setTitle("Gestión de Pagos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pagosController = new PagosController();
        reservasDAO = new ReservasDAO();
        clientesDAO = new ClientesDAO();
        
         // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel superior para agregar pagos (con GridBagLayout)
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos del Pago")); // Borde con título
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expansión horizontal

        // Panel para "Datos del Pago" (lado izquierdo)
        JPanel panelDatosPago = new JPanel(new GridLayout(4, 2, 10, 10)); // Ajusta las filas según tus campos
        panelDatosPago.add(new JLabel("Reserva:"));
        comboReserva = new JComboBox<>();
        cargarReservasEnCombo();
        panelDatosPago.add(comboReserva);
        panelDatosPago.add(new JLabel("Método de Pago:"));
        comboMetodo = new JComboBox<>(new String[]{"Seleccione...", "Tarjeta", "PayPal", "Efectivo"});
        panelDatosPago.add(comboMetodo);
        panelDatosPago.add(new JLabel("Estado:"));
        comboEstado = new JComboBox<>(new String[]{"Seleccione...", "Pendiente", "Exitoso", "Fallido"});
        panelDatosPago.add(comboEstado);
        panelDatosPago.add(new JLabel("Monto ($):"));
        campoMonto = new JTextField();
        panelDatosPago.add(campoMonto);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5; // Para que ocupe la mitad del ancho
        panelSuperior.add(panelDatosPago, gbc);


        // Panel para "Acciones" (lado derecho)
        JPanel panelAcciones = new JPanel(new GridLayout(2, 1, 10, 10)); // Ajusta las filas según tus botones
        JButton btnEmitir = crearBoton("Emitir Factura", new ImageIcon("ruta/del/icono/emitir.png")); // Reemplaza con la ruta del icono
        panelAcciones.add(btnEmitir);
        JButton btnVolver = crearBoton("Volver", new ImageIcon("ruta/del/icono/volver.png")); // Reemplaza con la ruta del icono
        panelAcciones.add(btnVolver);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5; // Para que ocupe la mitad del ancho
        panelSuperior.add(panelAcciones, gbc);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH); // Panel superior en la parte NORTH

        // Tabla para listar pagos
        modeloTabla = new DefaultTableModel(new String[]{"ID Pago", "Reserva (Cliente)", "Método", "Estado", "Monto", "Fecha", "Acciones"}, 0);
        tablaPagos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPagos);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        TableColumn columnaAcciones = tablaPagos.getColumnModel().getColumn(6); // La columna "Acciones" está en el índice 6
        columnaAcciones.setPreferredWidth(150);
        
        
        // Cargar pagos al abrir
        cargarPagos();
        //cargarReservasEnCombo();

        // Acción para emitir factura
        btnEmitir.addActionListener(e -> emitirFactura());

        // Acción para volver al menú principal
        btnVolver.addActionListener(e -> {
            new MainMenu(nombreUsuario);
            dispose();
        });
        
        add(panelPrincipal);

        //setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarReservasEnCombo() {
        comboReserva.addItem("Seleccione...");
        List<Reserva> reservas = reservasDAO.listarReservas();
        for (Reserva reserva : reservas) {
            Cliente cliente = clientesDAO.obtenerClientePorId(reserva.getIdCliente());
            if (cliente != null) {
                comboReserva.addItem(reserva.getIdReserva() + " - " + cliente.getNombres() + " " + cliente.getApellidos());
            }
        }
    }


// Método para cargar pagos en la tabla
private void cargarPagos() {
    modeloTabla.setRowCount(0);
    List<Pago> pagos = pagosController.listarPagos();
    for (Pago pago : pagos) {
        Reserva reserva = reservasDAO.obtenerReservaPorId(pago.getIdReserva());
        Cliente cliente = clientesDAO.obtenerClientePorId(reserva.getIdCliente());
        String nombreCliente = (cliente != null) ? cliente.getNombres() + " " + cliente.getApellidos() : "Desconocido";

        modeloTabla.addRow(new Object[]{
                pago.getIdPago(),
                nombreCliente,
                pago.getMetodoPago(),
                pago.getEstado(),
                String.format("$ %.2f", pago.getMonto()),
                pago.getFechaPago().toString(),
                "Acciones"
        });
    }

    tablaPagos.setRowHeight(35); // 🔹 Aumenta la altura de las filas
    tablaPagos.setDefaultRenderer(Object.class, new TableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = new DefaultTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (BotonEditor.esFilaAnulada(row)) {
                c.setBackground(Color.YELLOW); // 🔹 Toda la fila será amarilla
            } else {
                c.setBackground(Color.WHITE);
            }
            return c;
        }
    });

    tablaPagos.getColumnModel().getColumn(6).setCellRenderer(new BotonRenderer());
    tablaPagos.getColumnModel().getColumn(6).setCellEditor(new BotonEditor(tablaPagos, pagosController));
}






    private void emitirFactura() {
        try {
            String reservaSeleccionada = (String) comboReserva.getSelectedItem();
            String metodoSeleccionado = (String) comboMetodo.getSelectedItem();
            String estadoSeleccionado = (String) comboEstado.getSelectedItem();
            double monto = Double.parseDouble(campoMonto.getText());

                        if ("Seleccione...".equals(reservaSeleccionada) || "Seleccione...".equals(metodoSeleccionado) || 
                "Seleccione...".equals(estadoSeleccionado) || campoMonto.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idReserva = Integer.parseInt(reservaSeleccionada.split(" - ")[0].trim());
            LocalDate fechaPago = LocalDate.now();

            Pago nuevoPago = new Pago(0, idReserva, monto, metodoSeleccionado, estadoSeleccionado, fechaPago);
            String resultado = pagosController.agregarPago(nuevoPago);

            JOptionPane.showMessageDialog(this, resultado);
            cargarPagos();
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para crear botones con estilo
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
    

    private void anularPago(int idPago) {
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea anular la factura?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            String resultado = pagosController.anularPago(idPago);
            JOptionPane.showMessageDialog(this, resultado);
            cargarPagos();
        }
    }

    private void mostrarFactura(Pago pago) {
        JFrame facturaFrame = new JFrame("Factura de Pago");
        facturaFrame.setSize(600, 500);
        facturaFrame.setLayout(new BorderLayout());
        
        // Panel para la información de la factura (con GridBagLayout)
        JPanel panelInfo = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiquetas y valores de la factura (con GridBagConstraints)
    gbc.gridx = 0; gbc.gridy = 0; panelInfo.add(new JLabel("ID Pago:"), gbc);
    gbc.gridx = 1; gbc.gridy = 0; panelInfo.add(new JLabel(String.valueOf(pago.getIdPago())), gbc); // Set the text value

    gbc.gridx = 0; gbc.gridy = 1; panelInfo.add(new JLabel("Reserva:"), gbc);
    gbc.gridx = 1; gbc.gridy = 1; 
    Reserva reserva = reservasDAO.obtenerReservaPorId(pago.getIdReserva()); // Get the Reserva object
    panelInfo.add(new JLabel(String.valueOf(reserva.getIdReserva())), gbc); // Set the text value

    gbc.gridx = 0; gbc.gridy = 2; panelInfo.add(new JLabel("Cliente:"), gbc);
    gbc.gridx = 1; gbc.gridy = 2; 
    Cliente cliente = clientesDAO.obtenerClientePorId(reserva.getIdCliente()); // Get the Cliente object
    panelInfo.add(new JLabel(cliente.getNombres() + " " + cliente.getApellidos()), gbc); // Set the text value

    gbc.gridx = 0; gbc.gridy = 3; panelInfo.add(new JLabel("Método de Pago:"), gbc);
    gbc.gridx = 1; gbc.gridy = 3; panelInfo.add(new JLabel(pago.getMetodoPago()), gbc); // Set the text value

    gbc.gridx = 0; gbc.gridy = 4; panelInfo.add(new JLabel("Estado:"), gbc);
    gbc.gridx = 1; gbc.gridy = 4; panelInfo.add(new JLabel(pago.getEstado()), gbc); // Set the text value

    gbc.gridx = 0; gbc.gridy = 5; panelInfo.add(new JLabel("Monto:"), gbc);
    gbc.gridx = 1; gbc.gridy = 5; panelInfo.add(new JLabel(String.format("$ %.2f", pago.getMonto())), gbc); // Set the text value

    gbc.gridx = 0; gbc.gridy = 6; panelInfo.add(new JLabel("Fecha de Pago:"), gbc);
    gbc.gridx = 1; gbc.gridy = 6; panelInfo.add(new JLabel(pago.getFechaPago().toString()), gbc); // Set the text value


        facturaFrame.add(panelInfo, BorderLayout.CENTER); // Panel en el centro

        // Panel para el botón "Aceptar" (con FlowLayout y estilo)
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Alineación a la derecha
        JButton btnAceptar = crearBoton("Aceptar", null); // Usamos la función crearBoton
        panelBoton.add(btnAceptar);
        facturaFrame.add(panelBoton, BorderLayout.SOUTH); // Botón en la parte inferior

        btnAceptar.addActionListener(e -> facturaFrame.dispose());

        facturaFrame.setLocationRelativeTo(this);
        facturaFrame.setVisible(true);
    }

    private void limpiarCampos() {
        comboReserva.setSelectedIndex(0);
        comboMetodo.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
        campoMonto.setText("");
    }
}


