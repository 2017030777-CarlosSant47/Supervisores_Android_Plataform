package com.carlossant47.testingfunctions;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public final class MainSupervision {

    static boolean exito = false;
    public MainSupervision()
    {

    }

    public static int lengthtxt(EditText txt)
    {
        return txt.getText().toString().length();
    }


    @NonNull
    public static String convertString(int i)
    {
        return String.valueOf(i);
    }

    public static int atoi(String s)
    {
        return Integer.parseInt(s);
    }


    public static int atoi(EditText s)
    {
        int r = 0;
        if(s.getText().toString().matches(""))
        {
            r = 0;
        }
        else
        {
            r = atoi(s.getText().toString());
        }
        return r;
    }

    public static int atoi(TextView txt)
    {
        return Integer.parseInt(txt.getText().toString());
    }


    public void printf(String s)
    {
        System.out.println(s);
    }

    public static boolean InsertVisita()
    {
        boolean con = false;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("ids", convertString(1));
        params.add("ide", convertString(284));
        params.add("nalum", convertString(300));
        params.add("nivel", convertString(1));
        params.add("idsup", convertString(2));

        client.post(Permissions.urlInertVisita, params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {

                Log.d("Server_resp", "INFORMATION IN SERVER");

            }
            public void onFailure(int statusCode,  cz.msebera.android.httpclient.Header[] headerses, String responseString, Throwable throwable) {
                Log.e("Error", String.valueOf(atoi(responseString)));
                if(atoi(responseString) == 1)
                {
                    exito =  true;
                }
                else {
                    exito = false;
                }
            }
        });
        con = exito;
        exito = false;
        return con;

    }


    public static String returnNameMonth(int month)
    {
        String m = "";
        switch (month) {
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






}
