/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.PasajesDAO;
import dao.RutasDAO;
import model.Pasaje;
import model.Rutas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PasajesView extends JFrame {
    private JComboBox<String> comboRuta;
    private JComboBox<String> comboTipoTransporte;
    private JTextField campoFechaSalida, campoHoraSalida, campoPrecio, campoDisponibilidad;
    private JTable tablaPasajes;
    private DefaultTableModel modeloTabla;
    private PasajesDAO pasajesDAO;
    private RutasDAO rutasDAO;
    private String nombreUsuario;
    private DecimalFormat formatoDolar = new DecimalFormat("$0.00");

    public PasajesView(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;

        setTitle("Gestión de Pasajes");
        setSize(1100, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pasajesDAO = new PasajesDAO();
        rutasDAO = new RutasDAO();
        
        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());


        // Panel para "Información del Pasaje" y "Acciones" 
        JPanel panelInfoAcciones = new JPanel(new GridBagLayout());
        panelInfoAcciones.setBorder(BorderFactory.createTitledBorder("Información del Pasaje y Acciones"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Para que los componentes se expandan horizontalmente

        // Panel para "Información del Pasaje"
        JPanel panelInfoPasaje = new JPanel(new GridLayout(3, 2, 10, 10)); // Ajustamos a 4 filas
        panelInfoPasaje.setBorder(BorderFactory.createTitledBorder("Información del Pasaje"));
        
        // Etiqueta "Ruta (Origen - Destino):"
        JLabel lblRuta = new JLabel("Ruta (Origen - Destino):");
        lblRuta.setHorizontalAlignment(SwingConstants.RIGHT); // Alineamos a la derecha
        panelInfoPasaje.add(lblRuta);
        
        //panelInfoPasaje.add(new JLabel("Ruta (Origen - Destino):"));
        comboRuta = new JComboBox<>();
        cargarRutasEnCombo();
        comboRuta.setPreferredSize(new Dimension(200, 30)); // Ajustamos el tamaño
        comboRuta.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Añadimos un borde
        panelInfoPasaje.add(comboRuta);

        panelInfoPasaje.add(new JLabel("Tipo Transporte:"));
        String[] opcionesTransporte = {"Seleccione", "Avion", "Tren", "Autobus"};
        comboTipoTransporte = new JComboBox<>(opcionesTransporte);
        panelInfoPasaje.add(comboTipoTransporte);

        panelInfoPasaje.add(new JLabel("Fecha Salida (YYYY-MM-DD):"));
        campoFechaSalida = new JTextField();
        panelInfoPasaje.add(campoFechaSalida);

        panelInfoPasaje.add(new JLabel("Hora Salida (HH:MM:SS):"));
        campoHoraSalida = new JTextField();
        panelInfoPasaje.add(campoHoraSalida);

        panelInfoPasaje.add(new JLabel("Precio ($):"));
        campoPrecio = new JTextField();
        panelInfoPasaje.add(campoPrecio);

        panelInfoPasaje.add(new JLabel("Disponibilidad:"));
        campoDisponibilidad = new JTextField();
        panelInfoPasaje.add(campoDisponibilidad);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInfoAcciones.add(panelInfoPasaje, gbc);
        
        // Panel para "Acciones"
        JPanel panelAcciones = new JPanel(new GridLayout(2, 1, 10, 10));
        panelAcciones.setBorder(BorderFactory.createTitledBorder("Acciones"));

        // Botones para acciones
        JButton btnAgregar = crearBoton("Agregar Pasaje", new ImageIcon("src/main/resources/icons/agregar_pasaje.png"));
        panelAcciones.add(btnAgregar);

        JButton btnVolver = crearBoton("Volver", new ImageIcon("src/main/resources/icons/retornar.png"));
        panelAcciones.add(btnVolver);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelInfoAcciones.add(panelAcciones, gbc);

        panelPrincipal.add(panelInfoAcciones, BorderLayout.NORTH);

        // Tabla para listar pasajes
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Origen", "Destino", "Tipo Transporte", "Fecha Salida", "Hora Salida", "Precio", "Disponibilidad"}, 0);
        tablaPasajes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPasajes);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Pasajes"));
        
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);
        
        // Cargar pasajes al abrir
        cargarPasajes();

        // Acción para agregar pasaje
        btnAgregar.addActionListener(e -> agregarPasaje());

        // Acción para volver al menú principal
        btnVolver.addActionListener(e -> {
            new MainMenu(nombreUsuario); // Regresa al menú principal
            dispose(); // Cierra la ventana actual
        });

        
        setVisible(true);
    }

    private void cargarRutasEnCombo() {
        comboRuta.addItem("Seleccione");
        List<Rutas> rutas = rutasDAO.listarRutas();
        for (Rutas ruta : rutas) {
            comboRuta.addItem(ruta.getIdRuta() + " - " + ruta.getOrigen() + " a " + ruta.getDestino());
        }
    }

    private void cargarPasajes() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        List<Pasaje> pasajes = pasajesDAO.listarPasajes();
        for (Pasaje pasaje : pasajes) {
            modeloTabla.addRow(new Object[]{
                pasaje.getIdPasaje(),
                obtenerOrigen(pasaje.getIdRuta()),
                obtenerDestino(pasaje.getIdRuta()),
                pasaje.getTipoTransporte(),
                pasaje.getFechaSalida().toString(),
                pasaje.getHoraSalida().toString(),
                formatoDolar.format(pasaje.getPrecio()),
                pasaje.getDisponibilidad()
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
    

    private String obtenerOrigen(int idRuta) {
        Rutas ruta = rutasDAO.obtenerRutaPorId(idRuta);
        return ruta != null ? ruta.getOrigen() : "Desconocido";
    }

    private String obtenerDestino(int idRuta) {
        Rutas ruta = rutasDAO.obtenerRutaPorId(idRuta);
        return ruta != null ? ruta.getDestino() : "Desconocido";
    }

    private void agregarPasaje() {
        try {
            String rutaSeleccionada = (String) comboRuta.getSelectedItem();
            if ("Seleccione".equals(rutaSeleccionada)) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una ruta válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idRuta = Integer.parseInt(rutaSeleccionada.split(" - ")[0]);

            String tipoTransporte = (String) comboTipoTransporte.getSelectedItem();
            if ("Seleccione".equals(tipoTransporte)) {
                JOptionPane.showMessageDialog(this, "Seleccione un tipo de transporte válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate fechaSalida = LocalDate.parse(campoFechaSalida.getText());
            LocalTime horaSalida = LocalTime.parse(campoHoraSalida.getText());

            double precio;
            try {
                precio = Double.parseDouble(campoPrecio.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese un precio válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int disponibilidad;
            try {
                disponibilidad = Integer.parseInt(campoDisponibilidad.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida de asientos disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pasaje nuevoPasaje = new Pasaje(0, idRuta, tipoTransporte, fechaSalida, horaSalida, precio, disponibilidad);
            String resultado = pasajesDAO.agregarPasaje(nuevoPasaje);

            JOptionPane.showMessageDialog(this, resultado);
            cargarPasajes();
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        comboRuta.setSelectedIndex(0);
        comboTipoTransporte.setSelectedIndex(0);
        campoFechaSalida.setText("");
        campoHoraSalida.setText("");
        campoPrecio.setText("");
        campoDisponibilidad.setText("");
    }
}
