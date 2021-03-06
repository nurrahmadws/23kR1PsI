package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.matatabi.padm.adapters.KecamatanAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Kabupaten;
import com.example.matatabi.padm.model.KabupatenResponse;
import com.example.matatabi.padm.model.Kecamatan;
import com.example.matatabi.padm.model.KecamatanResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKecamatanActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private KecamatanAdapter kecamatanAdapter;
    private List<Kecamatan> kecamatanList;
    private Spinner spinKabforKec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kecamatan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_kecamatans);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DataKecamatanActivity.this));
        spinKabforKec = findViewById(R.id.spinKabforKec);

        Call<KabupatenResponse> calll = RetrofitClient.getmInstance().getApi().spinKab();
        calll.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listSpinKab = new ArrayList<>();
                for (int i = 0; i < kabupatenList.size(); i++){
                    listSpinKab.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DataKecamatanActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKab);
                spinKabforKec.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        spinKabforKec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kabupatenName = parent.getItemAtPosition(position).toString();
                listKecamatan(kabupatenName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fab = findViewById(R.id.fab_kec);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataKecamatanActivity.this, AddKecamatanActivity.class));
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void listKecamatan(String kabupatenName){
        final ProgressDialog progressDialog = new ProgressDialog(DataKecamatanActivity.this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();

        String nm_kabupaten = spinKabforKec.getSelectedItem().toString();

        if (kabupatenName.equals(nm_kabupaten)){
            Call<KecamatanResponse> call = RetrofitClient.getmInstance().getApi().readKec(nm_kabupaten);
            call.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(@NonNull Call<KecamatanResponse> call, @NonNull Response<KecamatanResponse> response) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    kecamatanList = response.body().getKecamatanList();
                    kecamatanAdapter = new KecamatanAdapter(DataKecamatanActivity.this, kecamatanList);
                    recyclerView.setAdapter(kecamatanAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<KecamatanResponse> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(DataKecamatanActivity.this, "Server Gagal Merespon", Toast.LENGTH_SHORT).show();
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
