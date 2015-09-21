package com.patels95.sanam.ilovemarshmallow;

public class Product {

    private String mBrandName;
    private String mPrice;
    private String mImageUrl;
    private String mAsin;
    private String mProductUrl;
    private int mProductRating;
    private String mProductName;

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
}
