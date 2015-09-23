package com.patels95.sanam.ilovemarshmallow;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String mBrandName;
    private String mPrice;
    private String mImageUrl;
    private Bitmap mBitmap;
    private String mAsin;
    private String mProductUrl;
    private int mProductRating;
    private String mProductName;

    public Product(){

    }

    private Product(Parcel in){
        mBrandName = in.readString();
        mPrice = in.readString();
        mImageUrl = in.readString();
        mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        mAsin = in.readString();
        mProductUrl = in.readString();
        mProductRating = in.readInt();
        mProductName = in.readString();
    }

    public String getBrandName() {
        return mBrandName;
    }

    public void setBrandName(String brandName) {
        mBrandName = brandName;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getAsin() {
        return mAsin;
    }

    public void setAsin(String asin) {
        mAsin = asin;
    }

    public String getProductUrl() {
        return mProductUrl;
    }

    public void setProductUrl(String productUrl) {
        mProductUrl = productUrl;
    }

    public int getProductRating() {
        return mProductRating;
    }

    public void setProductRating(int productRating) {
        mProductRating = productRating;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBrandName);
        dest.writeString(mPrice);
        dest.writeString(mImageUrl);
        dest.writeParcelable(mBitmap, flags);
        dest.writeString(mAsin);
        dest.writeString(mProductUrl);
        dest.writeInt(mProductRating);
        dest.writeString(mProductName);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
