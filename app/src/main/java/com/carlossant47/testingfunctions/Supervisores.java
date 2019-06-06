package com.carlossant47.testingfunctions;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.MessageFormat;

public class Supervisores extends AppCompatActivity {

    EditText txtAPaterno,txtAMateno,txtPNom,txtSNom,txtINE,txtCURP,txtRFC,txtDirec,txtColonia,txtTel,txtCel,txtFNacio,
            txtFIngreso,txtUser,txtpassword;
    FloatingActionButton btnNext;
    Supervisores2 supervisor = new Supervisores2();
    DBSupervicion dbsupervicion = new DBSupervicion(Supervisores.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisores);
        if(!Permissions.isNettworkConnect(getApplicationContext())){
            alertDialog(getResources().getString(R.string.Advertencia), getResources().getString(R.string.sConexion));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtAMateno= findViewById(R.id.txtAMateno);
        txtAPaterno= findViewById(R.id.txtAPaterno);
        txtPNom= findViewById(R.id.txtPNom);
        txtSNom= findViewById(R.id.txtSNom);
        txtINE= findViewById(R.id.txtINE);
        txtCURP= findViewById(R.id.txtCURP);
        txtRFC= findViewById(R.id.txtRFC);
        txtDirec= findViewById(R.id.txtDirec);
        txtColonia= findViewById(R.id.txtColonia);
        txtTel= findViewById(R.id.txtTel);
        txtCel= findViewById(R.id.txtCel);
        txtFNacio= findViewById(R.id.txtFNacio);
        txtFIngreso= findViewById(R.id.txtFIngreso);
        txtUser= findViewById(R.id.txtUser);
        txtpassword= findViewById(R.id.txtpassword);

        btnNext= findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supervisor.Amaterno = txtAMateno.getText().toString();
                supervisor.APaterno = txtAPaterno.getText().toString();
                supervisor.PNom = txtPNom.getText().toString();
                supervisor.SNom = txtSNom.getText().toString();
                supervisor.INE = txtINE.getText().toString();
                supervisor.CURP = txtCURP.getText().toString();
                supervisor.RFC = txtRFC.getText().toString();
                supervisor.Direc = txtDirec.getText().toString();
                supervisor.Colonia = txtColonia.getText().toString();
                supervisor.Tel = txtTel.getText().toString();
                supervisor.Cel = txtCel.getText().toString();
                supervisor.FNacio = txtFNacio.getText().toString();
                supervisor.FIngreso = txtFIngreso.getText().toString();
                supervisor.User = txtUser.getText().toString();
                supervisor.password = txtpassword.getText().toString();
                dbsupervicion.openDatabase();
                dbsupervicion.insertearSupervisor(supervisor);
                dbsupervicion.closeDatabase();

                new AlertDialog.Builder(Supervisores.this)
                        //.setIcon(getResources().getDrawable(R.drawable.msgicon))
                        .setTitle("Exito")
                        .setMessage("Se a guardado correctamente")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
            }
        });
    }//////////////////
    public void alertDialog(String title, String msg){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.show();
    }



    public class Supervisores2
    {
        public String Amaterno, APaterno, PNom,SNom, INE, CURP, RFC, Direc, Colonia, Tel, Cel, FNacio, FIngreso, User, password;
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
