package com.example.notecook.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.notecook.Models.FavDB;
import com.example.notecook.Models.Post;
import com.example.notecook.Models.Recipes;
import com.example.notecook.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
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
    private FavDB favDB;
    HeaderViewHolder holderFav;

    public DetailRecipeAdapter(Context context, Recipes recipes) {
        this.context = context;
        this.recipes = recipes;
        ingredients = recipes.getIngredientName();
        steps = recipes.getInstructions();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        favDB = new FavDB(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        if(viewType == TYPE_HEADER) {
            //create table on first
            SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
            boolean firstStart = prefs.getBoolean("firstStart", true);
            if (firstStart) {
                createTableOnFirstStart();
            }
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
        position = position - 1;
        if(holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(recipes);
            holderFav = ((HeaderViewHolder) holder);
            readCursorData(recipes, holderFav);
        }
        if(holder instanceof IngredientsViewHolder) {
            ((IngredientsViewHolder) holder).bind(ingredients.get(position));
        }
        if(holder instanceof StepsViewHolder) {
            ((StepsViewHolder) holder).bind(steps.get(position - ingredients.size() - 1));
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size() + steps.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        position = position - 1;
        if (position == -1) {
            return TYPE_HEADER;
        } else if (position < ingredients.size()) {
            return TYPE_INGREDIENTS;
        } else if (position == ingredients.size()) {
            return TYPE_MIDDLE;
        } else if ((position - ingredients.size() - 1) < steps.size()) {
            return TYPE_STEPS;
        }
        return -1;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvCreator;
        ImageView ivPicture;
        Button favBtn;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvCreator = itemView.findViewById(R.id.tvCreator);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            favBtn = itemView.findViewById(R.id.btnFavorite);

            // add to fav btn
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (recipes.getFavStatus().equals("0")) {
                        recipes.setFavStatus("1");
                        URL url = null;
                        try {
                            url = new URL(recipes.getImage());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        favDB.insertIntoTheDatabase(recipes.getTitle(), recipes.getImage(), recipes.getAuthor(),
                                recipes.getReadyInMinutes() + "", recipes.getIngredientName(), recipes.getInstructions(),
                                recipes.getFavStatus(), "10", recipes.getId());
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    } else {
                        recipes.setFavStatus("0");
                        favDB.remove_fav(recipes.getId());
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_shadow);
                    }
                }
            });
        }

        public void bind(Recipes recipes) {
            tvName.setText(recipes.getTitle());
            tvCreator.setText(recipes.getAuthor() + ", " + Integer.toString(recipes.getReadyInMinutes()) + " min");
            int radius = 30;
            int margin = 10;
            if (recipes.getImage() != null) {
                String imageUrl = recipes.getImage(); // should be a url
                Glide.with(context).load(imageUrl).transform(new FitCenter(), new RoundedCornersTransformation(radius, margin)).into(ivPicture);
            } else{
                System.out.println("NO PICTURE TO SHOW");
            }
        }
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
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

    private void readCursorData(Recipes recipe, HeaderViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(recipe.getId());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                recipe.setFavStatus(item_fav_status);

                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_shadow);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
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
