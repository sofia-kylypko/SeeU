package com.test.seeu.ui.fragments.architecture;

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
import com.test.seeu.data.FirebaseHelper;
import com.test.seeu.data.models.ArchitectureModel;
import com.test.seeu.ui.adapters.RecyclerArchitectureAdapter;
import com.test.seeu.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ArchitectureFragment extends BaseFragment {
    private ArrayList<ArchitectureModel> architectureList;
    private SearchView searchView2;
    private Button btnCameraArch;

    RecyclerView recyclerViewArch;
    RecyclerArchitectureAdapter adapterRvArch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_architecture, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCameraArch = view.findViewById(R.id.btnCameraArch);

        searchView2 = view.findViewById(R.id.searchView2);
        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterRvArch.setArchitectureList(filter(newText));
                return false;
            }
        });

        recyclerViewArch = view.findViewById(R.id.ArchitectureRecyclerView);
        initRecyclerView();

    }

    private void listenData() {
        FirebaseHelper.getInstance().getData("architecture").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Firebase", "listen:error", error);
                return;
            }
            ArrayList<ArchitectureModel> temp = new ArrayList();
            for (DocumentChange dc : value.getDocumentChanges()) {
                temp.add(dc.getDocument().toObject(ArchitectureModel.class));
            }
            architectureList = temp;
            adapterRvArch.setArchitectureList(architectureList);
        });
    }

    private void initRecyclerView() {
        adapterRvArch = new RecyclerArchitectureAdapter();
        recyclerViewArch.setAdapter(adapterRvArch);
        recyclerViewArch.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listenData();
    }


    private List<ArchitectureModel> filter(String query) {
        ArrayList<ArchitectureModel> temp = new ArrayList<>();
        for (ArchitectureModel model : architectureList) {
            if (model.getName().toLowerCase().contains(query.toLowerCase())) {
                temp.add(model);
            }
        }
        return temp;
    }

}
