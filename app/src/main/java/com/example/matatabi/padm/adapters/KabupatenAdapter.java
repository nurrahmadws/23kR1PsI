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
import com.example.matatabi.padm.activities.EditKabupatenActivity;
import com.example.matatabi.padm.model.Kabupaten;

import java.util.List;

public class KabupatenAdapter extends RecyclerView.Adapter<KabupatenAdapter.KabupatenViewHolder> {
    private Context mCo;
    private List<Kabupaten> kabupatenList;

    public KabupatenAdapter(Context mCo, List<Kabupaten> kabupatenList) {
        this.mCo = mCo;
        this.kabupatenList = kabupatenList;
    }

    @NonNull
    @Override
    public KabupatenViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCo).inflate(R.layout.admin_recyclerview_read_kabkot, viewGroup, false);
        return new KabupatenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KabupatenViewHolder kabupatenViewHolder, int i) {
        Kabupaten kabupaten = kabupatenList.get(i);
        kabupatenViewHolder.txtIdkabkot.setText(Integer.toString(kabupaten.getId_kabupaten()));
        kabupatenViewHolder.txtKabkot.setText(kabupaten.getNm_kabupaten());
    }

    @Override
    public int getItemCount() {
        return kabupatenList.size();
    }

    class KabupatenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtIdkabkot, txtKabkot;
        public KabupatenViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdkabkot = itemView.findViewById(R.id.txtIdkabkot);
            txtKabkot = itemView.findViewById(R.id.txtKabkot);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String id_kabupaten = txtIdkabkot.getText().toString();
            String nm_kabupaten = txtKabkot.getText().toString();

            Intent intent = new Intent(mCo, EditKabupatenActivity.class);
            intent.putExtra("id_kabupaten", id_kabupaten);
            intent.putExtra("nm_kabupaten", nm_kabupaten);
            mCo.startActivity(intent);

        }
    }

}
