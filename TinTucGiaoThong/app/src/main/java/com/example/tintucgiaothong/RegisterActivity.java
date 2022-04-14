package com.example.tintucgiaothong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tintucgiaothong.domain.ChuDe;
import com.example.tintucgiaothong.domain.NguoiDung;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@SuppressLint("ResourceAsColor")
public class RegisterActivity extends AppCompatActivity {
    EditText username,password,re_password,email;
    RadioGroup select;
    RadioButton nam,nu;
    TextView mess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        TextView back=findViewById(R.id.backLogin);
        back.setOnClickListener(view ->{
            toLogin();
        });
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        re_password=findViewById(R.id.register_password_re_enter);
        email=findViewById(R.id.register_email);
        mess=findViewById(R.id.register_mess);
        select= findViewById(R.id.sex);
        nam=findViewById(R.id.nam);
        nu = findViewById(R.id.nu);
    }
    public void toLogin(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }


    public void addUser(View view){
        Log.d("mk", password.getText().toString());
        if (mess.getText().length() <=6){
            mess.setVisibility(View.VISIBLE);
            mess.setText("Password length is too short");
            mess.setTextColor(R.color.near_red);
        }else{
            if(re_password.getText().toString().equals(password.getText().toString())){
                mess.setVisibility(View.VISIBLE);
                mess.setText("Registry Success !!!");
                mess.setTextColor(R.color.reptile_green);
                Backend_register backend_register=new Backend_register(this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        backend_register.execute("http://10.0.2.2:8080/api/ttgt/register");
                    }
                });
            }else {
                mess.setText("Re-entered password does not match !!!");
                mess.setTextColor(R.color.near_red);
                mess.setVisibility(View.VISIBLE);
                password.setText("");
                re_password.setText("");
            }
        }


    }
    class Backend_register extends AsyncTask<String, InputStream, Void>{
        JSONObject jsonObject;
        RegisterActivity registerActivity;
        public Backend_register(RegisterActivity registerActivity){
            this.registerActivity=registerActivity;
        }
        @Override
        protected Void doInBackground(String... strings) {
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
                publishProgress(urlConnection.getErrorStream());
            } catch (IOException e) {

            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            jsonObject=new JSONObject();

            try {

                if (nam.isChecked()){
                    jsonObject.put("sex", "Nam");
                }
                if (nu.isChecked()){
                    jsonObject.put("sex", "Nu");
                }
                jsonObject.put("username", username.getText());
                jsonObject.put("password", password.getText());
                jsonObject.put("email", email.getText());


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @Override
        protected void onProgressUpdate(InputStream... progress) {
            InputStream inputStream=progress[0];
            Log.d("test", jsonObject.toString());
            if (inputStream != null){
                mess.setTextColor(R.color.near_red);
                mess.setText("Username and password is incorrect");
                mess.setVisibility(View.VISIBLE);
            }else{
                mess.setVisibility(View.VISIBLE);
            }
        }
    }
}
