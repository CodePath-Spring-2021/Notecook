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
import com.example.notecook.Models.Post;
import com.example.notecook.R;

import java.util.Arrays;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailFavAdapter {

    /*
    TO LOAD CORRECT IMAGE:

    if (favItemList.get(position).getItem_type().equals("10")) {     // for recipe for String imageUrl
            Glide.with(context).load(favItemList.get(position).getImage_url()).into(holder.favImageView);
        }
    else if (favItemList.get(position).getItem_type().equals("20")) {      // for post with byte[] image
            holder.favImageView.setImageBitmap(favItemList.get(position).getItem_image());
        }


    ALSO:

    both ingredients and instructions are strings, not lists (for both recipe and post items)
    */


}
