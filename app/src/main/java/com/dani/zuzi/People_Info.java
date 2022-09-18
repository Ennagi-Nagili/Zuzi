package com.dani.zuzi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class People_Info extends AppCompatActivity {
    private TextView username, email, number, cat_txt;
    private ImageView profile_image;
    private FirebaseFirestore firestore;
    private DocumentReference reference;
    private FirebaseUser user;
    private StorageReference reference2;
    private Button direct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_info);
        username = findViewById(R.id.name_txt);
        email = findViewById(R.id.email_txt);
        number = findViewById(R.id.number_txt);
        cat_txt = findViewById(R.id.cat_txt);
        profile_image = findViewById(R.id.profile_image);
        direct = findViewById(R.id.direct);
        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = firestore.collection("Users").document(user.getUid());
        reference2 = FirebaseStorage.getInstance().getReference(user.getUid() + ".jpg");

        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(People_Info.this, Direct.class);
                startActivity(intent);
            }
        });

        reference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            username.setText("İstifadəçi adı: " + documentSnapshot.getData().get("name").toString());
                            email.setText("Email: " + documentSnapshot.getData().get("email").toString());
                            number.setText("Əlaqə nömrəsi:" + documentSnapshot.getData().get("contact").toString());
                        }
                    }
                });

        reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(People_Info.this)
                        .load(uri)
                        .into(profile_image);
            }
        });
    }
}