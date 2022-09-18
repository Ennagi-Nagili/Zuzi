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

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportHolder> {
    private ArrayList<Sport_item> productList;
    private Context context;
    private SportAdapter.OnItemClickListener listener;

    public SportAdapter(ArrayList<Sport_item> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public SportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        return new SportHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SportHolder holder, int position) {
        Sport_item sport_item = productList.get(position);
        holder.setData(sport_item);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class SportHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public SportHolder(@NonNull View itemView) {
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

        public void setData(Sport_item sport_item) {
            this.name.setText(sport_item.getName());
            this.image.setImageResource(sport_item.getImage());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Sport_item sport_item, int position);
    }

    public void setOnItemClickListener(SportAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
