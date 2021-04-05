package com.test.seeu;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.test.seeu.ArchitectureFragment.ArchitectureFragment;
import com.test.seeu.Fragments.MyViewPagerAdapter;
import com.test.seeu.PaintingFragment.PaintingFragment;
import com.test.seeu.PaintingFragment.PaintingModel;
import com.test.seeu.PaintingFragment.RecyclerPaintingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.test.seeu.NetworkUtils.getResponseFromUrl;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager containerLay;
    private MyViewPagerAdapter adapterFragment;

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private static long resultOp;

    private Button btnUpdate;

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
        btnUpdate = findViewById(R.id.btnUpdate);

        adapterFragment = new MyViewPagerAdapter(getSupportFragmentManager(), Arrays.asList(new PaintingFragment(), new ArchitectureFragment()));
        containerLay = findViewById(R.id.containerLay);
        containerLay.setAdapter(adapterFragment);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(containerLay);

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
}