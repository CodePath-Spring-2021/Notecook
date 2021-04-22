package com.example.notecook.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Recipes {
    String title;
    String image;
    int readyInMinutes;
    List<String> instructions = new ArrayList<>();
    String author;
    List<String> ingredientName = new ArrayList<>();

    public Recipes(JSONObject jsonObject, List<String> stepsForOneRecipe) throws JSONException {
        title = jsonObject.getString("title");
        image = jsonObject.getString("image");
        readyInMinutes = jsonObject.getInt("readyInMinutes");
        author = jsonObject.getString("creditsText");
        JSONArray ingredients = jsonObject.getJSONArray("extendedIngredients");
        for(int i = 0; i < ingredients.length(); i++) {
            JSONObject ingredientsObject = ingredients.getJSONObject(i);
            ingredientName.add(ingredientsObject.getString("original"));
        }
        instructions = stepsForOneRecipe;
    }

    public static List<Recipes> RecipeList(JSONObject jsonObject, List<String> stepsForOneRecipe) throws JSONException {
        List<Recipes> recipes = new ArrayList<>();
        recipes.add(new Recipes(jsonObject, stepsForOneRecipe));
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
