package com.example.matatabi.padm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Kabupaten;
import com.example.matatabi.padm.model.KabupatenResponse;
import com.example.matatabi.padm.model.Value;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditKecamatanActivity extends AppCompatActivity {

    private EditText edtTexIdKecamatanEdit, edtTexKecamatanEdit;
    private Spinner spinKabEd;
    private Button btn_Edit_kec, btn_hapus_kec, btn_batal_keced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kecamatan);

        edtTexIdKecamatanEdit = findViewById(R.id.edtTexIdKecamatanEdit);
        edtTexKecamatanEdit = findViewById(R.id.edtTexKecamatanEdit);

        spinKabEd = findViewById(R.id.spinKabEd);

        btn_Edit_kec = findViewById(R.id.btn_Edit_kec);
        btn_hapus_kec = findViewById(R.id.btn_hapus_kec);
        btn_batal_keced = findViewById(R.id.btn_batal_keced);

        Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().spinKab();
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                if (response.isSuccessful()){
                    List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                    List<String> listSpinKabKeced = new ArrayList<>();
                    for (int i = 0; i < kabupatenList.size(); i++){
                        listSpinKabKeced.add(kabupatenList.get(i).getNm_kabupaten());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditKecamatanActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKabKeced);
                    spinKabEd.setAdapter(adapter);
                }else {
                    Toast.makeText(EditKecamatanActivity.this, "Gagal Mengambil Data Kabupaten", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(EditKecamatanActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        edtTexIdKecamatanEdit.setText(intent.getStringExtra("id_kecamatan"));
        edtTexIdKecamatanEdit.setKeyListener(edtTexIdKecamatanEdit.getKeyListener());
        edtTexIdKecamatanEdit.setKeyListener(null);

        String nm_kecamatan = intent.getStringExtra("nm_kecamatan");
        edtTexKecamatanEdit.setText(nm_kecamatan);

        String nm_kabupaten = intent.getStringExtra("nm_kabupaten");
        if (nm_kabupaten.equals(nm_kabupaten)){
            spinKabEd.setSelected(true);
        }

        btn_Edit_kec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(EditKecamatanActivity.this);
                al.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data Ini?\nPeriksa Kembali Data Sebelum di Ubah")
                        .setCancelable(false)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id_kecamatan = edtTexIdKecamatanEdit.getText().toString();
                                String nm_kabupaten = spinKabEd.getSelectedItem().toString();
                                String nm_kecamatan = edtTexKecamatanEdit.getText().toString();

                                if (nm_kecamatan.isEmpty()){
                                    edtTexKecamatanEdit.setError("Kecamatan Harus Diisi");
                                    edtTexKecamatanEdit.requestFocus();
                                    return;
                                }

                                Call<Value> call = RetrofitClient.getmInstance().getApi().editKec(id_kecamatan, nm_kabupaten, nm_kecamatan);
                                call.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")){
                                            Toast.makeText(EditKecamatanActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(EditKecamatanActivity.this, DataKecamatanActivity.class));
                                        }else {
                                            Toast.makeText(EditKecamatanActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(EditKecamatanActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(EditKecamatanActivity.this, DataKecamatanActivity.class));
                    }
                });
                AlertDialog dialog = al.create();
                dialog.show();
            }
        });

        btn_hapus_kec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder au = new AlertDialog.Builder(EditKecamatanActivity.this);
                au.setTitle("PERINGATAN!").setMessage("Yakin Ingin Menghapus Data Ini?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id_kecamatan = edtTexIdKecamatanEdit.getText().toString();
                                Call<Value> callD = RetrofitClient.getmInstance().getApi().deleteKec(id_kecamatan);
                                callD.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")){
                                            Toast.makeText(EditKecamatanActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(EditKecamatanActivity.this, DataKecamatanActivity.class));
                                        }else {
                                            Toast.makeText(EditKecamatanActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(EditKecamatanActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = au.create();
                alertDialog.show();
            }
        });
        btn_batal_keced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditKecamatanActivity.this, DataKecamatanActivity.class));
            }
        });
    }
}
