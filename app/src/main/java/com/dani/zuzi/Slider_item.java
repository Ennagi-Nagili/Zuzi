package com.dani.zuzi;

public class Slider_item {
    private int image;
    private String write;

    public Slider_item(int image, String write) {
        this.image = image;
        this.write = write;
    }

    public Slider_item() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }
}
