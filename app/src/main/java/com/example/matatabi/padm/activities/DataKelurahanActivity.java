package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.KelurahanAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Kabupaten;
import com.example.matatabi.padm.model.KabupatenResponse;
import com.example.matatabi.padm.model.Kecamatan;
import com.example.matatabi.padm.model.KecamatanResponse;
import com.example.matatabi.padm.model.Kelurahan;
import com.example.matatabi.padm.model.KelurahanResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKelurahanActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private KelurahanAdapter kelurahanAdapter;
    private List<Kelurahan> kelurahanList;
    private FloatingActionButton fab_kel;
    private Spinner spinKabforKel, spinKecforKel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kelurahan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_kelurahans);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DataKelurahanActivity.this));
        spinKabforKel = findViewById(R.id.spinKabforKel);
        spinKecforKel = findViewById(R.id.spinKecforKel);
        fab_kel = findViewById(R.id.fab_kel);

        Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().spinKab();
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listSpinKabForKel = new ArrayList<>();
                for (int i = 0; i < kabupatenList.size(); i++){
                    listSpinKabForKel.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DataKelurahanActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKabForKel);
                spinKabforKel.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        spinKabforKel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kabupatenName = parent.getItemAtPosition(position).toString();
                listKecamatan(kabupatenName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinKecforKel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kecamatanName = parent.getItemAtPosition(position).toString();
                listKelurahan(kecamatanName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fab_kel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataKelurahanActivity.this, AddKelurahanActivity.class));
            }
        });
    }
    private void listKecamatan(String kabupatenName){
        String nm_kabupaten = spinKabforKel.getSelectedItem().toString();
        if (kabupatenName.equals(nm_kabupaten)){
            Call<KecamatanResponse> kecamatanResponseCall = RetrofitClient.getmInstance().getApi().spinKec(nm_kabupaten);
            kecamatanResponseCall.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(Call<KecamatanResponse> call, Response<KecamatanResponse> response) {
                    List<Kecamatan> kecamatanList = response.body().getKecamatanList();
                    List<String> listspinKecForKel = new ArrayList<>();
                    for (int i = 0; i < kecamatanList.size(); i++){
                        listspinKecForKel.add(kecamatanList.get(i).getNm_kecamatan());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(DataKelurahanActivity.this, android.R.layout.simple_spinner_dropdown_item, listspinKecForKel);
                    spinKecforKel.setAdapter(arrayAdapter);
                }

                @Override
                public void onFailure(Call<KecamatanResponse> call, Throwable t) {

                }
            });
        }
    }
    private void listKelurahan(String kecamatanName){
        final ProgressDialog progressDialog = new ProgressDialog(DataKelurahanActivity.this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();

        String nm_kecamatan = spinKecforKel.getSelectedItem().toString();
        if (kecamatanName.equals(nm_kecamatan)){
            Call<KelurahanResponse> kelurahanResponseCall = RetrofitClient.getmInstance().getApi().readKel(nm_kecamatan);
            kelurahanResponseCall.enqueue(new Callback<KelurahanResponse>() {
                @Override
                public void onResponse(Call<KelurahanResponse> call, Response<KelurahanResponse> response) {
                    progressDialog.dismiss();
                    kelurahanList = response.body().getKelurahanList();
                    kelurahanAdapter = new KelurahanAdapter(DataKelurahanActivity.this, kelurahanList);
                    recyclerView.setAdapter(kelurahanAdapter);
                }

                @Override
                public void onFailure(Call<KelurahanResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(DataKelurahanActivity.this, "Server Gagal Merespon", Toast.LENGTH_SHORT).show();
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
