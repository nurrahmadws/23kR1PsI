package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.AllMahasiswaAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Mahasiswa;
import com.example.matatabi.padm.model.MahasiswaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAllDataMhsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AllMahasiswaAdapter allMahasiswaAdapter;
    private List<Mahasiswa> mahasiswaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_data_mhs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_all_mhs_admin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminAllDataMhsActivity.this));

        final ProgressDialog progressDialog = new ProgressDialog(AdminAllDataMhsActivity.this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();

        Call<MahasiswaResponse> call = RetrofitClient.getmInstance().getApi().readAllMhs();
        call.enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                progressDialog.dismiss();
                mahasiswaList = response.body().getMahasiswaList();
                allMahasiswaAdapter = new AllMahasiswaAdapter(AdminAllDataMhsActivity.this, mahasiswaList);
                recyclerView.setAdapter(allMahasiswaAdapter);
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AdminAllDataMhsActivity.this, "Server Gagal Merespon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
