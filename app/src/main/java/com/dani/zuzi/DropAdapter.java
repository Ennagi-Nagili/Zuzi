package com.dani.zuzi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DropAdapter extends BaseAdapter {
    private ArrayList<Products> list;
    private Context context;
    private View view;
    private ImageView image;
    private TextView text;

    public DropAdapter(ArrayList<Products> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.category, null);
        image = view.findViewById(R.id.image);
        text = view.findViewById(R.id.name);
        image.setImageResource(list.get(i).getImage());
        text.setText(list.get(i).getName());
        return view;
    }
}
