package com.example.matatabi.padm.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.EditMhsActivity;
import com.example.matatabi.padm.activities.MahasiswaActivity;
import com.example.matatabi.padm.activities.ShowMapMhsActivity;
import com.example.matatabi.padm.activities.ShowMarkerMhsAlamatActivity;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.display_image.ImageLoader;
import com.example.matatabi.padm.model.Value;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import com.example.matatabi.padm.model.Mahasiswa;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        mahasiswaViewHolder.txtUsernameMhs.setText(mahasiswa.getUsername());
        mahasiswaViewHolder.txtNik.setText(mahasiswa.getNik());
        mahasiswaViewHolder.txtNama.setText(mahasiswa.getNama());
        mahasiswaViewHolder.txtTempatLahir.setText(mahasiswa.getTempat_lahir());
        mahasiswaViewHolder.txtTglLahir.setText(mahasiswa.getTgl_lahir());
        mahasiswaViewHolder.txtJk.setText(mahasiswa.getJk());
        mahasiswaViewHolder.txtNoHp.setText(mahasiswa.getNo_hp());
        mahasiswaViewHolder.txtEmail.setText(mahasiswa.getEmail());
        mahasiswaViewHolder.txtFakultas.setText(mahasiswa.getFakultas());
        mahasiswaViewHolder.txtProdi.setText(mahasiswa.getProdi());
        mahasiswaViewHolder.txtAngkatan.setText(mahasiswa.getAngkatan());
        mahasiswaViewHolder.txtKelas.setText(mahasiswa.getKelas());
        mahasiswaViewHolder.txtProvinsi.setText(mahasiswa.getProvinsi());
        mahasiswaViewHolder.txtKabupaten.setText(mahasiswa.getNm_kabupaten());
        mahasiswaViewHolder.txtKecamatan.setText(mahasiswa.getNm_kecamatan());
        mahasiswaViewHolder.txtKelurahan.setText(mahasiswa.getNm_kelurahan());
        mahasiswaViewHolder.txtLat.setText(mahasiswa.getNm_lat());
        mahasiswaViewHolder.txtLNg.setText(mahasiswa.getNm_lng());
        mahasiswaViewHolder.txtAlamatSekarang.setText(mahasiswa.getAlamat_sekarang());
        mahasiswaViewHolder.txtLatAlamatSekarang.setText(mahasiswa.getLat_alamat_sekarang());
        mahasiswaViewHolder.txtLngAlamatSekarang.setText(mahasiswa.getLng_alamat_sekarang());
        Picasso.with(context).load("http://192.168.43.207/api/mahasiswa/"+mahasiswa.getImage()).resize(354, 472)
                .centerCrop().skipMemoryCache().into(mahasiswaViewHolder.imgView_photo_Show);

