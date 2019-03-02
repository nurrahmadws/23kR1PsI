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
import com.example.matatabi.padm.activities.DetailMhsActivity;
import com.example.matatabi.padm.model.Mahasiswa;

import java.util.List;

public class AllMahasiswaAdapter extends RecyclerView.Adapter<AllMahasiswaAdapter.AllMahasiswaViewHolder> {
    private Context context;
    private List<Mahasiswa> mahasiswaList;

    public AllMahasiswaAdapter(Context context, List<Mahasiswa> mahasiswaList) {
        this.context = context;
        this.mahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public AllMahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.mhs_recyclerview_read_data_mhs, viewGroup, false);
        return new AllMahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMahasiswaViewHolder allMahasiswaViewHolder, int i) {
        Mahasiswa mahasiswa = mahasiswaList.get(i);
        allMahasiswaViewHolder.txtNimReadAllMhs.setText(mahasiswa.getNim());
        allMahasiswaViewHolder.txtUsernameReasAllMhs.setText(mahasiswa.getUsername());
        allMahasiswaViewHolder.txtNikReadAllMhs.setText(mahasiswa.getNik());
        allMahasiswaViewHolder.txtNamaReadAllMhs.setText(mahasiswa.getNama());
        allMahasiswaViewHolder.txtJkReadAllMhs.setText(mahasiswa.getJk());
        allMahasiswaViewHolder.txtTempatLahirReadAllMhs.setText(mahasiswa.getTempat_lahir());
        allMahasiswaViewHolder.txtTglLahirReadAllMhs.setText(mahasiswa.getTgl_lahir());
        allMahasiswaViewHolder.txtNoHpReadAllMhs.setText(mahasiswa.getNo_hp());
        allMahasiswaViewHolder.txtEmailReadAllMhs.setText(mahasiswa.getEmail());
        allMahasiswaViewHolder.txtFakultasReadAllMhs.setText(mahasiswa.getFakultas());
        allMahasiswaViewHolder.txtProdiReadAllMhs.setText(mahasiswa.getProdi());
        allMahasiswaViewHolder.txtAngkatanReadAllMhs.setText(mahasiswa.getAngkatan());
        allMahasiswaViewHolder.txtKelasReadAllMhs.setText(mahasiswa.getKelas());
        allMahasiswaViewHolder.txtProvinsiReadAllMhs.setText(mahasiswa.getProvinsi());
        allMahasiswaViewHolder.txtKabupatenReadAllMhs.setText(mahasiswa.getNm_kabupaten());
        allMahasiswaViewHolder.txtKecamatanReadAllMhs.setText(mahasiswa.getNm_kecamatan());
        allMahasiswaViewHolder.txtKelurahanReadAllMhs.setText(mahasiswa.getNm_kelurahan());
        allMahasiswaViewHolder.txtLatReadAllMhs.setText(mahasiswa.getNm_lat());
        allMahasiswaViewHolder.txtLNgReadAllMhs.setText(mahasiswa.getNm_lng());
        allMahasiswaViewHolder.txtAlamatSekarangReadAllMhs.setText(mahasiswa.getAlamat_sekarang());
        allMahasiswaViewHolder.txtLatAlamatSekarangReadAllMhs.setText(mahasiswa.getLat_alamat_sekarang());
        allMahasiswaViewHolder.txtLngAlamatSekarangReadAllMhs.setText(mahasiswa.getLng_alamat_sekarang());
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    class AllMahasiswaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtNimReadAllMhs, txtUsernameReasAllMhs, txtNikReadAllMhs, txtNamaReadAllMhs, txtJkReadAllMhs, txtTempatLahirReadAllMhs, txtTglLahirReadAllMhs,
                txtNoHpReadAllMhs, txtEmailReadAllMhs, txtFakultasReadAllMhs, txtProdiReadAllMhs, txtAngkatanReadAllMhs, txtKelasReadAllMhs,
                txtProvinsiReadAllMhs, txtKabupatenReadAllMhs, txtKecamatanReadAllMhs, txtKelurahanReadAllMhs, txtLatReadAllMhs, txtLNgReadAllMhs,
                txtAlamatSekarangReadAllMhs, txtLatAlamatSekarangReadAllMhs, txtLngAlamatSekarangReadAllMhs;
        AllMahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNimReadAllMhs = itemView.findViewById(R.id.txtNimReadAllMhs);
            txtUsernameReasAllMhs = itemView.findViewById(R.id.txtUsernameReadAllMhs);
            txtNikReadAllMhs = itemView.findViewById(R.id.txtNikReadAllMhs);
            txtNamaReadAllMhs = itemView.findViewById(R.id.txtNamaReadAllMhs);
            txtJkReadAllMhs = itemView.findViewById(R.id.txtjkReadAllMhs);
            txtTempatLahirReadAllMhs = itemView.findViewById(R.id.txtTempatLahirReadAllMhs);
            txtTglLahirReadAllMhs = itemView.findViewById(R.id.txtTglLahirReadAllMhs);
            txtNoHpReadAllMhs = itemView.findViewById(R.id.txtNoHpReadAllMhs);
            txtEmailReadAllMhs = itemView.findViewById(R.id.txtEmailReadAllMhs);
            txtFakultasReadAllMhs = itemView.findViewById(R.id.txtFakultasReadAllMhs);
            txtProdiReadAllMhs = itemView.findViewById(R.id.txtProdiReadAllMhs);
            txtAngkatanReadAllMhs = itemView.findViewById(R.id.txtAngkatanReadAllMhs);
            txtKelasReadAllMhs = itemView.findViewById(R.id.txtKelasReadAllMhs);
            txtProvinsiReadAllMhs = itemView.findViewById(R.id.txtProvinsiReadAllMhs);
            txtKabupatenReadAllMhs = itemView.findViewById(R.id.txtKabupatenReadAllMhs);
            txtKecamatanReadAllMhs = itemView.findViewById(R.id.txtKecamatanReadAllMhs);
            txtKelurahanReadAllMhs = itemView.findViewById(R.id.txtKelurahanReadAllMhs);
            txtLatReadAllMhs = itemView.findViewById(R.id.txtLatReadAllMhs);
            txtLNgReadAllMhs = itemView.findViewById(R.id.txtLngReadAllMhs);
            txtAlamatSekarangReadAllMhs = itemView.findViewById(R.id.txtAlamatSekarangReadAllMhs);
            txtLatAlamatSekarangReadAllMhs = itemView.findViewById(R.id.txtLatAlamatSekarangReadAllMhs);
            txtLngAlamatSekarangReadAllMhs = itemView.findViewById(R.id.txtLngAlamatSekarangReadAllMhs);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String nim = txtNimReadAllMhs.getText().toString();
            String username = txtUsernameReasAllMhs.getText().toString();
            String nik = txtNikReadAllMhs.getText().toString();
            String nama = txtNamaReadAllMhs.getText().toString();
            String jk = txtJkReadAllMhs.getText().toString();
            String tempat_lahir = txtTempatLahirReadAllMhs.getText().toString();
            String tgl_lahir = txtTglLahirReadAllMhs.getText().toString();
            String no_hp = txtNoHpReadAllMhs.getText().toString();
            String email = txtEmailReadAllMhs.getText().toString();
            String fakultas = txtFakultasReadAllMhs.getText().toString();
            String prodi = txtProdiReadAllMhs.getText().toString();
            String angkatan = txtAngkatanReadAllMhs.getText().toString();
            String kelas = txtKelasReadAllMhs.getText().toString();
            String provinsi = txtProvinsiReadAllMhs.getText().toString();
            String nm_kabupaten = txtKabupatenReadAllMhs.getText().toString();
            String nm_kecamatan = txtKecamatanReadAllMhs.getText().toString();
            String nm_kelurahan = txtKelurahanReadAllMhs.getText().toString();
            String nm_lat = txtLatReadAllMhs.getText().toString();
            String nm_lng = txtLNgReadAllMhs.getText().toString();
            String alamat_sekarang = txtAlamatSekarangReadAllMhs.getText().toString();
            String lat_alamat_sekarang = txtLatAlamatSekarangReadAllMhs.getText().toString();
            String lng_alamat_sekarang = txtLngAlamatSekarangReadAllMhs.getText().toString();

            Intent intent  = new Intent(context, DetailMhsActivity.class);
            intent.putExtra("nim", nim);
            intent.putExtra("username", username);
            intent.putExtra("nik", nik);
            intent.putExtra("nama", nama);
            intent.putExtra("jk", jk);
            intent.putExtra("tempat_lahir", tempat_lahir);
            intent.putExtra("tgl_lahir", tgl_lahir);
            intent.putExtra("no_hp", no_hp);
            intent.putExtra("email", email);
            intent.putExtra("fakultas", fakultas);
            intent.putExtra("prodi", prodi);
            intent.putExtra("angkatan", angkatan);
            intent.putExtra("kelas", kelas);
            intent.putExtra("provinsi", provinsi);
            intent.putExtra("nm_kabupaten", nm_kabupaten);
            intent.putExtra("nm_kecamatan", nm_kecamatan);
            intent.putExtra("nm_kelurahan", nm_kelurahan);
            intent.putExtra("nm_lat", nm_lat);
            intent.putExtra("nm_lng", nm_lng);
            intent.putExtra("alamat_sekarang", alamat_sekarang);
            intent.putExtra("lat_alamat_sekarang", lat_alamat_sekarang);
            intent.putExtra("lng_alamat_sekarang", lng_alamat_sekarang);
            context.startActivity(intent);
        }
    }
}
