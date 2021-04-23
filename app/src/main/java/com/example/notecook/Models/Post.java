package com.example.notecook.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_TITLE= "title";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_TIME = "readyInMinutes";
    public static final String KEY_INGREDIENTSLIST = "ingredientsList";
    public static final String KEY_INSTRUCTIONS = "instructions";

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

    public String getIngredientsList() { return KEY_INGREDIENTSLIST; }

    public void setIngredientsList(String ingredientsList) { put(KEY_INGREDIENTSLIST, ingredientsList);    }

    public String getRecipeInstructions() { return KEY_INSTRUCTIONS; }

    public void setRecipeInstructions(String instructions) { put(KEY_INSTRUCTIONS, instructions);    }
}

