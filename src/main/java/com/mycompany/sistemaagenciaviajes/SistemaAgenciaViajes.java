/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemaagenciaviajes;

import view.LoginView;

/**
 *
 * @author maite
 */
public class SistemaAgenciaViajes {
        public static void main(String[] args) {
        // Carga la vista principal
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }


}
