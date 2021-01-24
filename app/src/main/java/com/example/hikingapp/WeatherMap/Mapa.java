package com.example.hikingapp.WeatherMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.hikingapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // GRAD KOJ E ODBRAN SE PRAKJA PREKU INTENT
        Intent intent = getIntent();
        mess = intent.getStringExtra("EXTRA_MESSAGE");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] parts = mess.split(" ");

        double lon = Double.parseDouble(parts[0]);
        double lat = Double.parseDouble(parts[1]);
        LatLng loc = new LatLng(lon, lat);
        Log.v(lon + " " + lat, "lokacija");
        mMap.addMarker(new MarkerOptions().position(loc).title(parts[0] + " " + parts[1]));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 12.0f));
    }

}