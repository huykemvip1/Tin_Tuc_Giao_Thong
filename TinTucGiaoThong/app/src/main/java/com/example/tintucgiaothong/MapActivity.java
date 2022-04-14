package com.example.tintucgiaothong;

import android.Manifest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;
@SuppressLint("MissingPermission")
// ----- haven't key api google map
public class MapActivity extends Fragment implements OnMapReadyCallback {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap gm;
    private TextView textView;
    private PlacesClient placesClient;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.map, container, false);
        textView=view.findViewById(R.id.location);
        Places.initialize(view.getContext(),"huykemvip1");
        placesClient=Places.createClient(view.getContext());
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(view.getContext());
        //---------------
        ActivityCompat.requestPermissions((Activity) view.getContext(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                        ,Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
        getLocation(view);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gm=googleMap;
        gm.setMyLocationEnabled(true);
    }

    public void getLocation(View view){

        int loc1=ContextCompat.checkSelfPermission(view.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int loc2=ContextCompat.checkSelfPermission(view.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if(loc1 == PackageManager.PERMISSION_GRANTED
                && loc2 == PackageManager.PERMISSION_GRANTED){

            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                    Place.Field.LAT_LNG);
            FindCurrentPlaceRequest request =
                    FindCurrentPlaceRequest.newInstance(placeFields);
            Task<FindCurrentPlaceResponse> placeResult =
                          placesClient.findCurrentPlace(request);
            textView.setText(placeResult.toString());
            placeResult.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
                @Override
                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                    FindCurrentPlaceResponse findCurrentPlaceResponse=task.getResult();
                    String test[]=new String[1];
                    for(PlaceLikelihood placeLikelihood: findCurrentPlaceResponse.getPlaceLikelihoods()){
                        test[0]=placeLikelihood.getPlace().getAddress();
                        textView.setText(test[0]+" ");
                        break;
                    }
                }
            });
        }else{
            ActivityCompat.requestPermissions((Activity) view.getContext(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            ,Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }
    }
}