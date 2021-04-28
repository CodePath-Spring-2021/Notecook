package com.example.notecook.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Fav {

    protected String item_title;
    protected String item_author;
    protected String key_id;
    //protected ParseFile item_image;
    private byte[] item_image;

    public Fav() {
    }

    public Fav(String item_title, String key_id, byte[] item_image) {
        this.item_title = item_title;
        this.key_id = key_id;
        this.item_image = item_image;
    }

    public String getItem_author() {
        return item_author;
    }

    public void setItem_author(String item_author) {
        this.item_author = item_author;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public Bitmap getItem_image() {
        Bitmap image = BitmapFactory.decodeByteArray(this.item_image, 0, this.item_image.length);
        return image;
    }
}
