package com.carlossant47.testingfunctions;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by CarlosSant47 on 12/05/2018.
 */

public class Catalogo_Supervicion {


    //LAS OPCCIONES QUE SEAN DE BUENO, REGULAR, MALO SERAN ASI RESPECTIVAMENTE (3, 2, 1)
    //SI Y NO: 1, 0




    public int internet, matdidactico, biblotead, platoformweb, laboratorio2, puntoencuentro, invernadero,
            sustentable, horno, buenamblab, talleresp, escintalacion, apdocente, rpfealumno, propuestad,
            actualizarRVOE, DrvoeSoperar;
    public String otros;

    //PARTE COMENTARIOS
    static public class Comentarios
    {
         int internet, matdidactico, biblotead, platoformweb, laboratorio2, puntoencuentro, invernadero,
                sustentable, horno, buenamblab, talleresp, escintalacion, apdocente, rpfealumno, propuestad,
                actualizarRVOE, DrvoeSoperar;
         String otros;
    }


    //PARTE DE CONDICIONES GENERALES DE INSTITUCION
    static public class Condiciones
    {
        int pipitre, mesabanco, aula, taller, laboratorio, snitariohom, sanitariomuj, recdidactico, higiene, seguridad, bilboteca, centrocom, internet1, internet2, auditorio, areaverde, preteccioncivil, bombero, pesenalado;
        //LA Ñ NO va ha hacer un tronadero :v
    }
    ///CONTROL ESCOLAR Y RVOE, LOS PUSE JUENTOS PORK ASI LO PUSE >:v
    static public class controlESCRV
    {
        int rsepyc, tainscrito, tabajo, tacierre, tatec, tatsu, talic, taesp, tamtr, tadir, movilidad, RVOE1, REVOE2, REVOEoperando, RVOEsinoperar, RVOEfederal1, RVOEfederal2;
    }


    //PARTE DE PLANTA DOCENTE
    static public class PlantaDoc
    {
        int modalidad, planEstudio, Autorizado, Sautorizacion, curricumul, Actanacimiento, nacionalidad, gradotec, gradolic, gradoesp, gradomtr, gradodr, titulo, cedula, dipconstacia, perfilmateria, SNI1, SNI2;
        String sugerencias;
    }

    static public class PreguntasDoc
    {
        public PreguntasDoc()
        {

        }
        int semestre, id, proacademico, nmsemestre, asisprofesor, ccdht, ccdhp, evaluar, reglamateria, reglamento, listaasis1, listaasis2, discapacidad1, discapacidad2, Becados, indigena1, indegena2, idioma1, idioma2;
        String Comentarios, asegnatura, grupo;
    }



    static public class ListSupervicion
    {
        String nombreEscuela, fecha;
        int id;

    }

    static public class ListInstituciones
    {
        private String nombre;
        private String informacion;
        private int id;
        private int color;
        private int numRVOE;
        private int idIntitucion;
        private int status;
        private String fecha;
        String zona;

        public void setZona(String zona){
            this.zona = zona;
        }

        public String getZona()
        {
            return this.zona;
        }


        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getInformacion() {
            return informacion;
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

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
    }


    static public class PlantaDocente
    {
        private int idsupervision;
        private int curriculum;
        private int acta_naci;
        private int nacionalidad;
        private int titulo;
        private int cedula;
        private int diplomas;
        private int perfil_materia;
        private int sniv;
        private int nivel_sniv;
        private String añoing_sniv;
        private int ssit;
        private int nivel_ssnit;
        private String año_ssnit;
        private String sugerencias;



        public void setIdsupervision(int idsupervision) {
            this.idsupervision = idsupervision;
        }

        public void setCurriculum(int curriculum) {
            this.curriculum = curriculum;
        }

        public void setActa_naci(int acta_naci) {
            this.acta_naci = acta_naci;
        }

        public void setNacionalidad(int nacionalidad) {
            this.nacionalidad = nacionalidad;
        }

