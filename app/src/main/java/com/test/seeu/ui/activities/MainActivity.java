package com.test.seeu.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.test.seeu.ui.adapters.MyViewPagerAdapter;
import com.test.seeu.R;
import com.test.seeu.ui.fragments.paintingfragment.PaintingFragment;
import com.test.seeu.ui.base.BaseActivity;
import com.test.seeu.ui.fragments.architecture.ArchitectureFragment;

import java.util.Arrays;

public class MainActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager containerLay;
    private MyViewPagerAdapter adapterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapterFragment = new MyViewPagerAdapter(getSupportFragmentManager(), Arrays.asList(new PaintingFragment(), new ArchitectureFragment()));
        containerLay = findViewById(R.id.containerLay);
        containerLay.setAdapter(adapterFragment);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(containerLay);
    }

//    public void outputInfo() {
//        Intent intent = new Intent(this.PaintingFragment, ActivityInfo.class);
//        startActivity(intent);
//    }
}