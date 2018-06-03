package com.example.lemanh_dev.mylovelyplace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lemanh_dev.mylovelyplace.ActivityUtils;
import com.example.lemanh_dev.mylovelyplace.HttpDataHandler;
import com.example.lemanh_dev.mylovelyplace.R;
import com.example.lemanh_dev.mylovelyplace.data.PlaceRepo;
import com.example.lemanh_dev.mylovelyplace.data.models.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.lemanh_dev.mylovelyplace.ActivityUtils.FLAG_IS_INSERT_OR_UPDATE;

public class AddPlaceActivity extends AppCompatActivity {

    @BindView(R.id.titleAct)
    TextView txtTitleAct;
    @BindView(R.id.txtLat)
    TextView txtlat;
    @BindView(R.id.txtLng)
    TextView txtlng;
    @BindView(R.id.addPlace_txtPlaceAddress)
    EditText txtPlaceAddress;
    @BindView(R.id.addPlace_txtPlaceName)
    EditText txtPlacename;
    @BindView(R.id.addPlace_txtPlaceDescription)
    EditText txtPlacedescription;
    @BindView(R.id.addPlace_imgPlace)
    ImageView imagePlace;

    private static final int REQUEST_CODE =1;
    private String categoryID;
    private String placeDetailID;
    //cợ xác định có chụp ảnh hay chưa
    private boolean isTaken = false;

    private ProgressDialog progressDialog;
    private PlaceRepo placeRepo;
    private Place placeDetail;
    //cờ xác định insert hay update
    private boolean flag = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        ButterKnife.bind(this);
        //lấy thtông in cờ là Insert hay Update
        flag = getIntent().getExtras().getBoolean(FLAG_IS_INSERT_OR_UPDATE);

