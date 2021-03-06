package com.example.notecook.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import org.parceler.Parcel;

@Parcel
public class Fav {

    protected String item_title;
    protected String item_author;
    protected String key_id;
    //protected ParseFile item_image;
    protected byte[] item_image;
    protected String image_url;
    protected String item_type;
    protected String item_time;
    protected String ingredientsList;
    protected String instructions;
    protected String fav_status;

    public Fav() {
    }

    public Fav(String item_title, String key_id, byte[] item_image, String item_type, String author, String time, String ingredientsList, String instructions, String fav_status) {
        this.item_title = item_title;
        this.key_id = key_id;
        this.item_image = item_image;
        this.item_type = item_type; // 20 for post, 10 for recipe
        this.item_author = author;
        this.item_time = time;
        this.ingredientsList = ingredientsList;
        this.instructions = instructions;
        this.fav_status = fav_status;
    }

    public Fav(String title, String id, String image, String item_type, String author, String time, String ingredientsList, String instructions, String fav_status) {
        this.item_title = title;
        this.key_id = id;
        this.image_url = image;
        this.item_type = item_type; // 20 for post, 10 for recipe
        this.item_author = author;
        this.item_time = time;
        this.ingredientsList = ingredientsList;
        this.instructions = instructions;
        this.fav_status = fav_status;
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

    public String getImage_url() {
        return image_url;
    }

    public String getItem_type() {
        return item_type;
    }

    public String getIngredients() {
        return ingredientsList;
    }

    public String getSteps() {
        return instructions;
    }

    public void setFav_status(String favStatus) {
        fav_status = favStatus;
    }

    public String getFav_status() {
        return fav_status;
    }

    public String getItem_time() {
        return item_time;
    }
}
