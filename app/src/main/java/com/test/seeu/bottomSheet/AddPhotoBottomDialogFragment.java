package com.test.seeu.bottomSheet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentChange;
import com.test.seeu.R;
import com.test.seeu.camera.CameraActivity;
import com.test.seeu.data.FirebaseHelper;
import com.test.seeu.data.models.PaintingModel;

import java.util.ArrayList;

public class AddPhotoBottomDialogFragment extends BottomSheetDialogFragment {

    private ImageView imageInfo1, btnClose;
    private TextView nameInfo1, authorInfo1, mainInfo1;
    public static String KAY_INFO="KAY_INFO";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_photo_bottom_sheet, container,
                false);

        // get the views and attach the listener

        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        String key=bundle.getString(KAY_INFO);

        mainInfo1=view.findViewById(R.id.mainInfo);
        imageInfo1=view.findViewById(R.id.imageInfo);
        nameInfo1=view.findViewById(R.id.nameInfo);
        authorInfo1=view.findViewById(R.id.authorInfo);

        btnClose = view.findViewById(R.id.btnClose);

       btnClose.setOnClickListener(v -> {
            Intent goToCamera = new Intent(this.getContext(), CameraActivity.class);
            startActivity(goToCamera);
        });

        listenData(key);


    }

    private void listenData(String key) {
        FirebaseHelper.getInstance().getDataById("arts", key).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Firebase", "listen:error", error);
                return;
            }

            PaintingModel model=new PaintingModel();

            model.setAuthor(value.getString("author"));
            model.setMainInfo(value.getString("mainInfo"));
            model.setName(value.getString("name"));
            model.setPhoto(value.getString("photo"));

            mainInfo1.setText(model.getMainInfo());//доделать
            authorInfo1.setText(model.getAuthor());
            nameInfo1.setText(model.getName());

            FirebaseHelper.getInstance()
                    .getReference(model.getPhoto())
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> Glide.with(getContext())
                            .asBitmap()
                            .load(uri)
                            .into(imageInfo1))
                    .addOnFailureListener(e -> Log.e("Firebase storage:",e.getLocalizedMessage()));
        });
    }
}
