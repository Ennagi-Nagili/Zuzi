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

public class Technology extends AppCompatActivity {
    private RecyclerView recycler;
    private TechnologyAdapter adapter;
    private RecyclerView recycler2;
    private TechnologyCompanyAdapter adapter2;
    private FirebaseFirestore firestore;
    private CollectionReference reference;
    private DocumentReference ref;
    private ArrayList<TechnologyCompany> userList;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button back;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology);
        recycler = findViewById(R.id.technology_recycler);
        adapter = new TechnologyAdapter(Technology_item.getData(), Technology.this);
        recycler2 = findViewById(R.id.technology_company_recycler);
        userList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Users");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        back = findViewById(R.id.btn_back);
        preferences = getSharedPreferences("Zuzi", MODE_PRIVATE);
        editor = preferences.edit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Technology.this, Home.class);
                startActivity(intent);
            }
        });

        recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(Technology.this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        recycler2.setHasFixedSize(true);
        LinearLayoutManager manager2 = new LinearLayoutManager(Technology.this, LinearLayoutManager.VERTICAL, false);
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
                                                TechnologyCompany technologyCompany = new TechnologyCompany();
                                                technologyCompany.setCompany(documentSnapshot1.getData().get("company").toString());
                                                technologyCompany.setCeo(documentSnapshot1.getData().get("name").toString());
                                                technologyCompany.setImage(R.drawable.ic_profile);
                                                technologyCompany.setUid(documentSnapshot1.getData().get("uid").toString());

                                                userList.add(technologyCompany);
                                            }

                                            adapter2 = new TechnologyCompanyAdapter(userList, Technology.this);
                                            recycler2.setAdapter(adapter2);

                                            adapter2.setOnItemClickListener(new TechnologyCompanyAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(TechnologyCompany technologyCompany, int position) {
                                                    Intent intent = new Intent(Technology.this, Company_Profile.class);
                                                    intent.putExtra("name", technologyCompany.getCompany());
                                                    intent.putExtra("ceo", technologyCompany.getCeo());
                                                    intent.putExtra("uid", technologyCompany.getUid());
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    });
                        }
                    }
                });

        adapter.setOnItemClickListener(new TechnologyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Technology_item technology_item, int position) {
                if (preferences.getString("check", null) != "false") {
                    editor.putString("check", "true");
                    editor.apply();
                }

                if (technology_item.getName() == "Laptop") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Technology.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +technology_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +technology_item.getPrice());
                                    intent.putExtra("image", technology_item.getImage());
                                    intent.putExtra("detail", "Bu bir laptopdur");
                                    intent.putExtra("id", "laptop");
                                    intent.putExtra("activity", "technology");
                                    intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }

                else if (technology_item.getName() == "Qulaqlıq") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Technology.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +technology_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +technology_item.getPrice());
                                    intent.putExtra("image", technology_item.getImage());
                                    intent.putExtra("detail", "Bu bir qulaqlıqdır");
                                    intent.putExtra("id", "ear");
                                    intent.putExtra("activity", "technology");
                                    intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }

                else if (technology_item.getName() == "Klaviatura") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Technology.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +technology_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +technology_item.getPrice());
                                    intent.putExtra("image", technology_item.getImage());
                                    intent.putExtra("detail", "Bu bir klaviaturadır");
                                    intent.putExtra("id", "keyboard");
                                    intent.putExtra("activity", "technology");
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