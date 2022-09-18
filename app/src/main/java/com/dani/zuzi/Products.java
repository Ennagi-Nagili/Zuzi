package com.dani.zuzi;

import com.dani.zuzi.R;

import java.util.ArrayList;

public class Products {
    private String name;
    private int image;

    public Products(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Products() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    static public ArrayList<Products> getData() {
        ArrayList<Products> productList = new ArrayList<>();

        String[] names = {"Ehtiyat hissələri", "Sağlamlıq", "Qida", "İdman", "Texnologiya"};
        int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
                R.drawable.image5};

        for (int i = 0; i < images.length; i++) {
            Products products = new Products();
            products.setImage(images[i]);
            products.setName(names[i]);

            productList.add(products);
        }

        return productList;
    }
}
