package com.example.notecook.Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.notecook.R;

import static com.parse.Parse.getApplicationContext;


public class TypeIngredientsFragment extends Fragment {

    EditText etIngredients;
    Button btnSearch;
    String ingredientsList;
    private static TypeIngredientsFragment instance = null;

    public TypeIngredientsFragment() {
        // Required empty public constructor
    }

    public static TypeIngredientsFragment getInstance() {
        return instance;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ComposeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TakePictureFragment newInstance(String param1, String param2) {
        TakePictureFragment fragment = new TakePictureFragment();
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
        tv.setText("Find a Recipe");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(40);
        tv.setTextColor(Color.WHITE);
        tv.setTypeface(typeface, typeface.BOLD);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);
        instance = this;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type_ingredients, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etIngredients = view.findViewById(R.id.etIngredients);
        btnSearch = view.findViewById(R.id.btnSearch);

        // Set typing listener on EditText
        etIngredients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Fires right before text is changing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Fires right as the text is being changed (even supplies the range of text)
                int length = etIngredients.length();
                if (length == 0) {
                    btnSearch.setEnabled(false);
                    btnSearch.setBackgroundColor(Color.LTGRAY);
                    btnSearch.setTextColor(Color.GRAY);
                }
                else {
                    // when list is not empty
                    btnSearch.setEnabled(true);
                    btnSearch.setBackgroundColor(getResources().getColor(R.color.green_200));
                    btnSearch.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Fires right after the text has changed
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsList = etIngredients.getText().toString();
                Toast.makeText(getContext(), "Searching for your recipes!", Toast.LENGTH_SHORT).show();
                etIngredients.setText("");
                Bundle bundle = new Bundle();
                bundle.putString("ingredientsList", ingredientsList);
                RecipeListFragment fragment2 = new RecipeListFragment();
                fragment2.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.flContainer, fragment2).commit();
            }
        });
    }
}