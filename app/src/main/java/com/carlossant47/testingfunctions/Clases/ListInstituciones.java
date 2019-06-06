package com.carlossant47.testingfunctions.Clases;

public class ListInstituciones {
    private String nombre;
    private String informacion;
    private int id;
    private int color;
    private int numRVOE;
    private int idIntitucion;
    private int status;
    String zona;
    Fecha fecha;

    public ListInstituciones(ListInstituciones listInstituciones) {
        nombre=listInstituciones.nombre;
        informacion=listInstituciones.informacion;
        id=listInstituciones.id;
        color=listInstituciones.color;
        numRVOE=listInstituciones.numRVOE;
        idIntitucion=listInstituciones.idIntitucion;
        status=listInstituciones.status;
        zona=listInstituciones.zona;
        fecha=listInstituciones.fecha;
    }

    public ListInstituciones()
    {

    }

    public void setZona(String zona){
        this.zona = zona;
    }

    public String getZona()
    {
        return this.zona;
    }


    public String getNombre() {
        if(nombre == null)
            nombre = "Informacion no disponible";
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInformacion() {
        if(!this.informacion.equals("null"))
            return informacion;
        else
            return "Informacion no disponible por el momento";
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getNumRVOE() {
        return numRVOE;
    }

    public void setNumRVOE(int numRVOE) {
        this.numRVOE = numRVOE;
    }

    public int getIdIntitucion() {
        return idIntitucion;
    }

    public void setIdIntitucion(int idIntitucion) {
        this.idIntitucion = idIntitucion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }
}
