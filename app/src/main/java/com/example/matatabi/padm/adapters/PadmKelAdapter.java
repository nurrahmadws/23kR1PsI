package com.example.matatabi.padm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.model.PadmKelurahan;

import java.util.List;

public class PadmKelAdapter extends RecyclerView.Adapter<PadmKelAdapter.PadmKelViewHolder> {
    private Context mcc;
    private List<PadmKelurahan> padmKelurahanList;

    public PadmKelAdapter(Context mcc, List<PadmKelurahan> padmKelurahanList) {
        this.mcc = mcc;
        this.padmKelurahanList = padmKelurahanList;
    }

    @NonNull
    @Override
    public PadmKelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcc).inflate(R.layout.admin_recyclerview_read_padm_kel, viewGroup, false);
        return new PadmKelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PadmKelViewHolder padmKelViewHolder, int i) {
        PadmKelurahan padmKelurahan = padmKelurahanList.get(i);
        padmKelViewHolder.txtKelurahanPadm.setText(padmKelurahan.getNm_kelurahan());
        padmKelViewHolder.txtKelurahanPadmKec.setText(padmKelurahan.getNm_kecamatan());
        padmKelViewHolder.txt_total_padm_kel.setText(padmKelurahan.getTotal_mahasiswa());
    }

    @Override
    public int getItemCount() {
        return padmKelurahanList.size();
    }

    class PadmKelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtKelurahanPadm, txtKelurahanPadmKec, txt_total_padm_kel;

        PadmKelViewHolder(@NonNull View itemView) {
            super(itemView);
            txtKelurahanPadm = itemView.findViewById(R.id.txtKelurahanPadm);
            txtKelurahanPadmKec = itemView.findViewById(R.id.txtKelurahanPadmKec);
            txt_total_padm_kel = itemView.findViewById(R.id.txtTotalPadm_Kel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
