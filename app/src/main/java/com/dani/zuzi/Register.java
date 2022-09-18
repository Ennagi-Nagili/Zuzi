package com.dani.zuzi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Register extends AppCompatActivity {
    private EditText register_email, register_password, register_name, register_repeat, register_contact, register_company, register_voen;
    private TextView new_login;
    private String txt_email, txt_password, txt_name, txt_repeat, txt_contact, txt_company, txt_voen;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button signUp, btn_category;
    private HashMap<String, Object> data;
    private FirebaseFirestore firestore;
    private AlertDialog.Builder alert;
    private RecyclerView recycler1;
    private CategoryAdapter adapter1;
    private ArrayList<Category> list;
    private DatabaseReference reference;
    private String category;
    private HashMap<String, Object> data2;
    private ArrayList<Products> lists;
    private ArrayList<Products> cat_list;
    private ArrayList<Spinner> spinners;
    private RecyclerView spin_recycler;
    private ArrayList<Spin> spins;
    private DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_name = findViewById(R.id.register_name);
        register_repeat = findViewById(R.id.register_repeat);
        register_company = findViewById(R.id.register_company);
        register_voen = findViewById(R.id.register_voen);
        new_login = findViewById(R.id.new_login);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        signUp = findViewById(R.id.signUp);
        firestore = FirebaseFirestore.getInstance();
        btn_category = findViewById(R.id.btn_category);
        alert = new AlertDialog.Builder(Register.this);
        recycler1 = findViewById(R.id.reg_recycler);
        list = new ArrayList<>();
        register_contact = findViewById(R.id.register_contact);
        data2 = new HashMap<>();
        lists = new ArrayList<>();
        cat_list = new ArrayList<>();
        spinners = new ArrayList<>();
        spins = new ArrayList<>();
        reference2 = FirebaseDatabase.getInstance().getReference("Category");

        recycler1.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(Register.this, LinearLayoutManager.VERTICAL, false);
        recycler1.setLayoutManager(manager);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        new_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(Register.this);
                final View view1 = inflater.inflate(R.layout.alert, null);
                alert.setView(view1);
                alert.setCancelable(true);

                spin_recycler = view1.findViewById(R.id.spin_recycler);
                spin_recycler.setHasFixedSize(true);
                LinearLayoutManager manager = new LinearLayoutManager(Register.this, LinearLayoutManager.VERTICAL, false);
                spin_recycler.setLayoutManager(manager);

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

                Spinner spinner1 = new Spinner(Register.this);
                Spinner spinner2 = new Spinner(Register.this);
                Spinner spinner3 = new Spinner(Register.this);
                Spinner spinner4 = new Spinner(Register.this);
                Spinner spinner5 = new Spinner(Register.this);

                DropAdapter adapter = new DropAdapter(list1, Register.this);
                DropAdapter adapter2 = new DropAdapter(list2, Register.this);
                DropAdapter adapter3 = new DropAdapter(list3, Register.this);
                DropAdapter adapter4 = new DropAdapter(list4, Register.this);
                DropAdapter adapter5 = new DropAdapter(list5, Register.this);

                spin(spinner1, "Ehtiyat hissələri");
                spin(spinner2, "Sağlamlıq");
                spin(spinner3, "Qida");
                spin(spinner4, "İdman");
                spin(spinner5, "Texnologiya");

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

                RegisterAdapter adapter6 = new RegisterAdapter(spins, Register.this, cat_list, reference2, signUp, recycler1);
                spin_recycler.setAdapter(adapter6);

                SearchView search = view1.findViewById(R.id.search);
                search.clearFocus();

                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        filterList(s, spin_recycler, adapter6);
                        return false;
                    }
                });

                alert.show();
            }
        });
    }

    public void register() {
        txt_email = register_email.getText().toString();
        txt_password = register_password.getText().toString();
        txt_name = register_name.getText().toString();
        txt_repeat = register_repeat.getText().toString();
        txt_contact = register_contact.getText().toString();
        txt_company = register_company.getText().toString();
        txt_voen = register_voen.getText().toString();

        if (!TextUtils.isEmpty(txt_email) && !TextUtils.isEmpty(txt_password) && !TextUtils.isEmpty(txt_name) && !TextUtils.isEmpty(txt_contact) && !TextUtils.isEmpty(txt_company) && !TextUtils.isEmpty(txt_voen)) {
            if (txt_password.equals(txt_repeat)) {
                auth.createUserWithEmailAndPassword(txt_email, txt_password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = auth.getCurrentUser();

                                    data = new HashMap<>();
                                    data.put("name", txt_name);
                                    data.put("company", txt_company);
                                    data.put("email", txt_email);
                                    data.put("uid", user.getUid());
                                    data.put("type", "company");
                                    data.put("contact", txt_contact);
                                    data.put("voen", txt_voen);

                                    firestore.collection("Users").document(user.getUid())
                                            .set(data)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Register.this, "Successfully signed up", Toast.LENGTH_SHORT).show();
                                                        SharedPreferences preferences = getSharedPreferences("com.dani.Zuzi", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = preferences.edit();
                                                        editor.putString("email", txt_email);
                                                        editor.putString("password", txt_password);
                                                        editor.apply();
                                                        login();
                                                    } else {
                                                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                    Random random = new Random();

                                    reference = FirebaseDatabase.getInstance().getReference("Category").child(user.getUid());

                                    for (int i = 0; i < cat_list.size(); i++) {
                                        data2.put("category", cat_list.get(i).getName());
                                        reference.child(String.valueOf(random.nextInt())).setValue(data2);
                                    }

                                } else {
                                    Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            else {
                Toast.makeText(Register.this, "Passwords didn't match.", Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(Register.this, Intro.class);
                            finish();
                            startActivity(intent);
                        }
                    }).addOnFailureListener(Register.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void filterList(String text, RecyclerView recycler, RegisterAdapter adapter) {
        ArrayList<Spin> list1 = new ArrayList<>();

        for (int i = 0; i < spinners.size(); i++) {
            Products products = (Products) spinners.get(i).getItemAtPosition(0);

            if (products.getName().toLowerCase().contains(text.toLowerCase())) {
                Spin spin = new Spin();
                spin.setSpin(spinners.get(i));
                list1.add(spin);
            }
        }

        adapter = new RegisterAdapter(list1, Register.this, cat_list, reference2, signUp, recycler1);

        recycler.setAdapter(adapter);
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
                        adapter1 = new CategoryAdapter(cat_list, Register.this);
                        recycler1.setAdapter(adapter1);
                        signUp.setEnabled(true);
                    }

                    else {
                        Toast.makeText(Register.this, "Bir kateqoriya iki dəfə seçilə bilməz", Toast.LENGTH_SHORT).show();
                    }
                }

                adapterView.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}