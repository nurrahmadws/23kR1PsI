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

public class DetailMhsAdapter extends RecyclerView.Adapter<DetailMhsAdapter.DetailMhsViewHolder> {
    private Context mc;
    private List<Mahasiswa> mahasiswaList;

    public DetailMhsAdapter(Context mc, List<Mahasiswa> mahasiswaList) {
        this.mc = mc;
        this.mahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public DetailMhsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mc).inflate(R.layout.mhs_recyclerview_detail_mjs, viewGroup, false);
        return new DetailMhsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailMhsViewHolder detailMhsViewHolder, int i) {
        Mahasiswa mahasiswa = mahasiswaList.get(i);
        detailMhsViewHolder.txtNimReadDetailMhs.setText(mahasiswa.getNim());
        detailMhsViewHolder.txtUsernameReadDetailMhs.setText(mahasiswa.getUsername());
        detailMhsViewHolder.txtNikReadDetailMhs.setText(mahasiswa.getNik());
        detailMhsViewHolder.txtNamaReadDetailMhs.setText(mahasiswa.getNama());
        detailMhsViewHolder.txtJkReadDetailMhs.setText(mahasiswa.getJk());
        detailMhsViewHolder.txtTempatLahirReadDetailMhs.setText(mahasiswa.getTempat_lahir());
        detailMhsViewHolder.txtTglLahirReadDetailMhs.setText(mahasiswa.getTgl_lahir());
        detailMhsViewHolder.txtNoHpReadDetailMhs.setText(mahasiswa.getNo_hp());
        detailMhsViewHolder.txtEmailReadDetailMhs.setText(mahasiswa.getEmail());
        detailMhsViewHolder.txtFakultasReadDetailMhs.setText(mahasiswa.getFakultas());
        detailMhsViewHolder.txtProdiReadDetailMhs.setText(mahasiswa.getProdi());
        detailMhsViewHolder.txtAngkatanReadDetailMhs.setText(mahasiswa.getAngkatan());
        detailMhsViewHolder.txtKelasReadDetailMhs.setText(mahasiswa.getKelas());
        detailMhsViewHolder.txtProvinsiReadDetailMhs.setText(mahasiswa.getProvinsi());
        detailMhsViewHolder.txtKabupatenReadDetailMhs.setText(mahasiswa.getNm_kabupaten());
        detailMhsViewHolder.txtKecamatanReadDetailMhs.setText(mahasiswa.getNm_kecamatan());
        detailMhsViewHolder.txtKelurahanReadDetailMhs.setText(mahasiswa.getNm_kelurahan());
        detailMhsViewHolder.txtLatReadDetailMhs.setText(mahasiswa.getNm_lat());
        detailMhsViewHolder.txtLNgReadDetailMhs.setText(mahasiswa.getNm_lng());
        detailMhsViewHolder.txtAlamatSekarangReadDetailMhs.setText(mahasiswa.getAlamat_sekarang());
        detailMhsViewHolder.txtLatAlamatSekarangReadDetailMhs.setText(mahasiswa.getLat_alamat_sekarang());
        detailMhsViewHolder.txtLngAlamatSekarangReadDetailMhs.setText(mahasiswa.getLng_alamat_sekarang());
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    class DetailMhsViewHolder extends RecyclerView.ViewHolder{
        TextView txtNimReadDetailMhs, txtUsernameReadDetailMhs, txtNikReadDetailMhs, txtNamaReadDetailMhs, txtJkReadDetailMhs, txtTempatLahirReadDetailMhs,
                txtTglLahirReadDetailMhs, txtNoHpReadDetailMhs, txtEmailReadDetailMhs, txtFakultasReadDetailMhs, txtProdiReadDetailMhs, txtAngkatanReadDetailMhs,
                txtKelasReadDetailMhs, txtProvinsiReadDetailMhs, txtKabupatenReadDetailMhs, txtKecamatanReadDetailMhs, txtKelurahanReadDetailMhs, txtLatReadDetailMhs,
                txtLNgReadDetailMhs, txtAlamatSekarangReadDetailMhs, txtLatAlamatSekarangReadDetailMhs, txtLngAlamatSekarangReadDetailMhs;

        DetailMhsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNimReadDetailMhs = itemView.findViewById(R.id.txtNimReadDetailMhs);
            txtUsernameReadDetailMhs = itemView.findViewById(R.id.txtUsernameMhsReadDetailMhs);
            txtNikReadDetailMhs = itemView.findViewById(R.id.txtNikMhsReadDetailMhs);
            txtNamaReadDetailMhs = itemView.findViewById(R.id.txtNamaReadDetailMhs);
            txtJkReadDetailMhs = itemView.findViewById(R.id.txtjkReadDetailMhs);
            txtTempatLahirReadDetailMhs = itemView.findViewById(R.id.txtTempatLahirReadDetailMhs);
            txtTglLahirReadDetailMhs = itemView.findViewById(R.id.txtTglLahirReadDetailMhs);
            txtNoHpReadDetailMhs = itemView.findViewById(R.id.txtNoHpReadDetailMhs);
            txtEmailReadDetailMhs = itemView.findViewById(R.id.txtEmailReadDetailMhs);
            txtFakultasReadDetailMhs = itemView.findViewById(R.id.txtFakultasReadDetailMhs);
            txtProdiReadDetailMhs = itemView.findViewById(R.id.txtProdiReadDetailMhs);
            txtAngkatanReadDetailMhs = itemView.findViewById(R.id.txtAngkatanReadDetailMhs);
            txtKelasReadDetailMhs = itemView.findViewById(R.id.txtKelasReadDetailMhs);
            txtProvinsiReadDetailMhs = itemView.findViewById(R.id.txtProvinsiReadDetailMhs);
            txtKabupatenReadDetailMhs = itemView.findViewById(R.id.txtKabupatenMhsReadDetailMhs);
            txtKecamatanReadDetailMhs = itemView.findViewById(R.id.txtKecamatanMhsReadDetailMhs);
            txtKelurahanReadDetailMhs = itemView.findViewById(R.id.txtKelurahanMhsReadDetailMhs);
            txtLatReadDetailMhs = itemView.findViewById(R.id.txtLatitudeReadDetailMhs);
            txtLNgReadDetailMhs = itemView.findViewById(R.id.txtLongtitudeReadDetailMhs);
            txtAlamatSekarangReadDetailMhs = itemView.findViewById(R.id.txtAlamatSekarangReadDetailMhs);
            txtLatAlamatSekarangReadDetailMhs = itemView.findViewById(R.id.txtLatAlamatSekarangReadDetailMhs);
            txtLngAlamatSekarangReadDetailMhs = itemView.findViewById(R.id.txtLngAlamatSekarangReadDetailMhs);
        }
    }
}
