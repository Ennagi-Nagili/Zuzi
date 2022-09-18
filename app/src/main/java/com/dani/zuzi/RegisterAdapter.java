package com.dani.zuzi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.SpinHolder> {
    private ArrayList<Spin> productList;
    private Context context;
    private ArrayList<Products> cat_list;
    private DatabaseReference ref;
    private Button signUp;
    private RecyclerView recycler1;

    public RegisterAdapter(ArrayList<Spin> productList, Context context, ArrayList<Products> cat_list, DatabaseReference ref, Button signUp, RecyclerView recycler1) {
        this.productList = productList;
        this.context = context;
        this.cat_list = cat_list;
        this.ref = ref;
        this.signUp = signUp;
        this.recycler1 = recycler1;
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
                            signUp.setEnabled(true);
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