        public void setTitulo(int titulo) {
            this.titulo = titulo;
        }

        public void setCedula(int cedula) {
            this.cedula = cedula;
        }

        public void setDiplomas(int diplomas) {
            this.diplomas = diplomas;
        }

        public void setPerfil_materia(int perfil_materia) {
            this.perfil_materia = perfil_materia;
        }

        public void setSsniv(int ssniv) {
            this.setSniv(ssniv);
        }

        public void setNivel_sniv(int nivel_sniv) {
            this.nivel_sniv = nivel_sniv;
        }

        public void setAñoing_sniv(String añoing_sniv) {
            this.añoing_sniv = añoing_sniv;
        }

        public void setSsit(int ssit) {
            this.ssit = ssit;
        }

        public void setNivel_ssnit(int nivel_ssnit) {
            this.nivel_ssnit = nivel_ssnit;
        }

        public void setAño_ssnit(String año_ssnit) {
            this.año_ssnit = año_ssnit;
        }

        public void setSugerencias(String sugerencias) {
            this.sugerencias = sugerencias;
        }

        public void setSniv(int sniv) {
            this.sniv = sniv;
        }

        public int getIdsupervision() {
            return idsupervision;
        }

        public int getCurriculum() {
            return curriculum;
        }

        public int getActa_naci() {
            return acta_naci;
        }

        public int getNacionalidad() {
            return nacionalidad;
        }

        public int getTitulo() {
            return titulo;
        }

        public int getCedula() {
            return cedula;
        }

        public int getDiplomas() {
            return diplomas;
        }

        public int getPerfil_materia() {
            return perfil_materia;
        }

        public int getSniv() {
            return sniv;
        }

        public int getNivel_sniv() {
            return nivel_sniv;
        }

        public String getAñoing_sniv() {
            return añoing_sniv;
        }

        public int getSsit() {
            return ssit;
        }

        public int getNivel_ssnit() {
            return nivel_ssnit;
        }

        public String getAño_ssnit() {
            return año_ssnit;
        }

        public String getSugerencias() {
            return sugerencias;
        }
    }

    static class Supervision
    {
        private int plan_estudios;
        private int num_etapa;
        private int modalidad;
        private int docentes_regis;
        private int docentes_sregi;
        private int registados_sepyc;
        private int alumnos_ins;
        private int alumnos_baja;
        private int movilidad_inter;
        private int programas_actua;
        private int cuantos_pactua;
        private int rvoe_operando;
        private int rvoe_soperar;
        private int rvoe_federales;
        private String cuales_rvfed;
        private int pupitre;
        private int mesa_banco;
        private int numaulas;
        private int numtalleres;
        private int numlaboratorios;
        private int sanitarios_hom;
        private int snitarios_muj;
        private int recursos_dic;
        private int higiene;
        private int seguridad;
        private int bibloteca;
        private int centro_computo;
        private int internet;
        private int auditorio;
        private int area_verde;
        private int acre_proccivil;
        private int impacto_vial;
        private int acredi_bom;
        private int bibloteca_digi;
        private int plataforma_web;
        private int laboratorios;
        private int invernaderos;
        private int sustentable;
        private int hornos;
        private int buen_laboral;
        private int taller_esp;
        private int exelente_insta;
        private int elevador;
        private int rampa;
        private int estacionamiento;
        private int actua_plandocen;
        private int regla_progeva;
        private int propuestas_docente;
        private int actualizar_rvoe;
        private int rvoe_avisosoperar;
        private int encuentro_sen;
        private String inter_proveedor;
        private String inter_paquete;
        private String comen_procivil;
        private String comen_impactovial;
        private String coment_bom;
        private String otros;
        private String filepdf;

        public int getPlan_estudios() {
            return plan_estudios;
        }

        public void setPlan_estudios(int plan_estudios) {
            this.plan_estudios = plan_estudios;
        }

        public int getNum_etapa() {
            return num_etapa;
        }

        public void setNum_etapa(int num_etapa) {
            this.num_etapa = num_etapa;
        }

