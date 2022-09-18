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

public class Spare extends AppCompatActivity {
    private RecyclerView recycler;
    private SpareAdapter adapter;
    private RecyclerView recycler2;
    private SpareCompanyAdapter adapter2;
    private FirebaseFirestore firestore;
    private CollectionReference reference;
    private DocumentReference ref;
    private ArrayList<SpareCompany> userList;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button btn_back;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare);
        recycler = findViewById(R.id.spare_recycler);
        adapter = new SpareAdapter(Spare_item.getData(), Spare.this);
        recycler2 = findViewById(R.id.spare_company_recycler);
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
                Intent intent = new Intent(Spare.this, Home.class);
                startActivity(intent);
            }
        });

        recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(Spare.this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        recycler2.setHasFixedSize(true);
        LinearLayoutManager manager2 = new LinearLayoutManager(Spare.this, LinearLayoutManager.VERTICAL, false);
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
                                                SpareCompany spareCompany = new SpareCompany();
                                                spareCompany.setCompany(documentSnapshot1.getData().get("company").toString());
                                                spareCompany.setCeo(documentSnapshot1.getData().get("name").toString());
                                                spareCompany.setImage(R.drawable.ic_profile);
                                                spareCompany.setUid(documentSnapshot1.getData().get("uid").toString());

                                                userList.add(spareCompany);
                                            }

                                            adapter2 = new SpareCompanyAdapter(userList, Spare.this);
                                            recycler2.setAdapter(adapter2);

                                            adapter2.setOnItemClickListener(new SpareCompanyAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(SpareCompany spareCompany, int position) {
                                                    Intent intent = new Intent(Spare.this, Company_Profile.class);
                                                    intent.putExtra("name", spareCompany.getCompany());
                                                    intent.putExtra("ceo", spareCompany.getCeo());
                                                    intent.putExtra("uid", spareCompany.getUid());
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    });
                        }
                    }
                });

        adapter.setOnItemClickListener(new SpareAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Spare_item spare_item, int position) {
                if (preferences.getString("check", null) != "false") {
                    editor.putString("check", "true");
                    editor.apply();
                }

                if (spare_item.getName() == "Təkər") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Spare.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +spare_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +spare_item.getPrice());
                                    intent.putExtra("image", spare_item.getImage());
                                    intent.putExtra("detail", "Bu bir təkərdir");
                                    intent.putExtra("id", "wheel");
                                    intent.putExtra("activity", "spare");
                                    intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }

                else if (spare_item.getName() == "Motor") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Spare.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +spare_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +spare_item.getPrice());
                                    intent.putExtra("image", spare_item.getImage());
                                    intent.putExtra("detail", "Bu bir motordur");
                                    intent.putExtra("id", "motor");
                                    intent.putExtra("activity", "spare");
                                    intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }

                else if (spare_item.getName() == "İnjektor") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Spare.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +spare_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +spare_item.getPrice());
                                    intent.putExtra("image", spare_item.getImage());
                                    intent.putExtra("detail", "Bu bir injektordur");
                                    intent.putExtra("id", "injector");
                                    intent.putExtra("activity", "spare");
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