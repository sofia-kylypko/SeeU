package com.test.seeu.ui.activities;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyViewPagerAdapter adapterFragment = new MyViewPagerAdapter(getSupportFragmentManager(), Arrays.asList(new PaintingFragment(), new ArchitectureFragment()));
        ViewPager containerLay = findViewById(R.id.containerLay);
        containerLay.setAdapter(adapterFragment);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(containerLay);
    }
}