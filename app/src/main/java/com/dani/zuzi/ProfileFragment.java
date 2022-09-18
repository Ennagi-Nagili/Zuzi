package com.dani.zuzi;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.dani.zuzi.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private SearchView search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler);
        adapter = new ProductAdapter(Products.getData(), getContext());
        search = view.findViewById(R.id.search);
        search.clearFocus();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterlist(s);
                return false;
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Products products, int position) {
                if (products.getName() == "Ehtiyat hissələri") {
                    Intent intent1 = new Intent(getContext(), Spare.class);
                    getActivity().finish();
                        startActivity(intent1);
                }

                else if (products.getName() == "Sağlamlıq") {
                    Intent intent2 = new Intent(getContext(), Health.class);
                    getActivity().finish();
                        startActivity(intent2);
                }

                else if (products.getName() == "Qida") {
                    Intent intent3 = new Intent(getContext(), Food.class);
                    getActivity().finish();
                        startActivity(intent3);
                }

                else if (products.getName() == "İdman") {
                    Intent intent4 = new Intent(getContext(), Sport.class);
                    getActivity().finish();
                        startActivity(intent4);
                }

                else if(products.getName() == "Texnologiya") {
                    Intent intent5 = new Intent(getContext(), Technology.class);
                    getActivity().finish();
                        startActivity(intent5);
                }
            }
        });
    }

    public void filterlist(String text) {
        ArrayList<Products> list = new ArrayList<>();

        for (Products products: Products.getData()) {
            if (products.getName().toLowerCase().contains(text.toLowerCase())) {
                list.add(products);
            }
        }

        adapter = new ProductAdapter(list, getContext());

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Products products, int position) {
                if (products.getName() == "Ehtiyat hissələri") {
                    Intent intent1 = new Intent(getContext(), Spare.class);
                    getActivity().finish();
                    startActivity(intent1);
                }

                else if (products.getName() == "Sağlamlıq") {
                    Intent intent2 = new Intent(getContext(), Health.class);
                    getActivity().finish();
                    startActivity(intent2);
                }

                else if (products.getName() == "Qida") {
                    Intent intent3 = new Intent(getContext(), Food.class);
                    getActivity().finish();
                    startActivity(intent3);
                }

                else if (products.getName() == "İdman") {
                    Intent intent4 = new Intent(getContext(), Sport.class);
                    getActivity().finish();
                    startActivity(intent4);
                }

                else if(products.getName() == "Texnologiya") {
                    Intent intent5 = new Intent(getContext(), Technology.class);
                    getActivity().finish();
                    startActivity(intent5);
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }
}