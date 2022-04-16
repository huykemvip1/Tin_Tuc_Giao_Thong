package com.example.tintucgiaothong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class ToolbarActivity extends Fragment {
    private View view;
     TextView username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view=inflater.inflate(R.layout.toolbar, container, false);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my_security", Context.MODE_PRIVATE);
        username=view.findViewById(R.id.username);
        username.setText(sharedPref.getString("username",""));

        Toolbar toolbar=view.findViewById(R.id.toolbar_menu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_activity,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int check=item.getItemId();

        if(check == R.id.it_home){
            toHome();
            return true;
        }
        if(check == R.id.it_logout){
            Backend_ToLogin backend_toLogin=new Backend_ToLogin(this);
            backend_toLogin.execute("http://10.0.2.2:8080/api/ttgt/logout");
            return true;
        }
        if(check == R.id.it_post){
            toPost();
            return true;
        }
        if(check == R.id.it_profile){
            toProfile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toHome(){

        Intent intent=new Intent();
        intent.setClass(this.view.getContext(),Home.class);
        startActivity(intent);
    }
    public void toLogin(){
        Intent intent=new Intent();
        intent.setClass(this.view.getContext(),LoginActivity.class);
        startActivity(intent);
    }
    public void toPost(){
        Intent intent=new Intent();
        intent.setClass(this.view.getContext(),PostActivity.class);
        startActivity(intent);
    }
    public void toProfile(){
        Intent intent=new Intent();
        intent.setClass(this.view.getContext(), ProfileActivity.class);
        startActivity(intent);
    }

    class Backend_ToLogin extends AsyncTask<String , InputStream, Void>{
        ToolbarActivity toolbarActivity;
        public Backend_ToLogin(ToolbarActivity toolbarActivity){
            this.toolbarActivity=toolbarActivity;
        }
        @Override
        protected Void doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) URI.
                        create(strings[0]).
                        toURL().
                        openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                SharedPreferences sharedPreferences=toolbarActivity.getActivity()
                        .getSharedPreferences("my_security", Context.MODE_PRIVATE);
                urlConnection.setRequestProperty("username", sharedPreferences.getString("username", ""));
                urlConnection.setRequestProperty("Content-Type", "application/json");




                urlConnection.connect();
                publishProgress(urlConnection.getErrorStream());

            }catch (IOException e){

            }
            return null;
        }
        @Override
        protected void onProgressUpdate(InputStream... progress) {
            InputStream inputStream=progress[0];
            if (inputStream != null){
                Toast.makeText(toolbarActivity.getContext(),
                        "Failure !!!",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(toolbarActivity.getContext(),
                        "Success !!!",
                        Toast.LENGTH_SHORT).show();
            }
            toLogin();
        }
    }

    private String token(){
        SharedPreferences sharedPref = getContext().getSharedPreferences("my_security", Context.MODE_PRIVATE);
        return sharedPref.getString("token","");
    }
}
