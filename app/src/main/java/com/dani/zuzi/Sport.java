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

public class Sport extends AppCompatActivity {
    private RecyclerView recycler;
    private SportAdapter adapter;
    private RecyclerView recycler2;
    private SportCompanyAdapter adapter2;
    private FirebaseFirestore firestore;
    private CollectionReference reference;
    private DocumentReference ref;
    private ArrayList<SportCompany> userList;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button back;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);
        recycler = findViewById(R.id.sport_recycler);
        adapter = new SportAdapter(Sport_item.getData(), Sport.this);
        recycler2 = findViewById(R.id.sport_company_recycler);
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
                Intent intent = new Intent(Sport.this, Home.class);
                startActivity(intent);
            }
        });

        recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(Sport.this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        recycler2.setHasFixedSize(true);
        LinearLayoutManager manager2 = new LinearLayoutManager(Sport.this, LinearLayoutManager.VERTICAL, false);
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
                                                SportCompany sportCompany = new SportCompany();
                                                sportCompany.setCompany(documentSnapshot1.getData().get("company").toString());
                                                sportCompany.setCeo(documentSnapshot1.getData().get("name").toString());
                                                sportCompany.setImage(R.drawable.ic_profile);
                                                sportCompany.setUid(documentSnapshot1.getData().get("uid").toString());

                                                userList.add(sportCompany);
                                            }

                                            adapter2 = new SportCompanyAdapter(userList, Sport.this);
                                            recycler2.setAdapter(adapter2);

                                            adapter2.setOnItemClickListener(new SportCompanyAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(SportCompany sportCompany, int position) {
                                                    Intent intent = new Intent(Sport.this, Company_Profile.class);
                                                    intent.putExtra("name", sportCompany.getCompany());
                                                    intent.putExtra("ceo", sportCompany.getCeo());
                                                    intent.putExtra("uid", sportCompany.getUid());
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    });
                        }
                    }
                });

        adapter.setOnItemClickListener(new SportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Sport_item sport_item, int position) {
                if (preferences.getString("check", null) != "false") {
                    editor.putString("check", "true");
                    editor.apply();
                }

                if (sport_item.getName() == "Top") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Sport.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +sport_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +sport_item.getPrice());
                                    intent.putExtra("image", sport_item.getImage());
                                    intent.putExtra("detail", "Bu bir topdur");
                                    intent.putExtra("id", "ball");
                                    intent.putExtra("activity", "sport");
                                    intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }

                else if (sport_item.getName() == "Ağırlıq") {
                    reference.document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Intent intent = new Intent(Sport.this, Details.class);
                            intent.putExtra("name", "Məhsulun adı: " +sport_item.getName());
                            intent.putExtra("price", "Məhsulun qiyməti: " +sport_item.getPrice());
                            intent.putExtra("image", sport_item.getImage());
                            intent.putExtra("detail", "Bu bir ağırlıqdır");
                            intent.putExtra("id", "weight");
                            intent.putExtra("activity", "sport");
                            intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                            finish();
                            startActivity(intent);
                        }
                    });
                }

                else if (sport_item.getName() == "Boks əlcəyi") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Sport.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +sport_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +sport_item.getPrice());
                                    intent.putExtra("image", sport_item.getImage());
                                    intent.putExtra("detail", "Bu bir boks əlcəyidir");
                                    intent.putExtra("id", "box");
                                    intent.putExtra("activity", "sport");
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