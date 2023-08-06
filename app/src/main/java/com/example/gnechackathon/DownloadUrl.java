package com.example.gnechackathon;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUrl {

    public String retrieveUrl(String url) throws IOException {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String urlData = null;

        try {
            URL getUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) getUrl.openConnection();
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                urlData = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return urlData;
    }
}
