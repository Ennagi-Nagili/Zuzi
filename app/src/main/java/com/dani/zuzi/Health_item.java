package com.dani.zuzi;

import com.dani.zuzi.R;

import java.util.ArrayList;

public class Health_item {
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

    static public ArrayList<Health_item> getData() {
        ArrayList<Health_item> productList = new ArrayList<>();

        String[] names = {"Termometr", "Vitamin", "Tibbi iynə"};
        int[] images = {R.drawable.health1, R.drawable.health2, R.drawable.health3};
        String[] prices = {"5 azn", "11 azn", "1 azn"};

        for (int i = 0; i < images.length; i++) {
            Health_item health_item = new Health_item();
            health_item.setImage(images[i]);
            health_item.setName(names[i]);
            health_item.setPrice(prices[i]);

            productList.add(health_item);
        }

        return productList;
    }
}
