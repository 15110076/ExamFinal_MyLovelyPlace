package com.example.lemanh_dev.mylovelyplace.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lemanh_dev.mylovelyplace.data.models.DB_Utitls;

public class PlaceSqliteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PLACE";
    private static final int DB_VERSION = 1;

    private static final String CREATE_PLACE_TABLE_SQL =
            "CREATE TABLE " + DB_Utitls.PLACE_TBL_NAME + "("
                    +DB_Utitls.COLUMN_PLACE_ID + " " +DB_Utitls.TEXT_DATA_TYPE + " " +DB_Utitls.PRIMARY_KEY +", "
                    +DB_Utitls.COLUMN_PLACE_CATEGORY_ID + " " +DB_Utitls.TEXT_DATA_TYPE + " " +DB_Utitls.NOT_NULL +", "
                    +DB_Utitls.COLUMN_PLACE_NAME + " " +DB_Utitls.TEXT_DATA_TYPE + " " +DB_Utitls.NOT_NULL +", "
                    +DB_Utitls.COLUMN_PLACE_ADDRESS + " " +DB_Utitls.TEXT_DATA_TYPE + " " +DB_Utitls.NOT_NULL +", "
                    +DB_Utitls.COLUMN_PLACE_DESCRIPTION + " " +DB_Utitls.TEXT_DATA_TYPE + " " +DB_Utitls.NOT_NULL +", "
                    +DB_Utitls.COLUMN_PLACE_IMAGES + " " +DB_Utitls.BLOB_DATA_TYPE + " " +DB_Utitls.NOT_NULL +", "
                    +DB_Utitls.COLUMN_PLACE_LAT + " " +DB_Utitls.REAL_DATA_TYPE + " " +DB_Utitls.NOT_NULL +", "
                    +DB_Utitls.COLUMN_PLACE_LNG + " " +DB_Utitls.REAL_DATA_TYPE + " " +DB_Utitls.NOT_NULL
                    +")";
    private static final String CREATE_CATEGORY_TABLE_SQL =
            "CREATE TABLE " + DB_Utitls.CATEGORY_TBL_NAME + "("
                    +DB_Utitls.COLUMN_CATEGORY_ID + " " +DB_Utitls.TEXT_DATA_TYPE + " " +DB_Utitls.PRIMARY_KEY +", "
                    +DB_Utitls.COLUMN_CATEGORY_NAME + " " +DB_Utitls.TEXT_DATA_TYPE + " " +DB_Utitls.NOT_NULL
                    +")";

    private static final String INSERT_CATEGORY_SQL =
            "INSERT INTO " + DB_Utitls.CATEGORY_TBL_NAME + " (" +DB_Utitls.COLUMN_CATEGORY_ID+", "+ DB_Utitls.COLUMN_CATEGORY_NAME +")"
            + " VALUES "
            + "('1', 'Restaurant'), "+"('2', 'Cinema'), "+"('3', 'Fashion'), "+"('4', 'ATM')";




    public PlaceSqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLACE_TABLE_SQL);
        db.execSQL(CREATE_CATEGORY_TABLE_SQL);
        db.execSQL(INSERT_CATEGORY_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
