package com.example.matatabi.padm.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Value;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMhsDataPribadiActivity extends AppCompatActivity {
    private EditText edtNimEditMhs, edtUsernameEditMhs, edtNikEditMhs, edtNamaEditMhs, edtTempatLahirEditMhs,
            edtTglLahirEditMhs, edtTglLahirResult, edtNoHpEditMhs, edtEmailEditMhs, edtFakultasEditMhs, edtProdiEditMhs;
    private RadioGroup radioJkEditMhs;
    private RadioButton radioBtnLakiEditMhs, radioBtnPerempuanEditMhs, radioSexButtonEditMhs;
    private Spinner spinAngkatanEditMhs, spinKelasEditMhs;
    private SimpleDateFormat simpleDateFormat;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mhs_data_pribadi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        radioJkEditMhs = findViewById(R.id.radioJkEditMhs);
        radioBtnLakiEditMhs = findViewById(R.id.radioBtnLakiEditMhs);
        radioBtnPerempuanEditMhs = findViewById(R.id.radioBtnPerempuanEditMhs);

        spinAngkatanEditMhs = findViewById(R.id.spinAngkatanEditMhs);
        spinKelasEditMhs = findViewById(R.id.spinKelasEdtMhs);

        Button btn_edit_mhs = findViewById(R.id.btn_edit_mhs_pribadi_akademik);

        final Intent intent = getIntent();
        String nim = intent.getStringExtra("nim");
        edtNimEditMhs.setText(nim);
        String username = intent.getStringExtra("username");
        edtUsernameEditMhs.setText(username);
        String nik = intent.getStringExtra("nik");
        edtNikEditMhs.setText(nik);
        String nama = intent.getStringExtra("nama");
        edtNamaEditMhs.setText(nama);

        String tempat_lahir = intent.getStringExtra("tempat_lahir");
        edtTempatLahirEditMhs.setText(tempat_lahir);
        String tgl_lahir = intent.getStringExtra("tgl_lahir");
        edtTglLahirEditMhs.setText(tgl_lahir);
        String no_hp = intent.getStringExtra("no_hp");
        edtNoHpEditMhs.setText(no_hp);
        String email = intent.getStringExtra("email");
        edtEmailEditMhs.setText(email);

        edtNimEditMhs.setKeyListener(edtNimEditMhs.getKeyListener());
        edtNimEditMhs.setKeyListener(null);

        edtUsernameEditMhs.setKeyListener(edtUsernameEditMhs.getKeyListener());
        edtUsernameEditMhs.setKeyListener(null);

        edtFakultasEditMhs.setKeyListener(edtFakultasEditMhs.getKeyListener());
        edtFakultasEditMhs.setKeyListener(null);

        edtProdiEditMhs.setKeyListener(edtProdiEditMhs.getKeyListener());
        edtProdiEditMhs.setKeyListener(null);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Button btnTgl = findViewById(R.id.btnTglEdtMhs);
        btnTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        edtTglLahirEditMhs.setEnabled(false);

        btn_edit_mhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData();
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

    private void editData(){
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data ini?\nPeriksa Kembali Data Sebelum diubah").setCancelable(false)
                .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = new ProgressDialog(EditMhsDataPribadiActivity.this);
                        progressDialog.setMessage("Mengubah Data...");
                        progressDialog.show();

                        String nim = edtNimEditMhs.getText().toString();
                        String username = edtUsernameEditMhs.getText().toString();
                        String nik = edtNikEditMhs.getText().toString();
                        String nama = edtNamaEditMhs.getText().toString();

                        int selectedId = radioJkEditMhs.getCheckedRadioButtonId();
                        if (radioJkEditMhs.getCheckedRadioButtonId() == -1){
                            progressDialog.dismiss();
                            Toast.makeText(EditMhsDataPribadiActivity.this, "Jenis Kelamin Belum Dipilih", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(EditMhsDataPribadiActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
                        }

                        Call<Value> call = RetrofitClient.getmInstance().getApi().editMhsPribadiAk(nim, username, nik, nama, jk, tempat_lahir, tgl_lahir, no_hp, email, fakultas, prodi, angkatan, kelas);
                        call.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                                progressDialog.dismiss();
                                String value = response.body().getValue();
                                String message = response.body().getMessage();
                                if (value.equals("1")){
                                    Toast.makeText(EditMhsDataPribadiActivity.this, message, Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(EditMhsDataPribadiActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(EditMhsDataPribadiActivity.this, "Gagal", Toast.LENGTH_LONG).show();
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

    private boolean isEmailValid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
