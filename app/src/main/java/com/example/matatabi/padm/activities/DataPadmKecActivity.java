package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.PadmKecAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.PadmKecamatan;
import com.example.matatabi.padm.model.PadmKecamatanResponse;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPadmKecActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PadmKecAdapter padmKecAdapter;
    private List<PadmKecamatan> padmKecamatanList;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_padm_kec);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_padm_kec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DataPadmKecActivity.this));

        materialDesignFAM = findViewById(R.id.data_padmKec);
        floatingActionButton = findViewById(R.id.fab_show_chart_kec);

        final ProgressDialog progressDialog = new ProgressDialog(DataPadmKecActivity.this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();
        final Intent intent = getIntent();
        final String nm_kabupaten = intent.getStringExtra("nm_kabupaten");
        final String nm_kabupaten_go_intent = nm_kabupaten;

        final Call<PadmKecamatanResponse> call = RetrofitClient.getmInstance().getApi().showPadmKec(nm_kabupaten);
        call.enqueue(new Callback<PadmKecamatanResponse>() {
            @Override
            public void onResponse(@NonNull Call<PadmKecamatanResponse> call, @NonNull Response<PadmKecamatanResponse> response) {
                progressDialog.dismiss();
                padmKecamatanList = response.body().getPadmKecamatanList();
                padmKecAdapter = new PadmKecAdapter(DataPadmKecActivity.this, padmKecamatanList);
                recyclerView.setAdapter(padmKecAdapter);
            }

            @Override
            public void onFailure(Call<PadmKecamatanResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DataPadmKecActivity.this, "Sever Gagal Merespon", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DataPadmKecActivity.this, GrafikPadmKecamatanActivity.class);
                intent1.putExtra("nm_kabupaten", nm_kabupaten_go_intent);
                startActivity(intent1);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DataPadmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
