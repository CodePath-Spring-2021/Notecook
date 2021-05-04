package com.example.notecook.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notecook.Adapters.DetailPostAdapter;
import com.example.notecook.Adapters.DetailRecipeAdapter;
import com.example.notecook.Adapters.FavAdapter;
import com.example.notecook.Models.Fav;
import com.example.notecook.Models.FavDB;
import com.example.notecook.Models.Post;
import com.example.notecook.Models.Recipes;
import com.example.notecook.R;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.notecook.LoginActivity;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    Context context;
    public static final String TAG = "ProfileFragment";
    private RecyclerView rvFavs;
    protected FavAdapter favAdapter;
    private FavDB favDB;
    private TextView tvUsername;
    protected List<Fav> favPosts = new ArrayList<>();
    protected String recipeId;
    Fav favItem;
    String title;
    String type_status;
    String author;
    String time;
    String ingredientsList;
    String instructions;
    String fav_status;
    Recipes recipeItem;
    Post postItem;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getContext();

        // Changing the font of what is written on the Action Bar
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        Typeface typeface = ResourcesCompat.getFont(this.getContext(), R.font.euphoria_script);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Your Profile");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(40);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(typeface, typeface.BOLD);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFavs = view.findViewById(R.id.rvFavs);
        tvUsername = view.findViewById(R.id.tvUsername);
        favDB = new FavDB(getActivity());
        Button Logout = view.findViewById(R.id.buttonLogout);

        Intent in = getActivity().getIntent();
        String string = in.getStringExtra("message");
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirm Logout").
                        setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ParseUser.logOut();
                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                startActivity(i);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });

        tvUsername.setText(ParseUser.getCurrentUser().getUsername());
        favPosts = new ArrayList<>();
        //adapter = new PostsAdapter(getContext(), favPosts);
        //rvFavs.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvFavs.setLayoutManager(layoutManager);

        loadData();

        rvFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) context;
                Fragment detailFrag = new DetailFragment();
                Bundle bundle = new Bundle();
                if (type_status.equals("20")) {
                    bundle.putInt("key", Integer.parseInt(type_status));
                    bundle.putParcelable("post", Parcels.wrap(postItem));
                }
                else if (type_status.equals("10")) {
                    bundle.putInt("key", Integer.parseInt(type_status));
                    bundle.putParcelable("post", Parcels.wrap(recipeItem));
                }
                detailFrag.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, detailFrag).addToBackStack(null).commit();
            }
        });
    }

    private void loadData() {
        if (favPosts != null) {
            favPosts.clear();
        }
        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.select_all_favorite_list();
        try {
            while (cursor.moveToNext()) {
                title = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TITLE));
                author = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_AUTHOR));
                time = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TIME));
                ingredientsList = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_INGREDIENTS));
                instructions = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_STEPS));
                recipeId = cursor.getString(cursor.getColumnIndex(FavDB.KEY_ID));
                type_status = cursor.getString(cursor.getColumnIndex(FavDB.TYPE_STATUS));
                fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                if (type_status.equals("20")) {
                    byte[] image = cursor.getBlob(cursor.getColumnIndex(FavDB.ITEM_IMAGE));
                    favItem = new Fav(title, recipeId, image, type_status, author, time, ingredientsList, instructions, fav_status);
                    //recipeItem = new Recipes(title, author, image, time, instructions, ingredientsList, recipeId, fav_status);
                }
                else {
                    String imageUrl = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGEURL));
                    favItem = new Fav(title, recipeId, imageUrl, type_status, author, time, ingredientsList, instructions, fav_status);
                    //postItem = new Post(title, author, imageUrl, time, instructions, ingredientsList, recipeId, fav_status);
                }
                favPosts.add(favItem);
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

        favAdapter = new FavAdapter(getActivity(), favPosts);

        rvFavs.setAdapter(favAdapter);
    }
}
