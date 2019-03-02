package com.example.matatabi.padm.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMhsActivity extends AppCompatActivity {
    private EditText edtNimEditMhs, edtUsernameEditMhs, edtNikEditMhs, edtNamaEditMhs, edtTempatLahirEditMhs,
            edtTglLahirEditMhs, edtTglLahirResult, edtNoHpEditMhs, edtEmailEditMhs, edtFakultasEditMhs, edtProdiEditMhs, edtProvinsiEditMhs,
            edtAlamatSekarangEditMhs, edtLatAlamatSekarangEditMhs, edtLngAlamatSekarangEditMhs;
    private RadioGroup radioJkEditMhs;
    private RadioButton radioBtnLakiEditMhs, radioBtnPerempuanEditMhs, radioSexButtonEditMhs;
    private Spinner spinAngkatanEditMhs, spinKabupatenMEditMhs, spinKecamatanMEditMhs, spinKelurahanMEditMhs, spinLatEditMhs, spinLngEditMhs, spinKelasEditMhs;
    private SimpleDateFormat simpleDateFormat;

    Criteria criteria;
    LocationManager locationManager;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mhs);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        edtNimEditMhs = findViewById(R.id.edtNimEditMhs);
        edtUsernameEditMhs = findViewById(R.id.edtUsernameEditMhs);
        edtNikEditMhs = findViewById(R.id.edtNikEditMhs);
        edtNamaEditMhs = findViewById(R.id.edtNamaEditMhs);
        edtTempatLahirEditMhs = findViewById(R.id.edtTempat_lahirEdtMhs);
        edtTglLahirEditMhs = findViewById(R.id.edtTglLahirEdtMhs);
        edtTglLahirResult = findViewById(R.id.edtTglLahirEdtMhs);
        edtNoHpEditMhs = findViewById(R.id.edtNoHpEdtMhs);
        edtEmailEditMhs = findViewById(R.id.edtEmailEdtMhs);
        edtFakultasEditMhs = findViewById(R.id.edtFakultasEditMhs);
        edtProdiEditMhs = findViewById(R.id.edtProdiEditMhs);
        edtProvinsiEditMhs = findViewById(R.id.edtProvinsiEditMhs);
        edtAlamatSekarangEditMhs = findViewById(R.id.edtAlamatSekarangEditMhs);
        edtLatAlamatSekarangEditMhs = findViewById(R.id.edtLatAlamatSekarangEditMhs);
        edtLngAlamatSekarangEditMhs = findViewById(R.id.edtLngAlamatSekarangEditMhs);

        radioJkEditMhs = findViewById(R.id.radioJkEditMhs);
        radioBtnLakiEditMhs = findViewById(R.id.radioBtnLakiEditMhs);
        radioBtnPerempuanEditMhs = findViewById(R.id.radioBtnPerempuanEditMhs);

        spinAngkatanEditMhs = findViewById(R.id.spinAngkatanEditMhs);
        spinKabupatenMEditMhs = findViewById(R.id.spinKabupatenMEditMhs);
        spinKecamatanMEditMhs = findViewById(R.id.spinKecamatanMEditMhs);
        spinKelurahanMEditMhs = findViewById(R.id.spinKelurahanMEditMhs);
        spinLatEditMhs = findViewById(R.id.spinLatMEditMhs);
        spinLngEditMhs = findViewById(R.id.spinLngMEditMhs);
        spinKelasEditMhs = findViewById(R.id.spinKelasEdtMhs);

        Button btn_edit_mhs = findViewById(R.id.btn_edit_mhs);
        Button btn_batal_mhs_edit = findViewById(R.id.btn_batal_mhs_edit);

        final Intent intent = getIntent();
        String nim = intent.getStringExtra("nim");
        edtNimEditMhs.setText(nim);
        String username = intent.getStringExtra("username");
        edtUsernameEditMhs.setText(username);
        String nik = intent.getStringExtra("nik");
        edtNikEditMhs.setText(nik);
        String nama = intent.getStringExtra("nama");
        edtNamaEditMhs.setText(nama);
        String jk = intent.getStringExtra("jk");

        String tempat_lahir = intent.getStringExtra("tempat_lahir");
        edtTempatLahirEditMhs.setText(tempat_lahir);
        String tgl_lahir = intent.getStringExtra("tgl_lahir");
        edtTglLahirEditMhs.setText(tgl_lahir);
        String no_hp = intent.getStringExtra("no_hp");
        edtNoHpEditMhs.setText(no_hp);
        String email = intent.getStringExtra("email");
        edtEmailEditMhs.setText(email);
        String angkatan = intent.getStringExtra("angkatan");
        if (angkatan.equals("2013")){
            spinAngkatanEditMhs.setSelected(true);
        }else {
            spinAngkatanEditMhs.setSelected(false);
        }
        String alamat_sekarang = intent.getStringExtra("alamat_sekarang");
        edtAlamatSekarangEditMhs.setText(alamat_sekarang);

        String lat_alamat_sekarang = intent.getStringExtra("lat_alamat_sekarang");
        edtLatAlamatSekarangEditMhs.setText(lat_alamat_sekarang);;
        edtLatAlamatSekarangEditMhs.setKeyListener(edtLatAlamatSekarangEditMhs.getKeyListener());
        edtLatAlamatSekarangEditMhs.setKeyListener(null);

        String lng_alamat_sekarang = intent.getStringExtra("lng_alamat_sekarang");
        edtLngAlamatSekarangEditMhs.setText(lng_alamat_sekarang);
        edtLngAlamatSekarangEditMhs.setKeyListener(edtLngAlamatSekarangEditMhs.getKeyListener());
        edtLngAlamatSekarangEditMhs.setKeyListener(null);

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

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        edtTglLahirEditMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);

        Button btn_get_koordinat_alamat_sekarang_mhs_edit = findViewById(R.id.btn_get_koordinat_alamat_sekarang_mhs_edit);
        btn_get_koordinat_alamat_sekarang_mhs_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditMhsActivity.this, GetCoordinateManuallyEditActivity.class));
            }
        });
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edtTglLahirResult.setText(simpleDateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
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

    private boolean isEmailValid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void editMhs(){
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data ini?\nPeriksa Kembali Data Sebelum diubah").setCancelable(false)
                .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = new ProgressDialog(EditMhsActivity.this);
                        progressDialog.setMessage("Mengubah Data...");
                        progressDialog.show();

                        String nim = edtNimEditMhs.getText().toString();
                        String username = edtUsernameEditMhs.getText().toString();
                        String nik = edtNikEditMhs.getText().toString();
                        String nama = edtNamaEditMhs.getText().toString();

                        int selectedId = radioJkEditMhs.getCheckedRadioButtonId();
                        if (radioJkEditMhs.getCheckedRadioButtonId() == -1){
                            progressDialog.dismiss();
                            Toast.makeText(EditMhsActivity.this, "Jenis Kelamin Belum Dipilih", Toast.LENGTH_LONG).show();
                            return;
                        }
                        radioSexButtonEditMhs = findViewById(selectedId);

                        String jk = radioSexButtonEditMhs.getText().toString();
                        String tempat_lahir = edtTempatLahirEditMhs.getText().toString();
                        String tgl_lahir = edtTglLahirResult.getText().toString();
                        String no_hp = edtNoHpEditMhs.getText().toString();
                        String email = edtEmailEditMhs.getText().toString();
                        String fakultas = edtFakultasEditMhs.getText().toString();
                        final String prodi = edtProdiEditMhs.getText().toString();
                        String angkatan = spinAngkatanEditMhs.getSelectedItem().toString();
                        String kelas = spinKelasEditMhs.getSelectedItem().toString();
                        String provinsi = edtProvinsiEditMhs.getText().toString();
                        String nm_kabupaten = spinKabupatenMEditMhs.getSelectedItem().toString();
                        String nm_kecamatan = spinKecamatanMEditMhs.getSelectedItem().toString();
                        String nm_kelurahan = spinKelurahanMEditMhs.getSelectedItem().toString();
                        String nm_lat = spinLatEditMhs.getSelectedItem().toString();
                        String nm_lng = spinLngEditMhs.getSelectedItem().toString();
                        String alamat_sekarang = edtAlamatSekarangEditMhs.getText().toString();
                        String lat_alamat_sekarang = edtLatAlamatSekarangEditMhs.getText().toString();
                        String lng_alamat_sekarang = edtLngAlamatSekarangEditMhs.getText().toString();

                        if (nik.isEmpty()){
                            progressDialog.dismiss();
                            edtNikEditMhs.setError("NIK Harus Diisi");
                            edtNikEditMhs.requestFocus();
                            return;
                        }
                        if (nama.isEmpty()){
                            progressDialog.dismiss();
                            edtNamaEditMhs.setError("Nama Harus Diisi");
                            edtNamaEditMhs.requestFocus();
                            return;
                        }
                        if (tempat_lahir.isEmpty()){
                            progressDialog.dismiss();
                            edtTempatLahirEditMhs.setError("Tempat Lahir Harus Diisi");
                            edtTempatLahirEditMhs.requestFocus();
                            return;
                        }
                        if (tgl_lahir.isEmpty()){
                            progressDialog.dismiss();
                            edtTglLahirEditMhs.setError("Tanggal Lahir Harus Diisi");
                            edtTglLahirEditMhs.requestFocus();
                            return;
                        }else {
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    LocalDate.parse(tgl_lahir);
                                    progressDialog.dismiss();
                                }else {
                                    Toast.makeText(EditMhsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }catch (DateTimeParseException d){
                                progressDialog.dismiss();
                                edtTglLahirEditMhs.setError("Format Tanggal Lahir Salah");
                                edtTglLahirEditMhs.requestFocus();
                                return;
                            }
                        }
                        if (no_hp.isEmpty()){
                            progressDialog.dismiss();
                            edtNoHpEditMhs.setError("No Handphone Harus Diisi");
                            edtNoHpEditMhs.requestFocus();
                            return;
                        }
                        if (email.isEmpty()){
                            progressDialog.dismiss();
                            edtEmailEditMhs.setError("Email Harus Diisi");
                            edtEmailEditMhs.requestFocus();
                            return;
                        }
                        if (!isEmailValid(email)){
                            progressDialog.dismiss();
                            edtEmailEditMhs.setError("The Format Must Be Email");
                            edtEmailEditMhs.requestFocus();
                            return;
                        }
                        if (alamat_sekarang.isEmpty()){
                            progressDialog.dismiss();
                            edtAlamatSekarangEditMhs.setError("Alamat Harus Diisi");
                            edtAlamatSekarangEditMhs.requestFocus();
                            return;
                        }
                        Call<Value> call = RetrofitClient.getmInstance().getApi().editMhs(nim, username, nik, nama, jk, tempat_lahir, tgl_lahir,
                                no_hp, email, fakultas, prodi, angkatan, kelas, provinsi, nm_kabupaten, nm_kecamatan, nm_kelurahan, nm_lat, nm_lng,
                                alamat_sekarang, lat_alamat_sekarang, lng_alamat_sekarang);
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
                                Toast.makeText(EditMhsActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
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
