package com.example.notecook.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.notecook.R;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {
    public String KEY_TITLE= "title";
    public String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public String KEY_TIME = "readyInMinutes";
    //public String KEY_ID = "objectId";
    public String KEY_INGREDIENTSLIST = "ingredients";
    public String KEY_INSTRUCTIONS = "instructions";
    public String KEY_FAVSTATUS = "favStatus";

    public Post() {
    }

    /*
    public Post(String title, String author, String imageUrl, String time, String instructions, String ingredientsList, String recipeId, String fav_status) {
        setRecipeTitle(title);

        setCookTime(time);
        setRecipeInstructions(instructions);
        setIngredientsList(ingredientsList);

        setFavStatus(fav_status);
        this.author = author;
        this.image = image;
        this.instructions = Arrays.asList(instructions.split("\\r?\\n"));
        this.ingredientName = Arrays.asList(ingredientsList.split("\\r?\\n"));;
        this.recipeId = Integer.parseInt(recipeId);
        this.KEY_FAVSTATUS = fav_status;
    }
    */

    public byte[] getImageResource() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Bitmap bit = null;
        try {
            bit = BitmapFactory.decodeFile(this.getImage().getFile().getPath());
            Log.e("Recipes/Post", this.getImage().getFile().getPath());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bit.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] imageInByte = outputStream.toByteArray();
        return imageInByte;
    }

    public String getRecipeTitle() {
        return getString(KEY_TITLE);
    }

    public void setRecipeTitle(String title) { put(KEY_TITLE, title);    }

    public ParseFile getImage() { return getParseFile(KEY_IMAGE); }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) { put(KEY_USER, user); }

    public String getTimestamp() { return getString(KEY_CREATED_AT); }

    public String getCookTime() { return getString(KEY_TIME); }

    public void setCookTime(String time) { put(KEY_TIME, time);    }

    public String getIngredientsList() { return getString(KEY_INGREDIENTSLIST); }

    public void setIngredientsList(String ingredientsList) { put(KEY_INGREDIENTSLIST, ingredientsList);    }

    public String getRecipeInstructions() { return getString(KEY_INSTRUCTIONS); }

    public void setRecipeInstructions(String instructions) { put(KEY_INSTRUCTIONS, instructions);    }

    public String getKEY_ID() { return getObjectId(); }

    public void setFavStatus(String favStatus) {
        put(KEY_FAVSTATUS, favStatus);
    }

    public String getFavStatus() {
        return getString(KEY_FAVSTATUS);
    }
}

