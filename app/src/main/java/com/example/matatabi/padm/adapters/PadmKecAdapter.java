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
import com.example.matatabi.padm.activities.DataPadmKelActivity;
import com.example.matatabi.padm.model.PadmKecamatan;

import java.util.List;

public class PadmKecAdapter extends RecyclerView.Adapter<PadmKecAdapter.PadmKecViewHolder> {
    private Context context;
    private List<PadmKecamatan> padmKecamatanList;

    public PadmKecAdapter(Context context, List<PadmKecamatan> padmKecamatanList) {
        this.context = context;
        this.padmKecamatanList = padmKecamatanList;
    }

    @NonNull
    @Override
    public PadmKecViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_recyclerview_read_padm_kec, viewGroup, false);
        return new PadmKecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PadmKecViewHolder padmKecViewHolder, int i) {
        PadmKecamatan padmKecamatan = padmKecamatanList.get(i);
        padmKecViewHolder.txtKecamatanPadm.setText(padmKecamatan.getNm_kecamatan());
        padmKecViewHolder.txtKecamatanPadmKab.setText(padmKecamatan.getNm_kabupaten());
        padmKecViewHolder.txtTotalPadmKec.setText(padmKecamatan.getTotal_mahasiswa());
    }

    @Override
    public int getItemCount() {
        return padmKecamatanList.size();
    }

    class PadmKecViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtKecamatanPadm, txtTotalPadmKec, txtKecamatanPadmKab;

        PadmKecViewHolder(@NonNull View itemView) {
            super(itemView);
            txtKecamatanPadm = itemView.findViewById(R.id.txtKecamatanPadm);
            txtTotalPadmKec = itemView.findViewById(R.id.txtTotalPadm_kec);
            txtKecamatanPadmKab = itemView.findViewById(R.id.txtKecamatanPadmKab);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String nm_kecamatan = txtKecamatanPadm.getText().toString();

            Intent intent = new Intent(context, DataPadmKelActivity.class);
            intent.putExtra("nm_kecamatan", nm_kecamatan);
            context.startActivity(intent);
        }
    }
}
