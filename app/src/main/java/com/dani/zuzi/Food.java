package com.dani.zuzi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.dani.zuzi.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Food extends AppCompatActivity {
    private RecyclerView recycler;
    private FoodAdapter adapter;
    private RecyclerView recycler2;
    private FoodCompanyAdapter adapter2;
    private FirebaseFirestore firestore;
    private CollectionReference reference;
    private DocumentReference ref;
    private ArrayList<FoodCompany> userList;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button btn_back;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private StorageReference reference2;
    private ArrayList<StorageReference> refList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        recycler = findViewById(R.id.food_recycler);
        adapter = new FoodAdapter(Food_item.getData(), Food.this);
        recycler2 = findViewById(R.id.food_company_recycler);
        userList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Users");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        btn_back = findViewById(R.id.btn_back);
        preferences = getSharedPreferences("Zuzi", MODE_PRIVATE);
        editor = preferences.edit();
        refList = new ArrayList<>();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Food.this, Home.class);
                startActivity(intent);
            }
        });

        recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(Food.this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        recycler2.setHasFixedSize(true);
        LinearLayoutManager manager2 = new LinearLayoutManager(Food.this, LinearLayoutManager.VERTICAL, false);
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
                                                FoodCompany foodCompany = new FoodCompany();
                                                foodCompany.setCompany(documentSnapshot1.getData().get("company").toString());
                                                foodCompany.setCeo(documentSnapshot1.getData().get("name").toString());
                                                foodCompany.setUid(documentSnapshot1.getData().get("uid").toString());
                                                //reference2 = FirebaseStorage.getInstance().getReference(documentSnapshot1.getData().get("uid").toString() + ".jpg");

                                                userList.add(foodCompany);
                                            }

                                            refList.add(reference2);

                                            adapter2 = new FoodCompanyAdapter(userList, Food.this, refList);
                                            recycler2.setAdapter(adapter2);

                                            adapter2.setOnItemClickListener(new FoodCompanyAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(FoodCompany foodCompany, int position) {
                                                    Intent intent = new Intent(Food.this, Company_Profile.class);
                                                    intent.putExtra("name", foodCompany.getCompany());
                                                    intent.putExtra("ceo", foodCompany.getCeo());
                                                    intent.putExtra("uid", foodCompany.getUid());
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    });
                        }
                    }
                });

        adapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food_item food_item, int position) {
                if (preferences.getString("check", null) != "false") {
                    editor.putString("check", "true");
                    editor.apply();
                }

                if (food_item.getName() == "Hamburger") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Food.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +food_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +food_item.getPrice());
                                    intent.putExtra("image", food_item.getImage());
                                    intent.putExtra("detail", "Bu bir hamburgerdir");
                                    intent.putExtra("id", "hamburger");
                                    intent.putExtra("activity", "food");
                                    intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }

                else if (food_item.getName() == "Toyuq səbəti") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Food.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +food_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +food_item.getPrice());
                                    intent.putExtra("image", food_item.getImage());
                                    intent.putExtra("detail", "Bu bir toyuq səbətidir");
                                    intent.putExtra("id", "chicken");
                                    intent.putExtra("activity", "food");
                                    intent.putExtra("user", documentSnapshot.getData().get("name").toString());
                                    finish();
                                    startActivity(intent);
                                }
                            });
                }

                else if (food_item.getName() == "Salat") {
                    reference.document(user.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Intent intent = new Intent(Food.this, Details.class);
                                    intent.putExtra("name", "Məhsulun adı: " +food_item.getName());
                                    intent.putExtra("price", "Məhsulun qiyməti: " +food_item.getPrice());
                                    intent.putExtra("image", food_item.getImage());
                                    intent.putExtra("detail", "Bu bir salatdır");
                                    intent.putExtra("id", "salad");
                                    intent.putExtra("activity", "food");
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