package com.carlossant47.testingfunctions;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.carlossant47.testingfunctions.Clases.SupervicionPendiente;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;


public class Permissions {

    public Permissions(){

    }
    public final static String PDFFILE = "pdf1pro.pdf";
    public final static String fileconfig = "config";
    //EL NOMBRE DE LA CARPETA DONDE SE GUARDARAN LAS FOTOS
    public final static String LSD2 = "/SEPSupervisor";
    public final static String datafiles = "files";
    private final static String urlBase = "http://examplesandroid.000webhostapp.com/Supervisor_App/index.php/";
    public final static String urlDataSupervision = "ConData/DataSupervisiones";
    public final static String urlInertVisita = "ConData/InsertVisita";
    public final static String urlRVOES = "ConData/OnConsultRVOE";
    public final static String urlSaveRepresentantes = "ConSupervision/saveRepresentantes";
    public final static String urlLogin = "ConData/OnSessionData";
    public final static String urlGetVersion = "ConData/consultVersion";
    public final static String statusData = "statusData";
    public final static String dataSupervisor = "ConData/OnConsultSupervisor";
    public final static String sesion = "sesion";
    public final static String Nombre = "Nombre";
    public final static String email = "email";
    public final static String UPLOADSUPERVISION ="ConSupervision/saveSupervision";
    //DECUELVE SI HAY PERMISOS DE ESCRIBIR Y LEER EN LA MEMORIA
    public static boolean chekStoragePermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    //DEVUELVE SI HAY PERMISOS EN LA CAMERA
    public static boolean checkCameraPermission(Context context){
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

    }
    //CREA LA CARPETA DONDE SE ALOJARAN LAS FOTOGRAFIAS O CONETENIDO
    public static boolean CrearCarpeta(Context context){
        Boolean exito = false;
        if(chekStoragePermission(context)){//VUELVE A REVISAR LOS PERMISOS POR SI LAS MOSCAS
            File folderapp = new File(Environment.getExternalStorageDirectory()+LSD2);
            if(!folderapp.exists()){
                if(folderapp.mkdir()){
                    Log.w("La carpeta de creo ", folderapp.getAbsolutePath());
                    exito = true;
                }
            }else{
                Log.w("La carpeta ya existe ", folderapp.getAbsolutePath());
                exito = true;
            }
        }
        else{
            Log.w("No se tienen Permisos", "PERMISO DENEGADO");
            exito = false;

        }
        return exito;
    }




    //PERMITE SABER SI ESTA CONECTADO A UNA RED WIFI MAS NO SI SE PUEDE CONECTAR AL SERVIDOR :3
    public static final boolean isNettworkConnect(Context context) {
        ConnectivityManager conexion = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (conexion != null) {
            NetworkInfo[] info = conexion.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo aninfo : info) {
                    if (aninfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }




    public static class URLDEFINIIONSSERVER
    {
        public URLDEFINIIONSSERVER()
        {

        }
        //URLS DONDE SERAN SUBIDAD AL SERVIDOR TODOS LOS DATOS ASI COMO LA DESCARGA DE OTROS MAS

        String NombreEscuela = "Nombre", IDINSTITITUCION = "IDINSTI", Zona = "Zona", fechaSupernvision = "Fecha", id = "ID", Direccion = "Direccion", RVOE = "RVOE";

    }

    public static void toastMessage(String message, Context myContext)
    {
        Toast.makeText(myContext, message, Toast.LENGTH_LONG).show();
    }


    public static void toastMessage(int message, Context context)
    {
        Toast.makeText(context, context.getResources().getString(message), Toast.LENGTH_LONG).show();
    }


    public static int returnRadioButtonPosition(RadioGroup radioGroup)
    {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);
        return idx;
    }


    public static boolean alertDialog(int message, int title, Context context)
    {

        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(getString(title, context));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(getString(message, context));

        alertDialogBuilder.setPositiveButton(getString(R.string.accept, context), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return true;

    }
    public static boolean alertDialog(String message, String title, Context context)
    {

        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(getString(R.string.accept, context), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return true;
    }

    public static boolean alertDialog(String message, String title, Context context, final Runnable runnable)
    {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(getString(R.string.accept, context), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                runnable.run();
            }
        });
        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return true;

    }


    public static boolean alertDialogDesision(int message, int title, Context context, final Runnable actionYes)
    {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(getString(title, context));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(getString(message, context));

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel, context), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialogBuilder.setPositiveButton(getString(R.string.accept, context), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actionYes.run();
            }
        });
        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return true;

    }

    public static boolean alertDialogDesision(String message, String title, Context context, final Runnable actionYes)
    {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel, context), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialogBuilder.setPositiveButton(getString(R.string.accept, context), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actionYes.run();
            }
        });
        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return true;

    }

    public static boolean alertDialogDesision(String message, String title, Context context, final Runnable actionYes, final Runnable actionNo)
    {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel, context), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actionNo.run();
            }
        });

        alertDialogBuilder.setPositiveButton(getString(R.string.accept, context), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actionYes.run();
            }
        });
        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return true;

    }



    public static void snakBarMessage(View view, String text, int duration)
    {
        Snackbar.make(view, text, duration).setAction("Action", null).show();
    }

    public static String getString(int id, Context context)
    {
        return context.getResources().getString(id);
    }


    public static boolean sendDataServerString(RequestParams params, String url, Context context)
    {

        final boolean[] exito = {false};
        AsyncHttpClient client = new AsyncHttpClient();
        if(isNettworkConnect(context))
        {
            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    exito[0] = false;
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    exito[0] = true;
                }
            });
        }

        return exito[0];

    }

    public static void installApk(File file, Context context) {
        try {
            if (file.exists()) {
                String[] fileNameArray = file.getName().split(Pattern.quote("."));
                if (fileNameArray[fileNameArray.length - 1].equals("apk")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);

                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file),
                                "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String readFile(int rawFile,  Context context){
        StringBuilder file = new StringBuilder("");
        InputStream inputStream = context.getResources().openRawResource(rawFile);
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            file.append(line).append("\n");
        }
        scanner.close();
        return file.toString();
    }

    public static byte[] convertStreamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[10240];
        int i = Integer.MAX_VALUE;
        while ((i = is.read(buff, 0, buff.length)) > 0) {
            baos.write(buff, 0, i);
        }

        return baos.toByteArray(); // be sure to close InputStream in calling function
    }

    private static Uri getFileUri(Context context, File file) {
        return FileProvider.getUriForFile(context,
                context.getApplicationContext().getPackageName() + ".HelperClasses.GenericFileProvider"
                , file);
    }


    public static ArrayAdapter<String> selectPlan(int stringId, Context context)
    {
        String pal = context.getResources().getString(stringId);
        String itemst[] = new String[]{pal + " 1", pal + " 2", pal + " 3", pal + " 4", pal + " 5", pal + " 6", pal + " 7", pal + " 8", pal + " 9", pal + " 10", pal + " 11", pal + " 12", pal + " 13", pal + " 14", pal + " 15", pal + " 16"};
        return new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, itemst);
    }


}
