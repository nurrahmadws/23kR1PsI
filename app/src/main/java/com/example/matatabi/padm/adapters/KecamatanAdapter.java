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
import com.example.matatabi.padm.activities.EditKecamatanActivity;
import com.example.matatabi.padm.model.Kecamatan;

import java.util.List;

public class KecamatanAdapter extends RecyclerView.Adapter<KecamatanAdapter.KecamatanViewHolder> {
    private Context m;
    private List<Kecamatan> kecamatanList;

    public KecamatanAdapter(Context m, List<Kecamatan> kecamatanList) {
        this.m = m;
        this.kecamatanList = kecamatanList;
    }

    @NonNull
    @Override
    public KecamatanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(m).inflate(R.layout.admin_recyclerview_read_kecamatan, viewGroup, false);
        return new KecamatanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KecamatanViewHolder kecamatanViewHolder, int i) {
        Kecamatan kecamatan = kecamatanList.get(i);
        kecamatanViewHolder.txtIdkecamatan.setText(Integer.toString(kecamatan.getId_kecamatan()));
        kecamatanViewHolder.txtKabkotKec.setText(kecamatan.getNm_kabupaten());
        kecamatanViewHolder.txtKecamatan.setText(kecamatan.getNm_kecamatan());
    }

    @Override
    public int getItemCount() {
        return kecamatanList.size();
    }

    class KecamatanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtIdkecamatan, txtKabkotKec, txtKecamatan;

        public KecamatanViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdkecamatan = itemView.findViewById(R.id.txtIdkecamatan);
            txtKabkotKec = itemView.findViewById(R.id.txtKabkotKec);
            txtKecamatan = itemView.findViewById(R.id.txtKecamatan);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String id_kecamatan = txtIdkecamatan.getText().toString();
            String nm_kabupaten = txtKabkotKec.getText().toString();
            String nm_kecamatan = txtKecamatan.getText().toString();

            Intent intent = new Intent(m, EditKecamatanActivity.class);
            intent.putExtra("id_kecamatan", id_kecamatan);
            intent.putExtra("nm_kabupaten", nm_kabupaten);
            intent.putExtra("nm_kecamatan", nm_kecamatan);
            m.startActivity(intent);
        }
    }
}
