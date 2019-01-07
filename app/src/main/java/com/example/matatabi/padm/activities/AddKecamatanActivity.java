package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

public class AddKecamatanActivity extends AppCompatActivity {

    private EditText edtTexKecamatanAdd;
    private Spinner spinKabAdd;
    private Button btn_simpan_kec, btn_batal_kec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kecamatan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtTexKecamatanAdd = findViewById(R.id.edtTexKecamatanAdd);
        spinKabAdd = findViewById(R.id.spinKabAdd);
        btn_simpan_kec = findViewById(R.id.btn_simpan_kec);
        btn_batal_kec = findViewById(R.id.btn_batal_kec);

        Call<KabupatenResponse> kabupatenResponseCall = RetrofitClient.getmInstance().getApi().spinKab();
        kabupatenResponseCall.enqueue(new Callback<KabupatenResponse>() {
            @Override
            public void onResponse(Call<KabupatenResponse> call, Response<KabupatenResponse> response) {
                List<Kabupaten> kabupatenList = response.body().getKabupatenList();
                List<String> listSpinKabAdd = new ArrayList<>();
                for (int i = 0; i < kabupatenList.size(); i++){
                    listSpinKabAdd.add(kabupatenList.get(i).getNm_kabupaten());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddKecamatanActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinKabAdd);
                spinKabAdd.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<KabupatenResponse> call, Throwable t) {

            }
        });
        btn_simpan_kec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(AddKecamatanActivity.this);
                progressDialog.setMessage("Menambahkan Data...");
                progressDialog.show();

                String nm_kabupaten = spinKabAdd.getSelectedItem().toString();
                String nm_kecamatan = edtTexKecamatanAdd.getText().toString();

                if (nm_kecamatan.isEmpty()){
                    edtTexKecamatanAdd.setError("Kecamatan Harus Diisi");
                    edtTexKecamatanAdd.requestFocus();
                    return;
                }
                Call<Value> call = RetrofitClient.getmInstance().getApi().addKec(nm_kabupaten, nm_kecamatan);
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        progressDialog.dismiss();
                        String value = response.body().getValue();
                        String message = response.body().getMessage();
                        if (value.equals("1")){
                            Toast.makeText(AddKecamatanActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(AddKecamatanActivity.this, DataKecamatanActivity.class));
                        }else {
                            Toast.makeText(AddKecamatanActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        progressDialog.dismiss();
                        t.printStackTrace();
                        Toast.makeText(AddKecamatanActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_batal_kec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddKecamatanActivity.this, DataKecamatanActivity.class));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DataKecamatanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
