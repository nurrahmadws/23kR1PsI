package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.fragments.MhsBiodatakuFragment;
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

public class EditMhsActivity extends AppCompatActivity {
    private EditText edtNimEditMhs, edtUsernameEditMhs, edtNamaEditMhs, edtFakultasEditMhs, edtProdiEditMhs, edtProvinsiEditMhs;
    private RadioGroup radioJkEditMhs;
    private RadioButton radioBtnLakiEditMhs, radioBtnPerempuanEditMhs, radioSexButtonEditMhs;
    private Spinner spinAngkatanEditMhs, spinKabupatenMEditMhs, spinKecamatanMEditMhs, spinKelurahanMEditMhs, spinLatEditMhs, spinLngEditMhs;
    private Button btn_edit_mhs, btn_batal_mhs_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mhs);

        edtNimEditMhs = findViewById(R.id.edtNimEditMhs);
        edtUsernameEditMhs = findViewById(R.id.edtUsernameEditMhs);
        edtNamaEditMhs = findViewById(R.id.edtNamaEditMhs);
        edtFakultasEditMhs = findViewById(R.id.edtFakultasEditMhs);
        edtProdiEditMhs = findViewById(R.id.edtProdiEditMhs);
        edtProvinsiEditMhs = findViewById(R.id.edtProvinsiEditMhs);

        radioJkEditMhs = findViewById(R.id.radioJkEditMhs);
        radioBtnLakiEditMhs = findViewById(R.id.radioBtnLakiEditMhs);
        radioBtnPerempuanEditMhs = findViewById(R.id.radioBtnPerempuanEditMhs);

        spinAngkatanEditMhs = findViewById(R.id.spinAngkatanEditMhs);
        spinKabupatenMEditMhs = findViewById(R.id.spinKabupatenMEditMhs);
        spinKecamatanMEditMhs = findViewById(R.id.spinKecamatanMEditMhs);
        spinKelurahanMEditMhs = findViewById(R.id.spinKelurahanMEditMhs);
        spinLatEditMhs = findViewById(R.id.spinLatMEditMhs);
        spinLngEditMhs = findViewById(R.id.spinLngMEditMhs);

        btn_edit_mhs = findViewById(R.id.btn_edit_mhs);
        btn_batal_mhs_edit = findViewById(R.id.btn_batal_mhs_edit);

        final Intent intent = getIntent();
        String nim = intent.getStringExtra("nim");
        edtNimEditMhs.setText(nim);
        String username = intent.getStringExtra("username");
        edtUsernameEditMhs.setText(username);;
        String nama = intent.getStringExtra("nama");
        edtNamaEditMhs.setText(nama);
        String jk = intent.getStringExtra("jk");
        if (jk.equals("Perempuan")){
            radioBtnPerempuanEditMhs.setChecked(true);
        }else {
            radioBtnLakiEditMhs.setChecked(true);
        }
        String angkatan = intent.getStringExtra("angkatan");
        if (angkatan.equals("2013")){
            spinAngkatanEditMhs.setSelected(true);
        }else {
            spinAngkatanEditMhs.setSelected(false);
        }

        edtNimEditMhs.setKeyListener(edtNimEditMhs.getKeyListener());
        edtNimEditMhs.setKeyListener(null);

        edtUsernameEditMhs.setKeyListener(edtUsernameEditMhs.getKeyListener());
        edtUsernameEditMhs.setKeyListener(null);

        edtFakultasEditMhs.setKeyListener(edtFakultasEditMhs.getKeyListener());
        edtFakultasEditMhs.setKeyListener(null);

        edtProdiEditMhs.setKeyListener(edtProdiEditMhs.getKeyListener());
        edtProdiEditMhs.setKeyListener(null);

        edtProvinsiEditMhs.setKeyListener(edtProvinsiEditMhs.getKeyListener());
        edtProvinsiEditMhs.setKeyListener(null);

        Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().spinKab();
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listKabupateSpin = new ArrayList<>();
                for (int i = 0; i < kabupatenList.size(); i++){
                    listKabupateSpin.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditMhsActivity.this, android.R.layout.simple_spinner_dropdown_item, listKabupateSpin);
                spinKabupatenMEditMhs.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {
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
        btn_batal_mhs_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_edit_mhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMhs();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void listKecamatan(String kabupatenName){
        String nm_kabupaten = spinKabupatenMEditMhs.getSelectedItem().toString();
        if (kabupatenName.equals(nm_kabupaten)){
            Call<KecamatanResponse> kecamatanResponseCall = RetrofitClient.getmInstance().getApi().spinKec(nm_kabupaten);
            kecamatanResponseCall.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(Call<KecamatanResponse> call, Response<KecamatanResponse> response) {
                    List<Kecamatan> kecamatanList = response.body().getKecamatanList();
                    List<String> listKecamatanSpin = new ArrayList<>();
                    for (int i = 0; i < kecamatanList.size(); i++){
                        listKecamatanSpin.add(kecamatanList.get(i).getNm_kecamatan());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditMhsActivity.this, android.R.layout.simple_spinner_dropdown_item, listKecamatanSpin);
                    spinKecamatanMEditMhs.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<KecamatanResponse> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(EditMhsActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
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
                public void onResponse(Call<KelurahanResponse> call, Response<KelurahanResponse> response) {
                    List<Kelurahan> kelurahanList = response.body().getKelurahanList();
                    List<String> listKelurahanSpin = new ArrayList<>();
                    for (int i = 0; i < kelurahanList.size(); i++){
                        listKelurahanSpin.add(kelurahanList.get(i).getNm_kelurahan());
                    }
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(EditMhsActivity.this, android.R.layout.simple_spinner_dropdown_item, listKelurahanSpin);
                    spinKelurahanMEditMhs.setAdapter(stringArrayAdapter);
                }

                @Override
                public void onFailure(Call<KelurahanResponse> call, Throwable t) {

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
                public void onResponse(Call<LatlngResponse> call, Response<LatlngResponse> response) {
                    List<Latlng> latlngList = response.body().getLatlngList();
                    List<String> listLatLngSPin = new ArrayList<>();
                    for (int i = 0; i < latlngList.size(); i++){
                        listLatLngSPin.add(latlngList.get(i).getNm_lat());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditMhsActivity.this, android.R.layout.simple_spinner_dropdown_item, listLatLngSPin);
                    spinLatEditMhs.setAdapter(adapter);

                    List<String> listSpinLng = new ArrayList<>();
                    for (int i = 0; i < latlngList.size(); i++){
                        listSpinLng.add(latlngList.get(i).getNm_lng());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditMhsActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinLng);
                    spinLngEditMhs.setAdapter(arrayAdapter);
                }

                @Override
                public void onFailure(Call<LatlngResponse> call, Throwable t) {

                }
            });
        }
    }
    private void editMhs(){
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data ini?\nPeriksa Kembali Data Sebelun diubah").setCancelable(false)
                .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = new ProgressDialog(EditMhsActivity.this);
                        progressDialog.setMessage("Mengubah Data...");
                        progressDialog.show();

                        String nim = edtNimEditMhs.getText().toString();
                        String username = edtUsernameEditMhs.getText().toString();
                        String nama = edtNamaEditMhs.getText().toString();
                        int selectedId = radioJkEditMhs.getCheckedRadioButtonId();
                        radioSexButtonEditMhs = findViewById(selectedId);
                        String jk = radioSexButtonEditMhs.getText().toString();
                        String fakultas = edtFakultasEditMhs.getText().toString();
                        final String prodi = edtProdiEditMhs.getText().toString();
                        String angkatan = spinAngkatanEditMhs.getSelectedItem().toString();
                        String provinsi = edtProvinsiEditMhs.getText().toString();
                        String nm_kabupaten = spinKabupatenMEditMhs.getSelectedItem().toString();
                        String nm_kecamatan = spinKecamatanMEditMhs.getSelectedItem().toString();
                        String nm_kelurahan = spinKelurahanMEditMhs.getSelectedItem().toString();
                        String nm_lat = spinLatEditMhs.getSelectedItem().toString();
                        String nm_lng = spinLngEditMhs.getSelectedItem().toString();

                        if (nama.isEmpty()){
                            edtNamaEditMhs.setError("Nama Harus Diisi");
                            edtNamaEditMhs.requestFocus();
                            return;
                        }
                        Call<Value> call = RetrofitClient.getmInstance().getApi().editMhs(nim, username, nama, jk, fakultas, prodi,
                                angkatan, provinsi, nm_kabupaten, nm_kecamatan, nm_kelurahan, nm_lat, nm_lng);
                        call.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {
                                progressDialog.dismiss();
                                String value = response.body().getValue();
                                String message = response.body().getMessage();
                                if (value.equals("1")){
                                    Toast.makeText(EditMhsActivity.this, message, Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(EditMhsActivity.this, message, Toast.LENGTH_SHORT).show();
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
        AlertDialog alertDialog = ad.create();
        alertDialog.show();
    }

}
