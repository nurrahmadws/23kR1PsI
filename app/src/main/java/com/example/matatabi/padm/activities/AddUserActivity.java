package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {
    private EditText edtTextUsername,edtTextPassword;
    private Spinner spinLevel;
    private Button btnSimpan, btnBatal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        edtTextUsername = findViewById(R.id.edtTextUsername);
        edtTextPassword = findViewById(R.id.edtTextPassword);

        spinLevel = findViewById(R.id.spinLevel);
        btnSimpan = findViewById(R.id.btnRegistrasii);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtTextUsername.getText().toString();
                String password = edtTextPassword.getText().toString();
                String level = spinLevel.getSelectedItem().toString();

                if (username.isEmpty()){
                    edtTextUsername.setError("Username Tidak Boleh Kosong");
                    edtTextUsername.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    edtTextPassword.setError("Password Tidak Boleh Kosong");
                    edtTextPassword.requestFocus();
                    return;
                }

                if (password.length() < 6){
                    edtTextPassword.setError("Password Harus Lebih Dari 6 Karakter");
                    edtTextPassword.requestFocus();
                    return;
                }

                Call<Value> call = RetrofitClient.getmInstance().getApi().addUser(username, password, level);
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        String value = response.body().getValue();
                        String message = response.body().getMessage();
                        if (value.equals("1")){
                            Toast.makeText(AddUserActivity.this, message, Toast.LENGTH_SHORT).show();
                            finish();

                            startActivity(new Intent(AddUserActivity.this, DataUserActivity.class));
                        }else{
                            Toast.makeText(AddUserActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(AddUserActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnBatal = findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddUserActivity.this, DataUserActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
