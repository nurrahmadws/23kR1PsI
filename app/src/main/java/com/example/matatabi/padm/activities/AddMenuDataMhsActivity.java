package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.matatabi.padm.R;

public class AddMenuDataMhsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_data_mhs);

        Button btn_add_image = findViewById(R.id.btn_add_image);
        btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMenuDataMhsActivity.this, AddImageMhsActivity.class));
            }
        });
    }
}
