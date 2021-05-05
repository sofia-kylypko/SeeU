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
import com.test.seeu.data.FirebaseHelper;
import com.test.seeu.ui.base.BaseActivity;

public class ActivityInfo extends AppCompatActivity {

    ImageView imageInfo;
    TextView nameInfo, authorInfo, mainInfo;
    Button btnGallery, btnCamera;

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

//        btnCamera.setOnClickListener(view -> {
//            Intent intent = new Intent(this, CameraActivity.class);
//            startActivity(intent);
//        });
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
        btnCamera = findViewById(R.id.btnCamera);
    }
}