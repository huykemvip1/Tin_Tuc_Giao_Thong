package com.example.tintucgiaothong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tintucgiaothong.domain.NoiDung;
import com.example.tintucgiaothong.service.CustomAdapter;
import com.example.tintucgiaothong.service.DeletePostedService;
import com.example.tintucgiaothong.service.PostedService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PostedActivity extends AppCompatActivity {
    public long id_1;
    public RecyclerView recyclerView;
    public CustomAdapter customAdapter;
    public CardView cardView;
    List<NoiDung> noiDungs;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posted);

        cardView=findViewById(R.id.posted_card_view);


         sharedPreferences=getSharedPreferences("my_security", Context.MODE_PRIVATE);

        PostedService postedActivity=new PostedService(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                postedActivity.execute("http://10.0.2.2:8080/api/ttgt/getAll/"+sharedPreferences.getString("username", ""));
            }
        });

    }
    public void details_1(View view){
        Intent intent=new Intent();
        intent.setClass(this, DetailsActivity.class);
        intent.putExtra("id", id_1);
        startActivity(intent);
    }
    public void delete_1(View view) {
        DeletePostedService deletePostedService=new DeletePostedService(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                deletePostedService.execute("http://10.0.2.2:8080/api/ttgt/delete/"+id_1);
            }
        });
    }
    public void setNoidung(List<NoiDung> noiDungs) {
        recyclerView=findViewById(R.id.posted_ry);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext()
                , RecyclerView.VERTICAL
                ,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        customAdapter=new CustomAdapter(noiDungs,
                this,
                cardView);
        recyclerView.setAdapter(customAdapter);
    }
    public void checkErrorDelete(InputStream inputStream){
        if (inputStream == null){
            Toast.makeText(this, "Success Delete !!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Failure Delete !!!", Toast.LENGTH_SHORT).show();
        }
        //-----------reset lai background
        PostedService postedActivity=new PostedService(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                postedActivity.execute("http://10.0.2.2:8080/api/ttgt/getAll/"+sharedPreferences.getString("username", ""));
            }
        });
    }
}