package com.example.matatabi.padm.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edtTextUsernameLogin, edtTextPasswordLogin;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTextUsernameLogin = findViewById(R.id.edtTextUsernameLogin);

        edtTextPasswordLogin = findViewById(R.id.edtTextPasswordLogin);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(edtTextUsernameLogin.getText());
                String password = String.valueOf(edtTextPasswordLogin.getText());

                if (username.isEmpty()){
                    edtTextUsernameLogin.setError("Username Harus di Isi");
                    edtTextUsernameLogin.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    edtTextPasswordLogin.setError("Password Harus di Isi");
                    edtTextPasswordLogin.requestFocus();
                    return;
                }
                Call<LoginResponse> call = RetrofitClient.getmInstance().getApi().userLogin(username, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            Boolean value = response.body().getValue();
                            String messagee = response.body().getMessage();
                            if (value){
                                String message = response.body().getMessage();
                                String level = response.body().getLevel();
                                LoginResponse loginResponse = response.body();

                                if (level.equals("Admin")){
                                    String error = "Sukses, "+message;
                                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                                    finish();

//                                    SharedPrefManager.getmInstance(LoginActivity.this).saveUser(loginResponse.getUser());
                                    Intent goAdmin = new Intent(MainActivity.this, AdminActivity.class);
                                    startActivity(goAdmin);
                                }else if (level.equals("Mahasiswa")){
                                    String error = "Sukses, "+message;
                                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                                    finish();

//                                    SharedPrefManager.getmInstance(LoginActivity.this).saveUser(loginResponse.getUser());

                                    Intent goMhs = new Intent(MainActivity.this, MahasiswaActivity.class);
                                    startActivity(goMhs);
                                }

                            }else {
                                Toast.makeText(MainActivity.this, messagee, Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}
