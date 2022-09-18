package com.dani.zuzi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.dani.zuzi.R;

public class Forget extends AppCompatActivity {
    private EditText forget_email;
    private Button reset, btn_back;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        forget_email = findViewById(R.id.forget_email);
        reset = findViewById(R.id.reset);
        auth = FirebaseAuth.getInstance();
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forget.this, Login.class);
                startActivity(intent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(forget_email.getText().toString())) {
                    auth.sendPasswordResetEmail(forget_email.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Forget.this, "Password reset email has been sent", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Forget.this, Login.class);
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }
            }
        });
    }
}