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
import com.example.matatabi.padm.model.Kelurahan;
import com.example.matatabi.padm.model.KelurahanResponse;
import com.example.matatabi.padm.model.Value;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLatlngActivity extends AppCompatActivity {
    private EditText edtTexLatAdd, edtTexLngAdd;
    private Spinner spinKabAddLat, spinKecAddLat, spinKelAddLat;
    private Button btn_simpan_Lat, btn_batal_Lat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_latlng);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtTexLatAdd = findViewById(R.id.edtTexLatAdd);
        edtTexLngAdd = findViewById(R.id.edtTexLngAdd);
        spinKabAddLat = findViewById(R.id.spinKabAddLat);
        spinKecAddLat = findViewById(R.id.spinKecAddLat);
        spinKelAddLat = findViewById(R.id.spinKelAddLat);
        btn_batal_Lat = findViewById(R.id.btn_batal_Lat);
        btn_simpan_Lat = findViewById(R.id.btn_simpan_Lat);

        Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().spinKab();
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listSpinKabForAddLat = new ArrayList<>();
                for (int i = 0; i < kabupatenList.size(); i++){
                    listSpinKabForAddLat.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddLatlngActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKabForAddLat);
                spinKabAddLat.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {

            }
        });

        spinKabAddLat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kabupatenName = parent.getItemAtPosition(position).toString();
                listKecamatan(kabupatenName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinKecAddLat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kecamatanName = parent.getItemAtPosition(position).toString();
                listKelurahan(kecamatanName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_simpan_Lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(AddLatlngActivity.this);
                progressDialog.setMessage("Menambahkan Data...");
                progressDialog.show();

                String nm_kabupaten = spinKabAddLat.getSelectedItem().toString();
                String nm_kecamatan = spinKecAddLat.getSelectedItem().toString();
                String nm_kelurahan = spinKelAddLat.getSelectedItem().toString();
                String nm_lat = edtTexLatAdd.getText().toString();
                String nm_lng = edtTexLngAdd.getText().toString();

                if (nm_lat.isEmpty()){
                    edtTexLatAdd.setError("Latitude Harus Diisi");
                    edtTexLatAdd.requestFocus();
                    return;
                }
                if (nm_lng.isEmpty()){
                    edtTexLngAdd.setError("Longtitude Harus Diisi");
                    edtTexLngAdd.requestFocus();
                    return;
                }
                Call<Value> call = RetrofitClient.getmInstance().getApi().addLatlng(nm_kabupaten, nm_kecamatan, nm_kelurahan, nm_lat, nm_lng);
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        progressDialog.dismiss();
                        String value = response.body().getValue();
                        String message = response.body().getMessage();
                        if (value.equals("1")){
                            Toast.makeText(AddLatlngActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(AddLatlngActivity.this, DataLatLngActivity.class));
                        }else {
                            Toast.makeText(AddLatlngActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }
        });
        btn_batal_Lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddLatlngActivity.this, DataLatLngActivity.class));
            }
        });
    }

    private void listKecamatan(String kabupatenName){
        String nm_kabupaten = spinKabAddLat.getSelectedItem().toString();
        if (kabupatenName.equals(nm_kabupaten)){
            Call<KecamatanResponse> kecamatanResponseCall = RetrofitClient.getmInstance().getApi().spinKec(nm_kabupaten);
            kecamatanResponseCall.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(Call<KecamatanResponse> call, Response<KecamatanResponse> response) {
                    List<Kecamatan> kecamatanList = response.body().getKecamatanList();
                    List<String> listSpinKecForAddLat = new ArrayList<>();
                    for (int i = 0; i < kecamatanList.size(); i++){
                        listSpinKecForAddLat.add(kecamatanList.get(i).getNm_kecamatan());
                    }
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(AddLatlngActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKecForAddLat);
                    spinKecAddLat.setAdapter(stringArrayAdapter);
                }

                @Override
                public void onFailure(Call<KecamatanResponse> call, Throwable t) {

                }
            });
        }
    }

    private void listKelurahan(String kecamatanName){
        String nm_kecamatan = spinKecAddLat.getSelectedItem().toString();
        if (kecamatanName.equals(nm_kecamatan)){
            Call<KelurahanResponse> kelurahanResponseCall = RetrofitClient.getmInstance().getApi().spinKel(nm_kecamatan);
            kelurahanResponseCall.enqueue(new Callback<KelurahanResponse>() {
                @Override
                public void onResponse(Call<KelurahanResponse> call, Response<KelurahanResponse> response) {
                    List<Kelurahan> kelurahanList = response.body().getKelurahanList();
                    List<String> listSpinKelForAddLat = new ArrayList<>();
                    for (int i = 0; i < kelurahanList.size(); i++){
                        listSpinKelForAddLat.add(kelurahanList.get(i).getNm_kelurahan());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddLatlngActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKelForAddLat);
                    spinKelAddLat.setAdapter(adapter);
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
