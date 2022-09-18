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

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.HealthHolder> {
    private ArrayList<Health_item> productList;
    private Context context;
    private HealthAdapter.OnItemClickListener listener;

    public HealthAdapter(ArrayList<Health_item> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public HealthHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        return new HealthHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthHolder holder, int position) {
        Health_item health_item = productList.get(position);
        holder.setData(health_item);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HealthHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public HealthHolder(@NonNull View itemView) {
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

        public void setData(Health_item health_item) {
            this.name.setText(health_item.getName());
            this.image.setImageResource(health_item.getImage());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Health_item health_item, int position);
    }

    public void setOnItemClickListener(HealthAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
