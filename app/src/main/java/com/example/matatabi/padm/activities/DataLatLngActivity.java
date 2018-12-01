package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.LatLngAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Kabupaten;
import com.example.matatabi.padm.model.KabupatenResponse;
import com.example.matatabi.padm.model.Kecamatan;
import com.example.matatabi.padm.model.KecamatanResponse;
import com.example.matatabi.padm.model.Kelurahan;
import com.example.matatabi.padm.model.KelurahanResponse;
import com.example.matatabi.padm.model.Latlng;
import com.example.matatabi.padm.model.LatlngResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataLatLngActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LatLngAdapter latLngAdapter;
    private List<Latlng> latlngList;
    private FloatingActionButton fab_Lat;
    private Spinner spinKabforLat, spinKecforLat, spinKelforLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_lat_lng);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_latlng);
        recyclerView.setLayoutManager(new LinearLayoutManager(DataLatLngActivity.this));

        spinKabforLat = findViewById(R.id.spinKabforLat);
        spinKecforLat = findViewById(R.id.spinKecforLat);
        spinKelforLat = findViewById(R.id.spinKelforLat);

        fab_Lat = findViewById(R.id.fab_Lat);
        fab_Lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataLatLngActivity.this, AddLatlngActivity.class));
            }
        });

        Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().spinKab();
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listSpinKabForLat = new ArrayList<>();
                for (int i = 0; i < kabupatenList.size(); i++){
                    listSpinKabForLat.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(DataLatLngActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKabForLat);
                spinKabforLat.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {

            }
        });

        spinKabforLat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kabupatenName = parent.getItemAtPosition(position).toString();
                listKecamatan(kabupatenName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinKecforLat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kecamatanName = parent.getItemAtPosition(position).toString();
                listKelurahan(kecamatanName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinKelforLat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kelurahanName = parent.getItemAtPosition(position).toString();
                listLatlng(kelurahanName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void listKecamatan(String kabupatenName){
        String nm_kabupaten = spinKabforLat.getSelectedItem().toString();
        if (kabupatenName.equals(nm_kabupaten)){
            Call<KecamatanResponse> kecamatanResponseCall = RetrofitClient.getmInstance().getApi().spinKec(nm_kabupaten);
            kecamatanResponseCall.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(Call<KecamatanResponse> call, Response<KecamatanResponse> response) {
                    List<Kecamatan> kecamatanList = response.body().getKecamatanList();
                    List<String> listSpinKecForLat = new ArrayList<>();
                    for (int i = 0; i < kecamatanList.size(); i++){
                        listSpinKecForLat.add(kecamatanList.get(i).getNm_kecamatan());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DataLatLngActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKecForLat);
                    spinKecforLat.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<KecamatanResponse> call, Throwable t) {

                }
            });
        }
    }
    private void listKelurahan(String kecamatanName){
        String nm_kecamatan = spinKecforLat.getSelectedItem().toString();
        if (kecamatanName.equals(nm_kecamatan)){
            Call<KelurahanResponse> kelurahanResponseCall = RetrofitClient.getmInstance().getApi().spinKel(nm_kecamatan);
            kelurahanResponseCall.enqueue(new Callback<KelurahanResponse>() {
                @Override
                public void onResponse(Call<KelurahanResponse> call, Response<KelurahanResponse> response) {
                    List<Kelurahan> kelurahanList = response.body().getKelurahanList();
                    List<String> listSpinKelForLat = new ArrayList<>();
                    for (int i = 0; i < kelurahanList.size(); i++){
                        listSpinKelForLat.add(kelurahanList.get(i).getNm_kelurahan());
                    }
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(DataLatLngActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKelForLat);
                    spinKelforLat.setAdapter(stringArrayAdapter);
                }

                @Override
                public void onFailure(Call<KelurahanResponse> call, Throwable t) {

                }
            });
        }
    }
    private void listLatlng(String kelurahanName){
        String nm_kelurahan = spinKelforLat.getSelectedItem().toString();
        if (kelurahanName.equals(nm_kelurahan)){
            Call<LatlngResponse> latlngResponseCall = RetrofitClient.getmInstance().getApi().readLatlng(nm_kelurahan);
            latlngResponseCall.enqueue(new Callback<LatlngResponse>() {
                @Override
                public void onResponse(Call<LatlngResponse> call, Response<LatlngResponse> response) {
                    latlngList = response.body().getLatlngList();
                    latLngAdapter = new LatLngAdapter(DataLatLngActivity.this, latlngList);
                    recyclerView.setAdapter(latLngAdapter);
                }

                @Override
                public void onFailure(Call<LatlngResponse> call, Throwable t) {

                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, SubMenuDataDaerahActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
