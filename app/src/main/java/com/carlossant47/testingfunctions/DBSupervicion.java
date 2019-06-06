package com.carlossant47.testingfunctions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.carlossant47.testingfunctions.Clases.Fecha;
import com.carlossant47.testingfunctions.Clases.ListInstituciones;
import com.carlossant47.testingfunctions.Clases.Rvoe;
import com.carlossant47.testingfunctions.Clases.SupervicionPendiente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by CarlosSant47 on 12/05/2018.
 */

public class DBSupervicion {
    SQLiteDatabase database;
    Context context;
    DBHelper helper;


    private DBStruct.NamesSupervisiones namesSupervisiones;

    public DBSupervicion(Context context){
        this.context=context;
        this.helper=new DBHelper(context);
    }

    private String[] seleccionSupervisiones=new String[]{
            DBStruct.idSupervision,
            DBStruct.nombreIns,
            DBStruct.zona,
            DBStruct.fecha,
            DBStruct.colorL,
            DBStruct.numrvoe,
            DBStruct.direccionI,
            DBStruct.IDINSTITUCION,
            DBStruct.STATUS_SUP,
    };

    public void deleteDataTable()
    {

        database.delete(DBStruct.TABLE_Supervisiones, null, null);
        database.delete(DBStruct.TABLE_RVOES, null, null);
        database.delete(DBStruct.TABLE_SUPERVICIONESPEN, null, null);
        
    }


    //AGREGAR SUPERVISORES A SQL LITE CONMING SOON MISQCUAL xDD;
    public long insertearSupervisor(Supervisores.Supervisores2 supervisores)
    {
        ContentValues contentValues =new ContentValues();
        contentValues.put(DBStruct.amaterno,supervisores.Amaterno);
        contentValues.put(DBStruct.apaterno,supervisores.APaterno);
        contentValues.put(DBStruct.pnombre,supervisores.PNom);
        contentValues.put(DBStruct.snombre,supervisores.SNom);
        contentValues.put(DBStruct.ine,supervisores.INE);
        contentValues.put(DBStruct.curp,supervisores.CURP);
        contentValues.put(DBStruct.rfc,supervisores.RFC);
        contentValues.put(DBStruct.direccion,supervisores.Direc);
        contentValues.put(DBStruct.colonia,supervisores.Colonia);
        contentValues.put(DBStruct.telefono,supervisores.Tel);
        contentValues.put(DBStruct.celular,supervisores.Cel);
        contentValues.put(DBStruct.fnacimiento,supervisores.FNacio);
        contentValues.put(DBStruct.fingreso,supervisores.FIngreso);
        contentValues.put(DBStruct.usuario,supervisores.User);
        contentValues.put(DBStruct.clave,supervisores.password);
        return database.insert(DBStruct.TABLE_NAME_SUPERVISOR, null, contentValues);
    }

