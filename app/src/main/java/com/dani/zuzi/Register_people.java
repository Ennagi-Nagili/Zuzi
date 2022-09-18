package com.dani.zuzi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.dani.zuzi.R;

import java.util.HashMap;

public class Register_people extends AppCompatActivity {
    private EditText register_email, register_password, register_name, register_repeat, register_contact;
    private TextView new_login;
    private String txt_email, txt_password, txt_name, txt_repeat, txt_contact;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button signUp;
    private HashMap<String, Object> data;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_people);

        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_name = findViewById(R.id.register_name);
        register_repeat = findViewById(R.id.register_repeat);
        new_login = findViewById(R.id.new_login);
        auth = FirebaseAuth.getInstance();
        signUp = findViewById(R.id.signUp);
        firestore = FirebaseFirestore.getInstance();
        register_contact = findViewById(R.id.register_contact);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        new_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_people.this, Login.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void register() {
        txt_email = register_email.getText().toString();
        txt_password = register_password.getText().toString();
        txt_name = register_name.getText().toString();
        txt_repeat = register_repeat.getText().toString();
        txt_contact = register_contact.getText().toString();

        if (!TextUtils.isEmpty(txt_email) && !TextUtils.isEmpty(txt_password) && !TextUtils.isEmpty(txt_name) && !TextUtils.isEmpty(txt_contact)) {
            if (txt_password.equals(txt_repeat)) {
                auth.createUserWithEmailAndPassword(txt_email, txt_password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = auth.getCurrentUser();

                                    data = new HashMap<>();
                                    data.put("name", txt_name);
                                    data.put("email", txt_email);
                                    data.put("uid", user.getUid());
                                    data.put("type", "person");
                                    data.put("contact", txt_contact);

                                    firestore.collection("Users").document(user.getUid())
                                            .set(data)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Register_people.this, "Successfully signed up", Toast.LENGTH_SHORT).show();
                                                        SharedPreferences preferences = getSharedPreferences("com.dani.Zuzi", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = preferences.edit();
                                                        editor.putString("email", txt_email);
                                                        editor.putString("password", txt_password);
                                                        editor.apply();
                                                        login();
                                                    } else {
                                                        Toast.makeText(Register_people.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(Register_people.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            else {
                Toast.makeText(Register_people.this, "Passwords didn't match.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void login() {
        txt_email = register_email.getText().toString();
        txt_password = register_password.getText().toString();
        txt_name = register_name.getText().toString();
        txt_repeat = register_repeat.getText().toString();

        if (!TextUtils.isEmpty(txt_email) && !TextUtils.isEmpty(txt_password)) {
            auth.signInWithEmailAndPassword(txt_email, txt_password)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(Register_people.this, Intro.class);
                            finish();
                            startActivity(intent);
                        }
                    }).addOnFailureListener(Register_people.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register_people.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}