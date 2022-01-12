package com.test.seeu.camera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;
import com.test.seeu.R;
import com.test.seeu.bottomSheet.AddPhotoBottomDialogFragment;
import com.test.seeu.ui.activities.MainActivity;

import static com.test.seeu.bottomSheet.AddPhotoBottomDialogFragment.KAY_INFO;

public class CameraActivity extends AppCompatActivity {

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    private ImageView image;

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
        ImageView chose = findViewById(R.id.chose);
        chose.setOnClickListener(v -> hasCameraPermission());

        ImageView back3 = findViewById(R.id.back3);
        back3.setOnClickListener(v -> {
            Intent goToCamera = new Intent(this, MainActivity.class);
            startActivity(goToCamera);
            finish();
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
            assert data != null;
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            InputImage imageInput = InputImage.fromBitmap(imageBitmap, 90);
            image.setImageBitmap(imageBitmap);
            imageLabelingProcess(imageInput);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
    }

    private void imageLabelingProcess(InputImage image) {
        labeler.process(image)
                .addOnSuccessListener(labels -> {
                    times = 0;

                    for (ImageLabel label : labels) {
                        String responce = label.getText();
                        if ((times == 0)) {
                            if (responce.equals("Kiss")) {
                                if (label.getConfidence() >= 0.3) {
                                    otdelMet(label.getText());

                                    times++;
                                }
                            } else if (responce.equals("girlwithpeaches")) {
                                if (label.getConfidence() >= 0.5) {
                                    otdelMet(label.getText());

                                    times++;
                                } else {
                                    Toast toast = Toast.makeText(CameraActivity.this, "please, try to take better photo", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            } else {
                                if (label.getConfidence() >= 0.75) {

                                    otdelMet(label.getText());

                                } else {

                                    Toast toast = Toast.makeText(CameraActivity.this, "please, try to take better photo", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                times++;
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> {

                });
    }

    private void otdelMet(String key){
        AddPhotoBottomDialogFragment addPhotoBottomDialogFragment = new AddPhotoBottomDialogFragment(); //вытягивалка
        Bundle bundle = new Bundle();
        bundle.putString(KAY_INFO, key);
        addPhotoBottomDialogFragment.setArguments(bundle);
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),"add_photo_dialog_fragment");
    }
}