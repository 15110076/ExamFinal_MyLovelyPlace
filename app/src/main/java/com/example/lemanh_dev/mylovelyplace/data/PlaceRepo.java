package com.example.lemanh_dev.mylovelyplace.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lemanh_dev.mylovelyplace.data.models.Category;
import com.example.lemanh_dev.mylovelyplace.data.models.DB_Utitls;
import com.example.lemanh_dev.mylovelyplace.data.models.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceRepo {
    private static PlaceRepo instance = null;
    private PlaceSqliteHelper placeSqliteHelper;
    protected PlaceRepo(Context context) {
        // Exists only to defeat instantiation.
        placeSqliteHelper = new PlaceSqliteHelper(context);
    }
    public static PlaceRepo getInstance(Context context) {
        return(instance == null)? new PlaceRepo(context) :instance;
    }

    //lay danh sach Category
    public List<Category> getAllCategory(){
        List<Category> lstCategory = new ArrayList<>();

        SQLiteDatabase db = placeSqliteHelper.getReadableDatabase();

        String[] columns = {
                DB_Utitls.COLUMN_CATEGORY_ID,
                DB_Utitls.COLUMN_CATEGORY_NAME
        };

        Cursor cursor = db.query(DB_Utitls.CATEGORY_TBL_NAME,columns,null,null,null,null,null);

        if(cursor != null && cursor.getCount() >0){
            while (cursor.moveToNext())
            {
                String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_CATEGORY_ID));
                String categoryName =cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_CATEGORY_NAME));

                lstCategory.add(new Category(categoryID,categoryName));
            }
        }

        if(cursor!= null){
            cursor.close();
        }

        db.close();

        return lstCategory ;
    }
    //lay danh sach Place theo categoryID
    public List<Place> getAllPlace1(){
        List<Place> lstPlace = new ArrayList<>();

        SQLiteDatabase db = placeSqliteHelper.getReadableDatabase();

        String[] columns = {
                DB_Utitls.COLUMN_PLACE_ID,
                DB_Utitls.COLUMN_PLACE_CATEGORY_ID,
                DB_Utitls.COLUMN_PLACE_NAME,
                DB_Utitls.COLUMN_PLACE_ADDRESS,
                DB_Utitls.COLUMN_PLACE_DESCRIPTION,
                DB_Utitls.COLUMN_PLACE_IMAGES,
                DB_Utitls.COLUMN_PLACE_LAT,
                DB_Utitls.COLUMN_PLACE_LNG
        };



        Cursor cursor = db.query(DB_Utitls.PLACE_TBL_NAME,columns,null,null,null,null,null);

        if(cursor != null && cursor.getCount() >0){
            while (cursor.moveToNext())
            {
                String placeID = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_ID));
                String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_CATEGORY_ID));
                String placeName =cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_NAME));
                String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_ADDRESS));
                String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_DESCRIPTION));
                byte[] placeImages = cursor.getBlob(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_IMAGES));
                double placeLat = cursor.getDouble(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_LAT));
                double placeLng = cursor.getDouble(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_LNG));

                Place place = new Place.Builder()
                        .setPlaceID(placeID)
                        .setCategoryID(categoryID)
                        .setPlaceName(placeName)
                        .setPlaceAddress(placeAddress)
                        .setPlaceDescription(placeDescription)
                        .setPlaceImages(placeImages)
                        .setPlaceLat(placeLat)
                        .setPlaceLng(placeLng)
                        .buil();

                lstPlace.add(place);

            }
        }

        if(cursor!= null){
            cursor.close();
        }

        db.close();

        return lstPlace ;
    }


    //lay danh sach Place theo categoryID
    public List<Place> getAllPlace(String cateID){
        List<Place> lstPlace = new ArrayList<>();

        SQLiteDatabase db = placeSqliteHelper.getReadableDatabase();

        String[] columns = {
                DB_Utitls.COLUMN_PLACE_ID,
                DB_Utitls.COLUMN_PLACE_CATEGORY_ID,
                DB_Utitls.COLUMN_PLACE_NAME,
                DB_Utitls.COLUMN_PLACE_ADDRESS,
                DB_Utitls.COLUMN_PLACE_DESCRIPTION,
                DB_Utitls.COLUMN_PLACE_IMAGES,
                DB_Utitls.COLUMN_PLACE_LAT,
                DB_Utitls.COLUMN_PLACE_LNG
        };

        String selection = DB_Utitls.COLUMN_PLACE_CATEGORY_ID + " = ?";
        String[] selectionArgs = {cateID};

        Cursor cursor = db.query(DB_Utitls.PLACE_TBL_NAME,columns,selection,selectionArgs,null,null,null);

        if(cursor != null && cursor.getCount() >0){
            while (cursor.moveToNext())
            {
                String placeID = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_ID));
                String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_CATEGORY_ID));
                String placeName =cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_NAME));
                String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_ADDRESS));
                String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_DESCRIPTION));
                byte[] placeImages = cursor.getBlob(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_IMAGES));
                double placeLat = cursor.getDouble(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_LAT));
                double placeLng = cursor.getDouble(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_LNG));

                Place place = new Place.Builder()
                        .setPlaceID(placeID)
                        .setCategoryID(categoryID)
                        .setPlaceName(placeName)
                        .setPlaceAddress(placeAddress)
                        .setPlaceDescription(placeDescription)
                        .setPlaceImages(placeImages)
                        .setPlaceLat(placeLat)
                        .setPlaceLng(placeLng)
                        .buil();

                lstPlace.add(place);

            }
        }

        if(cursor!= null){
            cursor.close();
        }

        db.close();

        return lstPlace ;
    }

    //lay thong tin Place theo placeID
    public Place getPlaceByID(String cateID, String plID){
        Place place =null;

        SQLiteDatabase db = placeSqliteHelper.getReadableDatabase();

        String[] columns = {
                DB_Utitls.COLUMN_PLACE_ID,
                DB_Utitls.COLUMN_PLACE_CATEGORY_ID,
                DB_Utitls.COLUMN_PLACE_NAME,
                DB_Utitls.COLUMN_PLACE_ADDRESS,
                DB_Utitls.COLUMN_PLACE_DESCRIPTION,
                DB_Utitls.COLUMN_PLACE_IMAGES,
                DB_Utitls.COLUMN_PLACE_LAT,
                DB_Utitls.COLUMN_PLACE_LNG
        };

        String selection = DB_Utitls.COLUMN_PLACE_ID + " = ? AND " + DB_Utitls.COLUMN_PLACE_CATEGORY_ID + " = ?";
        String[] selectionArgs = {plID, cateID};

        Cursor cursor = db.query(DB_Utitls.PLACE_TBL_NAME,columns,selection,selectionArgs,null,null,null);

        if(cursor != null && cursor.getCount() >0){
            cursor.moveToFirst();

                String placeID = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_ID));
                String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_CATEGORY_ID));
                String placeName =cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_NAME));
                String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_ADDRESS));
                String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_DESCRIPTION));
                byte[] placeImages = cursor.getBlob(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_IMAGES));
                double placeLat = cursor.getDouble(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_LAT));
                double placeLng = cursor.getDouble(cursor.getColumnIndexOrThrow(DB_Utitls.COLUMN_PLACE_LNG));

                place = new Place.Builder()
                        .setPlaceID(placeID)
                        .setCategoryID(categoryID)
                        .setPlaceName(placeName)
                        .setPlaceAddress(placeAddress)
                        .setPlaceDescription(placeDescription)
                        .setPlaceImages(placeImages)
                        .setPlaceLat(placeLat)
                        .setPlaceLng(placeLng)
                        .buil();

        }

        if(cursor!= null){
            cursor.close();
        }

        db.close();

        return place;
    }

    //insert palce
    public void insert(Place place){
        SQLiteDatabase db = placeSqliteHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_Utitls.COLUMN_PLACE_ID,place.getPlaceID());
        contentValues.put(DB_Utitls.COLUMN_PLACE_CATEGORY_ID,place.getCategoryID());
        contentValues.put(DB_Utitls.COLUMN_PLACE_NAME,place.getPlaceName());
        contentValues.put(DB_Utitls.COLUMN_PLACE_ADDRESS,place.getPlaceAddress());
        contentValues.put(DB_Utitls.COLUMN_PLACE_DESCRIPTION,place.getPlaceDescription());
        contentValues.put(DB_Utitls.COLUMN_PLACE_IMAGES,place.getPlaceImages());
        contentValues.put(DB_Utitls.COLUMN_PLACE_LAT,place.getPlaceLat());
        contentValues.put(DB_Utitls.COLUMN_PLACE_LNG,place.getPlaceLng());

        db.insert(DB_Utitls.PLACE_TBL_NAME,null,contentValues);
        db.close();
    }

    //update place
    public void update(Place place){
        SQLiteDatabase db = placeSqliteHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_Utitls.COLUMN_PLACE_ID,place.getPlaceID());
        contentValues.put(DB_Utitls.COLUMN_PLACE_CATEGORY_ID,place.getCategoryID());
        contentValues.put(DB_Utitls.COLUMN_PLACE_NAME,place.getPlaceName());
        contentValues.put(DB_Utitls.COLUMN_PLACE_ADDRESS,place.getPlaceAddress());
        contentValues.put(DB_Utitls.COLUMN_PLACE_DESCRIPTION,place.getPlaceDescription());
        contentValues.put(DB_Utitls.COLUMN_PLACE_IMAGES,place.getPlaceImages());
        contentValues.put(DB_Utitls.COLUMN_PLACE_LAT,place.getPlaceLat());
        contentValues.put(DB_Utitls.COLUMN_PLACE_LNG,place.getPlaceLng());

        String selection = DB_Utitls.COLUMN_PLACE_ID + " = ?";
        String[] selectionArgs = {place.getPlaceID()};

        db.update(DB_Utitls.PLACE_TBL_NAME,contentValues,selection,selectionArgs);
        db.close();
    }

    //delete Place
    public void delete(String placeID){
        SQLiteDatabase db = placeSqliteHelper.getWritableDatabase();


        String selection = DB_Utitls.COLUMN_PLACE_ID + " = ?";
        String[] selectionArgs = {placeID};

        db.delete(DB_Utitls.PLACE_TBL_NAME,selection,selectionArgs);
        db.close();
    }
}
