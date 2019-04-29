package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.matatabi.padm.model.Latlng;
import com.example.matatabi.padm.model.LatlngResponse;
import com.example.matatabi.padm.model.Value;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMhsAsalDaerahActivity extends AppCompatActivity {
    private Spinner spinKabupatenMEditMhs, spinKecamatanMEditMhs, spinKelurahanMEditMhs, spinLatEditMhs, spinLngEditMhs;
    EditText edtProvinsiEditMhs, edtNimEditMhsAsal_daerah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mhs_asal_daerah);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinKabupatenMEditMhs = findViewById(R.id.spinKabupatenMEditMhs);
        spinKecamatanMEditMhs = findViewById(R.id.spinKecamatanMEditMhs);
        spinKelurahanMEditMhs = findViewById(R.id.spinKelurahanMEditMhs);
        spinLatEditMhs = findViewById(R.id.spinLatMEditMhs);
        spinLngEditMhs = findViewById(R.id.spinLngMEditMhs);

        edtProvinsiEditMhs = findViewById(R.id.edtProvinsiEditMhs);
        edtNimEditMhsAsal_daerah = findViewById(R.id.edtNimEditMhsAsal_daerah);

        Intent intent = getIntent();
        String nim = intent.getStringExtra("nim");
        edtNimEditMhsAsal_daerah.setText(nim);
        edtProvinsiEditMhs.setKeyListener(edtProvinsiEditMhs.getKeyListener());
        edtProvinsiEditMhs.setKeyListener(null);

        Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().spinKab();
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(@NonNull Call<KabupatenResponse> call, @NonNull Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listKabupateSpin = new ArrayList<>();
                for (int i = 0; i < kabupatenList.size(); i++){
                    listKabupateSpin.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditMhsAsalDaerahActivity.this, android.R.layout.simple_spinner_dropdown_item, listKabupateSpin);
                spinKabupatenMEditMhs.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<KabupatenResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        spinKabupatenMEditMhs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kabupatenName = parent.getItemAtPosition(position).toString();
                listKecamatan(kabupatenName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinKecamatanMEditMhs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kecamatanName = parent.getItemAtPosition(position).toString();
                listKelurahan(kecamatanName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinKelurahanMEditMhs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kelurahanName = parent.getItemAtPosition(position).toString();
                listLatLng(kelurahanName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btn_edit_mhs_asal_daerah = findViewById(R.id.btn_edit_mhs_asal_daerah);
        btn_edit_mhs_asal_daerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData();
            }
        });
    }

    private void listKecamatan(String kabupatenName){
        String nm_kabupaten = spinKabupatenMEditMhs.getSelectedItem().toString();
        if (kabupatenName.equals(nm_kabupaten)){
            Call<KecamatanResponse> kecamatanResponseCall = RetrofitClient.getmInstance().getApi().spinKec(nm_kabupaten);
            kecamatanResponseCall.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(@NonNull Call<KecamatanResponse> call, @NonNull Response<KecamatanResponse> response) {
                    List<Kecamatan> kecamatanList = response.body().getKecamatanList();
                    List<String> listKecamatanSpin = new ArrayList<>();
                    for (int i = 0; i < kecamatanList.size(); i++){
                        listKecamatanSpin.add(kecamatanList.get(i).getNm_kecamatan());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditMhsAsalDaerahActivity.this, android.R.layout.simple_spinner_dropdown_item, listKecamatanSpin);
                    spinKecamatanMEditMhs.setAdapter(adapter);
                }

                @Override
                public void onFailure(@NonNull Call<KecamatanResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(EditMhsAsalDaerahActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void listKelurahan(String kecamatanName){
        String nm_kecamatan = spinKecamatanMEditMhs.getSelectedItem().toString();
        if (kecamatanName.equals(nm_kecamatan)){
            Call<KelurahanResponse> kelurahanResponseCall = RetrofitClient.getmInstance().getApi().spinKel(nm_kecamatan);
            kelurahanResponseCall.enqueue(new Callback<KelurahanResponse>() {
                @Override
                public void onResponse(@NonNull Call<KelurahanResponse> call, @NonNull Response<KelurahanResponse> response) {
                    List<Kelurahan> kelurahanList = response.body().getKelurahanList();
                    List<String> listKelurahanSpin = new ArrayList<>();
                    for (int i = 0; i < kelurahanList.size(); i++){
                        listKelurahanSpin.add(kelurahanList.get(i).getNm_kelurahan());
                    }
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(EditMhsAsalDaerahActivity.this, android.R.layout.simple_spinner_dropdown_item, listKelurahanSpin);
                    spinKelurahanMEditMhs.setAdapter(stringArrayAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<KelurahanResponse> call, @NonNull Throwable t) {

                }
            });
        }
    }

    private void listLatLng(String kelurahanName){
        String nm_kelurahan = spinKelurahanMEditMhs.getSelectedItem().toString();
        if (kelurahanName.equals(nm_kelurahan)){
            Call<LatlngResponse> latlngResponseCall = RetrofitClient.getmInstance().getApi().spinLatlng(nm_kelurahan);
            latlngResponseCall.enqueue(new Callback<LatlngResponse>() {
                @Override
                public void onResponse(@NonNull Call<LatlngResponse> call, @NonNull Response<LatlngResponse> response) {
                    List<Latlng> latlngList = response.body().getLatlngList();
                    List<String> listLatLngSPin = new ArrayList<>();
                    for (int i = 0; i < latlngList.size(); i++){
                        listLatLngSPin.add(latlngList.get(i).getNm_lat());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditMhsAsalDaerahActivity.this, android.R.layout.simple_spinner_dropdown_item, listLatLngSPin);
                    spinLatEditMhs.setAdapter(adapter);

                    List<String> listSpinLng = new ArrayList<>();
                    for (int i = 0; i < latlngList.size(); i++){
                        listSpinLng.add(latlngList.get(i).getNm_lng());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditMhsAsalDaerahActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinLng);
                    spinLngEditMhs.setAdapter(arrayAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<LatlngResponse> call, @NonNull Throwable t) {

                }
            });
        }
    }

    private void editData(){
        AlertDialog.Builder au = new AlertDialog.Builder(this);
        au.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data ini?\nPeriksa Kembali Data Sebelum diubah").setCancelable(false)
                .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = new ProgressDialog(EditMhsAsalDaerahActivity.this);
                        progressDialog.setMessage("Mengubah Data...");
                        progressDialog.show();

                        String nim = edtNimEditMhsAsal_daerah.getText().toString();
                        String provinsi = edtProvinsiEditMhs.getText().toString();
                        String nm_kabupaten = spinKabupatenMEditMhs.getSelectedItem().toString();
                        String nm_kecamatan = spinKecamatanMEditMhs.getSelectedItem().toString();
                        String nm_kelurahan = spinKelurahanMEditMhs.getSelectedItem().toString();
                        String nm_lat = spinLatEditMhs.getSelectedItem().toString();
                        String nm_lng = spinLngEditMhs.getSelectedItem().toString();

                        Call<Value> call = RetrofitClient.getmInstance().getApi().editMhsAsalDaerah(nim, provinsi, nm_kabupaten, nm_kecamatan, nm_kelurahan, nm_lat, nm_lng);
                        call.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                                progressDialog.dismiss();
                                String value = response.body().getValue();
                                String message = response.body().getMessage();
                                if (value.equals("1")){
                                    Toast.makeText(EditMhsAsalDaerahActivity.this, message, Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(EditMhsAsalDaerahActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
