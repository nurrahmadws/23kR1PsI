package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Kabupaten;
import com.example.matatabi.padm.model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddKabupatenActivity extends AppCompatActivity {

    private EditText edtTextKabkota;
    private Button btn_simpan_kabkot, btn_batal_kabkot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kabupaten);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtTextKabkota = findViewById(R.id.edtTextKabkota);
        btn_simpan_kabkot = findViewById(R.id.btn_simpan_kab);
        btn_batal_kabkot = findViewById(R.id.btn_batal_kabkot);

        btn_simpan_kabkot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm_kabupaten = edtTextKabkota.getText().toString();
                if (nm_kabupaten.isEmpty()){
                    edtTextKabkota.setError("Kabupaten/Kota Harus Diisi");
                    edtTextKabkota.requestFocus();
                    return;
                }

                Call<Value> call = RetrofitClient.getmInstance().getApi().addKabkot(nm_kabupaten);
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        String value = response.body().getValue();
                        String message = response.body().getMessage();
                        if (value.equals("1")){
                            Toast.makeText(AddKabupatenActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(AddKabupatenActivity.this, DataKabupatenActivity.class));
                        }else {
                            Toast.makeText(AddKabupatenActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(AddKabupatenActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_batal_kabkot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddKabupatenActivity.this, DataKabupatenActivity.class));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DataKabupatenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
