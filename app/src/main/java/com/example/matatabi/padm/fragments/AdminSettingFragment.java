package com.example.matatabi.padm.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.MainActivity;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminSettingFragment extends Fragment implements View.OnClickListener{
    private EditText editTextUsernamee, editTextPasswordd, editTextLevell, editTextId_user;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_setting_fragment, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextId_user = view.findViewById(R.id.editTextId_user);
        editTextUsernamee = view.findViewById(R.id.editTextUsernamee);
        editTextPasswordd = view.findViewById(R.id.editTextPasswsordd);
        editTextLevell = view.findViewById(R.id.editTextLevell);

        sharedPreferences = this.getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(USERNAME)){
            editTextUsernamee.setText(sharedPreferences.getString(USERNAME, ""));
        }
        if (sharedPreferences.contains(PASSWORD)){
            editTextPasswordd.setText(sharedPreferences.getString(PASSWORD, ""));
        }

        editTextLevell.setKeyListener(editTextLevell.getKeyListener());
        editTextLevell.setKeyListener(null);

        view.findViewById(R.id.buttonChange).setOnClickListener(this);
        view.findViewById(R.id.buttonLogout).setOnClickListener(this);
        view.findViewById(R.id.buttonDelete).setOnClickListener(this);
    }

    private void updateUser(){
        AlertDialog.Builder al = new AlertDialog.Builder(getActivity());
        al.setTitle("PERINGATAN!").setMessage("Yakin Ingin Mengubah Data Ini?\nAnda Harus Login Kembali Jika Mengubah Data Ini")
                .setCancelable(false)
                .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = editTextUsernamee.getText().toString();
                        String password = editTextPasswordd.getText().toString();
                        String level = editTextLevell.getText().toString();

                        if (username.isEmpty()){
                            editTextUsernamee.setError("Username Harus Diisi");
                            editTextUsernamee.requestFocus();
                            return;
                        }
                        if (password.isEmpty()){
                            editTextPasswordd.setError("Password Harus Diisi");
                            editTextPasswordd.requestFocus();
                            return;
                        }
                        if (password.length() < 6){
                            editTextPasswordd.setError("Passowrd Harus Lebih Dari 6 Karakter");
                            editTextPasswordd.requestFocus();
                            return;
                        }
                        Call<Value> call = RetrofitClient.getmInstance().getApi().editUserLog(username, password, level);
                        call.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {
                                String value = response.body().getValue();
                                String message = response.body().getMessage();
                                if (value.equals("1")){
                                    Toast.makeText(getActivity(), message+ ", SILAHKAN LOGIN KEMBALI", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                }else {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = al.create();
        alertDialog.show();

    }

    private void logout(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteUser(){
        AlertDialog.Builder au = new AlertDialog.Builder(getActivity());
        au.setTitle("PERINGATAN!").setMessage("Yakin Ingin Menghapus Data Ini?\nAnda Harus Login Kembali dengan Username Lain Jika Menghapus Data Ini")
                .setCancelable(false)
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = editTextUsernamee.getText().toString();
                        Call<Value> valueCall = RetrofitClient.getmInstance().getApi().deleteUserLog(username);
                        valueCall.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {
                                String value = response.body().getValue();
                                String message = response.body().getMessage();
                                if (value.equals("1")){
                                    Toast.makeText(getActivity(), message+ ", SILAHKAN LOGIN KEMBALI", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = au.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonChange:
                updateUser();
                break;
            case R.id.buttonLogout:
                logout();
                break;
            case R.id.buttonDelete:
                deleteUser();
                break;
        }
    }
}