    public void insertarRvoes(final JSONArray rvoeOb)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int x = 0; x < rvoeOb.length(); x++)
                {
                    try {
                        ContentValues value = new ContentValues();
                        JSONObject rvoe= rvoeOb.getJSONObject(x);
                        //Log.w("rvoe", rvoe.toString());
                        value.put(DBStruct.RVOES, rvoe.toString());
                        database.insert(DBStruct.TABLE_RVOES, null, value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    public void insertSupervisiones(ArrayList <ListInstituciones> listSupervision)
    {
        for(int x = 0; x < listSupervision.size(); x++)
        {
            ContentValues contentValues =new ContentValues();
            contentValues.put(DBStruct.idSupervision, listSupervision.get(x).getId());
            contentValues.put(DBStruct.colorL, listSupervision.get(x).getColor());
            contentValues.put(DBStruct.nombreIns, listSupervision.get(x).getNombre());
            contentValues.put(DBStruct.fecha, listSupervision.get(x).getFecha().getFechaDateSQL());
            contentValues.put(DBStruct.zona, listSupervision.get(x).getZona());
            contentValues.put(DBStruct.direccionI, listSupervision.get(x).getInformacion());
            contentValues.put(DBStruct.numrvoe, listSupervision.get(x).getNumRVOE());
            contentValues.put(DBStruct.status, listSupervision.get(x).getStatus());
            contentValues.put(DBStruct.IDINSTITUCION, listSupervision.get(x).getIdIntitucion());
            long i = database.insert(DBStruct.TABLE_Supervisiones, null, contentValues);
            Log.i("IDS", String.valueOf(i));
        }
    }


    public void insertSupervicionPendiente(SupervicionPendiente pendiente)
    {
        ContentValues values = new ContentValues();
        values.put(DBStruct.ROW_PLANTADOCENTE, pendiente.getPlantaDocente());
        values.put(DBStruct.ROW_PROGRAMARVOE, pendiente.getProgramaRvoe());
        values.put(DBStruct.ROW_REPRESENTANTES, pendiente.getRepresentantesSup());
        values.put(DBStruct.ROW_REVICIONAULA, pendiente.getRevicionAula());
        values.put(DBStruct.ROW_SUPERVICION, pendiente.getSupervicion());
        values.put(DBStruct.ROW_FECHA, pendiente.getFecha());
        values.put(DBStruct.ROW_ID_SUPERVICION, pendiente.getId());
        database.insert(DBStruct.TABLE_SUPERVICIONESPEN, null, values);
    }

    public ArrayList<SupervicionPendiente> DataSupervicionPendiente()
    {
        ArrayList<SupervicionPendiente> list = new ArrayList<>();
        String[] params = new String[]{
                DBStruct.ROW_PLANTADOCENTE,
                DBStruct.ROW_PROGRAMARVOE,
                DBStruct.ROW_REPRESENTANTES,
                DBStruct.ROW_REVICIONAULA,
                DBStruct.ROW_SUPERVICION,
                DBStruct.ROW_FECHA,
                DBStruct._ID
        };
        Cursor c = database.query(DBStruct.TABLE_SUPERVICIONESPEN, params, null, null, null, null, null);
        if(c.moveToFirst())
        {
            do {
                SupervicionPendiente item = new SupervicionPendiente();
                item.setPlantaDocente(c.getString(0));
                item.setProgramaRvoe(c.getString(1));
                item.setRepresentantesSup(c.getString(2));
                item.setRevicionAula(c.getString(3));
                item.setSupervicion(c.getString(4));
                item.setFecha(c.getString(5));
                item.setId(c.getInt(6));
                list.add(item);

            }while (c.moveToNext());
            return list;
        }
        else
        {
            return list;
        }
    }






    public ArrayList<ListInstituciones> DataSupervisiones() {
        Log.w("TO DATA", "DATA");
        int x = 0;
        Cursor c = database.query(DBStruct.TABLE_Supervisiones, seleccionSupervisiones, null, null, null, null, null);
        ArrayList<ListInstituciones> listSup = new ArrayList<>();
        if(c.moveToFirst())
        {
            do {

                ListInstituciones list = new ListInstituciones();
                list.setId(c.getInt(0));
                list.setNombre(c.getString(1));
                list.setZona(c.getString(2));
                list.setFecha(new Fecha(c.getString(3)));
                list.setColor(c.getInt(4));
                list.setNumRVOE(c.getInt(5));
                list.setInformacion(c.getString(6));
                list.setIdIntitucion(c.getInt(7));
                list.setStatus(c.getInt(8));
                listSup.add(x, list);
                x++;
            } while (c.moveToNext());
            return listSup;
        }
        else
        {
            return listSup;
        }
    }


    public ArrayList<Rvoe> consultRvoes(int idInstitucion)
    {
        int position = 0;
        String[] selection = new String[]{DBStruct._ID, DBStruct.RVOES};
        Cursor c = database.query(DBStruct.TABLE_RVOES, selection, null, null, null,null, null);
        ArrayList<Rvoe> list = new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                try {
                    Rvoe rvoe = new Rvoe();
                    JSONObject object = new JSONObject(c.getString(1));
                    if(object.getInt("idInstitucion") == idInstitucion)
                    {
                        Log.w("Nose", object.getString("nombreCarrera") + " " + idInstitucion);
                        rvoe.setNombrecarrera(object.getString("nombreCarrera"));
                        rvoe.setIdsupervision(0);
                        rvoe.setClave_rvoe(object.getString("clave"));
                        rvoe.setNumalumnos(0);
                        list.add(position, rvoe);
                        position++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }while (c.moveToNext());
            return list;
        }
        else
        {
            return list;
        }


    }


    public int updateSupervision(long id, int status)
    {
        ContentValues argument = new ContentValues();
        argument.put(DBStruct.STATUS_SUP, status);
        return database.update(DBStruct.TABLE_Supervisiones, argument, DBStruct.idSupervision + "=" + id, null);
    }

    public void openDatabase() {
        database=helper.getWritableDatabase();
    }

    public void closeDatabase()
    {
        database.close();
    }
}


//EL PEPI ES PUTO xD999999