        public int getModalidad() {
            return modalidad;
        }

        public void setModalidad(int modalidad) {
            this.modalidad = modalidad;
        }

        public int getDocentes_regis() {
            return docentes_regis;
        }

        public void setDocentes_regis(int docentes_regis) {
            this.docentes_regis = docentes_regis;
        }

        public int getDocentes_sregi() {
            return docentes_sregi;
        }

        public void setDocentes_sregi(int docentes_sregi) {
            this.docentes_sregi = docentes_sregi;
        }

        public int getRegistados_sepyc() {
            return registados_sepyc;
        }

        public void setRegistados_sepyc(int registados_sepyc) {
            this.registados_sepyc = registados_sepyc;
        }

        public int getAlumnos_ins() {
            return alumnos_ins;
        }

        public void setAlumnos_ins(int alumnos_ins) {
            this.alumnos_ins = alumnos_ins;
        }

        public int getAlumnos_baja() {
            return alumnos_baja;
        }

        public void setAlumnos_baja(int alumnos_baja) {
            this.alumnos_baja = alumnos_baja;
        }

        public int getMovilidad_inter() {
            return movilidad_inter;
        }

        public void setMovilidad_inter(int movilidad_inter) {
            this.movilidad_inter = movilidad_inter;
        }

        public int getProgramas_actua() {
            return programas_actua;
        }

        public void setProgramas_actua(int programas_actua) {
            this.programas_actua = programas_actua;
        }

        public int getCuantos_pactua() {
            return cuantos_pactua;
        }

        public void setCuantos_pactua(int cuantos_pactua) {
            this.cuantos_pactua = cuantos_pactua;
        }

        public int getRvoe_operando() {
            return rvoe_operando;
        }

        public void setRvoe_operando(int rvoe_operando) {
            this.rvoe_operando = rvoe_operando;
        }

        public int getRvoe_soperar() {
            return rvoe_soperar;
        }

        public void setRvoe_soperar(int rvoe_soperar) {
            this.rvoe_soperar = rvoe_soperar;
        }

        public int getRvoe_federales() {
            return rvoe_federales;
        }

        public void setRvoe_federales(int rvoe_federales) {
            this.rvoe_federales = rvoe_federales;
        }

        public String getCuales_rvfed() {
            return cuales_rvfed;
        }

        public void setCuales_rvfed(String cuantos_rvfed) {
            this.cuales_rvfed = cuantos_rvfed;
        }

        public int getPupitre() {
            return pupitre;
        }

        public void setPupitre(int pupitre) {
            this.pupitre = pupitre;
        }

        public int getMesa_banco() {
            return mesa_banco;
        }

        public void setMesa_banco(int mesa_banco) {
            this.mesa_banco = mesa_banco;
        }

        public int getNumaulas() {
            return numaulas;
        }

        public void setNumaulas(int numaulas) {
            this.numaulas = numaulas;
        }

        public int getNumtalleres() {
            return numtalleres;
        }

        public void setNumtalleres(int numtalleres) {
            this.numtalleres = numtalleres;
        }

        public int getNumlaboratorios() {
            return numlaboratorios;
        }

        public void setNumlaboratorios(int numlaboratorios) {
            this.numlaboratorios = numlaboratorios;
        }

        public int getSanitarios_hom() {
            return sanitarios_hom;
        }

        public void setSanitarios_hom(int sanitarios_hom) {
            this.sanitarios_hom = sanitarios_hom;
        }

        public int getSnitarios_muj() {
            return snitarios_muj;
        }

        public void setSnitarios_muj(int snitarios_muj) {
            this.snitarios_muj = snitarios_muj;
        }

        public int getRecursos_dic() {
            return recursos_dic;
        }

        public void setRecursos_dic(int recursos_dic) {
            this.recursos_dic = recursos_dic;
        }

        public int getHigiene() {
            return higiene;
        }

        public void setHigiene(int higiene) {
            this.higiene = higiene;
        }

