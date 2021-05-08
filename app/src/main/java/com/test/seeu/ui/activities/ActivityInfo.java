package com.test.seeu.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.seeu.R;
import com.test.seeu.camera.CameraActivity;
import com.test.seeu.data.FirebaseHelper;

public class ActivityInfo extends AppCompatActivity {

    private ImageView imageInfo, btnGallery, txtCamera ;
    private TextView nameInfo, authorInfo, mainInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        init();
        getIntentMain();

        btnGallery.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        txtCamera.setOnClickListener(v -> {
            Intent goToCamera = new Intent(this, CameraActivity.class); //сомнения
            startActivity(goToCamera);
        });
    }

    private void getIntentMain() {

        Intent intent = getIntent();
        if(intent != null) {
            String image = intent.getStringExtra("image");
            String name = intent.getStringExtra("name");
            String info = intent.getStringExtra("info");
            String author = intent.getStringExtra("author");

            FirebaseHelper.getInstance()
                    .getReference(image)
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> Glide.with(imageInfo.getContext())
                            .asBitmap()
                            .load(uri)
                            .into(imageInfo))
                    .addOnFailureListener(e -> Log.e("Firebase storage:",e.getLocalizedMessage()));

            nameInfo.setText(name);
            authorInfo.setText(author);
            mainInfo.setText(info);
        }
    }

    private void init() {
        imageInfo = findViewById(R.id.imageInfo);
        nameInfo = findViewById(R.id.nameInfo);
        authorInfo = findViewById(R.id.authorInfo);
        mainInfo = findViewById(R.id.mainInfo);

        btnGallery = findViewById(R.id.btnGallery);
        txtCamera = findViewById(R.id.txtCamera);
    }
}