package com.dani.zuzi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dani.zuzi.R;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderHolder> {
    private ArrayList<Slider_item> slides;
    private ViewPager2 viewPager2;
    private ViewGroup.LayoutParams params;

    public SliderAdapter(ArrayList<Slider_item> slides, ViewPager2 viewPager2) {
        this.slides = slides;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide, parent, false);
        return new SliderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderHolder holder, int position) {
        holder.setImage(slides.get(position));
    }

    @Override
    public int getItemCount() {
        return slides.size();
    }

    class SliderHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public SliderHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slide_image);
            textView = itemView.findViewById(R.id.slide_text);
        }

        void setImage(@NonNull Slider_item slider_item) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(slider_item.getImage());
            textView.setText(slider_item.getWrite());
        }
    }
}
