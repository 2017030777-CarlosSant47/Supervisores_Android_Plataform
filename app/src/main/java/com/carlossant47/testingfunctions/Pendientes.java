package com.carlossant47.testingfunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.carlossant47.testingfunctions.Clases.SupervicionPendiente;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.lucasurbas.listitemview.ListItemView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.conn.ConnectTimeoutException;

public class Pendientes extends AppCompatActivity {


    private ListView mListPendientes;
    private LinearLayout empy, list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendientes);
        empy = findViewById(R.id.empy);
        list = findViewById(R.id.list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mListPendientes = findViewById(R.id.listPendientes);
        consultSupervisiones();


    }


    private void consultSupervisiones()
    {
        DBSupervicion dbSupervicion = new DBSupervicion(getApplicationContext());
        dbSupervicion.openDatabase();
        if(dbSupervicion.DataSupervicionPendiente().size() > 0)
        {
            AdapterView itemsList = new AdapterView(getApplicationContext(), R.layout.list_upload_supervision, dbSupervicion.DataSupervicionPendiente());
            mListPendientes.setAdapter(itemsList);
            dbSupervicion.closeDatabase();
            empy.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }

    }


    private class AdapterView extends ArrayAdapter<SupervicionPendiente> {
        Context context;
        int layoutview;
        ArrayList<SupervicionPendiente> pendientes;


        public AdapterView(Context context, int resource, ArrayList<SupervicionPendiente> pendientes) {
            super(context, resource, pendientes);
            this.context = context;
            this.layoutview = resource;
            this.pendientes = pendientes;

        }

        public View getView(final int position, View conterView, ViewGroup viewGroup) {
            LayoutInflater layout = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") final View view = layout.inflate(this.layoutview, null);
            ListItemView item_pendiente = view.findViewById(R.id.item_pendiente);
            //item_pendiente.setSubtitle(pendientes.get(position).getFecha());
            item_pendiente.setTitle("Supervision");
            //item_pendiente.setOnClickListener(uploadSupervicion(pendientes.get(position)));
            item_pendiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        JSONObject jsonObject = new JSONObject(pendientes.get(position).getSupervicion());
                        Log.w("IDVERDADERO", String.valueOf(jsonObject.getInt("id_supervision")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("IDSUPERVISION", String.valueOf(pendientes.get(position).getId()));
                    if(!Permissions.isNettworkConnect(Pendientes.this))
                    {
                        Permissions.alertDialog(R.string.conexion_requerida, R.string.error, Pendientes.this);
                    }
                    else
                    {
                        uploadSupervicion(pendientes.get(position)).onClick(view);
                    }
                }
            });

            return view;
        }
    }


    private View.OnClickListener uploadSupervicion(final SupervicionPendiente supervicion) {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final ProgressDialog progress = new ProgressDialog(Pendientes.this);
                progress.setTitle("Subiendo Informacion");
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.setMessage("Subiendo datos de la supervision, por favor espere");
                progress.show();
                AsyncHttpClient client = new AsyncHttpClient();
                if(Permissions.isNettworkConnect(getApplicationContext()))
                {
                    client.post(Config.concatUrlConection(Permissions.UPLOADSUPERVISION, getApplicationContext()), supervicion.getParamsServer(), new TextHttpResponseHandler(){
                        @Override
                        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                            //Asyn
                            //Log.e("Error", responseString)
                            progress.hide();
                            if(!(throwable.getCause() instanceof ConnectTimeoutException)) {
                                Permissions.alertDialog("Tiempo Agotado de espera, verifique su conexion a internet", "Error", Pendientes.this);
                            }
                            else
                            {
                                Permissions.alertDialog("Error al subir la informacion, vuelva a intentarlo otra vez", "Error", getApplicationContext(), new Runnable() {
                                    @Override
                                    public void run() {
                                        //view.performClick();
                                    }
                                });
                            }


                        }
                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                            progress.hide();
                            for (int x = 0; x < responseString.length(); x++)
                            {
                                if(responseString.charAt(x) == '1') {
                                    Permissions.alertDialog("La supervision se a completado con exito", "Completado", Pendientes.this, new Runnable() {
                                        @Override
                                        public void run() {
                                            //DBSupervicion
                                            //consultSupervisiones();

                                        }
                                    });
                                }
                                else if(responseString.charAt(x) == '0')
                                {
                                    Permissions.alertDialog("Error al subir la informacion, vuelva a intentarlo otra vez", "Error", Pendientes.this, new Runnable() {
                                        @Override
                                        public void run() {
                                            //view.performClick();
                                        }
                                    });
                                }
                            }
                        }

                    });

                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
