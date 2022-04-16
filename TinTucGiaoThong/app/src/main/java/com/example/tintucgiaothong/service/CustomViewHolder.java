package com.example.tintucgiaothong.service;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tintucgiaothong.PostedActivity;
import com.example.tintucgiaothong.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    ImageButton delete,information;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.posted_image);
        delete=itemView.findViewById(R.id.posted_delete);
        information=itemView.findViewById(R.id.posted_details);
    }


}
