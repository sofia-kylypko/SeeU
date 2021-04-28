package com.test.seeu.ui.fragments.paintingfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.test.seeu.R;
import com.test.seeu.camera.CameraActivity;
import com.test.seeu.data.FirebaseHelper;
import com.test.seeu.data.models.PaintingModel;
import com.test.seeu.ui.adapters.RecyclerPaintingAdapter;
import com.test.seeu.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class PaintingFragment extends BaseFragment {

    private ArrayList<PaintingModel> paintingList;
    private SearchView searchView;
    private Button btnCameraPaint;

    RecyclerView recyclerView;
    RecyclerPaintingAdapter adapterRv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_painting, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCameraPaint = view.findViewById(R.id.btnCameraArch);
        btnCameraPaint.setOnClickListener(v -> {
            Intent goToCamera = new Intent(getContext(), CameraActivity.class);
            startActivity(goToCamera);
        });

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterRv.setPaintingList(filter(newText));
                return false;
            }
        });

        recyclerView = view.findViewById(R.id.PaintingRecyclerView);
        initRecyclerView();

    }

    private void listenData() {
        FirebaseHelper.getInstance().getData("arts").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Firebase", "listen:error", error);
                return;
            }
            ArrayList<PaintingModel> tmp = new ArrayList();
            for (DocumentChange dc : value.getDocumentChanges()) {
                tmp.add(dc.getDocument().toObject(PaintingModel.class));
            }
            paintingList = tmp;
            adapterRv.setPaintingList(paintingList);
        });
    }

    private void initRecyclerView() {
        adapterRv = new RecyclerPaintingAdapter();
        recyclerView.setAdapter(adapterRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listenData();
    }


    private List<PaintingModel> filter(String query) {
        ArrayList<PaintingModel> temp = new ArrayList<>();
        for (PaintingModel model : paintingList) {
            if (model.getName().toLowerCase().contains(query.toLowerCase())) {
                temp.add(model);
            }
        }
        return temp;
    }
}
