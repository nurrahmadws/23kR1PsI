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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMhsAlamatSekarangActivity extends AppCompatActivity {
    private EditText edtMhsAlamatSekarang, edtMhsLatAlamatSekarang, edtMhsLngAlamatSekarang, edtMhsnim;
    private Button btn_edit_mhs_alamat_sekarang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mhs_alamat_sekarang);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtMhsAlamatSekarang = findViewById(R.id.edtMhsAlamatSekarang);
        edtMhsLatAlamatSekarang = findViewById(R.id.edtMhsLatAlamatSekarang);
        edtMhsLngAlamatSekarang = findViewById(R.id.edtMhsLngAlamatSekarang);
        edtMhsnim = findViewById(R.id.edtMhsnim);

        btn_edit_mhs_alamat_sekarang = findViewById(R.id.btn_edit_mhs_alamat_sekarang);

        Intent intent = getIntent();
        String nim = intent.getStringExtra("nim");
        edtMhsnim.setText(nim);
        String alamat_sekarang = intent.getStringExtra("alamat_sekarang");
        edtMhsAlamatSekarang.setText(alamat_sekarang);
        String lat_alamat_sekarang = intent.getStringExtra("lat_alamat_sekarang");
        edtMhsLatAlamatSekarang.setText(lat_alamat_sekarang);
        edtMhsLatAlamatSekarang.setKeyListener(edtMhsLatAlamatSekarang.getKeyListener());
        edtMhsLatAlamatSekarang.setKeyListener(null);
        String lng_alamat_sekarang = intent.getStringExtra("lng_alamat_sekarang");
        edtMhsLngAlamatSekarang.setText(lng_alamat_sekarang);
        edtMhsLngAlamatSekarang.setKeyListener(edtMhsLngAlamatSekarang.getKeyListener());
        edtMhsLngAlamatSekarang.setKeyListener(null);

        btn_edit_mhs_alamat_sekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData();
            }
        });
    }

    private void editData(){
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data ini?\nPeriksa Kembali Data Sebelum diubah").setCancelable(false)
                .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = new ProgressDialog(EditMhsAlamatSekarangActivity.this);
                        progressDialog.setMessage("Mengubah Data...");
                        progressDialog.show();

                        String nim = edtMhsnim.getText().toString();
                        String alamat_sekarang = edtMhsAlamatSekarang.getText().toString();
                        String lat_alamat_sekarang = edtMhsLatAlamatSekarang.getText().toString();
                        String lng_alamat_sekarang = edtMhsLngAlamatSekarang.getText().toString();

                        if (alamat_sekarang.isEmpty()){
                            progressDialog.dismiss();
                            edtMhsAlamatSekarang.setError("Alamat Harus Diisi");
                            edtMhsAlamatSekarang.requestFocus();
                        }
                        Call<Value> call = RetrofitClient.getmInstance().getApi().editMhsAlamatSekaran(nim, alamat_sekarang, lat_alamat_sekarang, lng_alamat_sekarang);
                        call.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                                progressDialog.dismiss();
                                String value = response.body().getValue();
                                String message = response.body().getMessage();
                                if (value.equals("1")){
                                    Toast.makeText(EditMhsAlamatSekarangActivity.this, message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(EditMhsAlamatSekarangActivity.this, MahasiswaActivity.class));
                                }else {
                                    Toast.makeText(EditMhsAlamatSekarangActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(EditMhsAlamatSekarangActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
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
