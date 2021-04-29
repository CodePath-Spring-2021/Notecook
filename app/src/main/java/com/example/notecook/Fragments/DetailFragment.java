package com.example.notecook.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.notecook.Adapters.DetailAdapter;
import com.example.notecook.Models.Post;
import com.example.notecook.Models.Recipes;
import com.example.notecook.R;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    public static final String TAG = "DetailFragment";
    protected TextView tvName;
    protected TextView tvCreator;
    protected TextView tvTime;
    protected ImageView ivPicture;
    protected RecyclerView rvList;
    protected AppCompatButton btnFavorite;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.tvName);
        tvCreator = view.findViewById(R.id.tvCreator);
        tvTime = view.findViewById(R.id.tvTime);
        ivPicture = view.findViewById(R.id.ivPicture);
        rvList = view.findViewById(R.id.rvList);
        btnFavorite = view.findViewById(R.id.btnFavorite);

        Bundle bundle = this.getArguments();
        int key = bundle.getInt("key");
        if(key == 10) {
            Recipes recipe = Parcels.unwrap(bundle.getParcelable("recipe"));
            tvName.setText(recipe.getTitle());
            tvCreator.setText(recipe.getAuthor());
            tvTime.setText(Integer.toString(recipe.getReadyInMinutes()) + "min");
            if(recipe.getImage() != null) {
                Glide.with(getContext()).load(recipe.getImage()).into(ivPicture);
            }
            DetailAdapter itemsAdapter = new DetailAdapter(getContext(), recipe.getIngredientName(), recipe.getInstructions());
            rvList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvList.setAdapter(itemsAdapter);
        }
        if(key == 20) {
            Post post = Parcels.unwrap(bundle.getParcelable("post"));
            tvName.setText(post.getRecipeTitle());
            tvCreator.setText(post.getUser().getUsername());
            tvTime.setText(post.getCookTime());
            if(post.getImage() != null) {
                Glide.with(getContext()).load(post.getImage().getUrl()).into(ivPicture);
            }
            List<String> ingredientsList = Arrays.asList(post.getIngredientsList().split("\\r?\\n"));
            List<String> instructions = Arrays.asList(post.getRecipeInstructions().split("\\r?\\n"));
            Log.i(TAG, "Ingredients list:" + ingredientsList);
            Log.i(TAG, "Recipe instructions:" + instructions);
            DetailAdapter detailAdapter = new DetailAdapter(getContext(), ingredientsList, instructions);
            rvList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvList.setAdapter(detailAdapter);
        }

/*        DetailAdapter itemsAdapter = new DetailAdapter(getContext(), recipes.getIngredientName(), recipes.getInstructions());
        lvIngredients.setAdapter(itemsAdapter);*/
  /*      List<String> ingredients = recipes.getIngredientName();
        ingredients.addAll(recipes.getInstructions());
       ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ingredients);
        lvIngredients.setAdapter(itemsAdapter);*/

    }
}