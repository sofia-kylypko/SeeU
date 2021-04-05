package com.test.seeu.PaintingFragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.seeu.DBHelper;
import com.test.seeu.R;

import java.util.ArrayList;
import java.util.List;

public class PaintingFragment extends Fragment {

    private ArrayList<PaintingModel> paintingList;
    private SearchView searchView;
    private Button btnGalleryPaint;

    RecyclerView recyclerView;
    RecyclerPaintingAdapter adapterRv;
    SQLiteDatabase sqLiteDatabase;
    DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_painting, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGalleryPaint = view.findViewById(R.id.btnGalleryArchitecture);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterRv.setPrintingList(filter(newText));
                return false;
            }
        });

        recyclerView = view.findViewById(R.id.PaintingRecyclerView);
        initRecyclerView();
        adapterRv.setPrintingList(sqliteToArray());
    }

    private void initRecyclerView() {
        adapterRv = new RecyclerPaintingAdapter(this.getContext());
        recyclerView.setAdapter(adapterRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    // получаем данные из SQLite и записываем их в Массив
    public List<PaintingModel> sqliteToArray() {
        dbHelper = new DBHelper(this.getContext());
        paintingList = new ArrayList<>();
        sqLiteDatabase = dbHelper.getWritableDatabase();

        // строим SQL-запрос
        String selection = DBHelper.KEY_ATTRACT + "= 0";

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_ATTRACTIONS,
                null,
                selection,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            // index_____ - это номер столбца в таблице
            int indexImage = cursor.getColumnIndex(DBHelper.KEY_IMAGE);
            int indexName = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int indexAuthor = cursor.getColumnIndex(DBHelper.KEY_AUTHOR);
            int indexDetails = cursor.getColumnIndex(DBHelper.KEY_DETAILS);
            int indexAttract = cursor.getColumnIndex(DBHelper.KEY_ATTRACT);

            while (cursor.moveToNext()) {
                paintingList.add(new PaintingModel(cursor.getString(indexName), cursor.getString(indexImage), cursor.getString(indexAuthor), cursor.getString(indexDetails)));
            }
        }
        return paintingList;
    }

    private List<PaintingModel> filter(String query) {
        ArrayList<PaintingModel> temp = new ArrayList<>();
        for (PaintingModel model: paintingList) {
            if (model.getName().toLowerCase().contains(query.toLowerCase())) {
                temp.add(model);
            }
        }
        return temp;
    }
}
