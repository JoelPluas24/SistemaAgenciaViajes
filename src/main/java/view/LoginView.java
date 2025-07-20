/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private UsuarioController usuarioController;

    public LoginView() {
        setTitle("Inicio de Sesión");
        setSize(350, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        usuarioController = new UsuarioController();

        // 🔹 Panel de fondo con color personalizado
        JPanel panelFondo = new JPanel();
        panelFondo.setLayout(new BorderLayout());
        panelFondo.setBackground(new Color(240, 240, 240)); // Fondo gris claro

        // 🔹 Panel de contenido
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panelContenido.setBackground(new Color(255, 255, 255)); // Fondo blanco
        panelFondo.add(panelContenido, BorderLayout.CENTER);

        // 🔹 Icono superior
        ImageIcon icono = null;
try {
    java.net.URL imgURL = getClass().getClassLoader().getResource("icons/login_icon.png");
    if (imgURL != null) {
        icono = new ImageIcon(imgURL);
    } else {
        System.out.println("⚠ No se encontró la imagen en la ruta: icons/login_icon.png");
    }
} catch (Exception e) {
    System.out.println("❌ Error al cargar la imagen: " + e.getMessage());
}


        JLabel labelIcono = new JLabel(new ImageIcon(icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        labelIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(labelIcono);
        panelContenido.add(Box.createVerticalStrut(15)); // Espacio

        // 🔹 Campos de entrada con etiquetas
        JLabel lblCorreo = new JLabel("Correo:");
        campoCorreo = new JTextField(20);
        campoCorreo.setPreferredSize(new Dimension(250, 30)); // Ancho y alto
        campoCorreo.setMaximumSize(new Dimension(250, 30)); // Evitar que crezca más
        campoCorreo.setAlignmentX(Component.LEFT_ALIGNMENT); // Alineación a la izquierda

        JLabel lblContrasena = new JLabel("Contraseña:");
        campoContrasena = new JPasswordField(20);
        campoContrasena.setPreferredSize(new Dimension(250, 30)); // Ancho y alto
        campoContrasena.setMaximumSize(new Dimension(250, 30)); // Evitar que crezca más
        campoContrasena.setAlignmentX(Component.LEFT_ALIGNMENT); // Alineación a la izquierda

        // 🔹 Panel de campos (usando un BoxLayout para mejor control)
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS)); // Layout vertical
        panelCampos.setBackground(Color.WHITE);

        JPanel panelCorreo = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel para Correo
        panelCorreo.setBackground(Color.WHITE);
        panelCorreo.add(lblCorreo);
        panelCorreo.add(campoCorreo);

        JPanel panelContrasena = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel para Contraseña
        panelContrasena.setBackground(Color.WHITE);
        panelContrasena.add(lblContrasena);
        panelContrasena.add(campoContrasena);


        panelCampos.add(panelCorreo);
        panelCampos.add(panelContrasena);

        panelContenido.add(panelCampos);
        panelContenido.add(Box.createVerticalStrut(20));

        // 🔹 Botones estilizados
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBackground(new Color(52, 152, 219));
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFocusPainted(false);
        btnIniciarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBackground(new Color(231, 76, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelContenido.add(btnIniciarSesion);
        panelContenido.add(Box.createVerticalStrut(10));
        panelContenido.add(btnSalir);

        add(panelFondo);

        // 🔹 Acciones de los botones
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarSesion() {
        String correo = campoCorreo.getText().trim();
        String contrasena = new String(campoContrasena.getPassword()).trim();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese su correo y contraseña.");
            return;
        }

        Usuario usuario = usuarioController.iniciarSesion(correo, contrasena);
        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + usuario.getNombre() + "!");
            new MainMenu(usuario.getNombre());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos. Intente nuevamente.");
        }
    }

    public static void main(String[] args) {
        new LoginView();
    }
}

