package com.dani.zuzi;

import com.dani.zuzi.R;

import java.util.ArrayList;

public class Spare_item {
    private int image;
    private String name;
    private String price;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    static public ArrayList<Spare_item> getData() {
        ArrayList<Spare_item> productList = new ArrayList<>();

        String[] names = {"Təkər", "Motor", "İnjektor"};
        int[] images = {R.drawable.spare1, R.drawable.spare2, R.drawable.spare3};
        String[] prices = {"20 azn", "40 azn", "15 azn"};

        for (int i = 0; i < images.length; i++) {
            Spare_item spare_item = new Spare_item();
            spare_item.setImage(images[i]);
            spare_item.setName(names[i]);
            spare_item.setPrice(prices[i]);

            productList.add(spare_item);
        }

        return productList;
    }
}