        //true là Insert và ngược lại
        if(flag){
            initInsert();
        }
        else {
            initUpdate();
        }



    }

    //khởi tạo khi chức năng là insert
    private void initInsert() {
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        txtTitleAct.setText(getResources().getText(R.string.text_title_act_insert));
        placeRepo= PlaceRepo.getInstance(this);
        initProgressDialog();
    }

    //khởi tạo khi chức năng là update
    private void initUpdate() {
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeDetailID = getIntent().getStringExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA);
        txtTitleAct.setText(getResources().getText(R.string.text_title_act_update));
        placeRepo= PlaceRepo.getInstance(this);
        initProgressDialog();
        progressDialog.show();
        setPlace();
    }
    //set place khi chức năng là update
    private void setPlace(){
        placeDetail = placeRepo.getPlaceByID(categoryID,placeDetailID);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(placeDetail.getPlaceImages() != null) {
                    Bitmap placeBitmap = BitmapFactory.decodeByteArray(placeDetail.getPlaceImages(), 0, placeDetail.getPlaceImages().length);
                    imagePlace.setImageBitmap(placeBitmap);
                }
                txtPlacename.setText(placeDetail.getPlaceName());
                txtPlaceAddress.setText(placeDetail.getPlaceAddress());
                txtPlacedescription.setText(placeDetail.getPlaceDescription());
                txtlat.setText(String.valueOf(placeDetail.getPlaceLat()));
                txtlng.setText(String.valueOf(placeDetail.getPlaceLng()));
                progressDialog.dismiss();

            }
        },4000);

    }

    //khởi tạo Progressdialog
    private void initProgressDialog() {
        progressDialog=new ProgressDialog(AddPlaceActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_saving_data));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    //lay hinh anh chup duoc
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //nếu chụp ảnh rồi
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            //kiểm tra dữ liệu trả về có null hay ko
            if(data ==null){
                isTaken =false;
            }
            else {
                //cờ đã chụp ảnh rồi thành true
                isTaken=true;
                //lấy ảnh vữa chuoj bỏ lên Image Place
                Bitmap placeImg = (Bitmap) data.getExtras().get("data");
                imagePlace.setImageBitmap(placeImg);
            }
        }
    }

    //lay thong tin lat va lng
    @OnClick(R.id.addPlace_getLocation)
    public void getLatLng(View v){
        new GetCoordinates().execute(txtPlaceAddress.getText().toString().replace(" ","+"));
    }

    //chup anh tu camera
    @OnClick(R.id.addPlace_imgPlace)
    public void openCamera(View v){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,REQUEST_CODE);
    }

    //tro ve activity truoc
    @OnClick(R.id.btnClose)
    public void goBack(){
        if(flag){
            //về activity PlaceActivity
            redirecttoListPlace();
        }
        else {
            //về activity DetailActivity
            redirecttoDetailPlace();
        }
    }

    //convert imageview to byte[]
    private byte[] convertImageToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }

    //xu ly su kien them moi 1 dia diem hoặc update 1 địa điểm
    @OnClick(R.id.btnAddSave)
    public void clickSave(View v){

        if(flag) {
            if (isTaken) {
                final String placeName = txtPlacename.getText().toString();
                final String placeAddress = txtPlaceAddress.getText().toString();
                final String placeDescription = txtPlacedescription.getText().toString();
                final String placeID = UUID.randomUUID().toString();
                final byte[] placeImage = convertImageToByte(imagePlace);
                if (Place.valiadteInput(categoryID, placeName, placeAddress, placeDescription)) {
                    if (TextUtils.isEmpty(txtlat.getText()) && TextUtils.isEmpty(txtlng.getText())) {
                        Toast.makeText(getApplicationContext(), "Lat and Lng current null. Please get location!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        final double placeLat = Double.parseDouble(txtlat.getText().toString());
                        final double placeLng = Double.parseDouble(txtlng.getText().toString());

                        progressDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Place place = new Place.Builder()
                                        .setPlaceID(placeID)
                                        .setCategoryID(categoryID)
                                        .setPlaceName(placeName)
                                        .setPlaceAddress(placeAddress)
                                        .setPlaceDescription(placeDescription)
                                        .setPlaceImages(placeImage)
                                        .setPlaceLat(placeLat)
                                        .setPlaceLng(placeLng)
                                        .buil();
                                placeRepo.insert(place);
                                progressDialog.dismiss();
                                redirecttoListPlace();

                            }
                        }, 3000);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in place's inform", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please take a picture!!!", Toast.LENGTH_SHORT).show();
            }
        }
        else {

            final String placeName = txtPlacename.getText().toString();
            final String placeAddress = txtPlaceAddress.getText().toString();
            final String placeDescription = txtPlacedescription.getText().toString();
            final byte[] placeImage = convertImageToByte(imagePlace);
            if (Place.valiadteInput(categoryID, placeName, placeAddress, placeDescription)) {
                if (TextUtils.isEmpty(txtlat.getText()) && TextUtils.isEmpty(txtlng.getText())) {
                    Toast.makeText(getApplicationContext(), "Lat and Lng current null. Please get location!!!", Toast.LENGTH_SHORT).show();
                } else {
                    final double placeLat = Double.parseDouble(txtlat.getText().toString());
                    final double placeLng = Double.parseDouble(txtlng.getText().toString());

                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Place place = new Place.Builder()
                                    .setPlaceID(placeDetailID)
                                    .setCategoryID(categoryID)
                                    .setPlaceName(placeName)
                                    .setPlaceAddress(placeAddress)
                                    .setPlaceDescription(placeDescription)
                                    .setPlaceImages(placeImage)
                                    .setPlaceLat(placeLat)
                                    .setPlaceLng(placeLng)
                                    .buil();
                            placeRepo.update(place);
                            progressDialog.dismiss();
                            redirecttoDetailPlace();

                        }
                    }, 3000);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please fill in place's inform", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //chuyen huong den trang chi tiet san place vua chon chinh sua
    private void redirecttoDetailPlace(){
        Intent detailPlaceIntent = new Intent(AddPlaceActivity.this,DetailPlaceActivity.class);
        detailPlaceIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA,categoryID);
        detailPlaceIntent.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA,placeDetailID);
        startActivity(detailPlaceIntent);
        finish();
    }

    //chuyen huong den trang danh sanh place
    private void redirecttoListPlace(){
        Intent placeIntent = new Intent(AddPlaceActivity.this,PlaceActivity.class);
        placeIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA,categoryID);
        startActivity(placeIntent);
        finish();
    }

    //xu ly lay kinh do va vi do cua dia chi
    private class GetCoordinates extends AsyncTask<String,Void,String> {
        ProgressDialog dialog = new ProgressDialog(AddPlaceActivity.this);
        //khi vựa nhấn Get loaction
        //mở progessDialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        //bắt đấu lấy dữ liệu
        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {

            }
            return null;
        }
        //post lên giao diện
        //và tắt progressDialog
        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);

                String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();

                txtlat.setText(String.format("%s",lat));
                txtlng.setText(String.format("%s",lng));

                if(dialog.isShowing())
                    dialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
