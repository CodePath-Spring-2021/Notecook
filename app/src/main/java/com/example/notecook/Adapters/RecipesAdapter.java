package com.example.notecook.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.notecook.Fragments.DetailFragment;
import com.example.notecook.Models.FavDB;
import com.example.notecook.Models.Recipes;
import com.example.notecook.R;

import org.parceler.Parcels;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder>{

    Context context;
    List<Recipes> recipe;
    private FavDB favDB;
    public static final int KEY_RECIPES = 10;
    Bitmap bitmap;
    Bitmap croppedBitmap;

    public RecipesAdapter(Context context, List<Recipes> recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        //create table on first
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipes recipes = recipe.get(position);
        holder.bind(recipes);
        readCursorData(recipes, holder);
    }

    @Override
    public int getItemCount() {
        return recipe.size();
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(Recipes recipe, RecipesAdapter.ViewHolder viewHolder) {
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

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivPhoto;
        Button favBtn;
        CardView rlRecipeContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            favBtn = itemView.findViewById(R.id.favBtn);
            rlRecipeContainer = itemView.findViewById(R.id.rlRecipeContainer);

            // add to fav btn
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Recipes recipeOne = recipe.get(position);

                    if (recipeOne.getFavStatus().equals("0")) {
                        recipeOne.setFavStatus("1");
                        URL url = null;
                        try {
                            url = new URL(recipeOne.getImage());
                            bitmap = getImageBitmapFromUrl(url);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        favDB.insertIntoTheDatabase(recipeOne.getTitle(), recipeOne.getImage(), recipeOne.getAuthor(),
                                recipeOne.getReadyInMinutes() + "", recipeOne.getIngredientName(), recipeOne.getInstructions(),
                                recipeOne.getFavStatus(), "10", recipeOne.getId());
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    } else {
                        recipeOne.setFavStatus("0");
                        favDB.remove_fav(recipeOne.getId());
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_shadow);
                    }
                }
            });
        }

        public void bind(Recipes recipes) {
            if(recipe == null) {
                tvTitle.setText("NO RECIPES TO SHOW, TRY DIFFERENT INGREDIENTS");
            }
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

            // place appropriate color on text over an image
            if (bitmap != null) {
                croppedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 3);
                setTextColorForImage(tvTitle, croppedBitmap);
            }
        }
    }

    private void setTextColorForImage(TextView tvRecipeTitle, Bitmap firstPhoto) {
        Palette.from(firstPhoto)
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        int titleTextColor = Color.BLACK;

                        Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                        if (swatch != null) {
                            titleTextColor = Color.WHITE;
                        }

                        /*
                        Palette.Swatch swatch = palette.getVibrantSwatch();
                        if (swatch == null && palette.getSwatches().size() > 0) {
                            swatch = palette.getSwatches().get(0);
                        }
                        if (swatch != null) {
                            titleTextColor = swatch.getTitleTextColor();
                        }*/

                        titleTextColor = ColorUtils.setAlphaComponent(titleTextColor, 255);
                        tvRecipeTitle.setTextColor(titleTextColor);
                    }
                });
    }

    public static Bitmap getImageBitmapFromUrl(URL url)
    {
        Bitmap bm = null;
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn.getResponseCode() != 200)
            {
                return bm;
            }
            conn.connect();
            InputStream is = conn.getInputStream();

            BufferedInputStream bis = new BufferedInputStream(is);
            try
            {
                bm = BitmapFactory.decodeStream(bis);
            }
            catch(OutOfMemoryError ex)
            {
                bm = null;
            }
            bis.close();
            is.close();
        } catch (Exception e) {}

        return bm;
    }
}
