package com.test.seeu;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Camera extends AppCompatActivity {
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private Activity context;

    public Camera(Activity context){
        this.context = context;
    }

    public void checkPermissionCamera() {
        if (hasCameraPermission(context)) {
            //enableCamera(context);
        } else {
            requestPermission(context);
        }
    }

    private void requestPermission(Activity context) {
        ActivityCompat.requestPermissions(context, CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
    }

   // private void enableCamera(Activity context) {
       // Intent intent = new Intent(context, CameraActivity.class);
      //  startActivity(intent);

    //}

    private boolean hasCameraPermission(Activity context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
}
