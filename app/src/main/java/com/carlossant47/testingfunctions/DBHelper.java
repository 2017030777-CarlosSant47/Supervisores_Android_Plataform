package com.carlossant47.testingfunctions;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.ParcelUuid;

import org.w3c.dom.Text;

/**
 * Created by CarlosSant47 on 10/03/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private final static String DROP_TABLE_SUPERVISIONES = "DROP TABLE IF EXISTS " + DBStruct.TABLE_Supervisiones;
    private final static String DROP_TABLE_RVOES = "DROP TABLE IF EXISTS " + DBStruct.TABLE_RVOES;
    private final static String DROP_TABLE_SUPERVICIONPEN = "DROP TABLE IF EXISTS " + DBStruct.TABLE_SUPERVICIONESPEN;
    private final static String DATABASE_NAME = "supervisor.db";
    private final static int DATABASE_VERSION = 10;
    private final static String INTEGER_TYPE = " INTEGER", TEXT_TYPE = " TEXT", COMA = ", ";

    private final static String SQLCREATE_SUPERVISIONES = "CREATE TABLE " + DBStruct.TABLE_Supervisiones + "(" +
            DBStruct.idSupervision + " INTEGER PRIMARY KEY " +
            COMA + DBStruct.nombreIns + TEXT_TYPE +
            COMA + DBStruct.fecha + TEXT_TYPE +
            COMA + DBStruct.zona + TEXT_TYPE +
            COMA + DBStruct.IDINSTITUCION + INTEGER_TYPE +
            COMA + DBStruct.direccionI + TEXT_TYPE +
            COMA + DBStruct.numrvoe + INTEGER_TYPE +
            COMA + DBStruct.STATUS_SUP + INTEGER_TYPE +
            COMA + DBStruct.colorL + TEXT_TYPE + ");";

    private final static String SQLCREATE_RVOES = "CREATE TABLE " + DBStruct.TABLE_RVOES + "(" + DBStruct._ID + " INTEGER PRIMARY KEY " +
            COMA + DBStruct.RVOES + TEXT_TYPE + ");";

    public final static String SQLCREATE_SUPERVICIONESPEN = "CREATE TABLE " + DBStruct.TABLE_SUPERVICIONESPEN + "(" +
            DBStruct._ID + " INTEGER PRIMARY KEY " +
            COMA + DBStruct.ROW_SUPERVICION + TEXT_TYPE +
            COMA + DBStruct.ROW_PLANTADOCENTE + TEXT_TYPE +
            COMA + DBStruct.ROW_PROGRAMARVOE + TEXT_TYPE +
            COMA + DBStruct.ROW_REPRESENTANTES + TEXT_TYPE +
            COMA + DBStruct.ROW_REVICIONAULA + TEXT_TYPE +
            COMA + DBStruct.ROW_FECHA + TEXT_TYPE +
            COMA + DBStruct.ROW_ID_SUPERVICION + INTEGER_TYPE +
            COMA + DBStruct.STATUS_SUP + INTEGER_TYPE + ");";
    //FIN



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLCREATE_RVOES);
        db.execSQL(SQLCREATE_SUPERVISIONES);
        db.execSQL(SQLCREATE_SUPERVICIONESPEN);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {
        db.execSQL(DROP_TABLE_RVOES);
        db.execSQL(DROP_TABLE_SUPERVISIONES);
        db.execSQL(DROP_TABLE_SUPERVICIONPEN);
        onCreate(db);
    }
}

