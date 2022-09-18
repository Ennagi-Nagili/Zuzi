package com.dani.zuzi;

import com.dani.zuzi.R;

import java.util.ArrayList;

public class Food_item {
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

    static public ArrayList<Food_item> getData() {
        ArrayList<Food_item> productList = new ArrayList<>();

        String[] names = {"Hamburger", "Toyuq səbəti", "Salat"};
        int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3};
        String[] prices = {"5 azn", "12 azn", "8 azn"};

        for (int i = 0; i < images.length; i++) {
            Food_item food_item = new Food_item();
            food_item.setImage(images[i]);
            food_item.setName(names[i]);
            food_item.setPrice(prices[i]);

            productList.add(food_item);
        }

        return productList;
    }
}
