package com.example.tintucgiaothong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

@SuppressLint("ResourceAsColor")
public class ForgotAccount extends AppCompatActivity {
    JSONObject jsonObject;
    EditText username,email;
    TextView mess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_account);
        username=findViewById(R.id.fg_username);
        email=findViewById(R.id.fg_email);
        mess=findViewById(R.id.fg_mess);
    }
    public void returnLogin(View view){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void findAccount(View view){
        BackEndForgot backEndForgot=new BackEndForgot();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                backEndForgot.execute("http://10.0.2.2:8080/api/ttgt/forgot");
            }
        });
    }
    class BackEndForgot extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) URI.
                        create(strings[0]).
                        toURL().
                        openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setUseCaches(false);

                OutputStream outputStream = urlConnection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                dataOutputStream.close();

                urlConnection.connect();

                InputStream inputStream=urlConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder=new StringBuilder();
                String ch;
                while ((ch = bufferedReader.readLine()) != null){
                    stringBuilder.append(ch);
                }
                publishProgress(stringBuilder.toString());

            }  catch (IOException e) {

            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            jsonObject=new JSONObject();
            try {
                jsonObject.put("username", username.getText());
                jsonObject.put("email", email.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            String kt=progress[0];
            if (kt.equals("UNE")){
                mess.setVisibility(View.VISIBLE);
                mess.setTextColor(R.color.near_red);
                mess.setText("Username is not exist");
            }
            if (kt.equals("ENE")){
                mess.setVisibility(View.VISIBLE);
                mess.setTextColor(R.color.near_red);
                mess.setText("Email does not match account");
            }
            if (kt.equals("OK")){
                mess.setVisibility(View.VISIBLE);
                mess.setText("Send Success !!!");
                mess.setTextColor(R.color.reptile_green);
            }
        }
    }
}