package com.test.seeu.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.test.seeu.R;
import com.test.seeu.ui.fragments.paintingfragment.PaintingFragment;

public class Responce extends AppCompatActivity {
    private TextView text;
    private Button toCamera;
    private Button toPainting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responce);
        text = findViewById(R.id.responce);
        text.setText("most likely it is " + getIntent().getStringExtra("responce"));
        toCamera = findViewById(R.id.toCamera);
        toCamera.setOnClickListener(v->{
            Intent intent = new Intent(Responce.this, CameraActivity.class);
            startActivity(intent);
        });
        toPainting = findViewById(R.id.toPaint);
        toPainting.setOnClickListener(v->{
            Intent intent2 = new Intent(Responce.this, PaintingFragment.class);
            startActivity(intent2);
        });
    }
}