package com.example.matatabi.padm.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.MapMhsBsdKec;
import com.example.matatabi.padm.model.MapMhsBsdKecResponse;
import com.example.matatabi.padm.other.CustomInfoWindowAdapter;
import com.example.matatabi.padm.other.MyItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkerAllMahasiswaBsdKelActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ClusterManager<MyItem> clusterManager;
    private List<MapMhsBsdKec> mapMhsBsdKecList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_all_mahasiswa_bsd_kel);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setUpCluster();
    }

    private void setUpCluster(){
//        mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(new LatLng(-0.486370, 117.149066), 10)));
        clusterManager = new ClusterManager<>(this, mMap);
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        mMap.setOnInfoWindowClickListener(clusterManager);
        mMap.setTrafficEnabled(true);
        addItems();
        clusterManager.cluster();

        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

    private void addItems(){
        Intent intent = getIntent();
        String nm_kecamatan = intent.getStringExtra("nm_kecamatan");

        Call<MapMhsBsdKecResponse> call = RetrofitClient.getmInstance().getApi().showMapMhsBsdKec(nm_kecamatan);
        call.enqueue(new Callback<MapMhsBsdKecResponse>() {
            @Override
            public void onResponse(@NonNull Call<MapMhsBsdKecResponse> call, @NonNull Response<MapMhsBsdKecResponse> response) {
                assert response.body() != null;
                mapMhsBsdKecList = response.body().getMapMhsBsdKecList();
                initLocation(mapMhsBsdKecList);
            }

            @Override
            public void onFailure(@NonNull Call<MapMhsBsdKecResponse> call, @NonNull Throwable t) {
                Toast.makeText(MarkerAllMahasiswaBsdKelActivity.this, "Gagal Merespon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initLocation(final List<MapMhsBsdKec> mapMhsBsdKecList1){
        for (int i=0; i < mapMhsBsdKecList1.size(); i++){
            double lat;
            double lng;
            String nama;
            String alamat;
            String kec;
            String ang;

            lat = Double.parseDouble(mapMhsBsdKecList1.get(i).getLat_alamat_sekarang());
            lng = Double.parseDouble(mapMhsBsdKecList1.get(i).getLng_alamat_sekarang());
            nama = mapMhsBsdKecList1.get(i).getNama();
            alamat = mapMhsBsdKecList1.get(i).getAlamat_sekarang();
            kec = mapMhsBsdKecList1.get(i).getNm_kecamatan();
            ang = mapMhsBsdKecList1.get(i).getAngkatan();

            MyItem myItem = new MyItem(lat, lng, nama, alamat, kec, ang);
            clusterManager.addItem(myItem);
            mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13)));
        }
    }

    private class RenderClusterInfoWindow extends DefaultClusterRenderer<MyItem>{

        public RenderClusterInfoWindow(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onClusterRendered(Cluster<MyItem> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
            markerOptions.title(item.getTitle());
            super.onBeforeClusterItemRendered(item, markerOptions);
        }
    }
}
