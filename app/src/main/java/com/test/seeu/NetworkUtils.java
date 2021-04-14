package com.test.seeu;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String RECIPE_BASE_URL = "http://www.recipepuppy.com/api/";
    private static final String RECIPE_OPTIONS_INGRADIENTS = "i";
    private static final String RECIPE_OPTIONS_DISH = "q";
    private static final String RECIPE_OPTIONS_PAGE = "p";

    // Генерируем URL для получения JSON
    public static URL generateURL(String dish) {
        Uri buildUri = Uri.parse(RECIPE_BASE_URL).buildUpon()
                .appendQueryParameter(RECIPE_OPTIONS_INGRADIENTS, "")
                .appendQueryParameter(RECIPE_OPTIONS_DISH, dish)
                .appendQueryParameter(RECIPE_OPTIONS_PAGE, "1")
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    // Генерируем URL для заполнения базы данных SQLite
    public static URL generateURLforSQLite(String num) {
        // построение строки URL-запроса (http://www.recipepuppy.com/api/?i=&q=&p=1)
        Uri buildUri = Uri.parse(RECIPE_BASE_URL).buildUpon()
                .appendQueryParameter(RECIPE_OPTIONS_INGRADIENTS, "")
                .appendQueryParameter(RECIPE_OPTIONS_DISH, "")
                .appendQueryParameter(RECIPE_OPTIONS_PAGE, num)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    // Передача нашего URL и получение JSON объекта
    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream is = urlConnection.getInputStream();
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
