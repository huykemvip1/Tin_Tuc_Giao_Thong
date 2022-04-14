package com.example.tintucgiaothong.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class HandleService {

    public String modify_day(String current){
        return current.substring(0,10)+"  "+current.substring(10,16);
    }
    public Bitmap addImage(String image_string){
        byte[] bytes= android.util.Base64.decode(image_string, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return  bitmap;
    }
    public byte[] getByteFromImageView(ImageView imageView){
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        return bytes;
    }
}
