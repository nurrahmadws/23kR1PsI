package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.KabupatenAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Kabupaten;
import com.example.matatabi.padm.model.KabupatenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKabupatenActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private KabupatenAdapter kabupatenAdapter;
    private List<Kabupaten> kabupatenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kabupaten);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_kabupatens);
        recyclerView.setHasFixedSize(true);
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
    protected void onResume() {
        loadData();
        super.onResume();
    }

    private void loadData(){
        final ProgressDialog progressDialog = new ProgressDialog(DataKabupatenActivity.this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();

        Call<KabupatenResponse> call = RetrofitClient.getmInstance().getApi().readKabkot();
        call.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                progressDialog.dismiss();
                kabupatenList = response.body().getKabupatenList();
                kabupatenAdapter = new KabupatenAdapter(DataKabupatenActivity.this, kabupatenList);
                recyclerView.setAdapter(kabupatenAdapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DataKabupatenActivity.this, "Server Gagal Merespon", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Cari Nama Kabupaten/Kota");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        recyclerView.setVisibility(View.GONE);
        final ProgressDialog progressDialog = new ProgressDialog(DataKabupatenActivity.this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();
        Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().searchKab(s);
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                progressDialog.dismiss();
                String value = response.body().getValue();
                recyclerView.setVisibility(View.VISIBLE);
                if (value.equals("1")){
                    kabupatenList = response.body().getKabupatenList();
                    kabupatenAdapter = new KabupatenAdapter(DataKabupatenActivity.this, kabupatenList);
                    recyclerView.setAdapter(kabupatenAdapter);
                }
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DataKabupatenActivity.this, "Server Gagal Merespon", Toast.LENGTH_LONG).show();
            }
        });
        return true;
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
