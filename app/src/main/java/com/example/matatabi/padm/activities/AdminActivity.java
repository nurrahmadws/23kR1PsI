package com.example.matatabi.padm.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.fragments.AdminHomeFragment;
import com.example.matatabi.padm.fragments.AdminQuickMenuFragment;
import com.example.matatabi.padm.fragments.AdminSettingFragment;

public class AdminActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new AdminHomeFragment());
    }

    private void displayFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerr, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.menu_home:
                fragment = new AdminHomeFragment();
                break;
            case R.id.menu_quick_menu:
                fragment = new AdminQuickMenuFragment();
                break;
            case R.id.menu_settings:
                fragment = new AdminSettingFragment();
                break;
        }
        if (fragment != null){
            displayFragment(fragment);
        }

        return false;
    }
}
