package com.example.notecook.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.notecook.R;

import java.util.Arrays;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    Post post;
    List<String> ingredients;
    List<String> instructions;
    final int TYPE_HEADER = 0;
    final int TYPE_INGREDIENTS = 1;
    final int TYPE_MIDDLE = 2;
    final int TYPE_STEPS = 3;
    private FavDB favDB;
    HeaderViewHolder holderFav;

    public DetailPostAdapter(Context context, Post post) {
        this.context = context;
        this.post = post;
        ingredients = Arrays.asList(post.getIngredientsList().split("\\r?\\n"));
        instructions = Arrays.asList(post.getRecipeInstructions().split("\\r?\\n"));
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
        if(holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(post);
            holderFav = ((HeaderViewHolder) holder);
            readCursorData(post, holderFav);
        }
        if(holder instanceof IngredientsViewHolder) {
            ((IngredientsViewHolder) holder).bind(ingredients.get(position));
        }
        if(holder instanceof StepsViewHolder) {
            ((StepsViewHolder) holder).bind(instructions.get(position - ingredients.size()));
        }

    }

    @Override
    public int getItemCount() {
        return ingredients.size() + instructions.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position < ingredients.size()) {
            return TYPE_INGREDIENTS;
        } else if (position == ingredients.size()) {
            return TYPE_MIDDLE;
        } else if ((position - ingredients.size()) < instructions.size()) {
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

            //add to fav btn
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (post.getFavStatus().equals("0")) {
                        post.setFavStatus("1");
                        favDB.insertIntoTheDatabase(post.getRecipeTitle(), post.getImageResource(),
                                post.getUser().getUsername(), post.getCookTime(), Arrays.asList(post.getIngredientsList().split("\\r?\\n")),
                                Arrays.asList(post.getRecipeInstructions().split("\\r?\\n")), post.getFavStatus(), "20", post.getKEY_ID());
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    } else {
                        post.setFavStatus("0");
                        favDB.remove_fav(post.getKEY_ID());
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_shadow);
                    }
                }
            });
        }

        public void bind(Post recipePost) {
            tvName.setText(recipePost.getRecipeTitle());
            tvCreator.setText(recipePost.getUser().getUsername() + ", " + recipePost.getCookTime() + " min");
            if(recipePost.getImage() != null) {
                int radius = 30;
                int margin = 10;
                Glide.with(context).load(recipePost.getImage().getUrl()).transform(new FitCenter(), new RoundedCornersTransformation(radius, margin)).into(ivPicture);
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

        public void bind(String instruction) {
            SpannableString string = new SpannableString(instruction);
            string.setSpan(new BulletSpan(10, context.getColor(R.color.black), 10), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvSteps.setText(string);
        }
    }

    private void readCursorData(Post post, HeaderViewHolder viewHolder) {
        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.read_all_data(post.getKEY_ID());
        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                post.setFavStatus(item_fav_status);

                // check fav status
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
}
