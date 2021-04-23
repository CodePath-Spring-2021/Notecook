package com.example.notecook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.PopupMenu;

import com.example.notecook.Fragments.CreateFragment;
import com.example.notecook.Fragments.DetailFragment;
import com.example.notecook.Fragments.HomeFragment;
import com.example.notecook.Fragments.ProfileFragment;
import com.example.notecook.Fragments.TakePictureFragment;
import com.example.notecook.Fragments.TypeIngredientsFragment;
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
        //actionBar.setLogo(R.drawable.nav_logo_whiteout);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new Fragment();
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_find: {
                        PopupMenu popupMenu = new PopupMenu(MainActivity.this, bottomNavigationView);
                        popupMenu.inflate(R.menu.menu_find_ingredient);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Fragment frag = new Fragment();
                                switch (item.getItemId()) {
                                    case R.id.menu_picture:
                                        frag = new TakePictureFragment();
                                        break;
                                    case R.id.menu_type:
                                        frag = new TypeIngredientsFragment();
                                        break;
                                }
                                fragmentManager.beginTransaction().replace(R.id.flContainer, frag).commit();
                                return true;
                            }
                        });
                        //show the menu
                        popupMenu.show();
                    }
                        break;
                    case R.id.action_compose:
                        fragment = new CreateFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        break;
                    default:
                        fragment = new HomeFragment();
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