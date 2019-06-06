package com.carlossant47.testingfunctions.Clases;

import com.carlossant47.testingfunctions.DBStruct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import static com.carlossant47.testingfunctions.MainSupervision.convertString;

public class SupervicionPendiente {

    private int id;
    private String supervicion;
    private String revicionAula;
    private String representantesSup;
    private String programaRvoe;
    private String plantaDocente;
    private String fecha;



    public SupervicionPendiente(int id, String supervicion, String revicionAula, String representantesSup, String programaRvoe, String plantaDocente, String fecha) {
        this.supervicion = supervicion;
        this.revicionAula = revicionAula;
        this.representantesSup = representantesSup;
        this.programaRvoe = programaRvoe;
        this.plantaDocente = plantaDocente;
        this.setId(id);
        this.setFecha((fecha));
    }


    public SupervicionPendiente(int id, Supervision supervision, ArrayList<RevisionAula> revisionAula, ArrayList<Representantes> representantes, ArrayList<Rvoe> rvoes, ArrayList<PlantaDocente> plantaDocentes,String fecha)
    {
        this.id = id;
        final ObjectMapper mapper = new ObjectMapper();
        try {
            this.supervicion = mapper.writeValueAsString(supervision);
            this.revicionAula = mapper.writeValueAsString(revisionAula);
            this.representantesSup = mapper.writeValueAsString(representantes);
            this.plantaDocente = mapper.writeValueAsString(plantaDocentes);
            this.programaRvoe = mapper.writeValueAsString(rvoes);
            this.setFecha(fecha);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public SupervicionPendiente()
    {
        this.supervicion = "";
        this.revicionAula = "";
        this.representantesSup = "";
        this.programaRvoe = "";
        this.plantaDocente = "";
        this.setId(0);
    }


    public String getSupervicion() {
        return supervicion;
    }

    public void setSupervicion(String supervicion) {
        this.supervicion = supervicion;
    }

    public String getRevicionAula() {
        return revicionAula;
    }

    public void setRevicionAula(String revicionAula) {
        this.revicionAula = revicionAula;
    }

    public String getRepresentantesSup() {
        return representantesSup;
    }

    public void setRepresentantesSup(String representantesSup) {
        this.representantesSup = representantesSup;
    }

    public String getProgramaRvoe() {
        return programaRvoe;
    }

    public void setProgramaRvoe(String programaRvoe) {
        this.programaRvoe = programaRvoe;
    }

    public String getPlantaDocente() {
        return plantaDocente;
    }

    public void setPlantaDocente(String plantaDocente) {
        this.plantaDocente = plantaDocente;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RequestParams getParamsServer()
    {
        final RequestParams params = new RequestParams();
        params.add("supervision", this.supervicion);
        params.add("revision", this.revicionAula);
        params.add("plantaDoc", this.plantaDocente);
        params.add("representantes", this.representantesSup);
        params.add(DBStruct.IDINSTITUCION, convertString(this.id));
        if(this.programaRvoe.length() > 0)
        {
            params.add("rvoe", this.programaRvoe);
        }
        return params;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
