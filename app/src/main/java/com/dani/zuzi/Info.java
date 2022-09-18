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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Info extends AppCompatActivity {
    private TextView username, email, number, cat_txt;
    private ImageView profile_image;
    private FirebaseFirestore firestore;
    private DocumentReference reference;
    private DatabaseReference ref, ref2;
    private FirebaseUser user;
    private StorageReference reference2;
    private Button direct;
    private RecyclerView recycler;
    private CategoryAdapter adapter;
    private ArrayList<Products> list;
    private Button add, delete;
    private ArrayList<Products> lists;
    private ArrayList<Products> cat_list;
    private CategoryAdapter adapter1;
    private RecyclerView recycler1;
    private ArrayList<Spinner> spinners;
    private ArrayList<Spin> spins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
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
        ref = FirebaseDatabase.getInstance().getReference("Category").child(user.getUid());
        recycler = findViewById(R.id.category_recycler);
        list = new ArrayList<>();
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        lists = new ArrayList<>();
        cat_list = new ArrayList<>();
        spinners = new ArrayList<>();
        spins = new ArrayList<>();

        recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(Info.this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);

        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Info.this, Direct.class);
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
                Glide.with(Info.this)
                        .load(uri)
                        .into(profile_image);
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    ref2 = ref.child(snap.getKey());

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

                    ref2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            for (DataSnapshot snap2: snapshot1.getChildren()) {
                                Products products = new Products();
                                products.setName(snap2.getValue().toString());

                                for (int i = 0; i < texts.length; i++) {
                                    if (snap2.getValue().toString().equals(texts[i])) {
                                        products.setImage(images[i]);
                                    }
                                }

                                list.add(products);
                            }

                            adapter = new CategoryAdapter(list, Info.this);
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.removeValue();
                finish();
                startActivity(getIntent());
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert1 = new AlertDialog.Builder(Info.this);

                LayoutInflater inflater = LayoutInflater.from(Info.this);
                final View view1 = inflater.inflate(R.layout.alert, null);
                alert1.setView(view1);

                alert1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        startActivity(getIntent());
                        dialogInterface.dismiss();
                    }
                });

                recycler1 = view1.findViewById(R.id.spin_recycler);
                recycler1.setHasFixedSize(true);
                LinearLayoutManager manager = new LinearLayoutManager(Info.this, LinearLayoutManager.VERTICAL, false);
                recycler1.setLayoutManager(manager);

                ArrayList<Products> list1 = new ArrayList();
                ArrayList<Products> list2 = new ArrayList();
                ArrayList<Products> list3 = new ArrayList();
                ArrayList<Products> list4 = new ArrayList();
                ArrayList<Products> list5 = new ArrayList();

                String[] texts1 = {"Ehtiyat hissələri", "Daxili hissələr", "Xarici hissələr", "Aksesuarlar"};
                String[] texts2 = {"Sağlamlıq", "Dərman", "Vitamin", "Tibb texnikası"};
                String[] texts3 = {"Qida", "Çin mətbəxi", "İtalyan mətbəxi", "Azərbaycan mətbəxi"};
                String[] texts4 = {"İdman", "Futbol", "Boks", "Üzgüçülük"};
                String[] texts5 = {"Texnologiya", "Laptoplar", "Qulaqlıqlar", "Monitorlar"};

                Integer[] images3 = {R.drawable.image3, R.drawable.china, R.drawable.italia, R.drawable.azerbaijan};
                Integer[] images2 = {R.drawable.image2, R.drawable.drug, R.drawable.vita, R.drawable.termo};
                Integer[] images1 = {R.drawable.image1, R.drawable.inner, R.drawable.outter, R.drawable.accesorie};
                Integer[] images4 = {R.drawable.image4, R.drawable.soccer, R.drawable.box, R.drawable.swimming};
                Integer[] images5 = {R.drawable.image5, R.drawable.laptop, R.drawable.head, R.drawable.monitor};

                filler(list1, texts1, images1);
                filler(list2, texts2, images2);
                filler(list3, texts3, images3);
                filler(list4, texts4, images4);
                filler(list5, texts5, images5);

                Spinner spinner1 = new Spinner(Info.this);
                Spinner spinner2 = new Spinner(Info.this);
                Spinner spinner3 = new Spinner(Info.this);
                Spinner spinner4 = new Spinner(Info.this);
                Spinner spinner5 = new Spinner(Info.this);

                DropAdapter adapter = new DropAdapter(list1, Info.this);
                DropAdapter adapter2 = new DropAdapter(list2, Info.this);
                DropAdapter adapter3 = new DropAdapter(list3, Info.this);
                DropAdapter adapter4 = new DropAdapter(list4, Info.this);
                DropAdapter adapter5 = new DropAdapter(list5, Info.this);

//                spin(spinner1, "Ehtiyat hissələri");
//                spin(spinner2, "Sağlamlıq");
//                spin(spinner3, "Qida");
//                spin(spinner4, "İdman");
//                spin(spinner5, "Texnologiya");

                spinner1.setAdapter(adapter);
                spinners.add(spinner1);
                spinner2.setAdapter(adapter2);
                spinners.add(spinner2);
                spinner3.setAdapter(adapter3);
                spinners.add(spinner3);
                spinner4.setAdapter(adapter4);
                spinners.add(spinner4);
                spinner5.setAdapter(adapter5);
                spinners.add(spinner5);

                for (Spinner spinner: spinners) {
                    Spin spin = new Spin();
                    spin.setSpin(spinner);
                    spins.add(spin);
                }

                SpinAdapter adapter6 = new SpinAdapter(spins, Info.this, recycler, cat_list, list, ref);
                recycler1.setAdapter(adapter6);

                SearchView search = view1.findViewById(R.id.search);
                search.clearFocus();

                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        filterList(s, recycler1, adapter6);
                        return false;
                    }
                });

                alert1.show();
            }
        });
    }

    public void filler(ArrayList<Products> list, String[] texts, Integer[] images) {
        for (int i = 0; i < 4; i++) {
            Products products = new Products();
            products.setName(texts[i]);
            products.setImage(images[i]);
            list.add(products);
            lists.add(products);
        }
    }

    public void spin(Spinner spinner, String error) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Hello");

                Products products = (Products) adapterView.getItemAtPosition(i);

                boolean check = true;
                boolean check1 = true;

                if (products.getName().equals(error)) {
                    check1 = false;
                }

                for (int a = 0; a < cat_list.size(); a++) {
                    if (cat_list.get(a).getName().equals(products.getName())) {
                        check = false;
                    }
                }

                if (check1) {
                    if (check) {
                        cat_list.add(products);
                        adapter1 = new CategoryAdapter(cat_list, Info.this);
                        recycler.setAdapter(adapter1);
                    }

                    else {
                        Toast.makeText(Info.this, "Bir kateqoriya iki dəfə seçilə bilməz", Toast.LENGTH_SHORT).show();
                    }
                }

                adapterView.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void filterList(String text, RecyclerView recycler, SpinAdapter adapter) {
        ArrayList<Spin> list1 = new ArrayList<>();

        for (int i = 0; i < spinners.size(); i++) {
            Products products = (Products) spinners.get(i).getItemAtPosition(0);

            if (products.getName().toLowerCase().contains(text.toLowerCase())) {
                Spin spin = new Spin();
                spin.setSpin(spinners.get(i));
                list1.add(spin);
            }
        }

        adapter = new SpinAdapter(list1, Info.this, recycler1, cat_list, list, ref);

        recycler.setAdapter(adapter);
    }
}