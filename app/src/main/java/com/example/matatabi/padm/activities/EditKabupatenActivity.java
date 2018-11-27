package com.example.matatabi.padm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditKabupatenActivity extends AppCompatActivity {

    private EditText edtTextKabkotaEdit, edtIdKabkotaEdit;
    private Button btn_edit_kab, btn_hapus_kab, btn_batal_kab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kabupaten);
        
        edtIdKabkotaEdit = findViewById(R.id.edtIdKabkotaEdit);
        edtTextKabkotaEdit = findViewById(R.id.edtTextKabkotaEdit);
        
        btn_edit_kab = findViewById(R.id.btn_edit_kab);
        btn_hapus_kab = findViewById(R.id.btn_hapus_kab);
        btn_batal_kab = findViewById(R.id.btn_batal_kab);

        Intent intent = getIntent();
        edtIdKabkotaEdit.setText(intent.getStringExtra("id_kabupaten"));
        edtIdKabkotaEdit.setKeyListener(edtIdKabkotaEdit.getKeyListener());
        edtIdKabkotaEdit.setKeyListener(null);
        
        String nm_kabupaten = intent.getStringExtra("nm_kabupaten");
        edtTextKabkotaEdit.setText(nm_kabupaten);
        
        btn_edit_kab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(EditKabupatenActivity.this);
                ad.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data Ini?\nPeriksa Kembali Data Sebelum Diubah")
                        .setCancelable(false)
                        .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id_kabupaten = edtIdKabkotaEdit.getText().toString();
                                String nm_kabupaten = edtTextKabkotaEdit.getText().toString();
                                
                                if (nm_kabupaten.isEmpty()){
                                    edtTextKabkotaEdit.setError("Kabupaten/Kota Harus Diisi");
                                    edtTextKabkotaEdit.requestFocus();
                                    return;
                                }

                                Call<Value> call = RetrofitClient.getmInstance().getApi().editKabkot(id_kabupaten, nm_kabupaten);
                                call.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")){
                                            Toast.makeText(EditKabupatenActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(EditKabupatenActivity.this, DataKabupatenActivity.class));
                                        }else {
                                            Toast.makeText(EditKabupatenActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(EditKabupatenActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        startActivity(new Intent(EditKabupatenActivity.this, DataKabupatenActivity.class));
                    }
                });
                AlertDialog alertDialog = ad.create();
                alertDialog.show();
            }
        });
        
        btn_hapus_kab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(EditKabupatenActivity.this);
                al.setTitle("PERINGATAN!").setMessage("Yakin Ingin Menghapus Data Ini?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id_kabupaten = edtIdKabkotaEdit.getText().toString();
                                Call<Value> call = RetrofitClient.getmInstance().getApi().deleteKabkot(id_kabupaten);
                                call.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")){
                                            Toast.makeText(EditKabupatenActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(EditKabupatenActivity.this, DataKabupatenActivity.class));
                                        }else {
                                            Toast.makeText(EditKabupatenActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(EditKabupatenActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(EditKabupatenActivity.this, DataKabupatenActivity.class));
                    }
                });
                AlertDialog dialog = al.create();
                dialog.show();
            }
        });

        btn_batal_kab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditKabupatenActivity.this, DataKabupatenActivity.class));
            }
        });
    }
}
 