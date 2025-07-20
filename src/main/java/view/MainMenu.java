/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu(String nombreUsuario) { // Recibe el nombre del usuario
        setTitle("Menú Principal - Bienvenido " + nombreUsuario);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para la etiqueta de bienvenida
        JPanel panelBienvenida = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblBienvenida = new JLabel("Bienvenido, " + nombreUsuario);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 20)); // Fuente más grande y negrita
        panelBienvenida.add(lblBienvenida);
        add(panelBienvenida, BorderLayout.NORTH); // Etiqueta en la parte superior
        
        // Panel para los botones (usamos GridLayout para la disposición)
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 20, 20)); // 3 filas, 2 columnas, espaciado
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Márgenes alrededor
        
        
        // Crear y agregar botones con estilo
        JButton btnClientes = crearBoton("Clientes", new ImageIcon("src/main/resources/icons/agregar_cliente.png"));
        JButton btnRutas = crearBoton("Rutas", new ImageIcon("src/main/resources/icons/ruta.png"));
        JButton btnPasajes = crearBoton("Pasajes", new ImageIcon("src/main/resources/icons/pasajes.png"));
        JButton btnReservas = crearBoton("Reservas", new ImageIcon("src/main/resources/icons/reservado.png"));
        //JButton btnInventarios = crearBoton("Inventarios", new ImageIcon("src/main/resources/icons/agregar_cliente.png"));
        JButton btnPagos = crearBoton("Pagos", new ImageIcon("src/main/resources/icons/pago.png"));
        JButton btnSalir = crearBoton("Salir", new ImageIcon("src/main/resources/icons/salida.png"));

        panelBotones.add(btnClientes);
        panelBotones.add(btnRutas);
        panelBotones.add(btnPasajes);
        panelBotones.add(btnReservas);
        //panelBotones.add(btnInventarios);
        panelBotones.add(btnPagos);
        panelBotones.add(new JLabel("")); // Espacio en blanco para el botón salir
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER); // Botones en el centro

        // Acciones para los botones
        btnClientes.addActionListener(e -> {
            new ClientesView(nombreUsuario);
            dispose();
        });

        btnRutas.addActionListener(e -> {
            new RutasView(nombreUsuario);
            dispose();
        });

        btnPasajes.addActionListener(e -> {
            new PasajesView(nombreUsuario);
            dispose();
        });

        btnReservas.addActionListener(e -> {
            new ReservasView(nombreUsuario);
            dispose();
        });
        
        btnPagos.addActionListener(e -> {
            new PagosView(nombreUsuario);
            dispose();
        });

        btnSalir.addActionListener(e -> System.exit(0));

        //btnInventarios.addActionListener(e -> {
        //    new InventariosView(nombreUsuario);
        //    dispose();
        //});



        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Método para crear botones con estilo
    private JButton crearBoton(String texto, ImageIcon icono) {
        JButton boton = new JButton(texto,icono);
        boton.setFont(new Font("Arial", Font.PLAIN, 16)); // Fuente más grande
        boton.setBackground(new Color(52, 152, 219)); // Color de fondo azul
        boton.setForeground(Color.WHITE); // Color de texto blanco
        boton.setFocusPainted(false); // Quitar el borde de enfoque
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Márgenes del botón
        
        // Ajustar el tamaño del icono (opcional)
        if (icono != null) {
            Image imagenEscalada = icono.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
            boton.setHorizontalTextPosition(SwingConstants.RIGHT); // Texto a la derecha del icono
            boton.setIconTextGap(10); // Espacio entre el icono y el texto
        }
        
        
        return boton;
    }
    
    
}
