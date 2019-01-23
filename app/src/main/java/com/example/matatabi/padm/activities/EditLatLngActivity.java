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
import com.example.matatabi.padm.model.Kelurahan;
import com.example.matatabi.padm.model.KelurahanResponse;
import com.example.matatabi.padm.model.Value;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditLatLngActivity extends AppCompatActivity {
    private EditText edtTexLatEd, edtTexLngEd, edtTexIdLatEd;
    private Spinner spinKabEdLat, spinKecEdLat, spinKelEdLat;
    private Button btn_edit_Lat, btn_hapus_Lat, btn_bataled_Lat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lat_lng);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtTexLatEd = findViewById(R.id.edtTexLatEd);
        edtTexLngEd = findViewById(R.id.edtTexLngEd);
        edtTexIdLatEd = findViewById(R.id.edtTexIdLatEd);
        spinKabEdLat = findViewById(R.id.spinKabEdLat);
        spinKecEdLat = findViewById(R.id.spinKecEdLat);
        spinKelEdLat = findViewById(R.id.spinKelEdLat);
        btn_edit_Lat = findViewById(R.id.btn_edit_Lat);
        btn_hapus_Lat = findViewById(R.id.btn_hapus_Lat);
        btn_bataled_Lat = findViewById(R.id.btn_bataled_Lat);

        Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().spinKab();
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listSpinKabForLated = new ArrayList<>();
                for (int i =0; i < kabupatenList.size(); i++){
                    listSpinKabForLated.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditLatLngActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKabForLated);
                spinKabEdLat.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {

            }
        });
        spinKabEdLat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kabupatenName = parent.getItemAtPosition(position).toString();
                listKecamatan(kabupatenName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinKecEdLat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kecamatanName = parent.getItemAtPosition(position).toString();
                listKelurahan(kecamatanName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();
        edtTexIdLatEd.setText(intent.getStringExtra("id_latlng"));
        edtTexIdLatEd.setKeyListener(edtTexIdLatEd.getKeyListener());
        edtTexIdLatEd.setKeyListener(null);

        String nm_lat = intent.getStringExtra("nm_lat");
        String nm_lng = intent.getStringExtra("nm_lng");
        edtTexLatEd.setText(nm_lat);
        edtTexLngEd.setText(nm_lng);

        btn_edit_Lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(EditLatLngActivity.this);
                al.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data Ini?\nPeriksa Kembali Data Sebelum Diubah")
                        .setCancelable(false)
                        .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = new ProgressDialog(EditLatLngActivity.this);
                                progressDialog.setMessage("Mengubah Data...");
                                progressDialog.show();

                                String id_latlng = edtTexIdLatEd.getText().toString();
                                String nm_kabupaten = spinKabEdLat.getSelectedItem().toString();
                                String nm_kecamatan = spinKecEdLat.getSelectedItem().toString();
                                String nm_kelurahan = spinKelEdLat.getSelectedItem().toString();
                                String nm_lat = edtTexLatEd.getText().toString();
                                String nm_lng = edtTexLngEd.getText().toString();

                                if (nm_lat.isEmpty()){
                                    progressDialog.dismiss();
                                    edtTexLatEd.setError("Latitude Harus Diisi");
                                    edtTexLatEd.requestFocus();
                                    return;
                                }
                                if (nm_lng.isEmpty()){
                                    progressDialog.dismiss();
                                    edtTexLngEd.setError("Longtitude Harus Diisi");
                                    edtTexLngEd.requestFocus();
                                    return;
                                }
                                Call<Value> call = RetrofitClient.getmInstance().getApi().editLatlng(id_latlng, nm_kabupaten, nm_kecamatan, nm_kelurahan, nm_lat, nm_lng);
                                call.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        progressDialog.dismiss();
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")){
                                            Toast.makeText(EditLatLngActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(EditLatLngActivity.this, DataLatLngActivity.class));
                                        }else {
                                            Toast.makeText(EditLatLngActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = al.create();
                alertDialog.show();
            }
        });
        btn_hapus_Lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder au = new AlertDialog.Builder(EditLatLngActivity.this);
                au.setTitle("PERINGATAN!").setMessage("Yakin Ingin Menghapus Data Ini?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = new ProgressDialog(EditLatLngActivity.this);
                                progressDialog.setMessage("Menghapus Data...");
                                progressDialog.show();
                                String id_latlng = edtTexIdLatEd.getText().toString();
                                Call<Value> valueCall = RetrofitClient.getmInstance().getApi().deleteLatlng(id_latlng);
                                valueCall.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        progressDialog.dismiss();
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")){
                                            Toast.makeText(EditLatLngActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(EditLatLngActivity.this, DataLatLngActivity.class));
                                        }else {
                                            Toast.makeText(EditLatLngActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        progressDialog.dismiss();
                                        Toast.makeText(EditLatLngActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = au.create();
                dialog.show();
            }
        });
        btn_bataled_Lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditLatLngActivity.this, DataLatLngActivity.class));
            }
        });
    }
    private void listKecamatan(String kabupatenName){
        String nm_kabupaten = spinKabEdLat.getSelectedItem().toString();
        if (kabupatenName.equals(nm_kabupaten)){
            Call<KecamatanResponse> kecamatanResponseCall = RetrofitClient.getmInstance().getApi().spinKec(nm_kabupaten);
            kecamatanResponseCall.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(Call<KecamatanResponse> call, Response<KecamatanResponse> response) {
                    List<Kecamatan> kecamatanList = response.body().getKecamatanList();
                    List<String> listSpinKecForLated = new ArrayList<>();
                    for (int i = 0; i < kecamatanList.size(); i++){
                        listSpinKecForLated.add(kecamatanList.get(i).getNm_kecamatan());
                    }
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(EditLatLngActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKecForLated);
                    spinKecEdLat.setAdapter(stringArrayAdapter);
                }

                @Override
                public void onFailure(Call<KecamatanResponse> call, Throwable t) {

                }
            });
        }
    }
    private void listKelurahan(String kecamatanName){
        String nm_kecamatan = spinKecEdLat.getSelectedItem().toString();
        if (kecamatanName.equals(nm_kecamatan)){
            Call<KelurahanResponse> kelurahanResponseCall = RetrofitClient.getmInstance().getApi().spinKel(nm_kecamatan);
            kelurahanResponseCall.enqueue(new Callback<KelurahanResponse>() {
                @Override
                public void onResponse(Call<KelurahanResponse> call, Response<KelurahanResponse> response) {
                    List<Kelurahan> kelurahanList = response.body().getKelurahanList();
                    List<String> listSpinKelForLated = new ArrayList<>();
                    for (int i = 0; i < kelurahanList.size(); i++){
                        listSpinKelForLated.add(kelurahanList.get(i).getNm_kelurahan());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditLatLngActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKelForLated);
                    spinKelEdLat.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<KelurahanResponse> call, Throwable t) {

                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DataLatLngActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
