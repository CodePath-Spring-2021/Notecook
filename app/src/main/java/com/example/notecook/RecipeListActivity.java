package com.example.notecook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.notecook.Models.Recipes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class RecipeListActivity extends AppCompatActivity {

    public static final String TAG = "RecipeListActivity";
    private RecyclerView rvRecipe;
    protected RecipesAdapter adapter;
    protected List<Recipes> recipesList;
    protected String ingredients;
    List<Integer> recipeIdList = new ArrayList<>();
    AsyncHttpClient client = new AsyncHttpClient();
    RequestParams params = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        String API_KEY = getString(R.string.API_KEY);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            ingredients = bundle.getString("ingredientsList");
        }else{
            Log.i(TAG, "Cannot get ingredientsList");
        }
        rvRecipe = findViewById(R.id.rvRecipe);
        recipesList = new ArrayList<>();
        adapter = new RecipesAdapter(this, recipesList);
        rvRecipe.setAdapter(adapter);
        rvRecipe.setLayoutManager(new LinearLayoutManager(this));
        getRecipesId(API_KEY);
    }

    protected void getRecipesId(String API_KEY) {
        params.put("ingredients", ingredients);
        params.put("apiKey", API_KEY);
        client.get("https://api.spoonacular.com/recipes/findByIngredients", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "Successful call!");
                JSONArray jsonArray = json.jsonArray;
                for(int j = 0; j < jsonArray.length(); j++) {
                    try {
                        JSONObject foundedRecipes = jsonArray.getJSONObject(j);
                        Log.d(TAG, "Results retrieved!");
                        recipeIdList.add(foundedRecipes.getInt("id"));
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON exception hit", e);
                        e.printStackTrace();
                    }
                }
                Log.i(TAG, "List: " + recipeIdList);
                getRecipes(API_KEY);
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "Failed call!" + s, throwable);
            }
        });
    }

    protected void getRecipes(String API_KEY) {
        List<String> stepsForOneRecipe = new ArrayList<>();
        for(int i = 0; i < recipeIdList.size(); i++) {
            params.put("apiKey", API_KEY);
            client.get("https://api.spoonacular.com/recipes/" + recipeIdList.get(i) + "/analyzedInstructions",
                    params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Headers headers, JSON json) {
                            Log.i(TAG, "Successful call for instructions!");
                            JSONArray jsonArray = json.jsonArray;
                            try {
                                JSONObject recipe = jsonArray.getJSONObject(0);
                                JSONArray stepsArray = recipe.getJSONArray("steps");
                                for(int j = 0; j < stepsArray.length(); j++) {
                                    stepsForOneRecipe.add(stepsArray.getJSONObject(j).getString("step"));
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON exception hit", e);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                            Log.e(TAG, "Fail call" + s, throwable);
                        }
                    });
            client.get("https://api.spoonacular.com/recipes/" + recipeIdList.get(i) + "/information"
                    , params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Headers headers, JSON json) {
                            Log.i(TAG, "Successful call for recipes!");
                            JSONObject jsonObject = json.jsonObject;
                            try {
                                recipesList.addAll(Recipes.RecipeList(jsonObject, stepsForOneRecipe));
                                Log.i(TAG, "Recipe instructions: " + recipesList.get(0).getTitle());
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON exception hit", e);
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                            Log.e(TAG, "Fail call", throwable);
                        }
                    });
        }
    }

}