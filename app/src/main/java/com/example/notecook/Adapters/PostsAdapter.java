package com.example.notecook.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.notecook.Fragments.DetailFragment;
import com.example.notecook.Models.FavDB;
import com.example.notecook.Models.Post;
import com.example.notecook.R;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private FavDB favDB;
    protected ImageView ivImage;
    protected TextView tvRecipeTitle;
    protected TextView tvAuthor;
    public static final int KEY_POST = 20;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        //User user = users.get(position);
        holder.bind(post);
        readCursorData(post, holder);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // add a list of items -- change to type used
    public void addAll(List<Post> postList) {
        posts.addAll(postList);
        notifyDataSetChanged();
    }

    private void readCursorData(Post post, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(post.getKEY_ID());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                post.setFavStatus(item_fav_status);

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button favBtn;
        CardView rlPostContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            ivImage = itemView.findViewById(R.id.ivImage);
            favBtn = itemView.findViewById(R.id.favBtn);
            rlPostContainer = itemView.findViewById(R.id.rlPostContainer);
            tvRecipeTitle = itemView.findViewById(R.id.tvRecipeTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);

            //add to fav btn
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Post post = posts.get(position);

                    if (post.getFavStatus().equals("0")) {
                        post.setFavStatus("1");
                        favDB.insertIntoTheDatabase(post.getRecipeTitle(), post.getImageResource(), post.getKEY_ID(), post.getFavStatus());
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    } else {
                        post.setFavStatus("0");
                        favDB.remove_fav(post.getKEY_ID());
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_shadow);
                    }
                }
            });
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            //ParseFile profileImage = user.getProfileImage();
            //if (profileImage != null) {
            //    Glide.with(context).load(profileImage.getUrl()).into(ivProfile);
            //}
            //else {
            //Glide.with(context).load(R.drawable.ic_launcher_foreground).into(ivProfile);
            //}
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            tvAuthor.setText(post.getUser().getUsername());
            tvRecipeTitle.setText(post.getRecipeTitle() + " - " + post.getCookTime() + "m");

            rlPostContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) context;
                    Fragment detailFrag = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", KEY_POST);
                    bundle.putParcelable("post", Parcels.wrap(post));
                    detailFrag.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, detailFrag).addToBackStack(null).commit();
                }
            });

            // place appropriate color on text over an image
            //Bitmap bitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();
            //setTextColorForImage(tvRecipeTitle, tvAuthor, bitmap);
        }
    }

    private void setTextColorForImage(TextView tvRecipeTitle, TextView tvAuthor, Bitmap firstPhoto) {
        Palette.from(firstPhoto)
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatch = palette.getVibrantSwatch();
                        if (swatch == null && palette.getSwatches().size() > 0) {
                            swatch = palette.getSwatches().get(0);
                        }

                        int titleTextColor = Color.WHITE;

                        if (swatch != null) {
                            titleTextColor = swatch.getTitleTextColor();
                            //titleTextColor = ColorUtils.setAlphaComponent(titleTextColor, 255);
                        }
                        tvRecipeTitle.setTextColor(titleTextColor);
                        tvAuthor.setTextColor(titleTextColor);
                    }
                });
    }
}

