package com.dani.zuzi;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dani.zuzi.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private ArrayList<Products> productList;
    private Context context;
    private OnItemClickListener listener;

    public ProductAdapter(ArrayList<Products> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public void setFilteredList(ArrayList<Products> filteredList) {
        productList = filteredList;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.products, parent, false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Products products = productList.get(position);
        holder.setData(products);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        TextView name;
        RelativeLayout layout;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            layout = itemView.findViewById(R.id.layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(productList.get(position), position);
                    }
                }
            });
        }

        public void setData(Products products) {
            this.name.setText(products.getName());
            this.layout.setBackgroundResource(products.getImage());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Products products, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
