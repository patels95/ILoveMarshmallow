package com.patels95.sanam.ilovemarshmallow.model;


import android.graphics.Bitmap;

public class ProductInfo {

    private String mBrandName;
    private String mDescription;
    private String mAsin;
    private String[] mGenders;
    private String mType;
    private String mProductName;
    private String mImageUrl;
    private Bitmap mBitmap;

    public String getBrandName() {
        return mBrandName;
    }

    public void setBrandName(String brandName) {
        mBrandName = brandName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getAsin() {
        return mAsin;
    }

    public void setAsin(String asin) {
        mAsin = asin;
    }

    public String[] getGenders() {
        return mGenders;
    }

    public void setGenders(String[] genders) {
        mGenders = genders;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}
