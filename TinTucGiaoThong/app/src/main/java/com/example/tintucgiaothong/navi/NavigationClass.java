package com.example.tintucgiaothong.navi;

import android.content.Context;
import android.content.Intent;

import com.example.tintucgiaothong.Home;
import com.example.tintucgiaothong.LoginActivity;
import com.example.tintucgiaothong.PostActivity;
import com.example.tintucgiaothong.RegisterActivity;

public class NavigationClass {
    public void toHome(Context context){

        Intent intent=new Intent();
        intent.setClass(context, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void toRegister(Context context){
        Intent intent=new Intent();
        intent.setClass(context, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void toLogin(Context context){
        Intent intent=new Intent();
        intent.setClass(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void toPost(Context context){
        Intent intent=new Intent();
        intent.setClass(context, PostActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
