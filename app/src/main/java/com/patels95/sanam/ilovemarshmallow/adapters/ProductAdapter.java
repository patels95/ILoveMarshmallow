package com.patels95.sanam.ilovemarshmallow.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.patels95.sanam.ilovemarshmallow.model.Product;
import com.patels95.sanam.ilovemarshmallow.R;
import com.patels95.sanam.ilovemarshmallow.ui.ProductInfoActivity;

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
        return mProducts.length;
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

            mBrandName.setText(product.getBrandName());
            mProductName.setText(product.getProductName());
            mPrice.setText(product.getPrice());
            mProductImage.setImageBitmap(product.getBitmap());

        }

        @Override
        public void onClick(View v) {
            TextView viewProductName = (TextView) v.findViewById(R.id.productName);
            String asin = "";
            for (int i = 0; i < mProducts.length; i++){
                if (mProducts[i].getProductName() == viewProductName.getText()){
                    asin = mProducts[i].getAsin();
                }
            }
            Intent intent = new Intent(mContext, ProductInfoActivity.class);
            intent.putExtra(ProductInfoActivity.PRODUCT_ASIN, asin);
            mContext.startActivity(intent);
        }
    }
}
