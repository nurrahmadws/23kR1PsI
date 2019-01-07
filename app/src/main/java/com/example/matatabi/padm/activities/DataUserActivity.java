package com.example.matatabi.padm.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.adapters.UsersAdapter;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Users;
import com.example.matatabi.padm.model.UsersResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataUserActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private UsersAdapter usersAdapter;
    private List<Users> usersList = new ArrayList<>();
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipe_refresh_users;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipe_refresh_users = findViewById(R.id.swipe_refresh_users);
        swipe_refresh_users.setColorSchemeColors(R.color.bgmhsEnd,R.color.colorBackgroundAdduser,R.color.colorHomeAccent,R.color.colorHomeYellow);

        recyclerView = findViewById(R.id.rv_users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DataUserActivity.this));
        usersList = new ArrayList<>();

        swipe_refresh_users.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        fab = findViewById( R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataUserActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        swipe_refresh_users.setRefreshing(false);
        final ProgressDialog progressDialog = new ProgressDialog(DataUserActivity.this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.show();

        Call<UsersResponse> call = RetrofitClient.getmInstance().getApi().readUsers();
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                progressDialog.dismiss();
                recyclerView.setVisibility(View.VISIBLE);

                String value = response.body().getValue();
                if (value.equals("1")){
                    usersList = response.body().getUsersList();
                    usersAdapter = new UsersAdapter(DataUserActivity.this, usersList);
                    recyclerView.setAdapter(usersAdapter);
                    usersAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Cari Username");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        recyclerView.setVisibility(View.GONE);
        Call<UsersResponse> usersResponseCall = RetrofitClient.getmInstance().getApi().searchUs(s);
        usersResponseCall.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                String value = response.body().getValue();
                recyclerView.setVisibility(View.VISIBLE);
                if (value.equals("1")){
                    usersList = response.body().getUsersList();
                    usersAdapter = new UsersAdapter(DataUserActivity.this, usersList);
                    recyclerView.setAdapter(usersAdapter);
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
