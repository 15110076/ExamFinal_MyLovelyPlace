package com.example.lemanh_dev.mylovelyplace.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lemanh_dev.mylovelyplace.ActivityUtils;
import com.example.lemanh_dev.mylovelyplace.R;
import com.example.lemanh_dev.mylovelyplace.data.PlaceRepo;
import com.example.lemanh_dev.mylovelyplace.data.models.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.txtCatergories_Restaurant)
    TextView txtRestaurant;
    @BindView(R.id.txtCategories_Cinema)
    TextView txtCinema;
    @BindView(R.id.txtCategories_Fashion)
    TextView txtFashion;
    @BindView(R.id.txtCategories_ATM)
    TextView txtATM;

    private PlaceRepo placeRepo;
    private List<Category> categoryList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        placeRepo = PlaceRepo.getInstance(this);
        categoryList=placeRepo.getAllCategory();

        txtRestaurant.setText(categoryList.get(0).getCategoryName());
        txtCinema.setText(categoryList.get(1).getCategoryName());
        txtFashion.setText(categoryList.get(2).getCategoryName());
        txtATM.setText(categoryList.get(3).getCategoryName());
    }

    @OnClick(R.id.layoutRestaurant)
    void clickOnRestaurant(){
        String categoryID = categoryList.get(0).getCategoryID();
        startActivity(categoryID);
    }
    @OnClick(R.id.layoutCinema)
    void clickOnCinema(){
        String categoryID = categoryList.get(1).getCategoryID();
        startActivity(categoryID);
    }

    @OnClick(R.id.layoutFashion)
    void clickOnFashion(){
        String categoryID = categoryList.get(2).getCategoryID();
        startActivity(categoryID);
    }
    @OnClick(R.id.layoutATM)
    void clickOnATM(){
        String categoryID = categoryList.get(3).getCategoryID();
        startActivity(categoryID);
    }

    private void startActivity(String categoryID){
        Intent placesIntent = new Intent(CategoriesActivity.this,PlaceActivity.class);
        placesIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA,categoryID);
        startActivity(placesIntent);
    }

}
