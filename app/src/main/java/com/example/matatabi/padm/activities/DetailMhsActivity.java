package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

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
    FloatingActionButton floatingActionButton1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mhs);

        editNimm = findViewById(R.id.editNimm);
        Intent intent = getIntent();
        editNimm.setText(intent.getStringExtra("nim"));
        editNimm.setKeyListener(editNimm.getKeyListener());
        editNimm.setKeyListener(null);

        recyclerView = findViewById(R.id.rv_detail_mhs);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailMhsActivity.this));

        materialDesignFAM = findViewById(R.id.FabdetailMhs);
        floatingActionButton1 = findViewById(R.id.fab_tampil_peta_mhs);

        final String nim = editNimm.getText().toString();
        Call<MahasiswaResponse> call = RetrofitClient.getmInstance().getApi().readDetailMhs(nim);
        call.enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                mahasiswaList = response.body().getMahasiswaList();
                detailMhsAdapter = new DetailMhsAdapter(DetailMhsActivity.this, mahasiswaList);
                recyclerView.setAdapter(detailMhsAdapter);
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {

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
    }
}