package com.dani.zuzi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.dani.zuzi.R;

public class MainActivity extends AppCompatActivity {
    private TextView zuzi;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zuzi = findViewById(R.id.zuzi);
        auth = FirebaseAuth.getInstance();

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        zuzi.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                remember();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void remember() {
        SharedPreferences preferences = getSharedPreferences("com.dani.Zuzi", MODE_PRIVATE);
        String email = preferences.getString("email", null);
        String password = preferences.getString("password", null);

        if (email != null && password != null) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            finish();
                            startActivity(intent);
                        }
                    }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        else  {
            Intent intent = new Intent(MainActivity.this, Question.class);
            finish();
            startActivity(intent);
        }
    }
}