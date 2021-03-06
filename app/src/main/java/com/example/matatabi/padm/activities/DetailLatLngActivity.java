package com.example.matatabi.padm.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Latlng;
import com.example.matatabi.padm.model.LatlngResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLatLngActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Latlng> latLngList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lat_lng);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getDataLocationFromDb();
    }

    private void getDataLocationFromDb(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menampilkan Lokasi...");
        progressDialog.show();

        String id_latlng = getIntent().getStringExtra("id_latlng");
        Call<LatlngResponse> call = RetrofitClient.getmInstance().getApi().detailLatlng(id_latlng);
        call.enqueue(new Callback<LatlngResponse>() {
            @Override
            public void onResponse(@NonNull Call<LatlngResponse> call, @NonNull Response<LatlngResponse> response) {
                progressDialog.dismiss();
                latLngList = response.body().getLatlngList();
                initLocation(latLngList);
            }

            @Override
            public void onFailure(@NonNull Call<LatlngResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailLatLngActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initLocation(List<Latlng> latLngList){
        for (int i=0; i < latLngList.size(); i++){
            LatLng location = new LatLng(Double.parseDouble(latLngList.get(i).getNm_lat()),
                    Double.parseDouble(latLngList.get(i).getNm_lng()));
            mMap.addMarker(new MarkerOptions().position(location).title(latLngList.get(i).getNm_kelurahan()));
            LatLng latLng = new LatLng(Double.parseDouble(latLngList.get(i).getNm_lat()),
                    Double.parseDouble(latLngList.get(i).getNm_lng()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 15.0f));
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            CircleOptions circleOptions = new CircleOptions().center(new LatLng(Double.parseDouble(latLngList.get(i).getNm_lat()),
                    Double.parseDouble(latLngList.get(i).getNm_lng()))).radius(1089).strokeColor(Color.RED)
                    .fillColor(Color.argb(128, 255, 0, 0));
            Circle circle = mMap.addCircle(circleOptions);
        }
    }
}
