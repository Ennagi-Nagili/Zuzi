package com.dani.zuzi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.dani.zuzi.R;

public class Login extends AppCompatActivity {
    private TextView new_account, forget;
    private EditText email, password;
    private String txtEmail, txtPassword;
    private Button login;
    private CheckBox check;
    private FirebaseAuth auth;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new_account = findViewById(R.id.new_account);
        forget = findViewById(R.id.forget);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        check = findViewById(R.id.check);
        login = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();
        preferences = this.getSharedPreferences("com.dani.Zuzi", MODE_PRIVATE);
        editor = preferences.edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Question.class);
                finish();
                startActivity(intent);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Forget.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void login() {
        txtEmail = email.getText().toString();
        txtPassword = password.getText().toString();

        if (!TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtPassword)) {
            auth.signInWithEmailAndPassword(txtEmail, txtPassword)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(Login.this, Home.class);
                            finish();
                            startActivity(intent);
                        }
                    }).addOnFailureListener(Login.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (check.isChecked()) {
            editor.putString("email", email.getText().toString());
            editor.putString("password", password.getText().toString());
            editor.apply();
        } else {
            editor.putString("email", null);
            editor.putString("password", null);
            editor.apply();
        }
    }
}