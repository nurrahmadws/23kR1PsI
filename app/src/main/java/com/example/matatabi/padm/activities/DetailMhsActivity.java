package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.DetailMhsAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Mahasiswa;
import com.example.matatabi.padm.model.MahasiswaResponse;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMhsActivity extends AppCompatActivity {
    private EditText editNimm;
    private RecyclerView recyclerView;
    private DetailMhsAdapter detailMhsAdapter;
    private List<Mahasiswa> mahasiswaList;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, fab_tampil_peta_mhs_alamat_sekarang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mhs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editNimm = findViewById(R.id.editNimm);
        final Intent intent = getIntent();
        editNimm.setText(intent.getStringExtra("nim"));
        editNimm.setKeyListener(editNimm.getKeyListener());
        editNimm.setKeyListener(null);

        recyclerView = findViewById(R.id.rv_detail_mhs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailMhsActivity.this));

        materialDesignFAM = findViewById(R.id.FabdetailMhs);
        floatingActionButton1 = findViewById(R.id.fab_tampil_peta_mhs);
        fab_tampil_peta_mhs_alamat_sekarang = findViewById(R.id.fab_tampil_peta_mhs_alamat_sekarang);

        final ProgressDialog progressDialog = new ProgressDialog(DetailMhsActivity.this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();

        final String nim = editNimm.getText().toString();
        Call<MahasiswaResponse> call = RetrofitClient.getmInstance().getApi().readDetailMhs(nim);
        call.enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                progressDialog.dismiss();
                mahasiswaList = response.body().getMahasiswaList();
                detailMhsAdapter = new DetailMhsAdapter(DetailMhsActivity.this, mahasiswaList);
                recyclerView.setAdapter(detailMhsAdapter);
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailMhsActivity.this, "Server Gagal Merespon", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailMhsActivity.this, ShowMapsAllMhsActivity.class);
                intent1.putExtra("nim", nim);
                startActivity(intent1);
            }
        });

        fab_tampil_peta_mhs_alamat_sekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(DetailMhsActivity.this, ShowMarkerMhsAlamatActivity.class);
                intent2.putExtra("nim", nim);
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
