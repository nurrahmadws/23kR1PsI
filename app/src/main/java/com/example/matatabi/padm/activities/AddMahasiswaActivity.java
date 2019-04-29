package com.example.matatabi.padm.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMahasiswaActivity extends AppCompatActivity {

    private EditText edtNim, edtUsername, edtNik, edtNama, edtTempatLahir, edtTglLahir, edtDateResul, edtNoHp, edtEmail,
            edtFakultas, edtProdi, edtProvinsi, edtAlamatSekarang, edtLatAlamatSekarang, edtLngAlamatSekarang;
    private RadioGroup radioJk;
    private Spinner spinAngkatan, spinKelas, spinKabupatenM, spinKecamatanM, spinKelurahanM, spinLat, spinLng;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    public static final String USERNAME = "username";

    Criteria criteria;
    LocationManager locationManager;

    //    FOR IMAGE
    private static final int PICK_FROM_GALLERY = 1;
    private ImageView img;
    private static final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    Button btn_get_photo;
    Uri path;
//    END OF IMAGE

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mahasiswa);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        edtNim = findViewById(R.id.edtNim);
        edtUsername = findViewById(R.id.edtUsername);
        edtNik = findViewById(R.id.edtNik);
        edtNama = findViewById(R.id.edtNama);
        edtTempatLahir = findViewById(R.id.edtTempat_lahir);
        edtTglLahir = findViewById(R.id.edtTglLahir);
        edtDateResul = findViewById(R.id.edtTglLahir);
        edtNoHp = findViewById(R.id.edtNoHp);
        edtEmail = findViewById(R.id.edtEmail);
        edtFakultas = findViewById(R.id.edtFakultas);
        edtProdi = findViewById(R.id.edtProdi);
        edtProvinsi = findViewById(R.id.edtProvinsi);
        edtAlamatSekarang = findViewById(R.id.edtAlamatSekarang);
        edtLatAlamatSekarang = findViewById(R.id.edtLatAlamatSekarang);
        edtLngAlamatSekarang = findViewById(R.id.edtLngAlamatSekarang);

        img = findViewById(R.id.img);

        radioJk = findViewById(R.id.radioJk);

        spinAngkatan = findViewById(R.id.spinAngkatan);
        spinKelas = findViewById(R.id.spinKelas);
        spinKabupatenM = findViewById(R.id.spinKabupatenM);
        spinKecamatanM = findViewById(R.id.spinKecamatanM);
        spinKelurahanM = findViewById(R.id.spinKelurahanM);
        spinLat = findViewById(R.id.spinLatM);
        spinLng = findViewById(R.id.spinLngM);

        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(USERNAME)) {
            edtUsername.setText(sharedPreferences.getString(USERNAME, ""));
        }
        if (sharedPreferences.contains(USERNAME)) {
            edtNim.setText(sharedPreferences.getString(USERNAME, ""));
        }

        edtNim.setKeyListener(edtNim.getKeyListener());
        edtNim.setKeyListener(null);

        final Intent intent = getIntent();
        String alamat_sekarang = intent.getStringExtra("alamat_sekarang");
        edtAlamatSekarang.setText(alamat_sekarang);

        String lat_alamat_sekarang = intent.getStringExtra("lat_alamat_sekarang");
        edtLatAlamatSekarang.setText(lat_alamat_sekarang);
        edtLatAlamatSekarang.setKeyListener(edtLatAlamatSekarang.getKeyListener());
        edtLatAlamatSekarang.setKeyListener(null);

        String lng_alamat_sekarang = intent.getStringExtra("lng_alamat_sekarang");
        edtLngAlamatSekarang.setText(lng_alamat_sekarang);
        edtLngAlamatSekarang.setKeyListener(edtLngAlamatSekarang.getKeyListener());
        edtLngAlamatSekarang.setKeyListener(null);

        edtUsername.setKeyListener(edtUsername.getKeyListener());
        edtUsername.setKeyListener(null);

        edtFakultas.setKeyListener(edtFakultas.getKeyListener());
        edtFakultas.setKeyListener(null);

        edtProdi.setKeyListener(edtProdi.getKeyListener());
        edtProdi.setKeyListener(null);

        edtProvinsi.setKeyListener(edtProvinsi.getKeyListener());
        edtProvinsi.setKeyListener(null);

        Call<KabupatenResponse> call = RetrofitClient.getmInstance().getApi().spinKab();
        call.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listSpinKab = new ArrayList<>();
                for (int i = 0; i < kabupatenList.size(); i++) {
                    listSpinKab.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddMahasiswaActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKab);
                spinKabupatenM.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        spinKabupatenM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kabupatenName = parent.getItemAtPosition(position).toString();
                listKecamatan(kabupatenName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinKecamatanM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kecamatanName = parent.getItemAtPosition(position).toString();
                listKelurahan(kecamatanName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinKelurahanM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kelurahanName = parent.getItemAtPosition(position).toString();
                listLatlng(kelurahanName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Button btnTgl = findViewById(R.id.btnTgl);
        btnTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        edtTglLahir.setEnabled(false);

        Button btn_simpan_mhs = findViewById(R.id.btn_simpan_mhs);
        btn_simpan_mhs.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                simpanMahasiswa();
            }
        });

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);

        btn_get_photo = findViewById(R.id.btn_get_photo);
        btn_get_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddMahasiswaActivity.this);
                dialog.setTitle("PEMBERITAHUAN!").setMessage("Pilih Open Gallery Jika Anda Memiliki Foto \nPilih Default Foto Jika Anda belum Memiliki Foto")
                        .setCancelable(false);
                dialog.setPositiveButton("Open Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectImage();
                    }
                });
                dialog.setNegativeButton("Default Foto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isImageNull();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
    }

    //    FOR IMAGE
    private void selectImage() {
        if (ActivityCompat.checkSelfPermission(AddMahasiswaActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddMahasiswaActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                img.setImageBitmap(bitmap);
                img.setVisibility(View.VISIBLE);
                btn_get_photo.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void isImageNull() {
        Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/drawable/default_photo");
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            img.setImageBitmap(bitmap);
            img.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] imgByt = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByt, Base64.DEFAULT);
    }
    //    END OF IMAGE

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edtDateResul.setText(simpleDateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void listKecamatan(String kabupatenName) {
        String nm_kabupaten = spinKabupatenM.getSelectedItem().toString();

        if (kabupatenName.equals(nm_kabupaten)) {
            Call<KecamatanResponse> calll = RetrofitClient.getmInstance().getApi().spinKec(nm_kabupaten);
            calll.enqueue(new Callback<KecamatanResponse>() {
                @Override
                public void onResponse(Call<KecamatanResponse> call, Response<KecamatanResponse> response) {
                    List<Kecamatan> kecamatanList = response.body().getKecamatanList();
                    List<String> listSpinKec = new ArrayList<>();
                    for (int i = 0; i < kecamatanList.size(); i++) {
                        listSpinKec.add(kecamatanList.get(i).getNm_kecamatan());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddMahasiswaActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKec);
                    spinKecamatanM.setAdapter(arrayAdapter);
                }

                @Override
                public void onFailure(Call<KecamatanResponse> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(AddMahasiswaActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void listKelurahan(String kecamatanName) {
        String nm_kecamatan = spinKecamatanM.getSelectedItem().toString();

        if (kecamatanName.equals(nm_kecamatan)) {
            Call<KelurahanResponse> callk = RetrofitClient.getmInstance().getApi().spinKel(nm_kecamatan);
            callk.enqueue(new Callback<KelurahanResponse>() {
                @Override
                public void onResponse(Call<KelurahanResponse> call, Response<KelurahanResponse> response) {
                    List<Kelurahan> kelurahanList = response.body().getKelurahanList();
                    List<String> listSpinKel = new ArrayList<>();
                    for (int i = 0; i < kelurahanList.size(); i++) {
                        listSpinKel.add(kelurahanList.get(i).getNm_kelurahan());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddMahasiswaActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKel);
                    spinKelurahanM.setAdapter(arrayAdapter);
                }

                @Override
                public void onFailure(Call<KelurahanResponse> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(AddMahasiswaActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void listLatlng(String kelurahanName) {
        String nm_kelurahan = spinKelurahanM.getSelectedItem().toString();

        if (kelurahanName.equals(nm_kelurahan)) {
            Call<LatlngResponse> latlngResponseCall = RetrofitClient.getmInstance().getApi().spinLatlng(nm_kelurahan);
            latlngResponseCall.enqueue(new Callback<LatlngResponse>() {
                @Override
                public void onResponse(Call<LatlngResponse> call, Response<LatlngResponse> response) {
                    List<Latlng> latlngList = response.body().getLatlngList();
                    List<String> listSpinLat = new ArrayList<>();
                    for (int i = 0; i < latlngList.size(); i++) {
                        listSpinLat.add(latlngList.get(i).getNm_lat());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddMahasiswaActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinLat);
                    spinLat.setAdapter(adapter);

                    List<String> listSpinLng = new ArrayList<>();
                    for (int i = 0; i < latlngList.size(); i++) {
                        listSpinLng.add(latlngList.get(i).getNm_lng());
                    }
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(AddMahasiswaActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinLng);
                    spinLng.setAdapter(stringArrayAdapter);
                }

                @Override
                public void onFailure(Call<LatlngResponse> call, Throwable t) {

                }
            });
        }
    }

    private boolean isEmailValid(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void simpanMahasiswa() {
        final ProgressDialog progressDialog = new ProgressDialog(AddMahasiswaActivity.this);
        progressDialog.setMessage("Menambahkan Data...");
        progressDialog.show();

        int selectedId = radioJk.getCheckedRadioButtonId();

        if (radioJk.getCheckedRadioButtonId() == -1) {
            progressDialog.dismiss();
            Toast.makeText(this, "Jenis Kelamin Belum Dipilih", Toast.LENGTH_LONG).show();
            return;
        }
        RadioButton radioSexButton = findViewById(selectedId);

        String nim = edtNim.getText().toString().trim();
        String username = edtUsername.getText().toString();
        String nik = edtNik.getText().toString().trim();
        String nama = edtNama.getText().toString().trim();
        String jk = radioSexButton.getText().toString().trim();
        String tempat_lahir = edtTempatLahir.getText().toString();
        String tgl_lahir = edtDateResul.getText().toString();
        String no_hp = edtNoHp.getText().toString();
        String email = edtEmail.getText().toString();
        String fakultas = edtFakultas.getText().toString().trim();
        String prodi = edtProdi.getText().toString().trim();
        String angkatan = spinAngkatan.getSelectedItem().toString().trim();
        String kelas = spinKelas.getSelectedItem().toString().trim();
        String provinsi = edtProvinsi.getText().toString().trim();
        String nm_kabupaten = spinKabupatenM.getSelectedItem().toString().trim();
        String nm_kecamatan = spinKecamatanM.getSelectedItem().toString().trim();
        String nm_kelurahan = spinKelurahanM.getSelectedItem().toString().trim();
        String nm_lat = spinLat.getSelectedItem().toString().trim();
        String nm_lng = spinLng.getSelectedItem().toString().trim();
        String alamat_sekarang = edtAlamatSekarang.getText().toString();
        String lat_alamat_sekarang = edtLatAlamatSekarang.getText().toString();
        String lng_alamat_sekarang = edtLngAlamatSekarang.getText().toString();
        String image = imageToString();

        if (nim.isEmpty()) {
            progressDialog.dismiss();
            edtNim.setError("NIM harus Diisi");
            edtNim.requestFocus();
            return;
        }
        if (nik != null && !nik.isEmpty() && !nik.equals("null")) {
            edtNik.setText(nik);
        }
        if (nama.isEmpty()) {
            progressDialog.dismiss();
            edtNama.setError("Nama Harus Diisi");
            edtNama.requestFocus();
            return;
        }
        if (tempat_lahir.isEmpty()) {
            progressDialog.dismiss();
            edtTempatLahir.setError("Tempat Lahir Harus Diisi");
            edtTempatLahir.requestFocus();
            return;
        }
        if (tgl_lahir.isEmpty()) {
            progressDialog.dismiss();
            edtTglLahir.setError("Tanggal Lahir Harus Diisi");
            edtTglLahir.requestFocus();
            return;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.parse(tgl_lahir);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
            } catch (DateTimeParseException d) {
                progressDialog.dismiss();
                edtTglLahir.setError("Format Tanggal Lahir Salah");
                edtTglLahir.requestFocus();
                return;
            }
        }
        if (no_hp.isEmpty()) {
            progressDialog.dismiss();
            edtNoHp.setError("No Hanphone Harus Diiisi");
            edtNoHp.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            progressDialog.dismiss();
            edtEmail.setError("Email Harus Diisi");
            edtEmail.requestFocus();
            return;
        }
        if (!isEmailValid(email)) {
            progressDialog.dismiss();
            edtEmail.setError("The Format Must Be Email");
            edtEmail.requestFocus();
            return;
        }
        if (alamat_sekarang.isEmpty()) {
            progressDialog.dismiss();
            edtAlamatSekarang.setError("Alamat Harus Diisi");
            edtAlamatSekarang.requestFocus();
            return;
        }
        if (image.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(this, "Photo Tidak Boleh Kosong!!!", Toast.LENGTH_LONG).show();
            return;
        }

        Call<Value> call = RetrofitClient.getmInstance().getApi().insertMhs(nim, username, nik, nama, jk, tempat_lahir, tgl_lahir, no_hp,
                email, fakultas, prodi, angkatan, kelas, provinsi, nm_kabupaten, nm_kecamatan, nm_kelurahan, nm_lat, nm_lng, alamat_sekarang,
                lat_alamat_sekarang, lng_alamat_sekarang, image);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                progressDialog.dismiss();
                String value = response.body().getValue();
                String message = response.body().getMessage();
                switch (value) {
                    case "1":
                        Toast.makeText(AddMahasiswaActivity.this, message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddMahasiswaActivity.this, MahasiswaActivity.class));
                        finish();
                        break;
                    case "0":
                        Toast.makeText(AddMahasiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "2":
                        Toast.makeText(AddMahasiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "3":
                        Toast.makeText(AddMahasiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "4":
                        Toast.makeText(AddMahasiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(AddMahasiswaActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
            }
        });
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
