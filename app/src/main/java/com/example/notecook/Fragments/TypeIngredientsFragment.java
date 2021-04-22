package com.example.notecook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.notecook.R;
import com.example.notecook.RecipeListActivity;


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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsList = etIngredients.getText().toString();
                Toast.makeText(getContext(), "Searching for your recipes!", Toast.LENGTH_SHORT).show();
                etIngredients.setText("");
                Intent intent = new Intent(getActivity(), RecipeListActivity.class);
                intent.putExtra("ingredientsList", ingredientsList);
                startActivity(intent);
            }
        });
    }
}