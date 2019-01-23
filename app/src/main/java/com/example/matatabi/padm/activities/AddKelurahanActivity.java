package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Kabupaten;
import com.example.matatabi.padm.model.KabupatenResponse;
import com.example.matatabi.padm.model.Kecamatan;
import com.example.matatabi.padm.model.KecamatanResponse;
import com.example.matatabi.padm.model.Value;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddKelurahanActivity extends AppCompatActivity {
    private EditText edtTexKelurahanAdd;
    private Spinner spinKabAddKel, spinKecAddKel;
    private Button btn_simpan_kel, btn_batal_kel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kelurahan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtTexKelurahanAdd = findViewById(R.id.edtTexKelurahanAdd);
        spinKabAddKel = findViewById(R.id.spinKabAddKel);
        spinKecAddKel = findViewById(R.id.spinKecAddKel);
        btn_simpan_kel = findViewById(R.id.btn_simpan_kel);
        btn_batal_kel = findViewById(R.id.btn_batal_kel);

        final Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().spinKab();
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listSpinKabForAddKel = new ArrayList<>();
                for (int i = 0; i < kabupatenList.size(); i++){
                    listSpinKabForAddKel.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddKelurahanActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKabForAddKel);
                spinKabAddKel.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {

            }
        });

        spinKabAddKel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kabupatenName = parent.getItemAtPosition(position).toString();
                listKecamatan(kabupatenName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_simpan_kel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ProgressDialog progressDialog = new ProgressDialog(AddKelurahanActivity.this);
                progressDialog.setMessage("Menambahkan Data...");
                progressDialog.show();

                String nm_kabupaten = spinKabAddKel.getSelectedItem().toString();
                String nm_kecamatan = spinKecAddKel.getSelectedItem().toString();
                String nm_kelurahan = edtTexKelurahanAdd.getText().toString();

                if (nm_kelurahan.isEmpty()){
                    progressDialog.dismiss();
                    edtTexKelurahanAdd.setError("Kolom Ini Harus Diisi");
                    edtTexKelurahanAdd.requestFocus();
                    return;
                }
                Call<Value> call = RetrofitClient.getmInstance().getApi().addKel(nm_kabupaten, nm_kecamatan, nm_kelurahan);
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        progressDialog.dismiss();
                        String value = response.body().getValue();
                        String message = response.body().getMessage();
                        if (value.equals("1")){
                            Toast.makeText(AddKelurahanActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(AddKelurahanActivity.this, DataKelurahanActivity.class));
                        }else {
                            Toast.makeText(AddKelurahanActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        progressDialog.dismiss();
                        t.printStackTrace();
                        Toast.makeText(AddKelurahanActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_batal_kel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddKelurahanActivity.this, DataKelurahanActivity.class));
            }
        });
    }
    private void listKecamatan(String kabupatenName){
        String nm_kabupaten = spinKabAddKel.getSelectedItem().toString();
        if (kabupatenName.equals(nm_kabupaten)){
            Call<KecamatanResponse> kecamatanResponseCall = RetrofitClient.getmInstance().getApi().spinKec(nm_kabupaten);
            kecamatanResponseCall.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(Call<KecamatanResponse> call, Response<KecamatanResponse> response) {
                    List<Kecamatan> kecamatanList = response.body().getKecamatanList();
                    List<String> listSpinKecForAddKel = new ArrayList<>();
                    for (int i = 0; i < kecamatanList.size(); i++){
                        listSpinKecForAddKel.add(kecamatanList.get(i).getNm_kecamatan());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddKelurahanActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKecForAddKel);
                    spinKecAddKel.setAdapter(arrayAdapter);
                }

                @Override
                public void onFailure(Call<KecamatanResponse> call, Throwable t) {

                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DataKelurahanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
