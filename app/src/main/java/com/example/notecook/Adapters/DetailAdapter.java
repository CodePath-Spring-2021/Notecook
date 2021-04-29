package com.example.notecook.Adapters;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notecook.Models.Recipes;
import com.example.notecook.R;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    final int VIEW_TYPE_INGREDIENTS = 0;
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
            tvIngredients = itemView.findViewById(R.id.tvIngredients);
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
    }
}
