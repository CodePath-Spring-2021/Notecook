package com.example.notecook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private final int REQUEST_CODE = 20;
    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Adding logo drawables to Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new TypeIngredientsFragment(); //CHANGE TO HOMEFRAGMENT
                        break;
                    case R.id.action_find:
                        fragment = new TakePictureFragment(); // HAVE ONE BE TAKEPIC AND OTHER TYPEINGREDIENTS
                        break;
                    case R.id.action_compose:
                        fragment = new TypeIngredientsFragment(); // CHANGE TO POSTFRAGMENT
                        break;
                    case R.id.action_profile:
                        fragment = new TakePictureFragment(); // CHANGE TO PROFILEFRAGMENT
                        break;
                    default:
                        fragment = new TypeIngredientsFragment(); //CHANGE TO HOMEFRAGMENT
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}