package com.dani.zuzi;

import com.dani.zuzi.R;

import java.util.ArrayList;

public class Sport_item {
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

    static public ArrayList<Sport_item> getData() {
        ArrayList<Sport_item> productList = new ArrayList<>();

        String[] names = {"Top", "Ağırlıq", "Boks əlcəyi"};
        int[] images = {R.drawable.sport1, R.drawable.sport2, R.drawable.sport3};
        String[] prices = {"4 azn", "13 azn", "10 azn"};

        for (int i = 0; i < images.length; i++) {
            Sport_item sport_item = new Sport_item();
            sport_item.setImage(images[i]);
            sport_item.setName(names[i]);
            sport_item.setPrice(prices[i]);

            productList.add(sport_item);
        }

        return productList;
    }
}
