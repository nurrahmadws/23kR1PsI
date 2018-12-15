package com.example.matatabi.padm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.DetailLatLngActivity;
import com.example.matatabi.padm.activities.EditLatLngActivity;
import com.example.matatabi.padm.model.Latlng;

import java.util.List;

public class LatLngAdapter extends RecyclerView.Adapter<LatLngAdapter.LatLngViewHolder> {
    private Context mcc;
    private List<Latlng> latlngList;

    public LatLngAdapter(Context mcc, List<Latlng> latlngList) {
        this.mcc = mcc;
        this.latlngList = latlngList;
    }

    @NonNull
    @Override
    public LatLngViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcc).inflate(R.layout.admin_recyclerview_read_latlng, viewGroup, false);
        return new LatLngViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LatLngViewHolder latLngViewHolder, int i) {
        Latlng latlng = latlngList.get(i);
        latLngViewHolder.txtIdLatlng.setText(Integer.toString(latlng.getId_latlng()));
        latLngViewHolder.txtKabkotLat.setText(latlng.getNm_kabupaten());
        latLngViewHolder.txtKecamatanLat.setText(latlng.getNm_kecamatan());
        latLngViewHolder.txtKelurahanLat.setText(latlng.getNm_kelurahan());
        latLngViewHolder.txtLat.setText(latlng.getNm_lat());
        latLngViewHolder.txtLng.setText(latlng.getNm_lng());
    }

    @Override
    public int getItemCount() {
        return latlngList.size();
    }

    class LatLngViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtIdLatlng, txtKabkotLat, txtKecamatanLat, txtKelurahanLat, txtLat, txtLng;
        Button btn_tampil_Lat;
        public LatLngViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdLatlng = itemView.findViewById(R.id.txtIdLatlng);
            txtKabkotLat = itemView.findViewById(R.id.txtKabkotLat);
            txtKecamatanLat = itemView.findViewById(R.id.txtKecamatanLat);
            txtKelurahanLat = itemView.findViewById(R.id.txtKelurahanLat);
            txtLat = itemView.findViewById(R.id.txtLat);
            txtLng = itemView.findViewById(R.id.txtLng);
            btn_tampil_Lat = itemView.findViewById(R.id.btn_tampil_Lat);
            btn_tampil_Lat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id_latlng = txtIdLatlng.getText().toString();
                    String nm_kabupaten = txtKabkotLat.getText().toString();
                    String nm_kecamatan = txtKecamatanLat.getText().toString();
                    String nm_kelurahan = txtKelurahanLat.getText().toString();
                    String nm_lat = txtLat.getText().toString();
                    String nm_lng = txtLng.getText().toString();

                    Intent intentt = new Intent(mcc, DetailLatLngActivity.class);
                    intentt.putExtra("id_latlng", id_latlng);
                    intentt.putExtra("nm_kabupaten", nm_kabupaten);
                    intentt.putExtra("nm_kecamatan", nm_kecamatan);
                    intentt.putExtra("nm_kelurahan", nm_kelurahan);
                    intentt.putExtra("nm_lat", nm_lat);
                    intentt.putExtra("nm_lng", nm_lng);
                    mcc.startActivity(intentt);
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String id_latlng = txtIdLatlng.getText().toString();
            String nm_kabupaten = txtKabkotLat.getText().toString();
            String nm_kecamatan = txtKecamatanLat.getText().toString();
            String nm_kelurahan = txtKelurahanLat.getText().toString();
            String nm_lat = txtLat.getText().toString();
            String nm_lng = txtLng.getText().toString();

            Intent intent = new Intent(mcc, EditLatLngActivity.class);
            intent.putExtra("id_latlng", id_latlng);
            intent.putExtra("nm_kabupaten", nm_kabupaten);
            intent.putExtra("nm_kecamatan", nm_kecamatan);
            intent.putExtra("nm_kelurahan", nm_kelurahan);
            intent.putExtra("nm_lat", nm_lat);
            intent.putExtra("nm_lng", nm_lng);
            mcc.startActivity(intent);
        }
    }
}
