package com.dani.zuzi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class Map extends AppCompatActivity {
    private MapView map;
    private final com.yandex.mapkit.geometry.Point location = new com.yandex.mapkit.geometry.Point(59.945933, 30.320045);
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = this.getSharedPreferences("Zuzi", MODE_PRIVATE);
        editor = preferences.edit();

        if (preferences.getString("check", null).equals("true")) {
            MapKitFactory.setApiKey("47525d59-ce41-4481-9086-94dd7fb7700d");
            MapKitFactory.initialize(this);
        }

        setContentView(R.layout.activity_map);
        map = findViewById(R.id.yandex);

        map.getMap().move(
                new CameraPosition(location, 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MapKitFactory.getInstance().onStop();
        map.onStop();
        editor.putString("check", "false");
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        map.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}