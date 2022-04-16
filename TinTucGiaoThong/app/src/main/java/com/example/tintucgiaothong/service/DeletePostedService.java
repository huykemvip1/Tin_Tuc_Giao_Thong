package com.example.tintucgiaothong.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.tintucgiaothong.PostedActivity;
import com.example.tintucgiaothong.domain.NoiDung;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

public class DeletePostedService extends AsyncTask<String, InputStream,String> {
    PostedActivity postedActivity;
    public DeletePostedService(PostedActivity postedActivity){
        this.postedActivity=postedActivity;
    }
    @Override
    protected String doInBackground(String... strings) {
        Log.d("test", strings[0]);
        HttpURLConnection urlConnection = null;
            try {
                SharedPreferences sharedPreferences=postedActivity.getSharedPreferences("my_security", Context.MODE_PRIVATE);
                urlConnection= (HttpURLConnection) URI.create(strings[0]).toURL().openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("DELETE");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("code",sharedPreferences.getString("token"
                        ,sharedPreferences.getString("token","")));
                urlConnection.connect();
                publishProgress(urlConnection.getErrorStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
    }
    @Override
    protected void onProgressUpdate(InputStream... progress) {
        postedActivity.checkErrorDelete(progress[0]);
    }
}
