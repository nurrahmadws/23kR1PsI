package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.matatabi.padm.R;

public class SubMenuDataDaerahActivity extends AppCompatActivity {

    private Button btn_kabupaten_kota, btn_kecamatan, btn_kelurahan_desa, btn_Lat_lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu_data_daerah);

        btn_kabupaten_kota = findViewById(R.id.btn_kabupaten_kota);
        btn_kecamatan = findViewById(R.id.btn_kecamatan);
        btn_kelurahan_desa = findViewById(R.id.btn_kelurahan_desa);
        btn_Lat_lng = findViewById(R.id.btn_Lat_lng);

        btn_kabupaten_kota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubMenuDataDaerahActivity.this, DataKabupatenActivity.class));
            }
        });

        btn_kecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubMenuDataDaerahActivity.this, DataKecamatanActivity.class));
            }
        });
        btn_kelurahan_desa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubMenuDataDaerahActivity.this, DataKelurahanActivity.class));
            }
        });
        btn_Lat_lng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubMenuDataDaerahActivity.this, DataLatLngActivity.class));
            }
        });
    }
}
