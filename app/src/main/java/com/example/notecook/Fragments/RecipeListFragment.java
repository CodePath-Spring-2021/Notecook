package com.example.notecook.Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.notecook.Models.Recipes;
import com.example.notecook.R;
import com.example.notecook.Adapters.RecipesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

import static com.parse.Parse.getApplicationContext;

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

        // Changing the font of what is written on the Action Bar
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        Typeface typeface = ResourcesCompat.getFont(this.getContext(), R.font.euphoria_script);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Recipes");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(40);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(typeface, typeface.BOLD);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);

        String API_KEY = getString(R.string.API_KEY);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            ingredients = bundle.getString("ingredientsList");
        }else{
            Log.i(TAG, "Cannot get ingredientsList");
        }
        if(recipesList == null) {
            getRecipesId(API_KEY);
            recipesList = new ArrayList<>();
        }
        rvRecipe = view.findViewById(R.id.rvRecipe);
        adapter = new RecipesAdapter(getContext(), recipesList);
        rvRecipe.setAdapter(adapter);
        rvRecipe.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void getRecipesId(String API_KEY) {
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
        for(int i = 0; i < recipeIdList.size(); i++) {
            params.put("apiKey", API_KEY);
            client.get("https://api.spoonacular.com/recipes/" + recipeIdList.get(i) + "/information"
                    , params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Headers headers, JSON json) {
                            Log.i(TAG, "Successful call for recipes!");
                            JSONObject jsonObject = json.jsonObject;
                            try {
                                recipesList.addAll(Recipes.RecipeList(jsonObject));
                                adapter.notifyDataSetChanged();
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
        }
    }
}