package com.patels95.sanam.ilovemarshmallow.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.patels95.sanam.ilovemarshmallow.R;
import com.patels95.sanam.ilovemarshmallow.model.ProductInfo;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductInfoActivity extends Activity {

    public static final String TAG = ProductInfoActivity.class.getSimpleName();
    public static final String PRODUCT_ASIN = "asin";
    public static final String PRODUCT_NAME = "name";

    private ProductInfo mProductInfo;

    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.infoImage) ImageView mInfoImage;
    @Bind(R.id.infoBrandName) TextView mInfoBrandName;
    @Bind(R.id.infoProductName) TextView mInfoProductName;
    @Bind(R.id.infoDescription) TextView mInfoDescription;
    @Bind(R.id.infoGenders) TextView mInfoGenders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String asin = intent.getStringExtra(PRODUCT_ASIN);
        String productName = intent.getStringExtra(PRODUCT_NAME);
        getActionBar().setTitle(productName);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setLayoutInvisible();
        getProductInfo(asin);
    }

    private void getProductInfo(String asin) {
        String url = "https://zappos.amazon.com/mobileapi/v1/product/asin/" + asin;

        if(isNetworkAvailable()){

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                    Log.e(TAG, "search request error");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            mProductInfo = parseProductInfo(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setLayoutVisible();
                                    updateDisplay(mProductInfo);
                                }
                            });
                        }
                        else {
                            Log.e(TAG, "search request error");
                        }
                    }
                    catch (IOException | JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });

        }
    }

    private ProductInfo parseProductInfo(String jsonData) throws JSONException {
        JSONObject result = new JSONObject(jsonData);

        JSONArray gendersJson = result.getJSONArray(getString(R.string.result_genders));
        String[] genders = new String[gendersJson.length()];
        for(int i = 0; i < gendersJson.length(); i++) {
            genders[i] = gendersJson.getString(i);
        }

        String description = Html.fromHtml(result.getString(getString(R.string.result_description))).toString();
        ProductInfo productInfo = new ProductInfo();

        productInfo.setBrandName(result.getString(getString(R.string.result_brand_name)));
        productInfo.setDescription(description);
        productInfo.setAsin(result.getString(getString(R.string.result_asin)));
        productInfo.setGenders(genders);
        productInfo.setType(result.getString(getString(R.string.result_product_type)));
        productInfo.setProductName(result.getString(getString(R.string.result_product_name)));
        productInfo.setImageUrl(result.getString(getString(R.string.result_default_image_url)));
        try {
            URL imageUrl = new URL(result.getString(getString(R.string.result_default_image_url)));
            Bitmap bm = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            productInfo.setBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productInfo;
    }

    private void updateDisplay(ProductInfo productInfo) {
        mInfoImage.setImageBitmap(productInfo.getBitmap());
        mInfoBrandName.setText(productInfo.getBrandName());
        mInfoProductName.setText(productInfo.getProductName());
        mInfoDescription.setText(productInfo.getDescription());
        String[] genders = productInfo.getGenders();
        String allGenders = "";
        for(int i = 0;i < genders.length; i++){
            allGenders += genders[i] + " ";
        }
        mInfoGenders.setText(allGenders);
    }

    private void setLayoutInvisible() {
        mProgressBar.setVisibility(View.VISIBLE);
        mInfoImage.setVisibility(View.INVISIBLE);
        mInfoBrandName.setVisibility(View.INVISIBLE);
        mInfoProductName.setVisibility(View.INVISIBLE);
        mInfoDescription.setVisibility(View.INVISIBLE);
        mInfoGenders.setVisibility(View.INVISIBLE);
    }

    private void setLayoutVisible() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mInfoImage.setVisibility(View.VISIBLE);
        mInfoBrandName.setVisibility(View.VISIBLE);
        mInfoProductName.setVisibility(View.VISIBLE);
        mInfoDescription.setVisibility(View.VISIBLE);
        mInfoGenders.setVisibility(View.VISIBLE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
