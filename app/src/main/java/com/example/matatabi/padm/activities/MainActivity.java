package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTextUsernameLogin = findViewById(R.id.edtTextUsernameLogin);
        edtTextPasswordLogin = findViewById(R.id.edtTextPasswordLogin);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        if (sharedPreferences.contains(USERNAME)){
//            edtTextUsernameLogin.setText(sharedPreferences.getString(USERNAME, ""));
//        }
//        if (sharedPreferences.contains(PASSWORD)){
//            edtTextPasswordLogin.setText(sharedPreferences.getString(PASSWORD, ""));
//        }

        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Tunggu Sebentar...");
                progressDialog.show();

                final String username = edtTextUsernameLogin.getText().toString();
                final String password = edtTextPasswordLogin.getText().toString();

                if (username.isEmpty()){
                    progressDialog.dismiss();
                    edtTextUsernameLogin.setError("Username Harus di Isi");
                    edtTextUsernameLogin.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    progressDialog.dismiss();
                    edtTextPasswordLogin.setError("Password Harus di Isi");
                    edtTextPasswordLogin.requestFocus();
                    return;
                }

                Call<LoginResponse> call = RetrofitClient.getmInstance().getApi().userLogin(username, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            Boolean value = response.body().getValue();
                            String messagee = response.body().getMessage();
                            if (value){
                                String message = response.body().getMessage();
                                String level = response.body().getLevel();

                                if (level.equals("Admin")){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(USERNAME, username);
                                    editor.putString(PASSWORD, password);
                                    editor.apply();

                                    String error = "Sukses, "+message;
                                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                                    finish();
                                    Intent goAdmin = new Intent(MainActivity.this, AdminActivity.class);
                                    startActivity(goAdmin);

                                }else if (level.equals("Mahasiswa")){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(USERNAME, username);
                                    editor.putString(PASSWORD, password);
                                    editor.apply();

                                    String error = "Sukses, "+message;
                                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                                    finish();

                                    Intent goMhs = new Intent(MainActivity.this, OnBoardingScreenMhs.class);
                                    goMhs.putExtra("username", username);
                                    startActivity(goMhs);
                                }

                            }else {
                                Toast.makeText(MainActivity.this, messagee, Toast.LENGTH_LONG).show();
                            }
                        }else {
                            progressDialog.dismiss();
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
