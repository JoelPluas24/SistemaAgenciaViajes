/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ReservasController;
import dao.ClientesDAO;
import dao.PasajesDAO;
import dao.RutasDAO;
import model.Reserva;
import model.Cliente;
import model.Pasaje;
import model.Rutas;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class ReservasView extends JFrame {
    private JComboBox<String> comboCliente, comboPasaje, comboEstado;
    private JTable tablaReservas;
    private JTextField campoBuscar;
    private DefaultTableModel modeloTabla;
    private ReservasController reservasController;
    private ClientesDAO clientesDAO;
    private PasajesDAO pasajesDAO;
    private String nombreUsuario;
    private RutasDAO rutasDAO;  // Agregar esta línea
    private int reservaSeleccionada = -1;


    public ReservasView(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;

        setTitle("Gestión de Reservas");
        setSize(700, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        reservasController = new ReservasController();
        clientesDAO = new ClientesDAO();
        pasajesDAO = new PasajesDAO();
        rutasDAO = new RutasDAO();
        
        // Panel principal con GridBagLayout
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH; // Para que los componentes se expandan
        
      
       
        // 1. Panel de Búsqueda Dinámica 
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel(" Buscar:"));
        campoBuscar = new JTextField(30);
        panelBusqueda.add(campoBuscar);
        
        // Posición en GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelPrincipal.add(panelBusqueda, gbc);


        // Panel para la información de la reserva
        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 10, 5)); // Ajusta el número de filas según tus necesidades
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Reserva"));

        panelFormulario.add(new JLabel("Cliente:"));
        comboCliente = new JComboBox<>();
        cargarClientesEnCombo();
        panelFormulario.add(comboCliente);

        panelFormulario.add(new JLabel("Pasaje (Origen - Destino | Tipo Transporte | Fecha | Hora):"));
        comboPasaje = new JComboBox<>();
        cargarPasajesEnCombo();
        panelFormulario.add(comboPasaje);
       
        panelFormulario.add(new JLabel("Estado:"));
        String[] opcionesEstado = {"Seleccione...", "Pendiente", "Confirmada", "Cancelada"};
        comboEstado = new JComboBox<>(opcionesEstado);
        panelFormulario.add(comboEstado);
  
        
        //panelSuperiorpanelFormulario.add(new JLabel("Buscar:"));
        //campoBuscar = new JTextField();
        //panelFormulario.add(campoBuscar);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelPrincipal.add(panelFormulario, gbc);
        
        // Panel para las acciones (botones)
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Acciones"));

        // Botones
        JButton btnAgregar = crearBoton("Agregar Reserva", new ImageIcon("src/main/resources/icons/agregar_reserva.png"));
        panelBotones.add(btnAgregar);
        
        JButton btnActualizar = crearBoton("Editar", new ImageIcon("src/main/resources/icons/editar_reserva.png"));
        panelBotones.add(btnActualizar);
        
        JButton btnEliminar = crearBoton("Eliminar", new ImageIcon("src/main/resources/icons/eliminar_reserva.png"));
        panelBotones.add(btnEliminar);

        JButton btnVolver = crearBoton("Volver", new ImageIcon("src/main/resources/icons/retornar.png"));
        panelBotones.add(btnVolver);

        gbc.gridx = 1;
        gbc.gridy = 1; // Misma fila que el formulario
        gbc.gridwidth = 1;
        panelPrincipal.add(panelBotones, gbc);


        // Tabla para listar reservas
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Cliente", "Pasaje", "Estado", "Fecha Reserva"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaReservas = new JTable(modeloTabla);
        tablaReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        //panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Posición en GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 2; // Debajo del formulario y los botones
        gbc.gridwidth = 2; // Ocupa ambas columnas
        gbc.weighty = 1.0; // Para que la tabla se expanda verticalmente
        gbc.fill = GridBagConstraints.BOTH; // Para que la tabla se expanda
        panelPrincipal.add(scrollPane, gbc);


        add(panelPrincipal);

        // Cargar reservas al abrir
        cargarReservas();

        // Acción para agregar reserva
        btnAgregar.addActionListener(e -> agregarReserva());
        btnActualizar.addActionListener(e -> actualizarReserva());
        btnEliminar.addActionListener(e -> eliminarReserva());

        // Acción para volver al menú principal
        btnVolver.addActionListener(e -> {
            new MainMenu(nombreUsuario);
            dispose();
        });

        // Seleccionar una fila en la tabla
tablaReservas.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        int filaSeleccionada = tablaReservas.getSelectedRow();
        if (filaSeleccionada != -1) {
            reservaSeleccionada = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());

            // Obtener el nombre del cliente de la tabla
            String clienteTexto = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
            seleccionarItemEnCombo(comboCliente, clienteTexto);

            // Obtener la descripción del pasaje de la tabla
            String pasajeTexto = modeloTabla.getValueAt(filaSeleccionada, 2).toString();
            seleccionarItemEnCombo(comboPasaje, pasajeTexto);

            // Seleccionar el estado directamente
            comboEstado.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
        }
    }
});

        
        // Búsqueda dinámica
        campoBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filtrarReservas(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filtrarReservas(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filtrarReservas(); }

    private void filtrarReservas() {
        String texto = campoBuscar.getText().toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tablaReservas.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
    }
        });

        
        setVisible(true);
        
        
        
    }
    
