package com.test.seeu.camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;
import com.test.seeu.R;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    private Button chose;
    private String txt1;
    private ImageView image;
    private InputImage imageInput;
    private Bitmap imageBitmap;


    private int times = 0;
    LocalModel localModel =
            new LocalModel.Builder()
                    .setAssetFilePath("model-export_icn_tflite-paint-2021-04-22T07_29_35.839241Z_model.tflite")
                    .build();
    CustomImageLabelerOptions customImageLabelerOptions = new CustomImageLabelerOptions.Builder(localModel)
            .setConfidenceThreshold(0.01f)
            .build();
    ImageLabeler labeler = ImageLabeling.getClient(customImageLabelerOptions);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        image = findViewById(R.id.imageView);
        hasCameraPermission();
        chose = findViewById(R.id.chose);
        chose.setOnClickListener(v -> {
            hasCameraPermission();
        });

    }

    private void hasCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            enableCamera();

        } else {
            requestPermission();
        }
    }

    private void enableCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            txt1 = "";
            imageBitmap = (Bitmap) data.getExtras().get("data");
            imageInput = InputImage.fromBitmap(imageBitmap, 90);
            image.setImageBitmap(imageBitmap);
            imageLabelingProcess();

        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
    }


    private void imageLabelingProcess() {
        labeler.process(imageInput)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> labels) {
                        times = 0;
                        Intent intent = new Intent(CameraActivity.this, Responce.class);
                        for (ImageLabel label : labels) {
                            String responce = label.getText();
                            if ((times == 0)) {
                                if(responce.equals("Kiss")) {
                                    if(label.getConfidence() >= 0.3) {
                                        txt1 = label.getText() + label.getConfidence();
                                        intent.putExtra("responce", txt1);
                                        times++;
                                    }
                                } else if(responce.equals("girlwithpeaches")){
                                    if(label.getConfidence() >= 0.5){
                                        txt1 = label.getText() + label.getConfidence();
                                        intent.putExtra("responce", txt1);
                                        times++;
                                    }else{
                                        Toast toast = Toast.makeText(CameraActivity.this, "please, try to take better photo", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }else{
                                    if(label.getConfidence() >= 0.75){
                                        txt1 = label.getText() + label.getConfidence();
                                        intent.putExtra("responce", txt1);
                                        times++;
                                    }else{

                                        Toast toast = Toast.makeText(CameraActivity.this, "please, try to take better photo", Toast.LENGTH_SHORT);
                                        toast.show();
                                        times++;
                                    }
                                }
                            }
                        }
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        txt1 = "fail";
                    }
                });

    }


}