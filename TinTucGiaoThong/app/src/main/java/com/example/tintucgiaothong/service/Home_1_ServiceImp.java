package com.example.tintucgiaothong.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tintucgiaothong.Home;
import com.example.tintucgiaothong.domain.NguoiDung;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Home_1_ServiceImp extends AsyncTask<String,List<NoiDung>,String> { // ---------- Xu ly ve tin tuc giao thong
    private Home home;
    public Home_1_ServiceImp(Home home){
        this.home=home;
    }
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        try {
            SharedPreferences sharedPreferences=home.getSharedPreferences("my_security", Context.MODE_PRIVATE);

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
        NoiDung noiDung_1=noiDungs.get(home.id_ttgt);
        home.creator_3.setText(noiDung_1.getUsername());
        home.day_3.setText(home.handleService.modify_day(noiDung_1.getCreation_time()));
        home.context_3.setText(noiDung_1.getContent());
        home.image_3.setImageBitmap(home.handleService.addImage(noiDung_1.getImage()));

        NoiDung noiDung_2=noiDungs.get(home.id_ttgt+1);
        home.creator_4.setText(noiDung_2.getUsername());
        home.day_4.setText(home.handleService.modify_day(noiDung_2.getCreation_time()));
        home.context_4.setText(noiDung_2.getContent());
        home.image_4.setImageBitmap(home.handleService.addImage(noiDung_2.getImage()));

        if (home.id_ttgt == 0){
           home.btn_left_tt.setVisibility(View.INVISIBLE);
        }else {
            home.btn_left_tt.setVisibility(View.VISIBLE);
        }
        if (home.id_ttgt == noiDungs.size()-2){
            home.btn_right_tt.setVisibility(View.INVISIBLE);
        }else {
            home.btn_right_tt.setVisibility(View.VISIBLE);
        }

        home.id_3=noiDung_1.getId();
        home.id_4=noiDung_2.getId();
    }

}
