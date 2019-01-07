package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

public class EditKelurahanActivity extends AppCompatActivity {
    private EditText edtTexKelurahanEd, edtTexIdKelurahanEd;
    private Spinner spinKabEdKel, spinKecEdKel;
    private Button btn_edit_kel, btn_hapus_kel, btn_bataled_kel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kelurahan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtTexKelurahanEd = findViewById(R.id.edtTexKelurahanEd);
        edtTexIdKelurahanEd = findViewById(R.id.edtTexIdKelurahanEd);
        spinKabEdKel = findViewById(R.id.spinKabEdKel);
        spinKecEdKel = findViewById(R.id.spinKecEdKel);
        btn_edit_kel = findViewById(R.id.btn_edit_kel);
        btn_bataled_kel = findViewById(R.id.btn_bataled_kel);
        btn_hapus_kel  = findViewById(R.id.btn_hapus_kel);

        Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().spinKab();
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listSpinKabForEdKel = new ArrayList<>();
                for (int i = 0;i < kabupatenList.size(); i++){
                    listSpinKabForEdKel.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(EditKelurahanActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKabForEdKel);
                spinKabEdKel.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {

            }
        });

        spinKabEdKel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kabupatenName = parent.getItemAtPosition(position).toString();
                listKecamatan(kabupatenName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();
        edtTexIdKelurahanEd.setText(intent.getStringExtra("id_kelurahan"));
        edtTexIdKelurahanEd.setKeyListener(edtTexIdKelurahanEd.getKeyListener());
        edtTexIdKelurahanEd.setKeyListener(null);

        String nm_kelurahan = intent.getStringExtra("nm_kelurahan");
        edtTexKelurahanEd.setText(nm_kelurahan);

        btn_edit_kel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(EditKelurahanActivity.this);
                ad.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data Ini?\nPeriksa Kemabali Data Sebelum Diubah")
                        .setCancelable(false)
                        .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = new ProgressDialog(EditKelurahanActivity.this);
                                progressDialog.setMessage("Mengubah Data...");
                                progressDialog.show();

                                String id_kelurahan = edtTexIdKelurahanEd.getText().toString();
                                String nm_kabupaten = spinKabEdKel.getSelectedItem().toString();
                                String nm_kecamatan = spinKecEdKel.getSelectedItem().toString();
                                String nm_kelurahan = edtTexKelurahanEd.getText().toString();

                                Call<Value> call = RetrofitClient.getmInstance().getApi().editKel(id_kelurahan, nm_kabupaten, nm_kecamatan, nm_kelurahan);
                                call.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        progressDialog.dismiss();
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")){
                                            Toast.makeText(EditKelurahanActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(EditKelurahanActivity.this, DataKelurahanActivity.class));
                                        }else {
                                            Toast.makeText(EditKelurahanActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        progressDialog.dismiss();
                                        t.printStackTrace();
                                        Toast.makeText(EditKelurahanActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = ad.create();
                alertDialog.show();
            }
        });
        btn_hapus_kel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(EditKelurahanActivity.this);
                al.setTitle("PERINGATAN!").setMessage("Yakin Ingin Menghapus Data Ini?").setCancelable(false)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = new ProgressDialog(EditKelurahanActivity.this);
                                progressDialog.setMessage("Menghapus Data...");
                                progressDialog.show();
                                String id_kelurahan = edtTexIdKelurahanEd.getText().toString();
                                Call<Value> valueCall = RetrofitClient.getmInstance().getApi().deleteKel(id_kelurahan);
                                valueCall.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        progressDialog.dismiss();
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")){
                                            Toast.makeText(EditKelurahanActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(EditKelurahanActivity.this, DataKelurahanActivity.class));
                                        }else {
                                            Toast.makeText(EditKelurahanActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        progressDialog.dismiss();
                                        t.printStackTrace();
                                        Toast.makeText(EditKelurahanActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = al.create();
                dialog.show();
            }
        });
        btn_bataled_kel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditKelurahanActivity.this, DataKelurahanActivity.class));
            }
        });

    }

    private void listKecamatan(String kabupatenName){
        String nm_kabupaten = spinKabEdKel.getSelectedItem().toString();
        if (kabupatenName.equals(nm_kabupaten)){
            Call<KecamatanResponse> kecamatanResponseCall = RetrofitClient.getmInstance().getApi().spinKec(nm_kabupaten);
            kecamatanResponseCall.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(Call<KecamatanResponse> call, Response<KecamatanResponse> response) {
                    List<Kecamatan> kecamatanList = response.body().getKecamatanList();
                    List<String> listSpinKecForKelEd = new ArrayList<>();
                    for (int i = 0; i < kecamatanList.size(); i++){
                        listSpinKecForKelEd.add(kecamatanList.get(i).getNm_kecamatan());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditKelurahanActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKecForKelEd);
                    spinKecEdKel.setAdapter(arrayAdapter);
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