        public int getSeguridad() {
            return seguridad;
        }

        public void setSeguridad(int seguridad) {
            this.seguridad = seguridad;
        }

        public int getBibloteca() {
            return bibloteca;
        }

        public void setBibloteca(int bibloteca) {
            this.bibloteca = bibloteca;
        }

        public int getCentro_computo() {
            return centro_computo;
        }

        public void setCentro_computo(int centro_computo) {
            this.centro_computo = centro_computo;
        }

        public int getInternet() {
            return internet;
        }

        public void setInternet(int internet) {
            this.internet = internet;
        }

        public int getAuditorio() {
            return auditorio;
        }

        public void setAuditorio(int auditorio) {
            this.auditorio = auditorio;
        }

        public int getArea_verde() {
            return area_verde;
        }

        public void setArea_verde(int area_verde) {
            this.area_verde = area_verde;
        }

        public int getAcre_proccivil() {
            return acre_proccivil;
        }

        public void setAcre_proccivil(int acre_proccivil) {
            this.acre_proccivil = acre_proccivil;
        }

        public int getImpacto_vial() {
            return impacto_vial;
        }

        public void setImpacto_vial(int impacto_vial) {
            this.impacto_vial = impacto_vial;
        }

        public int getAcredi_bom() {
            return acredi_bom;
        }

        public void setAcredi_bom(int acredi_bom) {
            this.acredi_bom = acredi_bom;
        }

        public int getBibloteca_digi() {
            return bibloteca_digi;
        }

        public void setBibloteca_digi(int bibloteca_digi) {
            this.bibloteca_digi = bibloteca_digi;
        }

        public int getPlataforma_web() {
            return plataforma_web;
        }

        public void setPlataforma_web(int plataforma_web) {
            this.plataforma_web = plataforma_web;
        }

        public int getLaboratorios() {
            return laboratorios;
        }

        public void setLaboratorios(int laboratorios) {
            this.laboratorios = laboratorios;
        }

        public int getInvernaderos() {
            return invernaderos;
        }

        public void setInvernaderos(int invernaderos) {
            this.invernaderos = invernaderos;
        }

        public int getSustentable() {
            return sustentable;
        }

        public void setSustentable(int sustentable) {
            this.sustentable = sustentable;
        }

        public int getHornos() {
            return hornos;
        }

        public void setHornos(int hornos) {
            this.hornos = hornos;
        }

        public int getBuen_laboral() {
            return buen_laboral;
        }

        public void setBuen_laboral(int buen_laboral) {
            this.buen_laboral = buen_laboral;
        }

        public int getTaller_esp() {
            return taller_esp;
        }

        public void setTaller_esp(int taller_esp) {
            this.taller_esp = taller_esp;
        }

        public int getExelente_insta() {
            return exelente_insta;
        }

        public void setExelente_insta(int exelente_insta) {
            this.exelente_insta = exelente_insta;
        }

        public int getElevador() {
            return elevador;
        }

        public void setElevador(int elevador) {
            this.elevador = elevador;
        }

        public int getRampa() {
            return rampa;
        }

        public void setRampa(int rampa) {
            this.rampa = rampa;
        }

        public int getEstacionamiento() {
            return estacionamiento;
        }

        public void setEstacionamiento(int estacionamiento) {
            this.estacionamiento = estacionamiento;
        }

        public int getActua_plandocen() {
            return actua_plandocen;
        }

        public void setActua_plandocen(int actua_plandocen) {
            this.actua_plandocen = actua_plandocen;
        }

        public int getRegla_progeva() {
            return regla_progeva;
        }

        public void setRegla_progeva(int regla_progeva) {
            this.regla_progeva = regla_progeva;
        }

        public int getPropuestas_docente() {
            return propuestas_docente;
        }

        public void setPropuestas_docente(int propuestas_docente) {
            this.propuestas_docente = propuestas_docente;
        }

        public int getActualizar_rvoe() {
            return actualizar_rvoe;
        }

