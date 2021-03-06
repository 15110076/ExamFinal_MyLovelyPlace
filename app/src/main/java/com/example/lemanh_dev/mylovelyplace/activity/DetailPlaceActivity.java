package com.example.lemanh_dev.mylovelyplace.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lemanh_dev.mylovelyplace.ActivityUtils;
import com.example.lemanh_dev.mylovelyplace.R;
import com.example.lemanh_dev.mylovelyplace.data.PlaceRepo;
import com.example.lemanh_dev.mylovelyplace.data.models.Place;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPlaceActivity extends AppCompatActivity {

    @BindView(R.id.detailPlace_imgPlace)
    ImageView imgPlace;
    @BindView(R.id.detalPlace_txtPlaceName)
    EditText txtplaceName;
    @BindView(R.id.detailPlace_txtPlaceAddress)
    EditText txtplaceAddress;
    @BindView(R.id.detailPlace_txtPlaceDescription)
    EditText txtplaceDescription;

    private String placeID;
    private String categoryID;
    private PlaceRepo placeRepo;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialogDelete;
    private ProgressDialog progressDialogUpdate;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);

        //khởi tao ButterKnife
        ButterKnife.bind(this);
        //hàm khởi tạo
        init();

    }

    private void init() {
        //Lấy placeID
        placeID =getIntent().getStringExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA);
        //lấy CategoryID
        categoryID=getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo=PlaceRepo.getInstance(this);
        //log xem thông tin lấy được hay ko
        Log.d("Test",placeID +" "+ categoryID);

        initProgressDialog();
        setPlace();


    }

    //method set Place
    private void setPlace(){
        //lấy danh sách Place dựa trên placeiD và categoryID
       place = placeRepo.getPlaceByID(categoryID,placeID);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(place.getPlaceImages() != null) {
                    Bitmap placeBitmap = BitmapFactory.decodeByteArray(place.getPlaceImages(), 0, place.getPlaceImages().length);
                    imgPlace.setImageBitmap(placeBitmap);
                }
                txtplaceName.setText(place.getPlaceName());
                txtplaceAddress.setText(place.getPlaceAddress());
                txtplaceDescription.setText(place.getPlaceDescription());
                progressDialog.dismiss();

            }
        },4000);

    }
    //method khởi tạo ProgressDialog cho setup place
    private void initProgressDialog(){
        progressDialog=new ProgressDialog(DetailPlaceActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_retrieving_data));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    //method khởi tạo ProgressDialog cho delete
    private void initProgressDialogdelete(){
        progressDialogDelete=new ProgressDialog(DetailPlaceActivity.this);
        progressDialogDelete.setIndeterminate(true);
        progressDialogDelete.setMessage(getResources().getString(R.string.text_deleting_data));
        progressDialogDelete.setCanceledOnTouchOutside(false);
        progressDialogDelete.show();
    }
    //method khởi tạo ProgressDialog cho update
    private void initProgressDialogupdate(){
        progressDialogUpdate=new ProgressDialog(DetailPlaceActivity.this);
        progressDialogUpdate.setIndeterminate(true);
        progressDialogUpdate.setMessage(getResources().getString(R.string.text_deleting_data));
        progressDialogUpdate.setCanceledOnTouchOutside(false);
        progressDialogUpdate.show();
    }

    //Xử lý sự kiện back về Activity trước đó
    @OnClick(R.id.btnClose)
    public void closeActivitiDetail(){
        //chuển đên Activity PlaceActivity
        redirecttoListPlace();

    }
    //xử lý chức năng Delete 1 place
    @OnClick(R.id.btnDelete)
    public void deletePlace(View view){
                //khởi tạo ProgressDialog
                initProgressDialogdelete();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Delete place theo PlaceID
                        placeRepo.delete(placeID);
                        //hủy progressDialog
                        progressDialogDelete.dismiss();
                        redirecttoListPlace();
                    }
                },3000);

    }

    //Xử lý sự kiện click edit 1 place với 1 category thuôc danh sách hiện tại
    //Put CategoryID qua để xác định thêm Place theo Category nào
    //và 1 cái cờ xác định là dung chức năng insert hay update
    @OnClick(R.id.btnEdit)
    public void editPlace(View view){
        Intent placeEditIntent = new Intent(DetailPlaceActivity.this,AddPlaceActivity.class);
        placeEditIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA,categoryID);
        placeEditIntent.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA,placeID);
        placeEditIntent.putExtra(ActivityUtils.FLAG_IS_INSERT_OR_UPDATE,false);
        startActivity(placeEditIntent);
        finish();

    }

    //chuyen huong den trang danh sanh place
    private void redirecttoListPlace(){
        Intent placeIntent = new Intent(DetailPlaceActivity.this,PlaceActivity.class);
        placeIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA,categoryID);
        startActivity(placeIntent);
        finish();
    }
}
