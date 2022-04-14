package com.example.tintucgiaothong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tintucgiaothong.domain.ChuDe;
import com.example.tintucgiaothong.domain.NguoiDung;
import com.example.tintucgiaothong.domain.NoiDung;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class PostActivity extends AppCompatActivity {
    EditText context,link;
    ImageButton button;
    RadioGroup select;
    NoiDung noiDung;
    Button button_upload;
    String token;
    RadioButton tin_tuc,tinh_hinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);
        noiDung=new NoiDung();
        context=findViewById(R.id.post_context);
        link=findViewById(R.id.link);
        select=findViewById(R.id.topic);
        tinh_hinh=findViewById(R.id.topic_th);
        tin_tuc=findViewById(R.id.topic_tt);


    }
    public void uploadFile(View view){
        SharedPreferences sharedPreferences=getSharedPreferences("my_security", Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token", "");
        noiDung.setContent(context.getText().toString());
        noiDung.setUsername(sharedPreferences.getString("username",""));
        //-----------------

        if (tinh_hinh.isChecked()){
            noiDung.setChude("Tinh Hinh Giao Thong");
        }
        if (tin_tuc.isChecked()){
            noiDung.setChude("Tin Tuc Giao Thong");
        }
        //-------------------
        Backend_PostActivity backend_postActivity=new Backend_PostActivity(this);

                backend_postActivity.execute("http://10.0.2.2:8080/api/ttgt/add");

    }
    public void postImage(View view){

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        link.setText(data.getData().toString());
        if (requestCode == 1){
            Bitmap bitmap=null;
            try {
               bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }

                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] bytes=byteArrayOutputStream.toByteArray();
                String chuoi= Base64.encodeToString(bytes,Base64.DEFAULT);
                // --------- set image
                noiDung.setImage(chuoi);
        }
    }
    //---------- back end

    class Backend_PostActivity extends AsyncTask<String,InputStream,String>{
    PostActivity postActivity;
    JSONObject jsonObject;
    public Backend_PostActivity(PostActivity postActivity){
        this.postActivity=postActivity;
    }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) URI.
                        create(strings[0]).
                        toURL().
                        openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                urlConnection.setRequestProperty("code",postActivity.token);
                urlConnection.setRequestProperty("Content-Type", "application/json");



                OutputStream outputStream=urlConnection.getOutputStream();
                DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
                dataOutputStream.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                dataOutputStream.close();

                urlConnection.connect();

                publishProgress(urlConnection.getErrorStream());
            }catch (IOException  e){

            }
                return null;
        }

        @Override
        protected void onPreExecute() {
             jsonObject=new JSONObject();

            JSONObject nguoiDung=new JSONObject();


            JSONObject chuDe=new JSONObject();


            try {
                chuDe.put("ten", noiDung.getChude());
                nguoiDung.put("username", noiDung.getUsername());
                jsonObject.put("content",noiDung.getContent());
                jsonObject.put("image",noiDung.getImage());
                jsonObject.put("nguoiDung", nguoiDung);
                jsonObject.put("chuDe", chuDe);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @Override
        protected void onProgressUpdate(InputStream... progress) {
            try {
                Log.d("nguoiDung",jsonObject.getJSONObject("nguoiDung").toString());
                Log.d("chuDe", jsonObject.getJSONObject("chuDe").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            InputStream inputStream=progress[0];
            if (inputStream != null){
                Toast.makeText(postActivity.getApplicationContext(),
                        "Failure !!!",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(postActivity.getApplicationContext(),
                        "Success !!!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}