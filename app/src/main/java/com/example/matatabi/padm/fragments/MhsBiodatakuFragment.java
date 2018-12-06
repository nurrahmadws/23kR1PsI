package com.example.matatabi.padm.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.AddMahasiswaActivity;
import com.example.matatabi.padm.adapters.MahasiswaAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Mahasiswa;
import com.example.matatabi.padm.model.MahasiswaResponse;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MhsBiodatakuFragment extends Fragment {
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4;
    private RecyclerView recyclerView;
    private MahasiswaAdapter mahasiswaAdapter;
    private List<Mahasiswa> mahasiswaList;
    private TextView txtUsernameMhsRead;
    public static final String mypreference = "mypref";
    public static final String USERNAME = "username";
    SharedPreferences sharedPreferences;
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
        materialDesignFAM = view.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = view.findViewById(R.id.fab_add_mhs);
        floatingActionButton2 = view.findViewById(R.id.fab_show_map_mhs);
        floatingActionButton3 = view.findViewById(R.id.fab_edit_mhs);
        floatingActionButton4 = view.findViewById(R.id.fab_delete_mhs);

        txtUsernameMhsRead = view.findViewById(R.id.txtUsernameMhsRead);
        sharedPreferences = this.getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(USERNAME)){
            txtUsernameMhsRead.setText(sharedPreferences.getString(USERNAME, ""));
        }

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMahasiswaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData(){
        String username = txtUsernameMhsRead.getText().toString();

        Call<MahasiswaResponse> call = RetrofitClient.getmInstance().getApi().readMhs(username);
        call.enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                mahasiswaList = response.body().getMahasiswaList();
                if (!mahasiswaList.isEmpty()){
                    floatingActionButton1.setEnabled(false);
                    floatingActionButton1.setVisibility(View.INVISIBLE);
                    floatingActionButton2.setEnabled(true);
                    floatingActionButton2.setVisibility(View.VISIBLE);
                    floatingActionButton3.setEnabled(true);
                    floatingActionButton3.setVisibility(View.VISIBLE);
                    floatingActionButton4.setEnabled(true);
                    floatingActionButton4.setVisibility(View.VISIBLE);
                }else {
                    floatingActionButton1.setEnabled(true);
                    floatingActionButton1.setVisibility(View.VISIBLE);
                    floatingActionButton2.setEnabled(false);
                    floatingActionButton2.setVisibility(View.INVISIBLE);
                    floatingActionButton3.setEnabled(false);
                    floatingActionButton3.setVisibility(View.INVISIBLE);
                    floatingActionButton4.setEnabled(false);
                    floatingActionButton4.setVisibility(View.INVISIBLE);
                }
                mahasiswaAdapter = new MahasiswaAdapter(getActivity(), mahasiswaList);
                recyclerView.setAdapter(mahasiswaAdapter);
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {

            }
        });
    }
}
