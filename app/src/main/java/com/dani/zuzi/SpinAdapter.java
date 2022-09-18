package com.dani.zuzi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SpinAdapter extends RecyclerView.Adapter<SpinAdapter.SpinHolder> {
    private ArrayList<Spin> productList;
    private Context context;
    private RecyclerView recycler1;
    private Button signUp;
    private ArrayList<Products> cat_list;
    private ArrayList<Products> list;
    private DatabaseReference ref;

    public SpinAdapter(ArrayList<Spin> productList, Context context, RecyclerView recycler1, Button signUp, ArrayList<Products> cat_list) {
        this.productList = productList;
        this.context = context;
        this.recycler1 = recycler1;
        this.signUp = signUp;
        this.cat_list = cat_list;
    }

    public SpinAdapter(ArrayList<Spin> productList, Context context, RecyclerView recycler1, ArrayList<Products> cat_list, ArrayList<Products> list, DatabaseReference ref) {
        this.productList = productList;
        this.context = context;
        this.recycler1 = recycler1;
        this.cat_list = cat_list;
        this.list = list;
        this.ref = ref;
    }

    @NonNull
    @Override
    public SpinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.spin, parent, false);
        return new SpinHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SpinHolder holder, int position) {
        Spin spin = productList.get(position);
        holder.setData(spin);
        holder.setSelect();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class SpinHolder extends RecyclerView.ViewHolder {
        Spinner spinner;

        public SpinHolder(@NonNull View itemView) {
            super(itemView);
            spinner = itemView.findViewById(R.id.spinner);
        }

        public void setData(Spin spin) {
            this.spinner.setAdapter(spin.getSpin().getAdapter());
        }

        public void setSelect() {
            cat_list.addAll(list);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Products products = (Products) adapterView.getItemAtPosition(i);
                    CategoryAdapter adapter1;

                    boolean check = true;
                    boolean check1 = true;

                    String[] texts = {"Ehtiyat hissələri", "Sağlamlıq", "Qida",
                            "İdman", "Texnologiya"};

                    for (String text: texts) {
                        if (products.getName().equals(text)) {
                            check1 = false;
                        }
                    }

                    for (int a = 0; a < cat_list.size(); a++) {
                        if (cat_list.get(a).getName().equals(products.getName())) {
                            check = false;
                        }
                    }

                    if (check1) {
                        if (check) {
                            cat_list.add(products);
                            adapter1 = new CategoryAdapter(cat_list, context);
                            recycler1.setAdapter(adapter1);

                            HashMap<String, Object> data = new HashMap<>();
                            data.put("category", products.getName());
                            Random random = new Random();
                            ref.child(String.valueOf(random.nextInt())).setValue(data);
                        }

                        else {
                            Toast.makeText(context, "Bir kateqoriya iki dəfə seçilə bilməz", Toast.LENGTH_SHORT).show();
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
}