        public void setActualizar_rvoe(int actualizar_rvoe) {
            this.actualizar_rvoe = actualizar_rvoe;
        }

        public int getRvoe_avisosoperar() {
            return rvoe_avisosoperar;
        }

        public void setRvoe_avisosoperar(int rvoe_avisosoperar) {
            this.rvoe_avisosoperar = rvoe_avisosoperar;
        }

        public int getEncuentro_sen() {
            return encuentro_sen;
        }

        public void setEncuentro_sen(int encuentro_sen) {
            this.encuentro_sen = encuentro_sen;
        }

        public String getInter_proveedor() {
            return inter_proveedor;
        }

        public void setInter_proveedor(String inter_proveedor) {
            this.inter_proveedor = inter_proveedor;
        }

        public String getInter_paquete() {
            return inter_paquete;
        }

        public void setInter_paquete(String inter_paquete) {
            this.inter_paquete = inter_paquete;
        }

        public String getComen_procivil() {
            return comen_procivil;
        }

        public void setComen_procivil(String comen_procivil) {
            this.comen_procivil = comen_procivil;
        }

        public String getComen_impactovial() {
            return comen_impactovial;
        }

        public void setComen_impactovial(String comen_impactovial) {
            this.comen_impactovial = comen_impactovial;
        }

        public String getComent_bom() {
            return coment_bom;
        }

        public void setComent_bom(String coment_bom) {
            this.coment_bom = coment_bom;
        }

        public String getOtros() {
            return otros;
        }

        public void setOtros(String otros) {
            this.otros = otros;
        }

        public String getFilepdf() {
            return filepdf;
        }

        public void setFilepdf(String filepdf) {
            this.filepdf = filepdf;
        }
    }

    public static class Representantes
    {

        private String nombre;
        private int idsupervision;
        private int tipo_credencial;
        private String numcredencial;
        private int tipo_rep;


        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getIdsupervision() {
            return idsupervision;
        }

        public void setIdsupervision(int idsupervision) {
            this.idsupervision = idsupervision;
        }

        public int getTipo_credencial() {
            return tipo_credencial;
        }

        public void setTipo_credencial(int tipo_credencial) {
            this.tipo_credencial = tipo_credencial;
        }

        public String getNumcredencial() {
            return numcredencial;
        }

        public void setNumcredencial(String numcredencial) {
            this.numcredencial = numcredencial;
        }

        public int getTipo_rep() {
            return tipo_rep;
        }

        public void setTipo_rep(int tipo_rep) {
            this.tipo_rep = tipo_rep;
        }

    }


    public static class RevisionAula
    {
        private int idsupervision;
        private int alumfuera;
        private int cuanto_alumfuera;
        private int alumdiscapacitados;
        private int cuanto_alumdis;
        private int becas_porcentaje;
        private int alumos_lenguainde;
        private int cuantos_alumleninde;
        private int alumnos_idioma;
        private int cuantos_alumidiom;
        private int programa_academico;
        private int etapa_plan;
        //private int materias_plan;
        private String gurpo;
        private int grado;
        private int maestros_asis;
        private int maestros_cartades;
        private int maestros_evaluacion;
        private int estudiantes_regla;
        private int maestros_regla;
        private String comentarios;

        public int getAlumfuera() {
            return alumfuera;
        }

        public void setAlumfuera(int alumfuera) {
            this.alumfuera = alumfuera;
        }

        public int getCuanto_alumfuera() {
            return cuanto_alumfuera;
        }

        public void setCuanto_alumfuera(int cuanto_alumfuera) {
            this.cuanto_alumfuera = cuanto_alumfuera;
        }

        public int getAlumdiscapacitados() {
            return alumdiscapacitados;
        }

        public void setAlumdiscapacitados(int alumdiscapacitados) {
            this.alumdiscapacitados = alumdiscapacitados;
        }

        public int getCuanto_alumdis() {
            return cuanto_alumdis;
        }

