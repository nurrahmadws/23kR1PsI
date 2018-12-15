package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.PadmAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Mahasiswa;
import com.example.matatabi.padm.model.MahasiswaResponse;
import com.example.matatabi.padm.model.Padm;
import com.example.matatabi.padm.model.PadmResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPadmActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PadmAdapter padmAdapter;
    private List<Padm> padmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_padm);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_padm);
        recyclerView.setLayoutManager(new LinearLayoutManager(DataPadmActivity.this));

        Call<PadmResponse> call = RetrofitClient.getmInstance().getApi().showPadm();
        call.enqueue(new Callback<PadmResponse>() {
            @Override
            public void onResponse(Call<PadmResponse> call, Response<PadmResponse> response) {
                padmList = response.body().getPadmList();
                padmAdapter = new PadmAdapter(DataPadmActivity.this, padmList);
                recyclerView.setAdapter(padmAdapter);
            }

            @Override
            public void onFailure(Call<PadmResponse> call, Throwable t) {

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
