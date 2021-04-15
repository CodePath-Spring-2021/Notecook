package com.example.notecook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.example.notecook.Models.Recipes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class TypeIngredientsActivity extends AppCompatActivity {

    public static final String TAG = "TypeIngredientsActivity";
    EditText etIngredients;
    Button btnSearch;
    List<Integer> recipeIdList = new ArrayList<>();
    List<Recipes> recipes = new ArrayList<>();
    AsyncHttpClient client = new AsyncHttpClient();
    RequestParams params = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_ingredients);

        String API_KEY = getString(R.string.API_KEY);

        etIngredients = findViewById(R.id.etIngredients);
        btnSearch = findViewById(R.id.btnSearch);
        getRecipesId(API_KEY);
    }

    private void getRecipesId(String API_KEY) {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientList = etIngredients.getText().toString();
                params.put("ingredients", ingredientList);
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
                                Log.e(TAG, "Failed call!", throwable);
                            }
                        });
                etIngredients.setText("");
            }
        });
    }

    private void getRecipes(String API_KEY) {
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
                            Log.e(TAG, "Fail call", throwable);
                        }
                    });
            client.get("https://api.spoonacular.com/recipes/" + recipeIdList.get(i) + "/information"
                    , params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Headers headers, JSON json) {
                            Log.i(TAG, "Successful call for recipes!");
                            JSONObject jsonObject = json.jsonObject;
                            try {
                                recipes = Recipes.RecipeList(jsonObject, stepsForOneRecipe);
                                Log.i(TAG, "Recipe instructions: " + recipes.get(0).getInstructions());
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