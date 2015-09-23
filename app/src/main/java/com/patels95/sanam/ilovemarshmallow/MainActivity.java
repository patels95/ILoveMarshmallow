package com.patels95.sanam.ilovemarshmallow;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private String mUserQuery;
    private Product[] mProducts;

    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_query_hint));
        // set listener to get user's search term
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setIconified(true);
                searchView.clearFocus();
                mUserQuery = query;
                Toast.makeText(MainActivity.this, mUserQuery, Toast.LENGTH_LONG).show();
                getSearchResults(query);
                searchItem.collapseActionView();
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    private void getSearchResults(String query) {
        String url = "https://zappos.amazon.com/mobileapi/v1/search?term=" + query;

        if (isNetworkAvailable()){
            mProgressBar.setVisibility(View.VISIBLE);

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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            mProducts = parseProductInfo(jsonData);
                            Log.v(TAG, "mProducts done");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
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

    private void updateDisplay() {
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//        mRecyclerView.setLayoutManager(layoutManager);
//        ProductAdapter adapter = new ProductAdapter(MainActivity.this, mProducts);
//        mRecyclerView.setAdapter(adapter);
    }

    private Product[] parseProductInfo(String jsonData) throws JSONException {
        JSONObject result = new JSONObject(jsonData);
        JSONArray data = result.getJSONArray(getString(R.string.results_json_array));

        Product[] products = new Product[data.length()];

        for(int i = 0; i < data.length(); i++){
            JSONObject currentProduct = data.getJSONObject(i);
            Product product = new Product();

            product.setBrandName(currentProduct.getString(getString(R.string.result_brand_name)));
            product.setPrice(currentProduct.getString(getString(R.string.result_price)));
            product.setImageUrl(currentProduct.getString(getString(R.string.result_image_url)));
            product.setAsin(currentProduct.getString(getString(R.string.result_asin)));
            product.setProductUrl(currentProduct.getString(getString(R.string.result_product_url)));
            product.setProductRating(currentProduct.getInt(getString(R.string.result_product_rating)));
            product.setProductName(currentProduct.getString(getString(R.string.result_product_name)));

            products[i] = product;
        }

        return products;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
