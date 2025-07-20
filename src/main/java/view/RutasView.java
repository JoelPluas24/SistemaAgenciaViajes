/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.RutasDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import model.Rutas;

public class RutasView extends JFrame {
    private JComboBox<String> comboOrigen, comboDestino;
    private JTextField campoDuracion;
    private JTable tablaRutas;
    private DefaultTableModel modeloTabla;
    private RutasDAO rutasDAO;
    private String nombreUsuario;

    public RutasView(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        
        setTitle("Gestión de Rutas");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        rutasDAO = new RutasDAO();

        // 📌 Panel principal con GridBagLayout
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.fill = GridBagConstraints.BOTH; // Important: Fill both horizontally and vertically
        gbc.weightx = 1.0; // Allow horizontal expansion
        gbc.weighty = 0.0; // Prevent vertical expansion for info and actions
        


        // Panel para "Información de Ruta"
        JPanel panelInfoRuta = new JPanel(new GridLayout(3, 2, 10, 10));
        panelInfoRuta.setBorder(BorderFactory.createTitledBorder("Información de Ruta"));

        panelInfoRuta.add(new JLabel("Origen:"));
        String[] ciudades = {"Seleccione...","Guayaquil","Cuenca","Quito","Galápagos","Carchi","Cotopaxi"};
        comboOrigen = new JComboBox<>(ciudades);
        panelInfoRuta.add(comboOrigen);

        panelInfoRuta.add(new JLabel("Destino:"));
        comboDestino = new JComboBox<>(ciudades);
        panelInfoRuta.add(comboDestino);

        panelInfoRuta.add(new JLabel("Duración (HH:MM:SS):"));
        campoDuracion = new JTextField();
        panelInfoRuta.add(campoDuracion);
        
        // Posición en GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelPrincipal.add(panelInfoRuta, gbc);
        
       
        
        // Panel para "Acciones"
        JPanel panelAcciones = new JPanel(new GridLayout(2, 1, 10, 10));
        panelAcciones.setBorder(BorderFactory.createTitledBorder("Acciones"));
        

        JButton btnAgregar = crearBoton("Agregar Ruta", new ImageIcon("src/main/resources/icons/agregar_viaje.png"));
        panelAcciones.add(btnAgregar);

        JButton btnVolver = crearBoton("Volver", new ImageIcon("src/main/resources/icons/retornar.png"));
        panelAcciones.add(btnVolver);
        
        // Posición en GridBagLayout
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // Span one column
        panelPrincipal.add(panelAcciones, gbc);

        

        // Tabla para listar rutas
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Origen", "Destino", "Duración"}, 0);
        tablaRutas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaRutas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Rutas"));
        
        gbc.gridx = 0;
        gbc.gridy = 1; // Place below the info/actions panels
        gbc.gridwidth = 2; // Span both columns
        gbc.weighty = 1.0; // Allow vertical expansion for the table
        panelPrincipal.add(scrollPane, gbc);

        // ¡Y aquí faltaba agregar el panel principal al JFrame!
        add(panelPrincipal);


        // Cargar rutas al abrir
        cargarRutas();

        // Acción para agregar ruta
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarRuta();
            }
        });

        // Acción para volver al menú principal
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu(nombreUsuario);
                dispose();
            }
        });

        
        setVisible(true);
    }

    private void cargarRutas() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        List<Rutas> rutas = rutasDAO.listarRutas();
        for (Rutas ruta : rutas) {
            modeloTabla.addRow(new Object[]{ruta.getIdRuta(), ruta.getOrigen(), ruta.getDestino(), ruta.getDuracion()});
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
    

    private void agregarRuta() {
        try {
            String origen = (String)comboOrigen.getSelectedItem();
            String destino = (String)comboDestino.getSelectedItem();
            
            //Validar rutas validas
            if("Seleccione...".equals(origen)|| "Seleccione...".equals(destino)){
                JOptionPane.showMessageDialog(this,"Por favor seleccione una ruta de origen y otra de destino válidas");
                return;
            }
            if(origen.equals(destino)){
                JOptionPane.showMessageDialog(this,"El origen y destino no pueden ser iguales");
            }
            
            LocalTime duracion = LocalTime.parse(campoDuracion.getText());

            Rutas nuevaRuta = new Rutas(0, origen, destino, duracion);
            String resultado = rutasDAO.agregarRuta(nuevaRuta);

            JOptionPane.showMessageDialog(this, resultado);
            cargarRutas();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        comboOrigen.setSelectedIndex(0);
        comboDestino.setSelectedIndex(0);
        campoDuracion.setText("");
    }
}

