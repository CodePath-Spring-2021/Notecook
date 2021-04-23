package com.example.notecook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.notecook.Fragments.DetailFragment;
import com.example.notecook.Models.Recipes;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder>{

    Context context;
    List<Recipes> recipe;

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
        TextView tvCookTime;
        ImageView ivImage;
        RelativeLayout rlRecipeContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCookTime = itemView.findViewById(R.id.tvCookTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivImage = itemView.findViewById(R.id.ivImage);
            rlRecipeContainer = itemView.findViewById(R.id.rlRecipeContainer);
        }

        public void bind(Recipes recipes) {
            tvTitle.setText(recipes.getTitle());
            tvCookTime.setText(Integer.toString(recipes.getReadyInMinutes()) + "min");
            if(ivImage != null) {
                Glide.with(context).load(recipes.getImage()).into(ivImage);
            }
            rlRecipeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) context;
                    Fragment detailFrag = new DetailFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, detailFrag).addToBackStack(null).commit();
                }
            });
        }
    }
}
