package com.carlossant47.testingfunctions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeoutException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;

import static com.carlossant47.testingfunctions.MainSupervision.atoi;

public class MainActivity extends AppCompatActivity {
    final int TIME_SPALSH = 3000;
    ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        progressbar = findViewById(R.id.progressbar);

        progressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), settings.class);
                startActivity(i);
            }
        });

        if(Permissions.isNettworkConnect(getApplicationContext()))
        {
            consultVersion();
        }
        else
        {
            openActivity();
        }
    }

    private void openActivity()
    {
        if (Config.getSession(MainActivity.this)) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }

                ;
            }, TIME_SPALSH);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                ;
            }, TIME_SPALSH);
        }
    }

    private void consultVersion()
    {
        //progressbar.setIndeterminate(false);
        final AsyncHttpClient client = new AsyncHttpClient();
        //client.setTimeout(2000);
        RequestParams params = new RequestParams();
        String version = "";
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        params.add("version", version);
        client.post(Config.concatUrlConection(Permissions.urlGetVersion, getApplicationContext()), params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject responseArray) {
                try {
                        if (responseArray.getBoolean("version")) {
                            UpdateActivity.Version version = new UpdateActivity.Version();
                            version.setVersion(Float.parseFloat(responseArray.getString("numv")));
                            version.setNotas(responseArray.getString("notas"));
                            version.setFile(responseArray.getString("apk"));
                            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                            intent.putExtra("versionApk", version);
                            startActivity(intent);
                            finish();
                        } else {
                            openActivity();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                    finish();
                }
            }
            /*
            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                progressbar.setText((long) 100 * bytesWritten / totalSize + "Loading...");
                progressbar.setProgress( 100 * new Integer((int) bytesWritten) / totalSize);

            }
            */

            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headerses, Throwable throwable, JSONObject jsonObject) {
                super.onFailure(statusCode, headerses, throwable, jsonObject);
                Log.e("Error", throwable.getMessage());
                if (throwable.getCause() instanceof ConnectTimeoutException)
                {
                    Log.e("HTTP CLIENT","Connection timeout !");
                }

                openActivity();
            }

        });
    }





}
