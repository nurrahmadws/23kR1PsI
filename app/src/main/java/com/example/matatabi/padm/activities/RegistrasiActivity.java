package com.example.matatabi.padm.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matatabi.padm.R;

public class RegistrasiActivity extends AppCompatActivity {
    private EditText edtTexNimRegistrasi, edtTexNamaRegistrasi, edtTexnohpRegistrasi;
    private Button btn_kirim_sms, btn_batal_sms;
    private Spinner spinAngkatanRegistrasi;

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtTexNimRegistrasi = findViewById(R.id.edtTexNimRegistrasi);
        edtTexNamaRegistrasi = findViewById(R.id.edtTexNamaRegistrasi);
        edtTexnohpRegistrasi = findViewById(R.id.edtTexnohpRegistrasi);

        edtTexnohpRegistrasi.setKeyListener(edtTexnohpRegistrasi.getKeyListener());
        edtTexnohpRegistrasi.setKeyListener(null);

        btn_kirim_sms = findViewById(R.id.btn_kirim_sms);
        btn_batal_sms = findViewById(R.id.btn_batal_sms);

        spinAngkatanRegistrasi = findViewById(R.id.spinAngkatanRegistrasi);

        if (Build.VERSION.SDK_INT >= 23){
            if (checkPermission()){
                Log.e("permission", "Permission already granted");
            }else {
                requestPermission();
            }
        }

        btn_batal_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_kirim_sms.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v) {
                String nim = edtTexNimRegistrasi.getText().toString();
                String nama = edtTexNamaRegistrasi.getText().toString();
                String no_hp = edtTexnohpRegistrasi.getText().toString();
                String angkatan = spinAngkatanRegistrasi.getSelectedItem().toString();

                if (nim.isEmpty()){
                    edtTexNimRegistrasi.setError("NIM harus diisi");
                    edtTexNimRegistrasi.requestFocus();
                    return;
                }
                if (nama.isEmpty()){
                    edtTexNamaRegistrasi.setError("Nama Harus diisi");
                    edtTexNamaRegistrasi.requestFocus();
                    return;
                }

                if (!TextUtils.isEmpty(nim) && !TextUtils.isEmpty(nama) && !TextUtils.isEmpty(no_hp) && !TextUtils.isEmpty(angkatan)){
                    if (checkPermission()){
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(no_hp, null, "NIM: " +nim+","+" NAMA: "+nama+","+ " ANGKATAN: "+angkatan, null, null);

                        Toast.makeText(RegistrasiActivity.this, "Pesan Berhasil Dikirim", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(RegistrasiActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(RegistrasiActivity.this, Manifest.permission.SEND_SMS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Accepted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                    btn_kirim_sms.setEnabled(false);
                }
                break;
        }
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
