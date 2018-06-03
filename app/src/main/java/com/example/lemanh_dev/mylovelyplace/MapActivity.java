package com.example.lemanh_dev.mylovelyplace;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lemanh_dev.mylovelyplace.activity.PlaceActivity;
import com.example.lemanh_dev.mylovelyplace.data.PlaceRepo;
import com.example.lemanh_dev.mylovelyplace.data.models.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap googleMap;
    private PlaceRepo placeRepo;

    private String categoryID;
    private List<Place> places;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void init() {
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo=PlaceRepo.getInstance(this);
        getPlaces();
    }

    private void getPlaces() {
        places=placeRepo.getAllPlace(categoryID);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap=googleMap;

        for (Place place:places){
            LatLng sydney = new LatLng(place.getPlaceLat(), place.getPlaceLng());
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title(place.getPlaceName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }

    }
}