private void seleccionarItemEnCombo(JComboBox<String> comboBox, String textoBuscado) {
    for (int i = 0; i < comboBox.getItemCount(); i++) {
        if (comboBox.getItemAt(i).equals(textoBuscado)) {
            comboBox.setSelectedIndex(i);
            return;
        }
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

    
    

    private void cargarClientesEnCombo() {
        comboCliente.addItem("Seleccione...");
        List<Cliente> clientes = clientesDAO.listarClientes();
        for (Cliente cliente : clientes) {
            comboCliente.addItem(cliente.getIdCliente() + " - " + cliente.getNombres() + " " + cliente.getApellidos());
        }
    }

private void cargarPasajesEnCombo() {
    comboPasaje.addItem("Seleccione...");
    List<Pasaje> pasajes = pasajesDAO.listarPasajes();
    for (Pasaje pasaje : pasajes) {
        Rutas ruta = rutasDAO.obtenerRutaPorId(pasaje.getIdRuta());
        String origen = (ruta != null) ? ruta.getOrigen() : "Desconocido";
        String destino = (ruta != null) ? ruta.getDestino() : "Desconocido";

        comboPasaje.addItem(pasaje.getIdPasaje() + " - " + origen + " a " + destino +
                " | " + pasaje.getTipoTransporte() + " | " + pasaje.getFechaSalida() + " | " + pasaje.getHoraSalida());
    }
}


private void cargarReservas() {
    modeloTabla.setRowCount(0);
    List<Reserva> reservas = reservasController.listarReservas();
    for (Reserva reserva : reservas) {
        modeloTabla.addRow(new Object[]{
                reserva.getIdReserva(),
                obtenerNombreCliente(reserva.getIdCliente()), // Solo Nombre y Apellido
                obtenerDescripcionPasaje(reserva.getIdPasaje()), // Origen, Destino, Tipo, Fecha, Hora
                reserva.getEstado(),
                reserva.getFechaReserva().toString()
        });
    }
}


private String obtenerNombreCliente(int idCliente) {
    Cliente cliente = clientesDAO.obtenerClientePorId(idCliente);
    return cliente != null ? cliente.getIdCliente() + " - " + cliente.getNombres() + " " + cliente.getApellidos() : "Desconocido";
}


private String obtenerDescripcionPasaje(int idPasaje) {
    Pasaje pasaje = pasajesDAO.obtenerPasajePorId(idPasaje);
    if (pasaje == null) {
        return "Desconocido";
    }

    // Obtener la ruta asociada al pasaje
    Rutas ruta = rutasDAO.obtenerRutaPorId(pasaje.getIdRuta());
    String origen = (ruta != null) ? ruta.getOrigen() : "Desconocido";
    String destino = (ruta != null) ? ruta.getDestino() : "Desconocido";

    // Asegurar que el ID del pasaje esté incluido en la descripción
    return idPasaje + " - " + origen + " a " + destino +
            " | " + pasaje.getTipoTransporte() +
            " | " + pasaje.getFechaSalida() +
            " | " + pasaje.getHoraSalida();
}



    private void agregarReserva() {
        try {
            String clienteSeleccionado = (String) comboCliente.getSelectedItem();
            String pasajeSeleccionado = (String) comboPasaje.getSelectedItem();
            String estadoSeleccionado = (String) comboEstado.getSelectedItem();

            // Validaciones
            if ("Seleccione...".equals(clienteSeleccionado) || "Seleccione...".equals(pasajeSeleccionado) || "Seleccione...".equals(estadoSeleccionado)) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idCliente = Integer.parseInt(clienteSeleccionado.split(" - ")[0].trim());
            int idPasaje = Integer.parseInt(pasajeSeleccionado.split(" - ")[0].trim());
            LocalDate fechaReserva = LocalDate.now();

            Reserva nuevaReserva = new Reserva(0, idCliente, idPasaje, estadoSeleccionado, fechaReserva);
            String resultado = reservasController.agregarReserva(nuevaReserva);

            JOptionPane.showMessageDialog(this, resultado);
            cargarReservas();
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: Seleccione un cliente y un pasaje válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
private void actualizarReserva() {
    if (reservaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una reserva para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String clienteSeleccionado = (String) comboCliente.getSelectedItem();
    String pasajeSeleccionado = (String) comboPasaje.getSelectedItem();
    String estadoSeleccionado = (String) comboEstado.getSelectedItem();

    // Validaciones
    if ("Seleccione...".equals(clienteSeleccionado) || "Seleccione...".equals(pasajeSeleccionado) || "Seleccione...".equals(estadoSeleccionado)) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        // Extraer solo el ID antes de la conversión
        int idCliente = Integer.parseInt(clienteSeleccionado.split("-")[0].trim());
        int idPasaje = Integer.parseInt(pasajeSeleccionado.split("-")[0].trim());

        Reserva reserva = new Reserva(reservaSeleccionada, idCliente, idPasaje, estadoSeleccionado, LocalDate.now());
        String resultado = reservasController.actualizarReserva(reserva);
        JOptionPane.showMessageDialog(this, resultado);
        cargarReservas();
        limpiarCampos();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error: Seleccione un cliente y un pasaje válidos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    
    private void eliminarReserva() {
        if (reservaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar la reserva?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            String resultado = reservasController.eliminarReserva(reservaSeleccionada);
            JOptionPane.showMessageDialog(this, resultado);
            cargarReservas();
            limpiarCampos();
        }
    }
    
    

    private void limpiarCampos() {
        comboCliente.setSelectedIndex(0);
        
        comboPasaje.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
        reservaSeleccionada = -1;
    }
}


