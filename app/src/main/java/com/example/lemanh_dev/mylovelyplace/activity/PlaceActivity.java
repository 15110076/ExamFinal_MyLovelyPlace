package com.example.lemanh_dev.mylovelyplace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lemanh_dev.mylovelyplace.ActivityUtils;
import com.example.lemanh_dev.mylovelyplace.R;
import com.example.lemanh_dev.mylovelyplace.adapter.PlacesAdapter;
import com.example.lemanh_dev.mylovelyplace.data.PlaceRepo;
import com.example.lemanh_dev.mylovelyplace.data.models.Category;
import com.example.lemanh_dev.mylovelyplace.data.models.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaceActivity extends AppCompatActivity {

    @BindView(R.id.lvPlaceAct)
    ListView lvPlaces;
    @BindView(R.id.txtNoData)
    TextView txtnodata;


    private String categoryID;
    private List<Place> places = new ArrayList<>();
    private PlacesAdapter placesAdapter;
    private PlaceRepo placeRepo;
    private ProgressDialog progressDialog;
    List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        categoryID=getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo=PlaceRepo.getInstance(this);
        placesAdapter=new PlacesAdapter(this,places);

        initProgressDialog();

        getPlaceByCategoryID();
        onPlaceClick();
    }

//    private void addtestdata(){
//        Place place = new Place.Builder()
//                .setPlaceID(UUID.randomUUID().toString())
//                .setCategoryID(categoryID)
//                .setPlaceName("abd")
//                .setPlaceAddress("abd")
//                .setPlaceDescription("abc")
//                .setPlaceImages(null)
//                .setPlaceLat(0)
//                .setPlaceLng(0)
//                .buil();
//        places.add(place);
//    }


    private void getPlaceByCategoryID(){

        places = placeRepo.getAllPlace(categoryID);
        //addtestdata();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!places.isEmpty()){

                    txtnodata.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Load Data Successed",Toast.LENGTH_SHORT).show();
                }
                lvPlaces.setAdapter(placesAdapter);
                placesAdapter.updatePlace(places);
                progressDialog.dismiss();
            }
        },4000);

    }

    private void initProgressDialog(){
        progressDialog=new ProgressDialog(PlaceActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_retrieving_data));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void onPlaceClick(){
        lvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = places.get(position);
                Intent detailPlace = new Intent(PlaceActivity.this,DetailPlaceActivity.class);
                detailPlace.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA,place.getPlaceID());
                detailPlace.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA,place.getCategoryID());
                startActivity(detailPlace);
            }
        });

    }

    @OnClick(R.id.btnAddNewPlace)
    public void addNewPlace(){
        Intent addPlace = new Intent(PlaceActivity.this,AddPlaceActivity.class);
        addPlace.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA,categoryID);
        addPlace.putExtra(ActivityUtils.FLAG_IS_INSERT_OR_UPDATE,true);
        startActivity(addPlace);
    }

    @OnClick(R.id.btnShowOnMap)
    public void showAllPlaceOnMap(){
        Toast.makeText(getApplicationContext(),"Show all",Toast.LENGTH_SHORT).show();
    }

}
