package com.example.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity); // Use the base layout
        setupBottomNavigation();
    }

    protected void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (bottomNavigationView == null) {
            throw new IllegalStateException("BottomNavigationView is missing in the layout.");
        }

        int selectedItemId = getSelectedMenuItemId();
        bottomNavigationView.setSelectedItemId(selectedItemId);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_back) {
                onBackPressed();
                return true;
            } else if (item.getItemId() == R.id.nav_wishlist && selectedItemId != R.id.nav_wishlist) {
                loadFragment(new FavoritesFragment());
                return true;
            } else if (item.getItemId() == R.id.nav_profile && selectedItemId != R.id.nav_profile) {
                loadFragment(new ProfileFragment());
                return true;
            }
            return false;
        });
    }

    protected abstract int getSelectedMenuItemId();

    /**
     * Helper method to load a fragment into the base_content_frame.
     */
    protected void loadFragment(Fragment fragment) {
        FrameLayout contentFrame = findViewById(R.id.base_content_frame);

        if (contentFrame == null) {
            throw new IllegalStateException("Content frame not found. Please ensure base_activity.xml defines a FrameLayout with id base_content_frame.");
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base_content_frame, fragment)
                .commit();
    }

    /**
     * Inject the child activity's layout into the base_content_frame.
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        FrameLayout contentFrame = findViewById(R.id.base_content_frame); // FrameLayout for child content
        if (contentFrame != null) {
            getLayoutInflater().inflate(layoutResID, contentFrame, true);
        } else {
            super.setContentView(layoutResID);
        }
    }
}
