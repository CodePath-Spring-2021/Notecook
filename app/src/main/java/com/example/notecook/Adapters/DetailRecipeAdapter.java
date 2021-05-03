package com.example.notecook.Adapters;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.notecook.Models.Recipes;
import com.example.notecook.R;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    Recipes recipes;
    List<String> ingredients;
    List<String> steps;
    final int TYPE_HEADER = 0;
    final int TYPE_INGREDIENTS = 1;
    final int TYPE_MIDDLE = 2;
    final int TYPE_STEPS = 3;


    public DetailRecipeAdapter(Context context, Recipes recipes) {
        this.context = context;
        this.recipes = recipes;
        ingredients = recipes.getIngredientName();
        steps = recipes.getInstructions();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        if(viewType == TYPE_HEADER) {
            View viewHeader = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(viewHeader);
        }
        if(viewType == TYPE_INGREDIENTS) {
            return new IngredientsViewHolder(view);
        }
        if(viewType == TYPE_MIDDLE) {
            View viewMiddle = LayoutInflater.from(context).inflate(R.layout.item_middle, parent, false);
            return new MiddleViewHolder(viewMiddle);
        }
        if(viewType == TYPE_STEPS) {
            return new StepsViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(recipes);
        }
        if(holder instanceof IngredientsViewHolder) {
            ((IngredientsViewHolder) holder).bind(ingredients.get(position));
        }
        if(holder instanceof StepsViewHolder) {
            ((StepsViewHolder) holder).bind(steps.get(position - ingredients.size()));
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size() + steps.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position < ingredients.size()) {
            return TYPE_INGREDIENTS;
        } else if (position == ingredients.size()) {
            return TYPE_MIDDLE;
        } else if ((position - ingredients.size()) < steps.size()) {
            return TYPE_STEPS;
        }
        return -1;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvCreator;
        ImageView ivPicture;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvCreator = itemView.findViewById(R.id.tvCreator);
            ivPicture = itemView.findViewById(R.id.ivPicture);
        }

        public void bind(Recipes recipes) {
            tvName.setText(recipes.getTitle());
            tvCreator.setText(recipes.getAuthor() + ", " + Integer.toString(recipes.getReadyInMinutes()) + " min");
            int radius = 30;
            int margin = 10;
            if(recipes.getImage() != null) {
                Glide.with(context).load(recipes.getImage()).transform(new FitCenter(), new RoundedCornersTransformation(radius, margin)).into(ivPicture);
            }else{
                System.out.println("NO PICTURE TO SHOW");
            }
        }
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView tvIngredients;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIngredients = itemView.findViewById(R.id.tvList);
        }

        public void bind(String ingredient) {
            SpannableString string = new SpannableString(ingredient);
            string.setSpan(new BulletSpan(10, context.getColor(R.color.black), 10), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvIngredients.setText(string);
        }
    }

    public class MiddleViewHolder extends RecyclerView.ViewHolder {

        TextView tvPrompt;

        public MiddleViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPrompt = itemView.findViewById(R.id.tvPrompt);
        }
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {

        TextView tvSteps;

        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSteps = itemView.findViewById(R.id.tvList);
        }

        public void bind(String step) {
            SpannableString string = new SpannableString(step);
            string.setSpan(new BulletSpan(10, context.getColor(R.color.black), 10), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvSteps.setText(string);
        }
    }
/*    final int VIEW_TYPE_INGREDIENTS = 0;
    final int VIEW_TYPE_STEPS = 1;

    Context context;
    List<String> ingredients;
    List<String> steps;

    public DetailAdapter(Context context, List<String> ingredients, List<String> steps) {
        this.context = context;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        if(viewType == VIEW_TYPE_INGREDIENTS) {
            return new IngredientViewHolder(view);
        }
        if(viewType == VIEW_TYPE_STEPS) {
            return new StepsViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof IngredientViewHolder) {
            ((IngredientViewHolder) holder).bind(ingredients.get(position));
        }

        if(holder instanceof StepsViewHolder) {
            ((StepsViewHolder) holder).bind(steps.get(position - ingredients.size()));
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size() + steps.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position < ingredients.size()){
            return VIEW_TYPE_INGREDIENTS;
        }

        if((position - ingredients.size()) < steps.size()){
            return VIEW_TYPE_STEPS;
        }

        return -1;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredients;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredients = itemView.findViewById(R.id.tvPrompt);
        }

        public void bind(String ingredient) {
            SpannableString string = new SpannableString(ingredient);
            string.setSpan(new BulletSpan(10, context.getColor(R.color.black), 10), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvIngredients.setText(string);
        }
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        TextView tvSteps;
        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSteps = itemView.findViewById(R.id.tvSteps);
        }

        public void bind(String step) {
            SpannableString string = new SpannableString(step);
            string.setSpan(new BulletSpan(10, context.getColor(R.color.black), 10), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvSteps.setText(string);
        }
    }*/
}
