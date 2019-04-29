package com.example.matatabi.padm.other;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.matatabi.padm.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem{
    private LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private String kecc;
    private String angkatan;

    public MyItem(double lat, double lng, String title, String snippet, String kec, String ang) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
        kecc = kec;
        angkatan = ang;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public String getKecc() {
        return kecc;
    }

    public String getAngkatan() {
        return angkatan;
    }
}
