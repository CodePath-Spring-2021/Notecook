package com.example.notecook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.notecook.Fragments.DetailFragment;
import com.example.notecook.Models.Post;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        //User user = users.get(position);
        holder.bind(post);
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvRecipeTitle;
        private TextView tvAuthor;
        private TextView tvTime;
        RelativeLayout rlPostContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            ivImage = itemView.findViewById(R.id.ivImage);
            rlPostContainer = itemView.findViewById(R.id.rlPostContainer);
            tvRecipeTitle = itemView.findViewById(R.id.tvRecipeTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvTime = itemView.findViewById(R.id.tvTime);
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
            tvAuthor.setText(post.getUser().getUsername());
            tvRecipeTitle.setText(post.getRecipeTitle());
            tvTime.setText(post.getCookTime() + "m");
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            rlPostContainer.setOnClickListener(new View.OnClickListener() {
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

