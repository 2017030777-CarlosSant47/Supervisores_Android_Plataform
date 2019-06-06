package com.carlossant47.testingfunctions.Clases;

import android.util.Log;

import java.util.Calendar;

public class Fecha {
    private int day;
    private int month;
    private int year;

    public Fecha()
    {
        setDay(0);
        setMonth(0);
        setYear(0);
    }

    public Fecha(Fecha fecha)
    {
        setDay(fecha.getDay());
        setMonth(fecha.getDay());
        setYear(fecha.getDay());
    }

    public Fecha(String fechaDateSQL)
    {
        setFechaDateSQL(fechaDateSQL);
    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setDay(String day) {
        this.day = Integer.parseInt(day);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setMonth(String month) {
        this.month = Integer.parseInt(month);
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setYear(String year) {
        this.year = Integer.parseInt(year);
    }

    public void setFechaDateSQL(String fecha)
    {
        Log.w("FECHA", fecha);
        this.setYear(new StringBuilder().append(fecha.charAt(0)).append(fecha.charAt(1)).append(fecha.charAt(2)).append(fecha.charAt(3)).toString());
        this.setMonth(new StringBuilder().append(fecha.charAt(5)).append(fecha.charAt(6)).toString());
        this.setDay(new StringBuilder().append(fecha.charAt(8)).append(fecha.charAt(9)).toString());
    }

    public String returnNameMonth()
    {
        String m = "";
        switch (this.month) {
            case 1:
                m = "Enero";
                break;
            case 2:
                m = "Febrero";
                break;
            case 3:
                m = "Marzo";
                break;
            case 4:
                m = "Abril";
                break;
            case 5:
                m = "Mayo";
                break;
            case 6:
                m = "Junio";
                break;
            case 7:
                m = "Julio";
                break;
            case 8:
                m = "Agosto";
                break;
            case 9:
                m = "Septiembre";
                break;
            case 10:
                m = "Octubre";
                break;
            case 11:
                m = "Noviembre";
                break;
            case 12:
                m = "Diciembre";
                break;
        }

        return m;
    }

    public String returnNameDay()
    {
        String day = "";

        switch (this.day) {
            case 1:
                day = "Lunes";
                break;
            case 2:
                day = "Martes";
                break;
            case 3:
                day = "Miercoles";
                break;
            case 4:
                day = "Jueves";
                break;
            case 5:
                day = "Viernes";
                break;
            case 6:
                day = "Sabado";
                break;
            case 7:
                day = "Domingo";
                break;
            default:
        }
        return day;
    }

    public String getFechaDateSQL()
    {
        String day = "", month = "";

        if(this.month < 10 && this.day < 10)
        {
            day = "0" + this.day;
            month = "0" + this.month;
        }
        else if(this.day < 10 && this.month >= 10)
        {
            day = "0" + this.day;
            month = String.valueOf(this.month);
        }

        else if(this.day >= 10 && this.month < 10)
        {
            day = String.valueOf(this.day);
            month = "0"+ this.month;
        }
        else
        {
            day = String.valueOf(this.day);
            month = String.valueOf(this.month);
        }

        return String.valueOf(this.year + "-" + month + "-" + day);
    }

    public String returnFecha()
    {
        return String.valueOf(this.day + " de " + this.returnNameMonth() + " del " + this.year);
    }
}
