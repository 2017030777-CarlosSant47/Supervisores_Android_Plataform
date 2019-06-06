package com.carlossant47.testingfunctions;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class settings extends AppCompatActivity {
    public static String action = "action", isntitucion = "institucion";
        int touch1 = 0, touch2;
        TextView lbversion;
    LinearLayout btnCamera, btnUrlconfig, btnAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lbversion = findViewById(R.id.lbversion);
        btnCamera = findViewById(R.id.btnCamera);
        btnUrlconfig = findViewById(R.id.btnURlCondifg);
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            lbversion.setText(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        btnUrlconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(settings.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.input_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(settings.this);
                alertDialogBuilderUserInput.setView(mView);

                final EditText txturl = (EditText) mView.findViewById(R.id.txtpromp);
                txturl.setHint("http://");
                txturl.setText(Config.getUrlConecrion(getApplicationContext()));
                alertDialogBuilderUserInput
                        .setTitle("URL de conexion")
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // PEPI PUTO xD
                                Config.setUrlConnection(txturl.getText().toString(), getApplicationContext());
                                //++Log.w("URL", txturl.getText().toString() + "/Supervisor_App/index.php/");

                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }
        });
        btnAbout = findViewById(R.id.btnAbout);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(settings.this, Camera_Photo.class);
                int actions = 0;
                i.putExtra(action, actions);
                startActivity(i);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touch1++;
                if(touch1 == 6)
                {
                    Intent i = new Intent(settings.this, About.class);
                    int actions = 0;
                    i.putExtra(action, actions);
                    startActivity(i);
                    touch1 = 0;
                }

            }
        });
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
