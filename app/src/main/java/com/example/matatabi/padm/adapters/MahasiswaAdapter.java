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
import android.widget.TextView;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.AddMahasiswaActivity;
import com.example.matatabi.padm.activities.EditMhsActivity;
import com.example.matatabi.padm.activities.OnBoardingScreenMhs;
import com.example.matatabi.padm.activities.ShowMapMhsActivity;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.fragments.MhsBiodatakuFragment;
import com.example.matatabi.padm.model.Value;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;
import com.example.matatabi.padm.model.Mahasiswa;

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
        mahasiswaViewHolder.txtNama.setText(mahasiswa.getNama());
        mahasiswaViewHolder.txtUsernameMhs.setText(mahasiswa.getUsername());
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
        TextView txtNim, txtUsernameMhs, txtNama, txtJk, txtFakultas, txtProdi, txtAngkatan, txtProvinsi, txtKabupaten, txtKecamatan, txtKelurahan, txtLat, txtLNg;
        FloatingActionMenu materialDesignFAM;
        FloatingActionButton floatingActionButton2, floatingActionButton3, floatingActionButton4;
        static final String mypreference = "mypref";
        static final String USERNAME = "username";
        SharedPreferences sharedPreferences;
        MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNim = itemView.findViewById(R.id.txtNim);
            txtUsernameMhs = itemView.findViewById(R.id.txtUsernameMhs);
            txtNama = itemView.findViewById(R.id.txtNama);
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

            materialDesignFAM = itemView.findViewById(R.id.material_design_android_floating_action_menu);
            floatingActionButton2 = itemView.findViewById(R.id.fab_show_map_mhs);
            floatingActionButton3 = itemView.findViewById(R.id.fab_edit_mhs);
            floatingActionButton4 = itemView.findViewById(R.id.fab_delete_mhs);

            sharedPreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(USERNAME)){
                txtUsernameMhs.setText(sharedPreferences.getString(USERNAME, ""));
            }
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
                    String nama = txtNama.getText().toString();
                    String jk = txtJk.getText().toString();
                    String fakultas = txtFakultas.getText().toString();
                    String prodi = txtProdi.getText().toString();
                    String angkatan = txtAngkatan.getText().toString();
                    String provinsi = txtProvinsi.getText().toString();
                    String nm_kabupaten = txtKabupaten.getText().toString();
                    String nm_kecamatan = txtKecamatan.getText().toString();
                    String nm_kelurahan = txtKelurahan.getText().toString();
                    String nm_lat = txtLat.getText().toString();
                    String nm_lng = txtLNg.getText().toString();

                    Intent intent = new Intent(context, EditMhsActivity.class);
                    intent.putExtra("nim", nim);
                    intent.putExtra("username", username);
                    intent.putExtra("nama", nama);
                    intent.putExtra("jk", jk);
                    intent.putExtra("fakultas", fakultas);
                    intent.putExtra("prodi", prodi);
                    intent.putExtra("angkatan", angkatan);
                    intent.putExtra("provinsi", provinsi);
                    intent.putExtra("nm_kabupaten", nm_kabupaten);
                    intent.putExtra("nm_kecamatan", nm_kecamatan);
                    intent.putExtra("nm_kelurahan", nm_kelurahan);
                    intent.putExtra("nm_lat", nm_lat);
                    intent.putExtra("nm_lng", nm_lng);
                    context.startActivity(intent);
                }
            });
            if (!mahasiswaList.isEmpty()){
                floatingActionButton2.setEnabled(true);
                floatingActionButton2.setVisibility(View.VISIBLE);
                floatingActionButton3.setEnabled(true);
                floatingActionButton3.setVisibility(View.VISIBLE);
                floatingActionButton4.setEnabled(true);
                floatingActionButton4.setVisibility(View.VISIBLE);
            }else {
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
                            String nim = txtNim.getText().toString();
                            Call<Value> call = RetrofitClient.getmInstance().getApi().deleteMahasiswa(nim);
                            call.enqueue(new Callback<Value>() {
                                @Override
                                public void onResponse(Call<Value> call, Response<Value> response) {
                                    String value = response.body().getValue();
                                    String message = response.body().getMessage();
                                    if (value.equals("1")) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        context.startActivity(new Intent(context, OnBoardingScreenMhs.class));
                                    } else {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Value> call, Throwable t) {

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
