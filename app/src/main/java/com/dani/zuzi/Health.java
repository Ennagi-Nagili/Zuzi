package com.dani.zuzi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.dani.zuzi.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Health extends AppCompatActivity {
    private RecyclerView recycler;
    private HealthAdapter adapter;
    private RecyclerView recycler2;
    private HealthCompanyAdapter adapter2;
    private FirebaseFirestore firestore;
    private CollectionReference reference;
    private DocumentReference ref;
    private ArrayList<HealthCompany> userList;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button btn_back;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        recycler = findViewById(R.id.health_recycler);
        adapter = new HealthAdapter(Health_item.getData(), Health.this);
        recycler2 = findViewById(R.id.health_company_recycler);
        userList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Users");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        btn_back = findViewById(R.id.btn_back);
        preferences = getSharedPreferences("Zuzi", MODE_PRIVATE);
        editor = preferences.edit();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Health.this, Home.class);
                startActivity(intent);
            }
        });

        recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(Health.this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        recycler2.setHasFixedSize(true);
        LinearLayoutManager manager2 = new LinearLayoutManager(Health.this, LinearLayoutManager.VERTICAL, false);
        recycler2.setLayoutManager(manager2);

        reference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()) {
                            ref = documentSnapshot.getReference();

                            ref.get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot1) {
                                            if (!documentSnapshot1.getData().get("uid").toString().equals(user.getUid()) && documentSnapshot1.getData().get("type").toString().equals("company")) {
                                                HealthCompany healthCompany = new HealthCompany();
                                                healthCompany.setCompany(documentSnapshot1.getData().get("company").toString());
                                                healthCompany.setCeo(documentSnapshot1.getData().get("name").toString());
                                                healthCompany.setImage(R.drawable.ic_profile);
                                                healthCompany.setUid(documentSnapshot1.getData().get("uid").toString());

                                                userList.add(healthCompany);
                                            }

                                            adapter2 = new HealthCompanyAdapter(userList, Health.this);
                                            recycler2.setAdapter(adapter2);

                                            adapter2.setOnItemClickListener(new HealthCompanyAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(HealthCompany healthCompany, int position) {
                                                    Intent intent = new Intent(Health.this, Company_Profile.class);
                                                    intent.putExtra("name", healthCompany.getCompany());
                                                    intent.putExtra("ceo", healthCompany.getCeo());
                                                    intent.putExtra("uid", healthCompany.getUid());
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    });
                        }
                    }
                });

        adapter.setOnItemClickListener(new HealthAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Health_item health_item, int position) {
                if (preferences.getString("check", null) != "false") {
                    editor.putString("check", "true");
                    editor.apply();
                }

                if (preferences.getString("check", null) != "false") {
                    editor.putString("check", "true");
                    editor.apply();
                }


                if (health_item.getName() == "Termometr") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Health.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +health_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +health_item.getPrice());
                                    intent.putExtra("image", health_item.getImage());
                                    intent.putExtra("detail", "Bu bir termometrdir");
                                    intent.putExtra("id", "thermometer");
                                    intent.putExtra("activity", "health");
                                    intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }

                else if (health_item.getName() == "Vitamin") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Health.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +health_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +health_item.getPrice());
                                    intent.putExtra("image", health_item.getImage());
                                    intent.putExtra("detail", "Bu bir vitamindir");
                                    intent.putExtra("id", "vitamin");
                                    intent.putExtra("activity", "health");
                                    intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }

                else if (health_item.getName() == "Tibbi iynə") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Health.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +health_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +health_item.getPrice());
                                    intent.putExtra("image", health_item.getImage());
                                    intent.putExtra("detail", "Bu bir tibbi iynədir");
                                    intent.putExtra("id", "vitamin");
                                    intent.putExtra("activity", "health");
                                    intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (manager.findLastCompletelyVisibleItemPosition() < (adapter.getItemCount() - 1)) {
                    manager.smoothScrollToPosition(recycler, new RecyclerView.State(), manager.findLastCompletelyVisibleItemPosition() +1);
                }

                else if(manager.findLastCompletelyVisibleItemPosition() == (adapter.getItemCount() - 1)) {
                    manager.smoothScrollToPosition(recycler, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);
    }
}