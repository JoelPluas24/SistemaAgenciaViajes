/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Time;
import java.time.LocalTime;

/**
 *
 * @author maite
 */
public class Rutas {
    private int idRuta;
    private String origen;
    private String destino;
    private LocalTime duracion;

    public Rutas(int idRuta, String origen, String destino, LocalTime duracion) {
        this.idRuta = idRuta;
        this.origen = origen;
        this.destino = destino;
        this.duracion = duracion;
    }


    
    public Rutas() {}

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }


    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }
    
   
   
    
    
}
