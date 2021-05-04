package com.example.notecook.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.notecook.R;
import com.parse.ParseException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Parcel
public class Recipes {
    String title;
    String sourceUrl;
    String image;
    int readyInMinutes;
    int recipeId;
    List<String> instructions = new ArrayList<>();
    String author;
    List<String> ingredientName = new ArrayList<>();
    String KEY_FAVSTATUS = "0";

    // empty constructor needed by the Parceler library
    public Recipes() {
    }



    public Recipes(JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        image = jsonObject.getString("image");
        recipeId = jsonObject.getInt("id");
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

    /*
    public Recipes(String title, String author, byte[] image, String time, String instructions, String ingredientsList, String recipeId, String fav_status) {
        this.title = title;
        this.author = author;
        this.image = image;
        this.readyInMinutes = Integer.parseInt(time);
        this.instructions = Arrays.asList(instructions.split("\\r?\\n"));
        this.ingredientName = Arrays.asList(ingredientsList.split("\\r?\\n"));;
        this.recipeId = Integer.parseInt(recipeId);
        this.KEY_FAVSTATUS = fav_status;
    }
    */

    public static List<Recipes> RecipeList(JSONObject jsonObject) throws JSONException {
        List<Recipes> recipes = new ArrayList<>();
        if (jsonObject.getJSONArray("analyzedInstructions").length() == 0) {
            Log.i("Recipes", "No displaying recipes without instructions");
        } else {
            recipes.add(new Recipes(jsonObject));
        }
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

    public void setFavStatus(String favStatus) {
        KEY_FAVSTATUS = favStatus;
    }

    public String getFavStatus() {
        return KEY_FAVSTATUS;
    }

    public String getId() {
        return recipeId + "";
    }

}
