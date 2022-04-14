package com.example.tintucgiaothong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tintucgiaothong.service.HandleService;
import com.example.tintucgiaothong.service.Home_1_ServiceImp;
import com.example.tintucgiaothong.service.Home_2_ServiceImp;

public class Home extends AppCompatActivity {
    public TextView btn_details_1,btn_details_2,btn_details_3,btn_details_4;
    public TextView day_1,day_2,day_3,day_4;
    public TextView creator_1,creator_2,creator_3,creator_4;
    public TextView context_1,context_2,context_3,context_4;
    public ImageButton btn_right_tt,btn_left_tt,btn_left_th,btn_right_th;
    public ImageView image_1,image_2,image_3,image_4;
    public long id_1,id_2,id_3,id_4;
    public int id_ttgt=0;
    public int id_thgt=0;
    public HandleService handleService;
    public ToolbarActivity fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        handleService=new HandleService();

        btn_right_tt=findViewById(R.id.btn_right_tt);
        btn_right_th=findViewById(R.id.btn_right_th);
        btn_left_tt=findViewById(R.id.btn_left_tt);
        btn_left_th=findViewById(R.id.btn_left_th);


        btn_details_1=findViewById(R.id.details_btn_1);
        day_1=findViewById(R.id.day_1);
        creator_1=findViewById(R.id.creator_1);
        context_1=findViewById(R.id.context_1);
        image_1=findViewById(R.id.image_1);
        btn_details_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                toDetails(id_1);
            }
        });

        btn_details_2=findViewById(R.id.details_btn_2);
        day_2=findViewById(R.id.day_2);
        creator_2=findViewById(R.id.creator_2);
        context_2=findViewById(R.id.context_2);
        image_2=findViewById(R.id.image_2);
        btn_details_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                toDetails(id_2);
            }
        });

        btn_details_3=findViewById(R.id.details_btn_3);
        day_3=findViewById(R.id.day_3);
        creator_3=findViewById(R.id.creator_3);
        context_3=findViewById(R.id.context_3);
        image_3=findViewById(R.id.image_3);
        btn_details_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                toDetails(id_3);
            }
        });

        btn_details_4=findViewById(R.id.details_btn_4);
        day_4=findViewById(R.id.day_4);
        creator_4=findViewById(R.id.creator_4);
        context_4=findViewById(R.id.context_4);
        image_4=findViewById(R.id.image_4);
        btn_details_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                toDetails(id_4);
            }
        });
        // -------------------
        Home_1_ServiceImp homeServiceImp_1=new Home_1_ServiceImp(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeServiceImp_1.execute("http://10.0.2.2:8080/api/ttgt/all/ttgt");
            }
        });
        Home_2_ServiceImp homeServiceImp_2=new Home_2_ServiceImp(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeServiceImp_2.execute("http://10.0.2.2:8080/api/ttgt/all/thgt");
            }
        });
        // ------------------------
    }
    public void right_tt(View view){
        Home_1_ServiceImp homeServiceImp_1=new Home_1_ServiceImp(this);
        id_ttgt=id_ttgt +1;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeServiceImp_1.execute("http://10.0.2.2:8080/api/ttgt/all/ttgt");
            }
        });
    }
    public void left_tt(View view){
        Home_1_ServiceImp homeServiceImp_1=new Home_1_ServiceImp(this);
        id_ttgt=id_ttgt-1 ;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeServiceImp_1.execute("http://10.0.2.2:8080/api/ttgt/all/ttgt");
            }
        });
    }
    public void right_th(View view){
        Home_2_ServiceImp homeServiceImp_2=new Home_2_ServiceImp(this);
        id_thgt=id_thgt +1;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeServiceImp_2.execute("http://10.0.2.2:8080/api/ttgt/all/thgt");
            }
        });
    }
    public void left_th(View view){
        Home_2_ServiceImp homeServiceImp_2=new Home_2_ServiceImp(this);
        id_thgt=id_thgt-1 ;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                homeServiceImp_2.execute("http://10.0.2.2:8080/api/ttgt/all/thgt");
            }
        });



    }
    public void toDetails(long id){
        Intent intent=new Intent();
        intent.setClass(this,DetailsActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}