package com.example.matatabi.padm.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.AddImageMhsActivity;
import com.example.matatabi.padm.activities.AddMahasiswaActivity;
import com.example.matatabi.padm.activities.GetCoordinateManuallyActivity;
import com.example.matatabi.padm.activities.MainActivity;
import com.example.matatabi.padm.adapters.MahasiswaAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Mahasiswa;
import com.example.matatabi.padm.model.MahasiswaResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MhsBiodatakuFragment extends Fragment {
    private RecyclerView recyclerView;
    private MahasiswaAdapter mahasiswaAdapter;
    private List<Mahasiswa> mahasiswaList;
    private TextView txtUsernameMhsRead;
    public static final String mypreference = "mypref";
    public static final String USERNAME = "username";
    SharedPreferences sharedPreferences;
    private FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mhs_biodataku_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_mhs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txtUsernameMhsRead = view.findViewById(R.id.txtUsernameMhsRead);
        sharedPreferences = this.getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(USERNAME)){
            txtUsernameMhsRead.setText(sharedPreferences.getString(USERNAME, ""));
        }
        fab = view.findViewById(R.id.fab_add_mhs);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                ad.setTitle("Pemberitahuan!");
                ad.setMessage("Untuk melanjutkan proses pengisian data, \nAnda harus mengisi terlebih dahulu alamat tempat tinggal anda yang sekarang");
                ad.setCancelable(false);
                ad.setPositiveButton("Saya Mengerti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), GetCoordinateManuallyActivity.class));
                    }
                });
                ad.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = ad.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil Data...");
        progressDialog.show();
        String username = txtUsernameMhsRead.getText().toString();
        Call<MahasiswaResponse> call = RetrofitClient.getmInstance().getApi().readMhs(username);
        call.enqueue(new Callback<MahasiswaResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(@NonNull Call<MahasiswaResponse> call, @NonNull Response<MahasiswaResponse> response) {
                progressDialog.dismiss();
                mahasiswaList = response.body().getMahasiswaList();
                mahasiswaAdapter = new MahasiswaAdapter(getActivity(), mahasiswaList);
                recyclerView.setAdapter(mahasiswaAdapter);
                mahasiswaAdapter.notifyDataSetChanged();
                if (mahasiswaList.isEmpty()){
                    fab.setEnabled(true);
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setEnabled(false);
                    fab.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MahasiswaResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(getActivity(), "Tidak Terhubung Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
