package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {
    private EditText editTextidUser, editTextUsername, editTextPassword;
    private Spinner spinLevelL;
    private Button btn_Ubah_user, btn_hapus_user, btn_batal_user;
    private AppCompatCheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextidUser = findViewById(R.id.editTextidUser);
        editTextUsername = findViewById(R.id.editTextUsernaame);
        editTextPassword = findViewById(R.id.editTextPasswordd);
        spinLevelL = findViewById(R.id.spinLevelL);

        checkBox = findViewById(R.id.checkboxPassEdit);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else {
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        Intent intent = getIntent();
        editTextidUser.setText(intent.getStringExtra("id_user"));
        editTextidUser.setKeyListener(editTextidUser.getKeyListener());
        editTextidUser.setKeyListener(null);

        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        editTextUsername.setText(username);
        editTextPassword.setText(password);

        String level = intent.getStringExtra("level");
        if (level.equals("Admin")) {
            spinLevelL.setSelected(true);
        } else if (level.equals("Mahasiswa")) {
            spinLevelL.setSelected(true);
        }

        btn_Ubah_user = findViewById(R.id.btn_ubah_user);
        btn_Ubah_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(EditUserActivity.this);
                al.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data Ini?\nPeriksa Kembali Data Sebelum diubah")
                        .setCancelable(false)
                        .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = new ProgressDialog(EditUserActivity.this);
                                progressDialog.setMessage("Sedang Mengubah Data...");
                                progressDialog.show();

                                String id_user = editTextidUser.getText().toString();
                                String username = editTextUsername.getText().toString();
                                String password = editTextPassword.getText().toString();
                                String level = spinLevelL.getSelectedItem().toString();

                                if (username.isEmpty()) {
                                    progressDialog.dismiss();
                                    editTextUsername.setError("Username Harus Diisi");
                                    editTextUsername.requestFocus();
                                    return;
                                }
                                if (password.isEmpty()) {
                                    progressDialog.dismiss();
                                    editTextPassword.setError("Password Harus Diisi");
                                    editTextPassword.requestFocus();
                                    return;
                                }
                                if (password.length() < 6) {
                                    progressDialog.dismiss();
                                    editTextPassword.setError("Password Harus Lebih 6 Karakter");
                                    editTextPassword.requestFocus();
                                    return;
                                }

                                Call<Value> call = RetrofitClient.getmInstance().getApi().editUser(id_user, username, password, level);
                                call.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        progressDialog.dismiss();
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")) {
                                            Toast.makeText(EditUserActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent intent = new Intent(EditUserActivity.this, DataUserActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        } else {
                                            Toast.makeText(EditUserActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        progressDialog.dismiss();
                                        t.printStackTrace();
                                        Toast.makeText(EditUserActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(EditUserActivity.this, DataUserActivity.class));
                    }
                });
                AlertDialog alertDialog = al.create();
                alertDialog.show();
            }
        });
        btn_hapus_user = findViewById(R.id.btn_hapus_user);
        btn_hapus_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ale = new AlertDialog.Builder(EditUserActivity.this);
                ale.setTitle("PERINGATAN!").setMessage("Ingin Menghapus Data Ini?").setCancelable(false)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = new ProgressDialog(EditUserActivity.this);
                                progressDialog.setMessage("Sedang Menghapus...");
                                progressDialog.show();

                                String id_user = editTextidUser.getText().toString();
                                Call<Value> call = RetrofitClient.getmInstance().getApi().deleteUser(id_user);
                                call.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        progressDialog.dismiss();
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")) {
                                            Toast.makeText(EditUserActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent intent = new Intent(EditUserActivity.this, DataUserActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        } else {
                                            Toast.makeText(EditUserActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        progressDialog.dismiss();
                                        t.printStackTrace();
                                        Toast.makeText(EditUserActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(EditUserActivity.this, DataUserActivity.class));
                    }
                });
                AlertDialog dialog = ale.create();
                dialog.show();
            }
        });
        btn_batal_user = findViewById(R.id.btn_batal_user);
        btn_batal_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditUserActivity.this, DataUserActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DataUserActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
