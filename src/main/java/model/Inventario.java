/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Inventario {
    private int idInventario;
    private int idRuta;
    private int idPasaje;
    private int cantidadDisponible;

    public Inventario(int idInventario, int idRuta, int idPasaje, int cantidadDisponible) {
        this.idInventario = idInventario;
        this.idRuta = idRuta;
        this.idPasaje = idPasaje;
        this.cantidadDisponible = cantidadDisponible;
    }

    public Inventario() {}

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdPasaje() {
        return idPasaje;
    }

    public void setIdPasaje(int idPasaje) {
        this.idPasaje = idPasaje;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}

