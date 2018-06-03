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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //method khởi tạo
        init();
        // Nhận thông báo SupportMapFragment và yêu cầu
        // khi bản đồ đã sẵn sàng để sử dụng.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void init() {
        //lấy thông tin category ID bên activity Place gửi qua
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        //get instant class PlceRepo
        placeRepo=PlaceRepo.getInstance(this);
        //lấy danh sách place tương úng với category vừa nhận được
        getPlaces();
    }
    //method lấy danh sách place
    private void getPlaces() {
        places=placeRepo.getAllPlace(categoryID);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap=googleMap;
        //duyệt từng place trong danh sách places
        //lấy thông tin lat va lng tạo thành 1 location trên map
        for (Place place:places){
            //setup latlng
            LatLng locationPlace = new LatLng(place.getPlaceLat(), place.getPlaceLng());
            //thêm 1 marker trên map và sat title cho nó là PlaceName
            googleMap.addMarker(new MarkerOptions().position(locationPlace)
                    .title(place.getPlaceName()));
            //di chuyển camera đến location mới thêm gần nhất
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(locationPlace));
        }

    }
}
