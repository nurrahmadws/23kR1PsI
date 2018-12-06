package com.example.matatabi.padm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.model.Mahasiswa;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {
    private Context context;
    private List<Mahasiswa> mahasiswaList;

    public MahasiswaAdapter(Context context, List<Mahasiswa> mahasiswaList) {
        this.context = context;
        this.mahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.mhs_recyclerview_read_biodataku, viewGroup, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder mahasiswaViewHolder, int i) {
        Mahasiswa mahasiswa = mahasiswaList.get(i);
        mahasiswaViewHolder.txtNim.setText(mahasiswa.getNim());
        mahasiswaViewHolder.txtNama.setText(mahasiswa.getNama());
        mahasiswaViewHolder.txtUsernameMhs.setText(mahasiswa.getUsername());
        mahasiswaViewHolder.txtno_hp.setText(mahasiswa.getNo_hp());
        mahasiswaViewHolder.txtJk.setText(mahasiswa.getJk());
        mahasiswaViewHolder.txtFakultas.setText(mahasiswa.getFakultas());
        mahasiswaViewHolder.txtProdi.setText(mahasiswa.getProdi());
        mahasiswaViewHolder.txtAngkatan.setText(mahasiswa.getAngkatan());
        mahasiswaViewHolder.txtProvinsi.setText(mahasiswa.getProvinsi());
        mahasiswaViewHolder.txtKabupaten.setText(mahasiswa.getNm_kabupaten());
        mahasiswaViewHolder.txtKecamatan.setText(mahasiswa.getNm_kecamatan());
        mahasiswaViewHolder.txtKelurahan.setText(mahasiswa.getNm_kelurahan());
        mahasiswaViewHolder.txtLat.setText(mahasiswa.getNm_lat());
        mahasiswaViewHolder.txtLNg.setText(mahasiswa.getNm_lng());
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNim, txtUsernameMhs, txtNama, txtno_hp, txtJk, txtFakultas, txtProdi, txtAngkatan, txtProvinsi, txtKabupaten, txtKecamatan, txtKelurahan, txtLat, txtLNg;
        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNim = itemView.findViewById(R.id.txtNim);
            txtUsernameMhs = itemView.findViewById(R.id.txtUsernameMhs);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtno_hp = itemView.findViewById(R.id.txtHp);
            txtJk = itemView.findViewById(R.id.txtjk);
            txtFakultas = itemView.findViewById(R.id.txtFakultas);
            txtProdi = itemView.findViewById(R.id.txtProdi);
            txtAngkatan = itemView.findViewById(R.id.txtAngkatan);
            txtProvinsi = itemView.findViewById(R.id.txtProvinsi);
            txtKabupaten = itemView.findViewById(R.id.txtKabupatenMhs);
            txtKecamatan = itemView.findViewById(R.id.txtKecamatanMhs);
            txtKelurahan = itemView.findViewById(R.id.txtKelurahanMhs);
            txtLat = itemView.findViewById(R.id.txtLatitude);
            txtLNg = itemView.findViewById(R.id.txtLongtitude);
        }
    }
}
