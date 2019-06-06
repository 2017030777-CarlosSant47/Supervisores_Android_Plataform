package com.carlossant47.testingfunctions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Camera_Photo extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    private int count = 0;
    int numPhoto = 6;
    private String[] nameFiles = new String[numPhoto];
    private Camera mCamera;
    TextView lbcantphotos;
    private CameraPreview mPreview;
    private PictureCallback mPicture;
    private FloatingActionButton switchCamera;
    private FloatingActionButton capture;
    private Context myContext;
    private RelativeLayout cameraPreview;
    private boolean cameraFront = false;
    AlertDialog dialog;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera__photo);
        drawer = findViewById(R.id.drCamera);

        NavigationView navigationView = findViewById(R.id.drLista);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//PARA QUE LA ORIENTACION SIMPRE SEA HORIZONTAL
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //QUE LA PANTALLA QUEDE ENCENDIDA
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lbcantphotos = findViewById(R.id.lbcantphotos);
        lbcantphotos.setText("Fotos " + String.valueOf(count) + " de " + String.valueOf(numPhoto));

        //PERMITE QUE LA APLICACION SEA PANTALLA COMPLETA
        myContext = this;
        initialize();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(count < numPhoto)
            {
                Log.w("Te faltan fotos", "NULL");
            }
            else
            {
                Intent i = new Intent();

                i.putExtra(Permissions.datafiles, nameFiles);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();
        }
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;
            }
        }
        return cameraId;
    }

    public void onResume() {
        super.onResume();
        if (!hasCamera(myContext)) {
            Toast toast = Toast.makeText(myContext, "Sorry, your phone does not have a camera!", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        if (mCamera == null) {
            //if the front facing camera does not exist
            if (findFrontFacingCamera() < 0) {
                Toast.makeText(this, "No front facing camera found.", Toast.LENGTH_LONG).show();
                switchCamera.setVisibility(View.GONE);
            }
            mCamera = Camera.open(findBackFacingCamera());
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
        }
    }

    public void initialize() {
        cameraPreview = findViewById(R.id.camera_preview);
        mPreview = new CameraPreview(myContext, mCamera);
        cameraPreview.addView(mPreview);

        capture = findViewById(R.id.button_capture);
        capture.setOnClickListener(captrureListener);

        switchCamera = findViewById(R.id.button_ChangeCamera);
        switchCamera.setOnClickListener(switchCameraListener);
    }

    OnClickListener switchCameraListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int camerasNumber = Camera.getNumberOfCameras();
            if (camerasNumber > 1) {
                releaseCamera();
                chooseCamera();
            } else {
                Toast toast = Toast.makeText(myContext, "Sorry, your phone has only one camera!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    };
    //NO TOKAR :V
    public void chooseCamera() {
        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                mCamera = Camera.open(cameraId);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                mCamera = Camera.open(cameraId);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }
    private PictureCallback getPictureCallback() {
        PictureCallback picture = new PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                File pictureFile = getOutputMediaFile();

                if (pictureFile == null) {
                    return;
                }
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    MassageImgPreview(pictureFile.getAbsolutePath(), pictureFile.getName());

                } catch (IOException ignored) {
                }
                mPreview.refreshCamera(mCamera);
            }
        };
        return picture;
    }

    OnClickListener captrureListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if(count < numPhoto)
            {
                mCamera.takePicture(null, null, mPicture);
            }
            else
            {
                Log.w("No", "Photos");
            }

        }
    };
    @SuppressLint("SimpleDateFormat")
    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + Permissions.LSD2);///LSD2 NO ES UNA REFERENCIA
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
         String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    public void MassageImgPreview(final String filePath, final String namefile) {

        AlertDialog.Builder promptFolios = new AlertDialog.Builder(Camera_Photo.this);
        View Mv = getLayoutInflater().inflate(R.layout.previephoto_layout, null);
        Button btndelete = Mv.findViewById(R.id.btndelete), btnSave = Mv.findViewById(R.id.btnsavephoto);
        btndelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                File delete = new File(filePath);
                if(delete.delete())
                {
                    Log.w("Eliminado", filePath);
                }
                else
                {
                    Log.w("No Eliminado", filePath);
                }
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nameFiles[count] = namefile;
                count++;
                lbcantphotos.setText("Fotos " + String.valueOf(count) + " de " + String.valueOf(numPhoto));
                //Toast toast = Toast.makeText(myContext, "Picture saved: " + namefile, Toast.LENGTH_LONG);
                //toast.show();
                dialog.dismiss();
            }
        });
        final ImageView iVview = Mv.findViewById(R.id.iVview);
        iVview.setImageURI(Uri.parse(filePath));
        promptFolios.setView(Mv);
        dialog = promptFolios.create();
        dialog.setCancelable(false);
        dialog.show();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Camera.Parameters parameters = mCamera.getParameters();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.w("Cambio", "Horizonal");
            parameters.set("orientation", "landscape");
            mCamera.setParameters(parameters);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.w("Cambio", "Vertical");
            parameters.set("orientation", "portrait");
            mCamera.setParameters(parameters);
        }
    }
}
