package com.dani.zuzi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.dani.zuzi.R;

public class Zoom extends AppCompatActivity {
    private ImageView zoom_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        Intent intent = getIntent();

        zoom_image = findViewById(R.id.zoom_image);
        zoom_image.setImageResource(intent.getIntExtra("zoom", R.drawable.ic_launcher_background));
    }
}