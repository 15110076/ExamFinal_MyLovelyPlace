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
import com.example.lemanh_dev.mylovelyplace.MapActivity;
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
        //khởi tạo để có thể dung thư viện butterKnife
        ButterKnife.bind(this);
        //khởi tạo dữ liệu
        init();
    }
    //method khởi tạo data
    private void init() {
        //lấy thông tin category ID từ activity CategoryActivity truyền qua
        categoryID=getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        //getinstant class PlaceRepo
        placeRepo=PlaceRepo.getInstance(this);
        //đỗ danh sách place vào Adapter vì danh sách place ko thể bỏ trực tiếp
        //vào ListView được phải qua trung gian là Adapter
        placesAdapter=new PlacesAdapter(this,places);
        //khởi tạo 1 ProgressDialog
        initProgressDialog();
        //hàm lấy danh sách place và bỏ vào ListView
        getPlaceByCategoryID();
        //
        onPlaceClick();
    }
    //method lấy danh sách place từ category ID
    private void getPlaceByCategoryID(){
        //lấy dánh sách
        places = placeRepo.getAllPlace(categoryID);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //kiểm tra danh sách có rỗng hay không
                if(!places.isEmpty()){

                    txtnodata.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Load Data Successed",Toast.LENGTH_SHORT).show();
                }
                //đỗ PlacesAdapter vào ListView
                lvPlaces.setAdapter(placesAdapter);
                //cập nhật lại dánh sách PlacesAdapter nếu có thay đổi xảy ra
                placesAdapter.updatePlace(places);
                //Hủy progressDialog
                progressDialog.dismiss();
            }
        },3000);

    }
    //method khởi tạo ProgressDialog
    private void initProgressDialog(){
        progressDialog=new ProgressDialog(PlaceActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_retrieving_data));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    //Xử lý sự kiện click của từng item trong listview
    //khi click sẽ put thông tin category ID vs Place ID qua activity datailPlace
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

    //Xử lý sự kiện click thêm 1 place mới với 1 category thuộc danh sách hiện tại
    //Put CategoryID qua để xác định thêm Place theo Category nào
    //và 1 cái cờ xác định là dung chức năng insert hay update
    @OnClick(R.id.btnAddNewPlace)
    public void addNewPlace(){
        Intent addPlace = new Intent(PlaceActivity.this,AddPlaceActivity.class);
        addPlace.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA,categoryID);
        addPlace.putExtra(ActivityUtils.FLAG_IS_INSERT_OR_UPDATE,true);
        startActivity(addPlace);
    }
    //Xử lý sự kiện hiện thị tất cả địa điểm có trong danh sách lên Map
    //Put CategoryID qua Activity MapActivity để lấy danh sách place theo categoryID
    @OnClick(R.id.btnShowOnMap)
    public void showAllPlaceOnMap(){
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA,categoryID);
        startActivity(intent);
    }

}
