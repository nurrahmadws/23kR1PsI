package com.example.matatabi.padm.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.EditKelurahanActivity;
import com.example.matatabi.padm.model.Kelurahan;

import java.util.List;

public class KelurahanAdapter extends RecyclerView.Adapter<KelurahanAdapter.KelurahanViewHolder> {
    private Context mcu;
    private List<Kelurahan> kelurahanList;

    public KelurahanAdapter(Context mcu, List<Kelurahan> kelurahanList) {
        this.mcu = mcu;
        this.kelurahanList = kelurahanList;
    }

    @NonNull
    @Override
    public KelurahanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcu).inflate(R.layout.admin_recyclerview_read_kelurahan, viewGroup, false);
        return new KelurahanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KelurahanViewHolder kelurahanViewHolder, int i) {
        Kelurahan kelurahan = kelurahanList.get(i);
        kelurahanViewHolder.txtIdkelurahan.setText(Integer.toString(kelurahan.getId_kelurahan()));
        kelurahanViewHolder.txtKabkotKel.setText(kelurahan.getNm_kabupaten());
        kelurahanViewHolder.txtKecamatanKel.setText(kelurahan.getNm_kecamatan());
        kelurahanViewHolder.txtKelurahan.setText(kelurahan.getNm_kelurahan());
    }

    @Override
    public int getItemCount() {
        return kelurahanList.size();
    }

    class KelurahanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtIdkelurahan, txtKabkotKel, txtKecamatanKel, txtKelurahan;
        public KelurahanViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdkelurahan = itemView.findViewById(R.id.txtIdkelurahan);
            txtKabkotKel = itemView.findViewById(R.id.txtKabkotKel);
            txtKecamatanKel = itemView.findViewById(R.id.txtKecamatanKel);
            txtKelurahan = itemView.findViewById(R.id.txtKelurahan);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String id_kelurahan = txtIdkelurahan.getText().toString();
            String nm_kabupaten = txtKabkotKel.getText().toString();
            String nm_kecamatan = txtKecamatanKel.getText().toString();
            String nm_kelurahan = txtKelurahan.getText().toString();

            Intent intent = new Intent(mcu, EditKelurahanActivity.class);
            intent.putExtra("id_kelurahan", id_kelurahan);
            intent.putExtra("nm_kabupaten", nm_kabupaten);
            intent.putExtra("nm_kecamatan", nm_kecamatan);
            intent.putExtra("nm_kelurahan", nm_kelurahan);
            mcu.startActivity(intent);
        }
    }
}
