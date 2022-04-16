package com.example.tintucgiaothong.service;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tintucgiaothong.PostedActivity;
import com.example.tintucgiaothong.R;
import com.example.tintucgiaothong.domain.NoiDung;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<NoiDung> noiDungs;
    private PostedActivity postedActivity;
    private CardView cardView;
    public CustomAdapter(List<NoiDung> noiDungs,PostedActivity context,CardView cardView){
        this.noiDungs=noiDungs;
        this.postedActivity=context;
        this.cardView=cardView;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_posted, parent,false);
        CustomViewHolder customViewHolder=new CustomViewHolder(view);

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        if (holder == null){
            return;
        }
        Log.d("noidung", noiDungs.get(0).getImage());
        HandleService handleService=new HandleService();
        holder.imageView.setImageBitmap(handleService
                .addImage(noiDungs.get(position).getImage()));

        // -----------Den noi xoa du lieu da chon
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postedActivity.id_1=noiDungs.get(holder.getAdapterPosition()).getId();
                postedActivity.delete_1(view);
            }
        });

        //------------Den noi Xem thong tin chi tiet du lieu da chon

        holder.information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postedActivity.id_1=noiDungs.get(holder.getAdapterPosition()).getId();
                postedActivity.details_1(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (noiDungs != null){
            return noiDungs.size();
        }
        return 0;
    }

}
