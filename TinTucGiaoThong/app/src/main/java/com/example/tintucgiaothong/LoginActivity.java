package com.example.tintucgiaothong;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.tintucgiaothong.domain.MaTheoDoi;
import com.example.tintucgiaothong.domain.NoiDung;
import com.example.tintucgiaothong.navi.NavigationClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

     EditText username;
     EditText password;
     String text="";
     NavigationClass navigationClass;
     TextView mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        Button bt_dangnhap=findViewById(R.id.bt_dangnhap);
        Button bt_dangky=findViewById(R.id.bt_dangky);
        username=findViewById(R.id.login_username);
        password=findViewById(R.id.login_password);
        mess=findViewById(R.id.failure);
        navigationClass=new NavigationClass();
            bt_dangky.setOnClickListener(view -> {
               navigationClass.toRegister(this);
            });
            bt_dangnhap.setOnClickListener(view ->{
                int i=0;
                ConnectBackEnd connectBackEnd=new ConnectBackEnd(this);
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              connectBackEnd.execute("http://10.0.2.2:8080/api/ttgt/login");
                          }
                      });

            });

            // --------------
    }
    public void forgotAccount(View view){
        Intent intent=new Intent();
        intent.setClass(this, ForgotAccount.class);
        startActivity(intent);
    }
    // ------------ Back end
     class ConnectBackEnd extends AsyncTask<String,String[],String> {
        LoginActivity loginActivity;
        public ConnectBackEnd(LoginActivity loginActivity){
            this.loginActivity=loginActivity;
        }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) URI.
                        create(strings[0]).
                        toURL().
                        openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("username",username.getText().toString());
                urlConnection.setRequestProperty("password",password.getText().toString());
                urlConnection.connect();


                if (urlConnection.getErrorStream() != null) {
                    String string[]=new String[1];
                    string[0]="";
                    publishProgress(string);
                    return null;
                }else{


                    InputStream inputStream=urlConnection.getInputStream();
                    InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder=new StringBuilder();
                    String ch;
                    while ((ch = bufferedReader.readLine()) != null){
                        stringBuilder.append(ch);
                    }
                    JSONObject jsonObject=new JSONObject(stringBuilder.toString());
                    String string[]=new String[2];
                    string[0]=jsonObject.getString("code");
                    string[1]=jsonObject
                            .getJSONObject("nguoiDung")
                            .getJSONObject("vaiTro").getString("ten");

                    publishProgress(string);
                    return null;
                }
            }  catch (IOException | JSONException e) {
               Log.d("error_connect",e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String[]... progress) {
            String string[]=progress[0];

            if (string.length <= 1){
                mess.setVisibility(View.VISIBLE);
            }else{
                String code=string[0];
                String role=string[1];
                SharedPreferences sharedPref = getSharedPreferences("my_security", MODE_PRIVATE);
                sharedPref.edit().putString("token",code).commit();
                sharedPref.edit().putString("username",username.getText().toString()).commit();
                sharedPref.edit().putString("role", role).commit();
                navigationClass.toHome(loginActivity.getApplicationContext());
            }
        }

    }

    }
