package com.example.travelguide;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Check for single location (latitude, longitude, and name)
        if (getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")) {
            double latitude = getIntent().getDoubleExtra("latitude", 0.0);
            double longitude = getIntent().getDoubleExtra("longitude", 0.0);
            String restaurantName = getIntent().getStringExtra("restaurantName"); // Get the name

            if (latitude != 0.0 && longitude != 0.0) {
                LatLng restaurantLocation = new LatLng(latitude, longitude);
                // Use the restaurant name as the marker title
                mMap.addMarker(new MarkerOptions().position(restaurantLocation).title(restaurantName));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));
            }
        }
        // Handle multiple locations (already implemented)
        else if (getIntent().hasExtra("latitudes") && getIntent().hasExtra("longitudes") && getIntent().hasExtra("names")) {
            ArrayList<Double> latitudes = (ArrayList<Double>) getIntent().getSerializableExtra("latitudes");
            ArrayList<Double> longitudes = (ArrayList<Double>) getIntent().getSerializableExtra("longitudes");
            ArrayList<String> names = getIntent().getStringArrayListExtra("names");

            if (latitudes != null && longitudes != null && names != null) {
                for (int i = 0; i < latitudes.size(); i++) {
                    LatLng location = new LatLng(latitudes.get(i), longitudes.get(i));
                    mMap.addMarker(new MarkerOptions().position(location).title(names.get(i)));
                    if (i == 0) {
                        // Move camera to the first location
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
                    }
                }
            }
        } else {
            Toast.makeText(this, "No location data provided.", Toast.LENGTH_SHORT).show();
        }
    }


}
