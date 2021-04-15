package com.example.notecook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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


public class TypeIngredientsFragment extends Fragment {

    public static final String TAG = "TypeIngredientsFragment";
    EditText etIngredients;
    Button btnSearch;
    private String ingredientList;
    List<Integer> recipeIdList = new ArrayList<>();
    List<Recipes> recipes = new ArrayList<>();
    AsyncHttpClient client = new AsyncHttpClient();
    RequestParams params = new RequestParams();
    private static TypeIngredientsFragment instance = null;

    public TypeIngredientsFragment() {
        // Required empty public constructor
    }

    public static TypeIngredientsFragment getInstance() {
        return instance;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ComposeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TakePictureFragment newInstance(String param1, String param2) {
        TakePictureFragment fragment = new TakePictureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type_ingredients, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String API_KEY = getString(R.string.API_KEY);

        etIngredients = view.findViewById(R.id.etIngredients);
        btnSearch = view.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientList = etIngredients.getText().toString();
                getRecipesId(API_KEY);
                etIngredients.setText("");
            }
        });

    }

    protected void getRecipesId(String API_KEY) {
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