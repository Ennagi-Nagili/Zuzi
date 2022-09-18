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

public class TechnologyAdapter extends RecyclerView.Adapter<TechnologyAdapter.TechnologyHolder> {
    private ArrayList<Technology_item> productList;
    private Context context;
    private TechnologyAdapter.OnItemClickListener listener;

    public TechnologyAdapter(ArrayList<Technology_item> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public TechnologyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        return new TechnologyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TechnologyHolder holder, int position) {
        Technology_item technology_item = productList.get(position);
        holder.setData(technology_item);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class TechnologyHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public TechnologyHolder(@NonNull View itemView) {
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

        public void setData(Technology_item technology_item) {
            this.name.setText(technology_item.getName());
            this.image.setImageResource(technology_item.getImage());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Technology_item technology_item, int position);
    }

    public void setOnItemClickListener(TechnologyAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
