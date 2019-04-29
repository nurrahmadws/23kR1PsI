package com.example.matatabi.padm.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetCoordinateManuallyEditActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private View mMarkerParentView;
    private ImageView mMarkerImageView;
    private EditText edtNimEditMhs;

    static final int REQUEST_LOCATION=1;

    private TextView mLocation, mLocationLat, mLocationLng;

    private static final String TAG = "MyActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    Criteria criteria;
    LocationManager locationManager;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_coordinate_manually_edit);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);

        // Initialize Places.
        if (!Places.isInitialized()){
            Places.initialize(getApplicationContext(), "AIzaSyCX120e-jUfRhC4JWbA7MAPLSOSjjsoazI");
        }
        mLocation = findViewById(R.id.location_text_view_alamat_sekarang);
        mLocationLat = findViewById(R.id.location_text_view_lat_asek);
        mLocationLng = findViewById(R.id.location_text_view_lng_asek);
        mMarkerParentView = findViewById(R.id.marker_view_include_edit);
        mMarkerImageView = findViewById(R.id.marker_icon_view);

        edtNimEditMhs = findViewById(R.id.edtNimEditMhss);

        final Intent intent = getIntent();
        final String nim = intent.getStringExtra("nim");
        edtNimEditMhs.setText(nim);

        getLocationPermission();
        configureCameraIdle();

        Button btnKonfirmasiAsek = findViewById(R.id.btn_konfirmasi_alamat_sekarang_edit);
        btnKonfirmasiAsek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alamat_sekarang = mLocation.getText().toString();
                String lat_alamat_sekarang = mLocationLat.getText().toString();
                String lng_alamat_sekarang = mLocationLng.getText().toString();

                Intent intent = new Intent(GetCoordinateManuallyEditActivity.this, EditMhsAlamatSekarangActivity.class);
                intent.putExtra("nim", nim);
                intent.putExtra("alamat_sekarang", alamat_sekarang);
                intent.putExtra("lat_alamat_sekarang", lat_alamat_sekarang);
                intent.putExtra("lng_alamat_sekarang", lng_alamat_sekarang);
                startActivity(intent);
            }
        });

    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void configureCameraIdle(){
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = mMap.getCameraPosition().target;
                Geocoder geocoder = new Geocoder(GetCoordinateManuallyEditActivity.this, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {

                        String address1 = addressList.get(0).getAddressLine(0);

                        mLocation.setText(address1);

                        String latitude = String.valueOf(addressList.get(0).getLatitude());
                        String longtitude = String.valueOf(addressList.get(0).getLongitude());
                        latitude = latitude.substring(0,latitude.indexOf(".")+6);
                        longtitude = longtitude.substring(0,longtitude.indexOf(".")+6);

                        if (!latitude.isEmpty() && !longtitude.isEmpty())
                            mLocationLat.setText(latitude);
                        mLocationLng.setText(longtitude);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(onCameraIdleListener);
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

        }
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        final ProgressDialog progressDialog = new ProgressDialog(GetCoordinateManuallyEditActivity.this);
        progressDialog.setMessage("Sedang Mencari Lokasi Anda...");
        progressDialog.show();

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            progressDialog.dismiss();
                            Location currentLocation = (Location) task.getResult();

                            MyLocationListener myLocationListener = new MyLocationListener();
                            if (currentLocation != null){
                                myLocationListener.onLocationChanged(currentLocation);
                            }
                        }else{
                            progressDialog.dismiss();
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(GetCoordinateManuallyEditActivity.this, "Can't Find Your Location, Try Again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }else {
                progressDialog.dismiss();
                Toast.makeText(this, "GPS Is Disable, Please Enable Your GPS", Toast.LENGTH_SHORT).show();
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            moveCamera(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, permissions, REQUEST_LOCATION);
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }
}
