package com.example.notecook.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.notecook.Fragments.DetailFragment;
import com.example.notecook.Models.Recipes;
import com.example.notecook.R;

import org.parceler.Parcels;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder>{

    Context context;
    List<Recipes> recipe;
    public static final int KEY_RECIPES = 10;

    public RecipesAdapter(Context context, List<Recipes> recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipes recipes = recipe.get(position);
        holder.bind(recipes);
    }

    @Override
    public int getItemCount() {
        return recipe.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivPhoto;
        AppCompatButton btnFavorite;
        CardView rlRecipeContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            rlRecipeContainer = itemView.findViewById(R.id.rlRecipeContainer);
        }

        public void bind(Recipes recipes) {
            tvTitle.setText(recipes.getTitle() + " - " + Integer.toString(recipes.getReadyInMinutes()) + "m");
            if(recipes.getImage() != null) {
                Glide.with(context).load(recipes.getImage()).into(ivPhoto);
            }
            rlRecipeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) context;
                    Fragment detailFrag = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", KEY_RECIPES);
                    bundle.putParcelable("recipe", Parcels.wrap(recipes));
                    detailFrag.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, detailFrag).addToBackStack(null).commit();
                }
            });
        }
    }
}
