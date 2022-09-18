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

public class SpareAdapter extends RecyclerView.Adapter<SpareAdapter.SpareHolder> {
    private ArrayList<Spare_item> productList;
    private Context context;
    private SpareAdapter.OnItemClickListener listener;

    public SpareAdapter(ArrayList<Spare_item> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public SpareHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        return new SpareHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SpareHolder holder, int position) {
        Spare_item spare_item = productList.get(position);
        holder.setData(spare_item);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class SpareHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public SpareHolder(@NonNull View itemView) {
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

        public void setData(Spare_item spare_item) {
            this.name.setText(spare_item.getName());
            this.image.setImageResource(spare_item.getImage());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Spare_item spare_item, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
