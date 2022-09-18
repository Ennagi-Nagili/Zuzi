package com.dani.zuzi;

import android.content.Context;

import java.util.ArrayList;

public class Drop {
    private Integer image;
    private String text;

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static ArrayList<Drop> getData(Context context) {
        Integer[] images = {R.drawable.china, R.drawable.italia, R.drawable.azerbaijan, R.drawable.drug,
        R.drawable.vita, R.drawable.termo, R.drawable.inner, R.drawable.outter, R.drawable.accesorie,
        R.drawable.soccer, R.drawable.box, R.drawable.swimming, R.drawable.laptop, R.drawable.head,
        R.drawable.monitor};

        String[] texts = {"Çin mətbəxi", "İtalyan mətbəxi", "Azərbaycan mətbəxi", "Dərman", "Vitamin",
        "Tibb texnikası", "Daxili hissələr", "Xarici hissələr", "Aksesuarlar", "Futbol", "Boks",
        "Üzgüçülük", "Laptoplar", "Qulaqlıqlar", "Monitorlar"};

        ArrayList<Drop> list = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            Drop drop = new Drop();
            drop.setImage(images[i]);
            drop.setText(texts[i]);
            list.add(drop);
        }

        return list;
    }
}
