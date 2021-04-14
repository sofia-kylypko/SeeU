package com.test.seeu.ArchitectureFragment;

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

public class ArchitectureFragment extends Fragment {

    private ArrayList<ArchitectureModel> architectureList;
    private SearchView searchView2;
    private Button btnGalleryArchitecture;

    RecyclerView recyclerView;
    RecyclerArchitectureAdapter adapter;
    SQLiteDatabase sqLiteDatabase;
    DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_architecture, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGalleryArchitecture = view.findViewById(R.id.btnGalleryArchitecture);

        searchView2 = view.findViewById(R.id.searchView2);
        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.setArchitectureList(filter(newText));
                return false;
            }
        });

        recyclerView = view.findViewById(R.id.ArchitectureRecyclerView);
        initRecyclerView();
        adapter.setArchitectureList(sqliteToArray());
    }

    private void initRecyclerView() {
        adapter = new RecyclerArchitectureAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    // получаем данные из SQLite и записываем их в Массив
    public List<ArchitectureModel> sqliteToArray() {
        dbHelper = new DBHelper(this.getContext());
        architectureList = new ArrayList<>();
        sqLiteDatabase = dbHelper.getWritableDatabase();

        // строим SQL-запрос
        String selection = DBHelper.KEY_ATTRACT + "= 1";

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
                architectureList.add(new ArchitectureModel(cursor.getString(indexName), cursor.getString(indexImage), cursor.getString(indexAuthor), cursor.getString(indexDetails)));
            }
        }
        return architectureList;
    }

    private List<ArchitectureModel> filter(String query) {
        ArrayList<ArchitectureModel> temp = new ArrayList<>();
        for (ArchitectureModel model: architectureList) {
            if (model.getName().toLowerCase().contains(query.toLowerCase())) {
                temp.add(model);
            }
        }
        return temp;
    }
}
