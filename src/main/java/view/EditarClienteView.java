/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ClientesController;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarClienteView extends JDialog {
    private JTextField campoNombres, campoApellidos, campoTelefono, campoCorreo, campoCedula;
    private JButton btnAceptar, btnCancelar;
    private ClientesController clientesController;
    private Cliente cliente;

    public EditarClienteView(JFrame parent, ClientesController controller, Cliente cliente) {
        super(parent, "Editar Cliente", true);
        this.clientesController = controller;
        this.cliente = cliente;

        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 10, 10));
        setLocationRelativeTo(parent);

        // Campos de entrada
        add(new JLabel("Nombres:"));
        campoNombres = new JTextField(cliente.getNombres());
        add(campoNombres);

        add(new JLabel("Apellidos:"));
        campoApellidos = new JTextField(cliente.getApellidos());
        add(campoApellidos);

        add(new JLabel("Teléfono:"));
        campoTelefono = new JTextField(cliente.getTelefono());
        add(campoTelefono);

        add(new JLabel("Correo:"));
        campoCorreo = new JTextField(cliente.getCorreo());
        add(campoCorreo);

        add(new JLabel("Cédula:"));
        campoCedula = new JTextField(cliente.getCedula());
        add(campoCedula);

        // Botones
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        add(btnAceptar);
        add(btnCancelar);

        // Acción del botón Aceptar (Guardar cambios)
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });

        // Acción del botón Cancelar (Cerrar sin guardar)
        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void actualizarCliente() {
        cliente.setNombres(campoNombres.getText());
        cliente.setApellidos(campoApellidos.getText());
        cliente.setTelefono(campoTelefono.getText());
        cliente.setCorreo(campoCorreo.getText());
        cliente.setCedula(campoCedula.getText());

        String resultado = clientesController.actualizarCliente(cliente);
        JOptionPane.showMessageDialog(this, resultado);
        dispose();
    }
}
