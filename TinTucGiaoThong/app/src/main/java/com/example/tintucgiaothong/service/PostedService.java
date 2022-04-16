package com.example.tintucgiaothong.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tintucgiaothong.PostedActivity;
import com.example.tintucgiaothong.R;
import com.example.tintucgiaothong.domain.NoiDung;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class PostedService extends AsyncTask<String, List<NoiDung>,String> {
    PostedActivity postedActivity;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PostedService(PostedActivity postedActivity){
        this.postedActivity=postedActivity;
    }
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        try {
            SharedPreferences sharedPreferences=postedActivity.getSharedPreferences("my_security", Context.MODE_PRIVATE);
            urlConnection= (HttpURLConnection) URI.create(strings[0]).toURL().openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("code",sharedPreferences.getString("token"
                    ,sharedPreferences.getString("token","")));
            urlConnection.connect();

            InputStream inputStream=urlConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder=new StringBuilder();
            String ch;
            while ((ch = bufferedReader.readLine()) != null){
                stringBuilder.append(ch);
            }

            JSONArray jsonArray=new JSONArray(stringBuilder.toString());
            List<NoiDung> noiDungs=new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=new JSONObject(jsonArray.get(i).toString());
                NoiDung noiDung=
                        new NoiDung(jsonObject.getLong("id"),jsonObject.getString("content"),
                                jsonObject.getString("image"),jsonObject.getString("creation_time"),
                                jsonObject.getJSONObject("nguoiDung").getString("username"),
                                jsonObject.getJSONObject("chuDe").getString("ten"));

                noiDungs.add(noiDung);
            }
            publishProgress(noiDungs);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(List<NoiDung>... progress) {
        List<NoiDung> noiDungs=progress[0];

        if(noiDungs.size() <1){
            postedActivity.setNoidung(null);
        }else{
            postedActivity.setNoidung(noiDungs);
        }
    }

}
