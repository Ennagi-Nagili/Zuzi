package com.dani.zuzi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dani.zuzi.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {
    private ArrayList<Food_item> productList;
    private Context context;
    private FoodAdapter.OnItemClickListener listener;

    public FoodAdapter(ArrayList<Food_item> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        return new FoodHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        Food_item food_item = productList.get(position);
        holder.setData(food_item);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            image = itemView.findViewById(R.id.item_image);

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

        public void setData(Food_item food_item) {
            this.name.setText(food_item.getName());
            this.image.setImageResource(food_item.getImage());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Food_item food_item, int position);
    }

    public void setOnItemClickListener(FoodAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}