package com.example.notecook.Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.notecook.Adapters.DetailFavAdapter;
import com.example.notecook.Adapters.DetailPostAdapter;
import com.example.notecook.Adapters.DetailRecipeAdapter;
import com.example.notecook.Adapters.FavAdapter;
import com.example.notecook.Models.Fav;
import com.example.notecook.Models.Post;
import com.example.notecook.Models.Recipes;
import com.example.notecook.R;

import org.parceler.Parcels;

import java.util.Objects;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    public static final String TAG = "DetailFragment";
    protected RecyclerView rvList;
    int key;
    protected AppCompatButton btnFavorite;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Changing the font of what is written on the Action Bar
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        Typeface typeface = ResourcesCompat.getFont(this.getContext(), R.font.euphoria_script);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Recipe Details");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(40);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(typeface, typeface.BOLD);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvList = view.findViewById(R.id.rvList);
        Bundle bundle = this.getArguments();
        key = bundle.getInt("key");
        if(key == 10) {
            Recipes recipe = Parcels.unwrap(bundle.getParcelable("recipe"));
            DetailRecipeAdapter recipeAdapter = new DetailRecipeAdapter(getContext(), recipe);
            rvList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvList.setAdapter(recipeAdapter);
        }
        if(key == 20) {
            Post post = Parcels.unwrap(bundle.getParcelable("post"));
            DetailPostAdapter postAdapter = new DetailPostAdapter(getContext(), post);
            rvList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvList.setAdapter(postAdapter);
        }
        if(key == 30) {
            Fav fav = Parcels.unwrap(bundle.getParcelable("favPost"));
            DetailFavAdapter favAdapter = new DetailFavAdapter(getContext(), fav);
            rvList.setLayoutManager(new LinearLayoutManager(getContext()));
            rvList.setAdapter(favAdapter);
        }
    }
}