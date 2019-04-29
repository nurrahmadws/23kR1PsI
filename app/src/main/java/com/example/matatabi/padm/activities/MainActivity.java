package com.example.matatabi.padm.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.LoginResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edtTextUsernameLogin, edtTextPasswordLogin;
    private Button btnLogin;
    private TextView txtRegister;
    private AppCompatCheckBox checkBox;

    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private static final int PERMISSIONS_REQUEST_CODE = 1240;
    String[] appPermissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET,
                               Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTextUsernameLogin = findViewById(R.id.edtTextUsernameLogin);
        edtTextPasswordLogin = findViewById(R.id.edtTextPasswordLogin);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        checkBox = findViewById(R.id.checkboxPass);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    edtTextPasswordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else {
                    edtTextPasswordLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrasiActivity.class));
            }
        });

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
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, messagee, Toast.LENGTH_LONG).show();
                            }
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Gagal Terhubung Internet", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Server Gagal Merespon", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        checkSelfPermission();
        gpsCheck();
    }

    private boolean checkSelfPermission(){
        List<String> listPermissionNeeded = new ArrayList<>();
        for (String perm : appPermissions)
        {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionNeeded.add(perm);
            }
        }
        if (!listPermissionNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), PERMISSIONS_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE)
        {
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;
            for (int i = 0; i < grantResults.length; i++)
            {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                {
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }
            if (deniedCount == 0)
            {
                onResume();
            }else {
                for (Map.Entry<String, Integer> entry : permissionResults.entrySet())
                {
                    String permName = entry.getKey();
                    int permResult = entry.getValue();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permName))
                    {
                        showDialog("", "Aplikasi Ini Membutuhkan Beberapa Izin Supaya Berjalan Dengan Lancar.",
                                "Izinkan", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        checkSelfPermission();
                                    }
                                }, "Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }, false);
                    }else {
                        showDialog("", "Anda Telah Menolak Beberapa Izin Untuk Aplikasi Ini, Silahkan Pergi Ke Pengaturan/Settings",
                                "Go To Settings", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, "Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }, false);
                        break;
                    }
                }
            }
        }
    }

    private void gpsCheck(){
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            alertDialog();
        }
    }

    private AlertDialog showDialog(String title, String msg, String positiveLabel, DialogInterface.OnClickListener positiveOnClick,
                                   String negativeLabel, DialogInterface.OnClickListener negativeOnClick, boolean isCancleAble)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(isCancleAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeOnClick);

        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    private void alertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("GPS Anda Belum Diaktifkan, Aktifkan Sekarang?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
