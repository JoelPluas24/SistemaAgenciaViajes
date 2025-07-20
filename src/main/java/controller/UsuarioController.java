/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario iniciarSesion(String correo, String contrasena) {
        return usuarioDAO.verificarCredenciales(correo, contrasena);
    }
}
