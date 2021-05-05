package com.example.notecook.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.notecook.Fragments.DetailFragment;
import com.example.notecook.Models.FavDB;
import com.example.notecook.Models.Fav;
import com.example.notecook.R;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private Context context;
    private List<Fav> favItemList;
    private FavDB favDB;
    public static final int KEY_FAV = 30;

    public FavAdapter(Context context, List<Fav> favItemList) {
        this.context = context;
        this.favItemList = favItemList;
    }

    // add a list of items -- change to type used
    public void addAll(List<Fav> favList) {
        favItemList.addAll(favList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav,
                parent, false);
        favDB = new FavDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.favTextView.setText(favItemList.get(position).getItem_title());
        Fav favItem = favItemList.get(position);
        holder.bind(favItem);
        if (favItemList.get(position).getItem_type().equals("10")) {
            Glide.with(context).load(favItemList.get(position).getImage_url()).into(holder.favImageView);
        }
        else if (favItemList.get(position).getItem_type().equals("20")) {
            holder.favImageView.setImageBitmap(favItemList.get(position).getItem_image());
        }
    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView favTextView;
        Button favBtn;
        ImageView favImageView;
        CardView cvContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favTextView = itemView.findViewById(R.id.tvRecipeTitle);
            favBtn = itemView.findViewById(R.id.favBtn);
            favImageView = itemView.findViewById(R.id.ivImage);
            cvContainer = itemView.findViewById(R.id.cvContainer);

            //remove from fav after click
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Fav favItem = favItemList.get(position);
                    favDB.remove_fav(favItem.getKey_id());
                    removeItem(position);
                }
            });
        }

        public void bind(Fav favItem) {
            cvContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) context;
                    Fragment detailFrag = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", KEY_FAV);
                    bundle.putParcelable("favPost", Parcels.wrap(favItem));
                    detailFrag.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, detailFrag).addToBackStack(null).commit();
                }
            });
        }
    }

    private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, favItemList.size());
    }
}
