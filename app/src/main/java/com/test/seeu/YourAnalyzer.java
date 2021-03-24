package com.test.seeu;

import android.annotation.SuppressLint;
import android.media.Image;

import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.vision.common.InputImage;

 class YourAnalyzer implements ImageAnalysis.Analyzer {

    @Override
    public void analyze(ImageProxy imageProxy) {
       @SuppressLint("UnsafeExperimentalUsageError")
       Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image =
                  InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());

                    }
    }
}
