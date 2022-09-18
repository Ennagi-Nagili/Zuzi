package com.dani.zuzi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private ArrayList<Products> productList;
    private Context context;
    private OnItemClickListener listener;

    public CategoryAdapter(ArrayList<Products> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.products, parent, false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Products products = productList.get(position);
        holder.setData(products);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);

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
            this.image.setImageResource(products.getImage());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Products products, int position);
    }

    public void setOnItemClickListener(CategoryAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
