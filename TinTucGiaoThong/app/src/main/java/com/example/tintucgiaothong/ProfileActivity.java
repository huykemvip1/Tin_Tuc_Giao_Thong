package com.example.tintucgiaothong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tintucgiaothong.service.DetailsNguoiDung;
import com.example.tintucgiaothong.service.UpdateNguoiDung;

public class ProfileActivity extends AppCompatActivity {
    public EditText password,re_password,birthday,fullname,email;
    public TextView mess,username,sex;
    public SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        username=findViewById(R.id.p_username);
        password=findViewById(R.id.p_password);
        re_password=findViewById(R.id.p_re_enter_password);
        birthday=findViewById(R.id.p_birthday);
        sex=findViewById(R.id.p_sex);
        fullname=findViewById(R.id.p_fullname);
        email=findViewById(R.id.p_email);
        mess=findViewById(R.id.p_mess);

         sharedPreferences=getSharedPreferences("my_security", Context.MODE_PRIVATE);
        DetailsNguoiDung detailsNguoiDung=new DetailsNguoiDung(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                detailsNguoiDung.execute("http://10.0.2.2:8080/api/ttgt/getDetails/"
                        +sharedPreferences.getString("username", ""));
            }
        });
    }
    @SuppressLint("ResourceAsColor")
    public void saveNguoiDung(View view){

        if (password.getText().toString().equals(re_password.getText().toString())){
            UpdateNguoiDung updateNguoiDung=new UpdateNguoiDung(this);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateNguoiDung.execute("http://10.0.2.2:8080/api/ttgt/update/"
                            +sharedPreferences.getString("username", ""));
                }
            });
        }else{
            mess.setVisibility(View.VISIBLE);
            mess.setText(" Re-Password is not match !!!!!");
            mess.setTextColor(R.color.near_red);
        }
    }
    public void toDetailsStatus(View view){
        Intent intent=new Intent(this,PostedActivity.class);
        startActivity(intent);
    }
}