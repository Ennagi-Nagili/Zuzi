package com.dani.zuzi;

import com.dani.zuzi.R;

import java.util.ArrayList;

public class Technology_item {
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

    static public ArrayList<Technology_item> getData() {
        ArrayList<Technology_item> productList = new ArrayList<>();

        String[] names = {"Laptop", "Qulaqlıq", "Klaviatura"};
        int[] images = {R.drawable.technology1, R.drawable.technology2, R.drawable.technology3};
        String[] prices = {"1550 azn", "45 azn", "30 azn"};

        for (int i = 0; i < images.length; i++) {
            Technology_item technology_item = new Technology_item();
            technology_item.setImage(images[i]);
            technology_item.setName(names[i]);
            technology_item.setPrice(prices[i]);

            productList.add(technology_item);
        }

        return productList;
    }
}
