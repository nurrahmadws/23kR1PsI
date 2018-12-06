package com.example.matatabi.padm.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.fragments.MhsBiodatakuFragment;
import com.example.matatabi.padm.fragments.MhsDataMahasiswaFragment;
import com.example.matatabi.padm.fragments.MhsTtgAppFragment;

public class MahasiswaActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav_mhs);
        navigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new MhsBiodatakuFragment());

    }

    private void displayFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_mhs, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.menu_biodataku:
                fragment = new MhsBiodatakuFragment();
                break;
            case R.id.menu_data_mhs:
                fragment = new MhsDataMahasiswaFragment();
                break;
            case R.id.menu_tentang_app:
                fragment = new MhsTtgAppFragment();
                break;
        }
        if (fragment != null){
            displayFragment(fragment);
        }

        return false;
    }
}
