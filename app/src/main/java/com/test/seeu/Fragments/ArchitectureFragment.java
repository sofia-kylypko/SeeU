package com.test.seeu.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.seeu.R;
import com.test.seeu.forContainer.RvAdapter;

import static com.test.seeu.MainActivity.mAttractArch;
import static com.test.seeu.MainActivity.mAuthorsArch;
import static com.test.seeu.MainActivity.mDetailsArch;
import static com.test.seeu.MainActivity.mImageArch;
import static com.test.seeu.MainActivity.mNamesArch;


public class ArchitectureFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_architecture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvArch);
        RvAdapter rvAdapter = new RvAdapter(this.getContext(), mImageArch, mNamesArch, mAuthorsArch, mDetailsArch, mAttractArch);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}
