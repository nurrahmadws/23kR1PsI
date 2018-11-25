package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.KabupatenAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Kabupaten;
import com.example.matatabi.padm.model.KabupatenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKabupatenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private KabupatenAdapter kabupatenAdapter;
    private List<Kabupaten> kabupatenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kabupaten);

        recyclerView = findViewById(R.id.rv_kabupatens);
        recyclerView.setLayoutManager(new LinearLayoutManager(DataKabupatenActivity.this));

        fab = findViewById(R.id.fab_kab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataKabupatenActivity.this, AddKabupatenActivity.class));
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadData();
    }

    private void loadData(){
        Call<KabupatenResponse> call = RetrofitClient.getmInstance().getApi().readKabkot();
        call.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                kabupatenList = response.body().getKabupatenList();
                kabupatenAdapter = new KabupatenAdapter(DataKabupatenActivity.this, kabupatenList);
                recyclerView.setAdapter(kabupatenAdapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {

            }
        });
    }
}
