package com.dani.zuzi;

import android.net.Uri;

import com.google.android.material.imageview.ShapeableImageView;

public class FoodCompany {
    private Uri image;
    private String company;
    private String ceo;
    private String uid;

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
