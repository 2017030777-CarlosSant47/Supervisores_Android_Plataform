package com.carlossant47.testingfunctions.Clases;

public class Pedientes {

    private int idSupervicion;
    private String fecha;


    public Pedientes()
    {
        this.setIdSupervicion(0);
        this.setFecha("");
    }

    public int getIdSupervicion() {
        return idSupervicion;
    }

    public void setIdSupervicion(int idSupervicion) {
        this.idSupervicion = idSupervicion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
