package com.carlossant47.testingfunctions;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carlossant47.testingfunctions.Clases.Rvoe;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {


    //PROGRAMING CARLOS SANTIAGO (CARLOSSANT47)
    ProgressBar prFile;
    Button btnDownload;
    TextView lbVersion, lbNotas;
    boolean download = false;
    static ArrayList<Rvoe> rvoeList = new ArrayList<>();
    final String FOLDERNAMEFILE = Environment.getExternalStorageDirectory()+Permissions.LSD2 + "/supervisor.apk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().hide();
        prFile = findViewById(R.id.prFile);

        lbNotas = findViewById(R.id.lbNotas);
        lbVersion = findViewById(R.id.lbVersion);
        btnDownload = findViewById(R.id.btnDownload);
        Intent i = new Intent();
        final Version version = new Version((Version) getIntent().getSerializableExtra("versionApk"));
        //getResources().getRaw()
        lbVersion.setText("Numero de version " + version.getVersion());
        lbNotas.setText(version.getNotas());
        Permissions.readFile(R.raw.estilo, UpdateActivity.this);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (download) {
                    Permissions.installApk(new File(FOLDERNAMEFILE), UpdateActivity.this);
                } else {
                    final DownloadTask downloadTask = new DownloadTask(UpdateActivity.this);
                    File file = new File(FOLDERNAMEFILE);
                    final PackageManager pm = getPackageManager();
                    PackageInfo info = pm.getPackageArchiveInfo(FOLDERNAMEFILE, 0);

                    if (file.exists()) {
                        file.delete();
                    }
                    downloadApk(version);
                    //downloadTask.execute(version.getFile());
                }
            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        private PowerManager.WakeLock mWakeLock;
        Context context;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;

            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();
                input = connection.getInputStream();
                output = new FileOutputStream(FOLDERNAMEFILE);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            //mWakeLock.acquire();
            //mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            prFile.setIndeterminate(false);
            prFile.setMax(100);
            prFile.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null)
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            else
            {
                btnDownload.setText("Instalar");
                download = true;
            }


        }
    }



    public static class Version implements Serializable
    {
        private float version;
        private String notas;
        private String file;

        public Version()
        {
            this.version = 1.0f;
            this.file = "supervisor.apk";
            this.notas = "vacio";
        }

        public Version(Version version)
        {
            this.version = version.version;
            this.file = version.file;
            this.notas = version.notas;
        }

        public float getVersion() {
            return version;
        }

        public void setVersion(float version) {
            this.version = version;
        }

        public String getNotas() {
            return notas;
        }

        public void setNotas(String notas) {
            this.notas = notas;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }

    public void downloadApk(Version version)
    {
        DownloadManager.Request download = new DownloadManager.Request((Uri.parse(version.getFile())));
        download.setDescription("Actualizacion de " + getResources().getString(R.string.app_name) + version.getVersion());
        download.setTitle("Descargando Actualizacion");
        download.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        download.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                "supervisor" + version.getVersion() + ".apk");
        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        final long downloadId = manager.enqueue(download);
        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean downloading = true;
                while (downloading) {
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);
                    Cursor cursor = manager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                    }
                    final double dl_progress = (bytes_downloaded / bytes_total) * 1024;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            prFile.setProgress((int) dl_progress);
                        }
                    });
                    //Log.d(, statusMessage(cursor));
                    cursor.close();
                }

            }
        }).start();

    }



}
