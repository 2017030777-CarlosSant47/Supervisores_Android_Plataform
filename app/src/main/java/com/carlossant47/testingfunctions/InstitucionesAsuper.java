package com.carlossant47.testingfunctions;

import android.graphics.Color;

/**
 * Created by Carlos Santiago on 5/7/2018.
 */

public class InstitucionesAsuper {
    private int id;
    private String nombreInstitucion;
    private int color;
    public InstitucionesAsuper(){
        this.nombreInstitucion = "";
        this.id = 0;
        this.color = 0;
    }

    public InstitucionesAsuper(int id, String nombreInstitucion, int color){
        this.id = id;
        this.color = color;
        this.nombreInstitucion = nombreInstitucion;
    }
    public String getNombreInstitucion(){
        return nombreInstitucion;
    }
    public int getColor() {return color;}
    public long getId(){
        return id;
    }

    public void setNombreInstitucion(String nombreInstitucion){
        this.nombreInstitucion = nombreInstitucion;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setColor(int color){this.color = color;}


}
