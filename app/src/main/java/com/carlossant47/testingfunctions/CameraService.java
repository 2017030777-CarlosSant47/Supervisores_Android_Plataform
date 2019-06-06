package com.carlossant47.testingfunctions;

import android.app.Service;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Carlos Santiago on 5/7/2018.
 */

public class CameraService extends Service
{

    static int x=0;
    //Camera variables
    //a surface holder
    private SurfaceHolder sHolder;
    //a variable to control the camera
    private Camera mCamera;
    //the camera parameters
    private Camera.Parameters parameters;
    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

        // ABRIR CAMARA
        mCamera = Camera.open(1);
        // SURFACEVIEW SE ENCARGA DE PROCESAR LA IMAGEN
        SurfaceView sv = new SurfaceView(getApplicationContext());


        try {
            // INDICAR A LA CAMARA QUE TIENE QUE VISUALIZAR EL LENTE EN EL SURFACEVIEW
            mCamera.setPreviewDisplay(sv.getHolder());

            SurfaceTexture st = new SurfaceTexture(MODE_PRIVATE);
            mCamera.setPreviewTexture(st);

            parameters = mCamera.getParameters();

            //set camera parameters
            mCamera.setParameters(parameters);
            mCamera.startPreview();

            // TOMAR FOTO
            mCamera.takePicture(null, null, mCall);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //Get a surface
        sHolder = sv.getHolder();
        //tells Android that this surface will have its data constantly replaced
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        return super.onStartCommand(intent, flags, startId);
    }

    Camera.PictureCallback mCall = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            //decode the data obtained by the camera into a Bitmap
            FileOutputStream outStream = null;
            try {
                x++;
                outStream = new FileOutputStream("/sdcard/" + Permissions.LSD2 + "/Image" + x + ".jpg");//LSD VAYA REFERENCIA XD
                outStream.write(data);
                outStream.close();
                mCamera.release();
                Log.w("La foto ", "Se Guardada");
            } catch (FileNotFoundException e) {
                Log.d("CAMERA", e.getMessage());
            } catch (IOException e) {
                Log.d("CAMERA", e.getMessage());
            }

        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}
