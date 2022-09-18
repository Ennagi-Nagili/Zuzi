package com.dani.zuzi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.dani.zuzi.R;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Details extends AppCompatActivity {
    private ImageView detail_image;
    private TextView detail_name, detail_price, detail_describe;
    private EditText comment;
    private Button ok;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private HashMap<String, Object> data;
    private Intent intent;
    private RecyclerView recycler;
    private CommentAdapter adapter;
    private ArrayList<Comment> userList;
    private DocumentReference reference2;
    private DatabaseReference reference;
    private DatabaseReference reference3;
    private DatabaseReference reference4;
    private Button back;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ImageView map;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = this.getSharedPreferences("Zuzi", MODE_PRIVATE);
        editor = preferences.edit();

        setContentView(R.layout.activity_details);
        detail_image = findViewById(R.id.detail_image);
        detail_name = findViewById(R.id.detail_name);
        detail_price = findViewById(R.id.detail_price);
        detail_describe = findViewById(R.id.detail_describe);
        comment = findViewById(R.id.comment);
        ok = findViewById(R.id.ok);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        data = new HashMap<>();
        intent = getIntent();
        recycler = findViewById(R.id.comment_recycler);
        userList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Comments");
        reference3 = reference.child(intent.getStringExtra("id"));
        reference4 = reference3.child(user.getUid());
        back = findViewById(R.id.btn_back);
        map = findViewById(R.id.map);
        reference2 = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());

        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    DatabaseReference ref = reference3.child(snap.getKey());

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            for (DataSnapshot snap1: snapshot1.getChildren()) {
                                DatabaseReference ref1 = ref.child(snap1.getKey());

                                ref1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        for (DataSnapshot snap2: snapshot2.getChildren()) {
                                            if (snap2.getKey().equals("name")) {
                                                name = snap2.getValue().toString();
                                            }
                                        }
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferences.getString("check", null) != "false") {
                    editor.putString("check", "true");
                    editor.apply();
                }
                Intent intent = new Intent(Details.this, Map.class);
                startActivity(intent);
            }
        });

        setter();

        detail_name.setText(intent.getStringExtra("name"));
        detail_price.setText(intent.getStringExtra("price"));
        detail_image.setImageResource(intent.getIntExtra("image", R.drawable.ic_launcher_background));
        detail_describe.setText(intent.getStringExtra("detail"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent.getStringExtra("activity").equals("food")) {
                    Intent intent1 = new Intent(Details.this, Food.class);
                    startActivity(intent1);
                } else if (intent.getStringExtra("activity").equals("health")) {
                    Intent intent1 = new Intent(Details.this, Health.class);
                    startActivity(intent1);
                } else if (intent.getStringExtra("activity").equals("spare")) {
                    Intent intent1 = new Intent(Details.this, Spare.class);
                    startActivity(intent1);
                } else if (intent.getStringExtra("activity").equals("sport")) {
                    Intent intent1 = new Intent(Details.this, Sport.class);
                    startActivity(intent1);
                } else if (intent.getStringExtra("activity").equals("technology")) {
                    Intent intent1 = new Intent(Details.this, Technology.class);
                    startActivity(intent1);
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok();
                startActivity(getIntent());
            }
        });

        recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(Details.this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);

        detail_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Details.this, Zoom.class);
                intent1.putExtra("zoom", intent.getIntExtra("image", R.drawable.ic_launcher_background));
                startActivity(intent1);
            }
        });
    }

    public void ok() {
        if (!TextUtils.isEmpty(comment.getText().toString())) {
            data.put("comment", comment.getText().toString());
            data.put("uid", user.getUid());
            data.put("name", intent.getStringExtra("user"));

            reference2.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            System.out.println(documentSnapshot.getData().get("name"));
                            data.put("name", documentSnapshot.getData().get("name"));
                        }
                    });

            Random random = new Random();

            reference4.child(String.valueOf(random.nextInt())).setValue(data)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Details.this, "Şərhiniz göndərildi", Toast.LENGTH_SHORT).show();
                            setter();
                        }
                    });
        }
    }

    public void setter() {
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    DatabaseReference ref = reference3.child(snap.getKey());

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            for (DataSnapshot snap2 : snapshot2.getChildren()) {
                                DatabaseReference ref2 = ref.child(snap2.getKey());

                                ref2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot3) {
                                        for (DataSnapshot snap3 : snapshot3.getChildren()) {
                                            Comment comment = new Comment();

                                            if (snap3.getKey().equals("comment")) {
                                                comment.setComment(snap3.getValue().toString());
                                            }

                                            if (snap3.getKey().equals("name")) {
                                                comment.setName(snap3.getValue().toString());
                                            }

                                            comment.setUid(snap.getKey());
                                            comment.setName(name);

                                            userList.add(comment);
                                            break;
                                        }

                                        adapter = new CommentAdapter(userList, Details.this);
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}