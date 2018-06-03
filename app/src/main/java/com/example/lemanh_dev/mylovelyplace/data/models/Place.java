package com.example.lemanh_dev.mylovelyplace.data.models;

import android.text.TextUtils;

public class Place {

    private String placeID;
    private String CategoryID;
    private String placeName;
    private String placeAddress;
    private String placeDescription;
    private byte[] placeImages;
    private double placeLat;
    private double placeLng;

    public Place(Builder builder) {
        this.placeID = builder.placeID;
        this.CategoryID = builder.CategoryID;
        this.placeName = builder.placeName;
        this.placeAddress = builder.placeAddress;
        this.placeDescription = builder.placeDescription;
        this.placeImages = builder.placeImages;
        this.placeLat = builder.placeLat;
        this.placeLng = builder.placeLng;
    }

    public String getPlaceID() {
        return placeID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public byte[] getPlaceImages() {
        return placeImages;
    }

    public double getPlaceLat() {
        return placeLat;
    }

    public double getPlaceLng() {
        return placeLng;
    }

    public static class Builder {
        private String placeID;
        private String CategoryID;
        private String placeName;
        private String placeAddress;
        private String placeDescription;
        private byte[] placeImages;
        private double placeLat;
        private double placeLng;

        public Builder setPlaceID(String placeID) {
            this.placeID = placeID;
            return this;
        }

        public Builder setCategoryID(String categoryID) {
            CategoryID = categoryID;
            return this;
        }

        public Builder setPlaceName(String placeName) {
            this.placeName = placeName;
            return this;
        }

        public Builder setPlaceAddress(String placeAddress) {
            this.placeAddress = placeAddress;
            return this;
        }

        public Builder setPlaceDescription(String placeDescription) {
            this.placeDescription = placeDescription;
            return this;
        }

        public Builder setPlaceImages(byte[] placeImages) {
            this.placeImages = placeImages;
            return this;
        }

        public Builder setPlaceLat(double placeLat) {
            this.placeLat = placeLat;
            return this;
        }

        public Builder setPlaceLng(double placeLng) {
            this.placeLng = placeLng;
            return this;
        }

        public Place buil(){
            return new Place(this);
        }

    }

    public static boolean valiadteInput(String categoryID, String placeName, String placeAddress, String placeDescription){
        return (TextUtils.isEmpty(categoryID) || TextUtils.isEmpty(placeName) ||TextUtils.isEmpty(placeAddress)
                || TextUtils.isEmpty(placeDescription)) ? false : true;
    }
}
