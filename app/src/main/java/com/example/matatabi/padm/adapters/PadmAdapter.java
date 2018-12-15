package com.example.matatabi.padm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.model.Padm;
import java.util.List;

public class PadmAdapter extends RecyclerView.Adapter<PadmAdapter.PadmViewHolder> {
    private Context mcu;
    private List<Padm> padmList;

    public PadmAdapter(Context mcu, List<Padm> padmList) {
        this.mcu = mcu;
        this.padmList = padmList;
    }

    @NonNull
    @Override
    public PadmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcu).inflate(R.layout.admin_recyclerview_readpadm, viewGroup, false);
        return new PadmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PadmViewHolder padmViewHolder, int i) {
        Padm padm = padmList.get(i);
        padmViewHolder.txtKabupatenPadm.setText(padm.getNm_kabupaten());
        padmViewHolder.txtTotalPadm.setText(padm.getTotal_mahasiswa());

    }

    @Override
    public int getItemCount() {
        return padmList.size();
    }

    class PadmViewHolder extends RecyclerView.ViewHolder{
        TextView txtKabupatenPadm, txtTotalPadm;
        PadmViewHolder(@NonNull View itemView) {
            super(itemView);
            txtKabupatenPadm = itemView.findViewById(R.id.txtKabupatenPadm);
            txtTotalPadm = itemView.findViewById(R.id.txtTotalPadm);
        }
    }
}
