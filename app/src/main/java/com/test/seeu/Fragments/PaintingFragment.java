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

import static com.test.seeu.MainActivity.mAttractPaint;
import static com.test.seeu.MainActivity.mAuthorsPaint;
import static com.test.seeu.MainActivity.mDetailsPaint;
import static com.test.seeu.MainActivity.mImagePaint;
import static com.test.seeu.MainActivity.mNamesPaint;

public class PaintingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_painting, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        RvAdapter rvAdapter = new RvAdapter(this.getContext(), mImagePaint, mNamesPaint, mAuthorsPaint, mDetailsPaint, mAttractPaint);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

}
