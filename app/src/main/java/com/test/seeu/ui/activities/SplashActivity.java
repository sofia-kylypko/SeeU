package com.test.seeu.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.test.seeu.R;
import com.test.seeu.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    Animation top, bottom;
    ImageView logo;
    TextView logoName;
   public static  final int DELAY_IN_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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