        public void setCuanto_alumdis(int cuanto_alumdis) {
            this.cuanto_alumdis = cuanto_alumdis;
        }

        public int getBecas_procentaje() {
            return becas_porcentaje ;
        }

        public void setBecas_procentaje(int becas_procentaje) {
            this.becas_porcentaje = becas_procentaje;
        }

        public int getAlumos_lenguainde() {
            return alumos_lenguainde;
        }

        public void setAlumos_lenguainde(int alumos_lenguainde) {
            this.alumos_lenguainde = alumos_lenguainde;
        }

        public int getCuantos_alumleninde() {
            return cuantos_alumleninde;
        }

        public void setCuantos_alumleninde(int cuantos_alumleninde) {
            this.cuantos_alumleninde = cuantos_alumleninde;
        }

        public int getAlumnos_idioma() {
            return alumnos_idioma;
        }

        public void setAlumnos_idioma(int alumnos_idioma) {
            this.alumnos_idioma = alumnos_idioma;
        }

        public int getCuantos_alumidiom() {
            return cuantos_alumidiom;
        }

        public void setCuantos_alumidiom(int cuantos_alumidiom) {
            this.cuantos_alumidiom = cuantos_alumidiom;
        }

        public int getPrograma_academico() {
            return programa_academico;
        }

        public void setPrograma_academico(int programa_academico) {
            this.programa_academico = programa_academico;
        }

        public int getEtapa_plan() {
            return etapa_plan;
        }

        public void setEtapa_plan(int etapa_plan) {
            this.etapa_plan = etapa_plan;
        }

        /*public int getMaterias_plan() {
            return materias_plan;
        }*/

        /*public void setMaterias_plan(int materias_plan) {
            this.materias_plan = materias_plan;
        }
        */

        public String getGurpo() {
            return gurpo;
        }

        public void setGurpo(String gurpo) {
            this.gurpo = gurpo;
        }

        public int getGrado() {
            return grado;
        }

        public void setGrado(int grado) {
            this.grado = grado;
        }

        public int getMaestros_asis() {
            return maestros_asis;
        }

        public void setMaestros_asis(int maestros_asis) {
            this.maestros_asis = maestros_asis;
        }

        public int getMaestros_cartades() {
            return maestros_cartades;
        }

        public void setMaestros_cartades(int maestros_cartades) {
            this.maestros_cartades = maestros_cartades;
        }

        public int getMaestros_evaluacion() {
            return maestros_evaluacion;
        }

        public void setMaestros_evaluacion(int maestros_evaluacion) {
            this.maestros_evaluacion = maestros_evaluacion;
        }

        public int getEstudiantes_regla() {
            return estudiantes_regla;
        }

        public void setEstudiantes_regla(int estudiantes_regla) {
            this.estudiantes_regla = estudiantes_regla;
        }

        public int getMaestros_regla() {
            return maestros_regla;
        }

        public void setMaestros_regla(int maestros_regla) {
            this.maestros_regla = maestros_regla;
        }

        public String getComentarios() {
            return comentarios;
        }

        public void setComentarios(String comentarios) {
            this.comentarios = comentarios;
        }


        public int getIdsupervision() {
            return idsupervision;
        }

        public void setIdsupervision(int idsupervision) {
            this.idsupervision = idsupervision;
        }
    }


    public static void saveRepresentantes(ArrayList<Representantes> representantes, Context context)
    {
        try {
            for (int x = 0; x < representantes.size(); x++)
            {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(representantes.get(x));
                Log.w("JSON", json);
                params.add("rep", json);
                if(Permissions.isNettworkConnect(context)) {
                    final int finalX = x;
                    client.post(Config.concatUrlConection(Permissions.urlSaveRepresentantes, context), params, new TextHttpResponseHandler() {
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseArray) {
                            Log.w("SUBIO" + finalX, responseArray);
                        }

                        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headerses, String responseString, Throwable throwable) {
                            Log.w("No subio", responseString);
                        }
                    });
                }
                else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
