package com.patels95.sanam.ilovemarshmallow;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Product[] mProducts;
    private Context mContext;

    public ProductAdapter(Context context, Product[] products){
        mContext = context;
        mProducts = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_list_item, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bindProduct(mProducts[position]);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mProductImage;
        public TextView mBrandName;
        public TextView mProductName;
        public TextView mPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);

            mProductImage = (ImageView) itemView.findViewById(R.id.productImage);
            mBrandName = (TextView) itemView.findViewById(R.id.brandName);
            mProductName = (TextView) itemView.findViewById(R.id.productName);
            mPrice = (TextView) itemView.findViewById(R.id.price);

            itemView.setOnClickListener(this);
        }

        public void bindProduct(Product product){
            try {
                URL imageUrl = new URL(product.getImageUrl());
                Bitmap bm = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                mProductImage.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mBrandName.setText(product.getBrandName());
            mProductName.setText(product.getProductName());
            mPrice.setText(product.getPrice());

        }

        @Override
        public void onClick(View v) {
            TextView viewProductName = (TextView) v.findViewById(R.id.productName);
            Toast.makeText(mContext, viewProductName.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
