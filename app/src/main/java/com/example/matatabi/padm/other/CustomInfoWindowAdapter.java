package com.example.matatabi.padm.other;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.MarkerAllMahasiswaBsdKelActivity;
import com.example.matatabi.padm.model.MapMhsBsdKec;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    private List<MapMhsBsdKec> mapMhsBsdKecList;

    public CustomInfoWindowAdapter(Activity context, List<MapMhsBsdKec> mapMhsBsdKecList) {
        this.context = context;
        this.mapMhsBsdKecList = mapMhsBsdKecList;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.custom_marker_info_window, null);
        TextView nama_mhs = view.findViewById(R.id.nama_mhs);
        TextView alamat_mhs = view.findViewById(R.id.alamat_mhs);
        TextView lat_alamat_mhs = view.findViewById(R.id.lat_alamat_mhs);
        TextView lng_alamat_mhs = view.findViewById(R.id.lng_alamat_mhs);
        return view;
    }
}
