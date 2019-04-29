package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.PadmKelAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.PadmKelurahan;
import com.example.matatabi.padm.model.PadmKelurahanResponse;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPadmKelActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PadmKelAdapter padmKelAdapter;
    private List<PadmKelurahan> padmKelurahanList;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton, fab_show_map_kel_mhs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_padm_kel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_padm_kel);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DataPadmKelActivity.this));

        materialDesignFAM = findViewById(R.id.data_padmKel);
        floatingActionButton = findViewById(R.id.fab_show_chart_kel);
        fab_show_map_kel_mhs = findViewById(R.id.fab_show_map_kel_mhs);

        final ProgressDialog progressDialog = new ProgressDialog(DataPadmKelActivity.this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();

        final Intent intent = getIntent();
        final String nm_kecamatan = intent.getStringExtra("nm_kecamatan");
        final String nm_kecamatan_go_intent = nm_kecamatan;

        Call<PadmKelurahanResponse> call = RetrofitClient.getmInstance().getApi().showPadmKel(nm_kecamatan);
        call.enqueue(new Callback<PadmKelurahanResponse>() {
            @Override
            public void onResponse(@NonNull Call<PadmKelurahanResponse> call, @NonNull Response<PadmKelurahanResponse> response) {
                progressDialog.dismiss();
                padmKelurahanList = response.body().getPadmKelurahanList();
                padmKelAdapter = new PadmKelAdapter(DataPadmKelActivity.this, padmKelurahanList);
                recyclerView.setAdapter(padmKelAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<PadmKelurahanResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DataPadmKelActivity.this, "Server Gagal Merespon", Toast.LENGTH_SHORT).show();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DataPadmKelActivity.this, GrafikPadmKelurahanActivity.class);
                intent1.putExtra("nm_kecamatan", nm_kecamatan_go_intent);
                startActivity(intent1);
            }
        });
        fab_show_map_kel_mhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(DataPadmKelActivity.this, MarkerAllMahasiswaBsdKelActivity.class);
                intent2.putExtra("nm_kecamatan", nm_kecamatan_go_intent);
                startActivity(intent2);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