//            int loader = R.drawable.ic_developer;
//        String image_url = "http://192.168.43.207/api/mahasiswa/"+mahasiswa.getImage();
//        ImageLoader imageLoader = new ImageLoader(context.getApplicationContext());
//        imageLoader.DisplayImage(image_url, loader, mahasiswaViewHolder.imgView_photo_Show);
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNim, txtUsernameMhs, txtNik, txtNama, txtJk, txtTempatLahir, txtTglLahir, txtNoHp, txtEmail,
                txtFakultas, txtProdi, txtAngkatan, txtKelas, txtProvinsi, txtKabupaten, txtKecamatan, txtKelurahan, txtLat, txtLNg,
                txtAlamatSekarang, txtLatAlamatSekarang, txtLngAlamatSekarang;
        ImageView imgView_photo_Show;
        FloatingActionMenu materialDesignFAM;
        FloatingActionButton floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;
        static final String mypreference = "mypref";
        static final String USERNAME = "username";
        SharedPreferences sharedPreferences;


        MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNim = itemView.findViewById(R.id.txtNim);
            txtUsernameMhs = itemView.findViewById(R.id.txtUsernameMhs);
            txtNik = itemView.findViewById(R.id.txtNik);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtJk = itemView.findViewById(R.id.txtjk);
            txtTempatLahir = itemView.findViewById(R.id.txtTempatLahir);
            txtTglLahir = itemView.findViewById(R.id.txtTglLahir);
            txtNoHp = itemView.findViewById(R.id.txtNoHp);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtFakultas = itemView.findViewById(R.id.txtFakultas);
            txtProdi = itemView.findViewById(R.id.txtProdi);
            txtAngkatan = itemView.findViewById(R.id.txtAngkatan);
            txtKelas = itemView.findViewById(R.id.txtKelas);
            txtProvinsi = itemView.findViewById(R.id.txtProvinsi);
            txtKabupaten = itemView.findViewById(R.id.txtKabupatenMhs);
            txtKecamatan = itemView.findViewById(R.id.txtKecamatanMhs);
            txtKelurahan = itemView.findViewById(R.id.txtKelurahanMhs);
            txtLat = itemView.findViewById(R.id.txtLatitude);
            txtLNg = itemView.findViewById(R.id.txtLongtitude);
            txtAlamatSekarang = itemView.findViewById(R.id.txtAlamatSekarang);
            txtLatAlamatSekarang = itemView.findViewById(R.id.txtLatAlamatSekarang);
            txtLngAlamatSekarang = itemView.findViewById(R.id.txtLngAlamatSekarang);
            imgView_photo_Show = itemView.findViewById(R.id.imgView_photo_Show);


            materialDesignFAM = itemView.findViewById(R.id.material_design_android_floating_action_menu);
            floatingActionButton2 = itemView.findViewById(R.id.fab_show_map_mhs);
            floatingActionButton3 = itemView.findViewById(R.id.fab_edit_mhs);
            floatingActionButton4 = itemView.findViewById(R.id.fab_delete_mhs);
            floatingActionButton5 = itemView.findViewById(R.id.fab_show_map_mhs_alamat_sekarang);

            sharedPreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(USERNAME)) {
                txtUsernameMhs.setText(sharedPreferences.getString(USERNAME, ""));
            }

            floatingActionButton5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowMarkerMhsAlamatActivity.class);
                    String nim = txtNim.getText().toString();
                    intent.putExtra("nim", nim);
                    context.startActivity(intent);
                }
            });

            floatingActionButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, ShowMapMhsActivity.class));
                }
            });
            floatingActionButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nim = txtNim.getText().toString();
                    String username = txtUsernameMhs.getText().toString();
                    String nik = txtNik.getText().toString();
                    String nama = txtNama.getText().toString();
                    String jk = txtJk.getText().toString();
                    String tempat_lahir = txtTempatLahir.getText().toString();
                    String tgl_lahir = txtTglLahir.getText().toString();
                    String no_hp = txtNoHp.getText().toString();
                    String email = txtEmail.getText().toString();
                    String fakultas = txtFakultas.getText().toString();
                    String prodi = txtProdi.getText().toString();
                    String angkatan = txtAngkatan.getText().toString();
                    String kelas = txtKelas.getText().toString();
                    String provinsi = txtProvinsi.getText().toString();
                    String nm_kabupaten = txtKabupaten.getText().toString();
                    String nm_kecamatan = txtKecamatan.getText().toString();
                    String nm_kelurahan = txtKelurahan.getText().toString();
                    String nm_lat = txtLat.getText().toString();
                    String nm_lng = txtLNg.getText().toString();
                    String alamat_sekarang = txtAlamatSekarang.getText().toString();
                    String lat_alamat_sekarang = txtLatAlamatSekarang.getText().toString();
                    String lng_alamat_sekaranga = txtLngAlamatSekarang.getText().toString();

                    Intent intent = new Intent(context, EditMhsActivity.class);
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
                    intent.putExtra("alamat_sekarang",alamat_sekarang);
                    intent.putExtra("lat_alamat_sekarang", lat_alamat_sekarang);
                    intent.putExtra("lng_alamat_sekarang", lng_alamat_sekaranga);
                    context.startActivity(intent);
                }
            });
            if (!mahasiswaList.isEmpty()) {
                floatingActionButton2.setEnabled(true);
                floatingActionButton2.setVisibility(View.VISIBLE);
                floatingActionButton3.setEnabled(true);
                floatingActionButton3.setVisibility(View.VISIBLE);
                floatingActionButton4.setEnabled(true);
                floatingActionButton4.setVisibility(View.VISIBLE);
            } else {
                floatingActionButton2.setEnabled(false);
                floatingActionButton2.setVisibility(View.INVISIBLE);
                floatingActionButton3.setEnabled(false);
                floatingActionButton3.setVisibility(View.INVISIBLE);
                floatingActionButton4.setEnabled(false);
                floatingActionButton4.setVisibility(View.INVISIBLE);
            }
            floatingActionButton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder al = new AlertDialog.Builder(context);
                    al.setTitle("Peringatan!");
                    al.setMessage("Yakin Ingin Menghapus Data Ini?");
                    al.setCancelable(false);
                    al.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String username = txtUsernameMhs.getText().toString();
                            Call<Value> call = RetrofitClient.getmInstance().getApi().deleteMahasiswa(username);
                            call.enqueue(new Callback<Value>() {
                                @Override
                                public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                                    String value = response.body().getValue();
                                    String message = response.body().getMessage();
                                    if (value.equals("1")) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        context.startActivity(new Intent(context, MahasiswaActivity.class));
                                    } else {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {

                                }
                            });
                        }
                    });
                    al.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = al.create();
                    dialog.show();
                }
            });
        }
    }

}
