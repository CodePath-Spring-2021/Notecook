package com.example.notecook.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Parcel
public class Recipes {
    String title;
    String sourceUrl;
    String image;
    int readyInMinutes;
    List<String> instructions = new ArrayList<>();
    String author;
    List<String> ingredientName = new ArrayList<>();

    // empty constructor needed by the Parceler library
    public Recipes() {
    }

    public Recipes(JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        image = jsonObject.getString("image");
        readyInMinutes = jsonObject.getInt("readyInMinutes");
        author = jsonObject.getString("creditsText");
        sourceUrl = jsonObject.getString("sourceUrl");
        JSONArray ingredients = jsonObject.getJSONArray("extendedIngredients");
        for(int i = 0; i < ingredients.length(); i++) {
            JSONObject ingredientsObject = ingredients.getJSONObject(i);
            ingredientName.add(ingredientsObject.getString("original"));
        }
        JSONArray analyzedInstructions = jsonObject.getJSONArray("analyzedInstructions");
        JSONObject object = analyzedInstructions.getJSONObject(0);
        JSONArray steps = object.getJSONArray("steps");
        for(int j = 0; j < steps.length(); j++) {
            instructions.add(steps.getJSONObject(j).getString("step"));
        }
    }

    public static List<Recipes> RecipeList(JSONObject jsonObject) throws JSONException {
        List<Recipes> recipes = new ArrayList<>();
        recipes.add(new Recipes(jsonObject));
        return recipes;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getIngredientName() {
        return ingredientName;
    }

    public List<String> getInstructions() {
        return instructions;
    }
}
