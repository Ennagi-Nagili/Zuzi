package com.dani.zuzi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dani.zuzi.R;

import java.util.ArrayList;

public class Intro extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        viewPager2 = findViewById(R.id.slider);

        ArrayList<Slider_item> slider_items = new ArrayList<>();

        int[] images = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
        String[] writes = {"Dinamik statistika", "İntensiv satışlar", "Çeşidli məhsullar"};

        for (int i = 0; i < images.length; i++) {
            slider_items.add(new Slider_item(images[i], writes[i]));
        }

        viewPager2.setAdapter(new SliderAdapter(slider_items, viewPager2));
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable, 3000);
        slideHandler.postDelayed(slideRunnable, 6000);
        slideHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intro.this, Home.class);
                finish();
                startActivity(intent);
            }
        }, 9000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }
}