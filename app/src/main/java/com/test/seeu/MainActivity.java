package com.test.seeu;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.test.seeu.Fragments.ArchitectureFragment;
import com.test.seeu.Fragments.MyViewPagerAdapter;
import com.test.seeu.Fragments.PaintingFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.test.seeu.NetworkUtils.getResponseFromUrl;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager containerLay;
    MyViewPagerAdapter adapter;

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    private static long resultOp;

    Button btnSearch;
    Button btnUpdate;
    public static EditText etSearch;

    public static ArrayList<String> mImagePaint = new ArrayList<>();
    public static ArrayList<String> mNamesPaint = new ArrayList<>();
    public static ArrayList<String> mAuthorsPaint = new ArrayList<>();
    public static ArrayList<String> mDetailsPaint = new ArrayList<>();
    public static ArrayList<Integer> mAttractPaint = new ArrayList<>();

    public static ArrayList<String> mImageArch = new ArrayList<>();
    public static ArrayList<String> mNamesArch = new ArrayList<>();
    public static ArrayList<String> mAuthorsArch = new ArrayList<>();
    public static ArrayList<String> mDetailsArch = new ArrayList<>();
    public static ArrayList<Integer> mAttractArch = new ArrayList<>();

    // получаем данные из SQLite и записываем их в Массив
    public void sqliteToArray() {
        sqLiteDatabase = dbHelper.getWritableDatabase();

        // строим SQL-запрос
        String selection = DBHelper.KEY_NAME+ " LIKE ?";
        String[] selectionArgs = new String[] { "%" + etSearch.getText().toString() + "%"};

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_ATTRACTIONS,
                null,
                selection,
                selectionArgs,
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
                if (cursor.getInt(indexAttract)==0) {
                    mImagePaint.add(cursor.getString(indexImage));
                    mNamesPaint.add(cursor.getString(indexName));
                    mAuthorsPaint.add(cursor.getString(indexAuthor));
                    mDetailsPaint.add(cursor.getString(indexDetails));
                    mAttractPaint.add(cursor.getInt(indexAttract));
                } else if (cursor.getInt(indexAttract)==1) {
                    mImageArch.add(cursor.getString(indexImage));
                    mNamesArch.add(cursor.getString(indexName));
                    mAuthorsArch.add(cursor.getString(indexAuthor));
                    mDetailsArch.add(cursor.getString(indexDetails));
                    mAttractArch.add(cursor.getInt(indexAttract));
                }
            }
        }

//        initRV(); // запуск RecyclerView
    }

    // ОТДЕЛЬНЫЙ ПОТОК
    // Класс для выноса в отдельный поток задачи Update Базы данных
    public class UpdateDB extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response = "";
            try {
                response = getResponseFromUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String res) {
            try {
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                sqLiteDatabase = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues(); // ContentValues заполняет строки подготовленной таблици

                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonO = jsonArray.getJSONObject(i);
                    int attr = new Random().nextInt(2);

                    contentValues.put(DBHelper.KEY_IMAGE, jsonO.getString("thumbnail"));
                    contentValues.put(DBHelper.KEY_NAME, jsonO.getString("title"));
                    contentValues.put(DBHelper.KEY_AUTHOR, jsonO.getString("title"));
                    contentValues.put(DBHelper.KEY_DETAILS, jsonO.getString("title"));
                    contentValues.put(DBHelper.KEY_ATTRACT, attr);

                    resultOp = sqLiteDatabase.insert(DBHelper.TABLE_ATTRACTIONS, null, contentValues);
                    System.out.println(contentValues.toString());
                    if (resultOp > 0) {
                        System.out.println("Добавили запись №" + resultOp + " ATTRACT = " + attr);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(v -> {
            if (!isInternetAvailable()) {
                sqLiteDatabase = dbHelper.getWritableDatabase();
                sqLiteDatabase.delete(DBHelper.TABLE_ATTRACTIONS, null, null);
                for (int i=0; i<=100; i++) {
                    URL generateURL = NetworkUtils.generateURLforSQLite(String.valueOf(i));
                    new UpdateDB().execute(generateURL); // запуск задачи в отдельном потоке
                }
            }
        });

        btnSearch.setOnClickListener(v -> {
            mImagePaint.clear();
            mNamesPaint.clear();
            mAuthorsPaint.clear();
            mDetailsPaint .clear();

            sqliteToArray();
        });

        adapter = new MyViewPagerAdapter(getSupportFragmentManager(), Arrays.asList(new PaintingFragment(), new ArchitectureFragment()));
        containerLay = findViewById(R.id.containerLay);
        containerLay.setAdapter(adapter);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(containerLay);
    }

    // метод для проверки на предмет подключения устройства к интернету
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

//    // запуск RecyclerView
//    public void initRV() {
//        RecyclerView recyclerView = findViewById(R.id.rv);
//        RvAdapter rvAdapter = new RvAdapter(this, mImagePaint, mNamesPaint, mAuthorsPaint, mDetailsPaint, mAttractPaint );
//        recyclerView.setAdapter(rvAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }
}