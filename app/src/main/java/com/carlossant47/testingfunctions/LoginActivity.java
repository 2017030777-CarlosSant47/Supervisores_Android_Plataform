package com.carlossant47.testingfunctions;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.carlossant47.testingfunctions.MainSupervision.*;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;

import static com.carlossant47.testingfunctions.MainSupervision.atoi;




public class LoginActivity extends AppCompatActivity {

    AppCompatButton btnlogin;
    ProgressDialog dialog;
    EditText txtpassword, txtuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        getSupportActionBar().hide();
        btnlogin = findViewById(R.id.btnlogin);
        txtpassword = findViewById(R.id.txtpassword);
        txtuser = findViewById(R.id.txtuser);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValiteText())
                {
                    LoginFunction(txtuser.getText().toString(), txtpassword.getText().toString());
                }
                else
                {
                    Permissions.snakBarMessage(v, Permissions.getString(R.string.camposVacios,
                            getApplicationContext()), Snackbar.LENGTH_SHORT);

                }
            }
        });


        txtpassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)
                {
                    LoginFunction(txtuser.getText().toString(), txtpassword.getText().toString());
                }
                return false;
            }
        });


    }

    private boolean ValiteText()
    {
        if(txtpassword.getText().toString().length() > 0 && txtuser.getText().toString().length() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    private void LoginFunction(final String user, String password) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("iduser", user);
        params.add("contra", password);

        if(Permissions.isNettworkConnect(getApplicationContext()))
        {
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setTitle(R.string.iniciando_sesion);

            dialog.setMessage(getResources().getString(R.string.conectando_server));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
            client.post(Config.concatUrlConection(Permissions.urlLogin, getApplicationContext()), params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray responseArray) {
                    int i = 0;
                    try {
                        JSONObject data = responseArray.getJSONObject(i);
                        Config config = new Config(LoginActivity.this);
                        config.setNombre(data.getString(Permissions.Nombre));
                        config.setSession(true);
                        config.setIdSupervisor(data.getInt("IDSUP"));
                        config.setEmail(user);
                        dialog.hide();
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode,  Header[] headerses, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headerses, responseString, throwable);
                    if (atoi(responseString) == 0) {
                        Permissions.alertDialog(R.string.contrasenaIncorrecta, R.string.error, LoginActivity.this);

                    } else {
                        Permissions.alertDialog(R.string.errorSesion, R.string.error, LoginActivity.this);
                    }


                    dialog.hide();
                }

                @Override
                public void onFailure(int status, Header[] headers, Throwable throwable, JSONObject jsonObject)
                {
                    if(!(throwable.getCause() instanceof ConnectTimeoutException))
                    {
                        Permissions.alertDialog("Tiempo Agotado de espera, verifique su conexion a internet", "Error", LoginActivity.this);
                        dialog.hide();
                    }
                    Log.e("MESSAGE", throwable.getMessage());
                }


            });
        }
        else
        {
            alertDialog("Error", "Se requiere conexion a internet");
        }
    }

    public void alertDialog(String title, String msg) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialogBuilder.show();
    }
}
