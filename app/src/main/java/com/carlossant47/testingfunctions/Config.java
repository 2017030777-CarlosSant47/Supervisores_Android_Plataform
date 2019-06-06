package com.carlossant47.testingfunctions;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {

    private Context context;

    private static final String URLCONECRION = "urlconection";
    private final static String urlBase = "http://pespeciales.upsin.edu.mx/supervisor-web/index.php/";
    Config(Context context)
    {
        this.context = context;
    }

    public String getNombre()
    {
        SharedPreferences sharedPref = this.context.getSharedPreferences(Permissions.fileconfig,0);
        return sharedPref.getString(Permissions.Nombre, "");
    }

    public static boolean getSession(Context contextA)
    {
        SharedPreferences sharedPref = contextA.getSharedPreferences(Permissions.fileconfig,0);
        return sharedPref.getBoolean(Permissions.sesion, false);
    }

    public String getEmail()
    {
        SharedPreferences sharedPref = this.context.getSharedPreferences(Permissions.fileconfig,0);
        return sharedPref.getString(Permissions.email, "");
    }

    public void setNombre(String Nombre) {
        SharedPreferences sharedPref = this.context.getSharedPreferences(Permissions.fileconfig,0);
        SharedPreferences.Editor preferences = sharedPref.edit();
        preferences.putString(Permissions.Nombre, Nombre);
        preferences.apply();
    }

    public void setSession(boolean s)
    {
        SharedPreferences sharedPref = this.context.getSharedPreferences(Permissions.fileconfig,0);
        SharedPreferences.Editor preferences = sharedPref.edit();
        preferences.putBoolean(Permissions.sesion, s);
        preferences.apply();
    }

    public void setEmail(String e)
    {
        SharedPreferences sharedPref = this.context.getSharedPreferences(Permissions.fileconfig,0);
        SharedPreferences.Editor preferences = sharedPref.edit();
        preferences.putString(Permissions.email, e);
        preferences.apply();
    }

    public static void setUrlConnection(String conection, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(Permissions.fileconfig,0);
        SharedPreferences.Editor preferences = sharedPref.edit();
        preferences.putString(URLCONECRION, conection);
        preferences.apply();
    }

    public void setUrlConnection(String conection){
        SharedPreferences sharedPref = this.context.getSharedPreferences(Permissions.fileconfig,0);
        SharedPreferences.Editor preferences = sharedPref.edit();
        preferences.putString(URLCONECRION, conection);
        preferences.apply();
    }


    public static String getUrlConecrion(Context c)
    {
        SharedPreferences sharedPref = c.getSharedPreferences(Permissions.fileconfig,0);
        return sharedPref.getString(URLCONECRION, urlBase);
    }


    public static String concatUrlConection(String urlConetion, Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(Permissions.fileconfig,0);
        return sharedPref.getString(URLCONECRION, urlBase + urlConetion);
    }

    public void setDowloadData(int i)
    {
        SharedPreferences sharedPref = this.context.getSharedPreferences(Permissions.fileconfig,0);
        SharedPreferences.Editor preferences = sharedPref.edit();
        preferences.putInt(Permissions.statusData, i);
        preferences.apply();
    }

    public static int getDowloadData(Context c)
    {
        SharedPreferences sharedPref = c.getSharedPreferences(Permissions.fileconfig,0);
        return sharedPref.getInt(Permissions.statusData, 0);
    }

    public void setIdSupervisor(int id)
    {
        SharedPreferences sharedPref = this.context.getSharedPreferences(Permissions.fileconfig,0);
        SharedPreferences.Editor preferences = sharedPref.edit();
        preferences.putInt("idsupervisor", id);
        preferences.apply();
    }

    public int getIdSupervisor()
    {
        SharedPreferences sharedPref = this.context.getSharedPreferences(Permissions.fileconfig,0);
        return sharedPref.getInt("idsupervisor", 0);
    }

    public static int getIdSupervisor(Context c)
    {
        SharedPreferences sharedPref = c.getSharedPreferences(Permissions.fileconfig,0);
        return sharedPref.getInt("idsupervisor", 0);
    }

    public void LogutOption()
    {
        this.setEmail("");
        this.setSession(false);
        this.setNombre("");
        this.setDowloadData(0);
    }



}
