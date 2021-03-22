package com.test.seeu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    Animation top, bottom;
    ImageView logo;
    TextView logoName;
   public static  final int DELAY_IN_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);


        logo = findViewById(R.id.logo);
        logoName = findViewById(R.id.logoName);


        logo.setAnimation(top);
        logoName.setAnimation(bottom);



        new Handler().postDelayed(() -> {
                startActivity (new Intent (SplashActivity.this, MainActivity.class));
            finish();
        }, DELAY_IN_MILLIS);
    }
}