package com.dani.zuzi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Company_Profile extends AppCompatActivity {
    private TextView name_txt, ceo_txt;
    private Button direct;
    private RecyclerView recycler;
    private CategoryAdapter adapter;
    private ArrayList<Products> list;
    private DatabaseReference reference;
    private ImageView imageView;
    private StorageReference reference2;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        Intent intent = getIntent();
        name_txt = findViewById(R.id.name_txt);
        ceo_txt = findViewById(R.id.ceo_txt);
        direct = findViewById(R.id.direct);
        recycler = findViewById(R.id.category_recycler);
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Category").child(intent.getStringExtra("uid"));
        imageView = findViewById(R.id.profile_image);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference2 = FirebaseStorage.getInstance().getReference(user.getUid() + ".jpg");

        reference2.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(Company_Profile.this)
                                .load(uri)
                                .into(imageView);
                    }
                });

        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Company_Profile.this, Direct.class);
                startActivity(intent);
            }
        });

        name_txt.setText("Şirkət adı: " +intent.getStringExtra("name"));
        ceo_txt.setText("Ceo: " +intent.getStringExtra("ceo"));

        recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(Company_Profile.this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    DatabaseReference reference2 = reference.child(snap.getKey());

                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            String[] texts = {"Ehtiyat hissələri", "Daxili hissələr", "Xarici hissələr", "Aksesuarlar",
                                    "Sağlamlıq", "Dərman", "Vitamin", "Tibb texnikası",
                                    "Qida", "Çin mətbəxi", "İtalyan mətbəxi", "Azərbaycan mətbəxi",
                                    "İdman", "Futbol", "Boks", "Üzgüçülük",
                                    "Texnologiya", "Laptoplar", "Qulaqlıqlar", "Monitorlar"};

                            Integer[] images = {R.drawable.image1, R.drawable.inner, R.drawable.outter, R.drawable.accesorie,
                                    R.drawable.image2, R.drawable.drug, R.drawable.vita, R.drawable.termo,
                                    R.drawable.image3, R.drawable.china, R.drawable.italia, R.drawable.azerbaijan,
                                    R.drawable.image4, R.drawable.soccer, R.drawable.box, R.drawable.swimming,
                                    R.drawable.image5, R.drawable.laptop, R.drawable.head, R.drawable.monitor};

                            for (DataSnapshot snap1: snapshot1.getChildren()) {
                                Products products = new Products();
                                products.setName(snap1.getValue().toString());

                                for (int i = 0; i < texts.length; i++) {
                                    if (snap1.getValue().toString().equals(texts[i])) {
                                        products.setImage(images[i]);
                                    }
                                }

                                list.add(products);
                            }

                            adapter = new CategoryAdapter(list, Company_Profile.this);
                            recycler.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}