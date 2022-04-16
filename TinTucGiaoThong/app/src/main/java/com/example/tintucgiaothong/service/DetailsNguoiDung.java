package com.example.tintucgiaothong.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tintucgiaothong.ProfileActivity;
import com.example.tintucgiaothong.domain.NguoiDung;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDate;

public class DetailsNguoiDung extends AsyncTask<String, NguoiDung,String> {
    ProfileActivity profileActivity;
    public String username;
    public DetailsNguoiDung(ProfileActivity profileActivity){
        this.profileActivity=profileActivity;
    }
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        try {
            SharedPreferences sharedPreferences=profileActivity.getSharedPreferences("my_security", Context.MODE_PRIVATE);
            username=sharedPreferences.getString("username", "");
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
            StringBuilder chuoi=new StringBuilder();
            String ch;
            while ((ch=bufferedReader.readLine()) !=null){
                chuoi.append(ch);
            }
            JSONObject jsonObject=new JSONObject(chuoi.toString());
            NguoiDung nguoiDung=new NguoiDung(
                    jsonObject.getLong("id"),
                    jsonObject.getString("username"),
                    jsonObject.getString("password"),
                    jsonObject.getString("fullname"),
                    jsonObject.getString("birthDay"),
                    jsonObject.getString("sex"),
                    jsonObject.getString("email")
            );
            publishProgress(nguoiDung);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onProgressUpdate(NguoiDung... values) {

        NguoiDung nguoiDung=values[0];
        profileActivity.username.setText(nguoiDung.getUsername());
        profileActivity.password.setText(nguoiDung.getPassword());
        profileActivity.re_password.setText(nguoiDung.getPassword());
        profileActivity.sex.setText(nguoiDung.getSex());
        profileActivity.email.setText(nguoiDung.getEmail());
        if (nguoiDung.getFullname().equals("")
                  || nguoiDung.getFullname() == null){
            profileActivity.fullname.setText("");
        }else {
            profileActivity.fullname.setText(nguoiDung.getFullname());
        }

        if (nguoiDung.getBirthDay().equals("")
                || nguoiDung.getBirthDay() == null){
            profileActivity.birthday.setText("");
        }else {
            profileActivity.birthday.setText(nguoiDung.getBirthDay());
        }

        if (nguoiDung.getBirthDay().equals("")
                || nguoiDung.getBirthDay() == null){
            profileActivity.birthday.setText("");
        }else {
            profileActivity.birthday.setText(nguoiDung.getBirthDay());
        }
    }
}
