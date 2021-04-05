package com.test.seeu;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private Button think;
    private Button chose;
    private TextView txt1;
    private ImageView image;
    static final int REQUEST_IMAGE_CAPTURE = 1;
   private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        think = findViewById(R.id.mlbtn);
        think.setOnClickListener(v->{
            txt1.setText("");
            imageLabelingProcess();
        });
        chose = findViewById(R.id.chose);
        chose.setOnClickListener(v -> {
            takePictureIntent();


        });
        if (hasCameraPermission()) {
            enableCamera();
        } else {
            requestPermission();
        }
        txt1 = findViewById(R.id.txt1);

        image = findViewById(R.id.imageView);




    }
    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    private void enableCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
    }

    private void takePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }

    private void imageLabelingProcess(){                                                         // процесс преобразования изображения из Bitmap в inputImage  и процес маркировки изображения
        ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
        InputImage image = InputImage.fromBitmap(imageBitmap, 90);
        labeler.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> labels) {
                        for (ImageLabel label : labels) {
                           txt1.setText(txt1.getText() + " " +label.getText()+" "+label.getConfidence()+"   h");

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

}