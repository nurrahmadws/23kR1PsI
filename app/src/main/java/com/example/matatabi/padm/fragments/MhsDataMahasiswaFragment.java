package com.example.matatabi.padm.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.AllMahasiswaAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Mahasiswa;
import com.example.matatabi.padm.model.MahasiswaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MhsDataMahasiswaFragment extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private AllMahasiswaAdapter allMahasiswaAdapter;
    private List<Mahasiswa> mahasiswaList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mhs_data_mahasiswa_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_all_mhs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData(){
        Call<MahasiswaResponse> call = RetrofitClient.getmInstance().getApi().readAllMhs();
        call.enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                mahasiswaList = response.body().getMahasiswaList();
                allMahasiswaAdapter = new AllMahasiswaAdapter(getActivity(), mahasiswaList);
                recyclerView.setAdapter(allMahasiswaAdapter);
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {

            }
        });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.search, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Cari NIM");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        recyclerView.setVisibility(View.GONE);
        Call<MahasiswaResponse> call = RetrofitClient.getmInstance().getApi().searchMhs(s);
        call.enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                String value = response.body().getValue();
                recyclerView.setVisibility(View.VISIBLE);
                if (value.equals("1")){
                    mahasiswaList = response.body().getMahasiswaList();
                    allMahasiswaAdapter = new AllMahasiswaAdapter(getActivity(), mahasiswaList);
                    recyclerView.setAdapter(allMahasiswaAdapter);
                }
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {

            }
        });
        return true;
    }
}
