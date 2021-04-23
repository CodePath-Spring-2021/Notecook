package com.example.notecook.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.notecook.Models.Recipes;
import com.example.notecook.R;
import com.example.notecook.RecipesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeListFragment extends Fragment {

    public static final String TAG = "RecipeListFragment";
    private RecyclerView rvRecipe;
    protected RecipesAdapter adapter;
    protected List<Recipes> recipesList;
    protected String ingredients;
    List<Integer> recipeIdList = new ArrayList<>();
    AsyncHttpClient client = new AsyncHttpClient();
    RequestParams params = new RequestParams();

    public RecipeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeListFragment newInstance(String param1, String param2) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String API_KEY = getString(R.string.API_KEY);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            ingredients = bundle.getString("ingredientsList");
        }else{
            Log.i(TAG, "Cannot get ingredientsList");
        }
        rvRecipe = view.findViewById(R.id.rvRecipe);
        recipesList = new ArrayList<>();
        adapter = new RecipesAdapter(getContext(), recipesList);
        rvRecipe.setAdapter(adapter);
        rvRecipe.setLayoutManager(new LinearLayoutManager(getContext()));
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