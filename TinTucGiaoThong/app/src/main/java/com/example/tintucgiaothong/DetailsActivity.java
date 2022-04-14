package com.example.tintucgiaothong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tintucgiaothong.domain.NoiDung;
import com.example.tintucgiaothong.service.HandleService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    TextView creator;
    TextView day;
    TextView context;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        creator=findViewById(R.id.creator);
        day=findViewById(R.id.day);
        context=findViewById(R.id.context_detail);
        imageView=findViewById(R.id.image_details);
        Intent intent=getIntent();
        long id=intent.getLongExtra("id",0);
        BackendDetails backendDetails=new BackendDetails(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                backendDetails.execute("http://10.0.2.2:8080/api/ttgt/get/"+id);
            }
        });
        // ------- tim  id cua page details


    }
    class BackendDetails extends AsyncTask<String, NoiDung,Void>{
        DetailsActivity detailsActivity;
        public BackendDetails(DetailsActivity detailsActivity){
            this.detailsActivity=detailsActivity;
        }
        @Override
        protected Void doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            try {
                SharedPreferences sharedPreferences=detailsActivity.getSharedPreferences("my_security", Context.MODE_PRIVATE);

                urlConnection= (HttpURLConnection) URI.create(strings[0]).toURL().openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("code",sharedPreferences.getString("token"
                        ,sharedPreferences.getString("token","")));
                urlConnection.connect();

                InputStream inputStream=urlConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder=new StringBuilder();
                String ch;
                while ((ch = bufferedReader.readLine()) != null){
                    stringBuilder.append(ch);
                }

                JSONObject jsonObject=new JSONObject(stringBuilder.toString());
                    NoiDung noiDung=
                            new NoiDung(jsonObject.getLong("id"),jsonObject.getString("content"),
                                    jsonObject.getString("image"),jsonObject.getString("creation_time"),
                                    jsonObject.getJSONObject("nguoiDung").getString("username"),
                                    jsonObject.getJSONObject("chuDe").getString("ten"));
                publishProgress(noiDung);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(NoiDung... progress) {
            HandleService handleService=new HandleService();
            NoiDung noiDung=progress[0];
            detailsActivity.context.setText(noiDung.getContent());
            detailsActivity.day.setText(noiDung.getCreation_time());
            detailsActivity.creator.setText(noiDung.getUsername());
            detailsActivity.imageView.setImageBitmap(handleService.addImage(noiDung.getImage()));
        }
    }
}