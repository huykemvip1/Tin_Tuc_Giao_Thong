package com.example.tintucgiaothong.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;

import com.example.tintucgiaothong.ProfileActivity;
import com.example.tintucgiaothong.R;
import com.example.tintucgiaothong.domain.NguoiDung;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class UpdateNguoiDung extends AsyncTask<String, InputStream,String> {
    ProfileActivity profileActivity;
    JSONObject nguoiDung;
    public UpdateNguoiDung(ProfileActivity profileActivity){
        this.profileActivity=profileActivity;
    }
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection= (HttpURLConnection) URI.create(strings[0]).toURL().openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("code",profileActivity.sharedPreferences.getString("token",""));

            OutputStream outputStream=urlConnection.getOutputStream();
            DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
            dataOutputStream.write(nguoiDung.toString().getBytes(StandardCharsets.UTF_8));
            dataOutputStream.close();

            urlConnection.connect();
            publishProgress(urlConnection.getErrorStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPreExecute() {
         nguoiDung=new JSONObject();
        try {
            nguoiDung.put("username", profileActivity.username.getText().toString());
            nguoiDung.put("password", profileActivity.password.getText().toString());
            nguoiDung.put("birthDay", profileActivity.birthday.getText().toString());
            nguoiDung.put("sex", profileActivity.sex.getText().toString());
            nguoiDung.put("email", profileActivity.email.getText().toString());
            nguoiDung.put("fullname", profileActivity.fullname.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onProgressUpdate(InputStream... values) {
        if (values[0] != null){
            profileActivity.mess.setVisibility(View.VISIBLE);
            profileActivity.mess.setText("Failure Save !!!!!");
            profileActivity.mess.setTextColor(R.color.near_red);
        }else{
            profileActivity.mess.setVisibility(View.VISIBLE);
            profileActivity.mess.setText("Success Save !!!!!");
            profileActivity.mess.setTextColor(R.color.reptile_green);
        }
    }
}
