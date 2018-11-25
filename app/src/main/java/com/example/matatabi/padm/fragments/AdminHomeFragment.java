package com.example.matatabi.padm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.DataUserActivity;
import com.example.matatabi.padm.activities.SubMenuDataDaerahActivity;

public class AdminHomeFragment extends Fragment {
    private Button btn_data_user, btn_data_daerah, btn_data_mahasiswa, btn_ttg_aplikasi, btn_sebaran_mhs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_data_user = view.findViewById(R.id.btn_data_user);
        btn_data_daerah = view.findViewById(R.id.btn_data_daerah);
        btn_data_mahasiswa = view.findViewById(R.id.btn_data_mahasiswa);
        btn_sebaran_mhs = view.findViewById(R.id.btn_data_sebaran_mhs);
        btn_ttg_aplikasi = view.findViewById(R.id.btn_ttg_aplikasi);

        btn_data_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), DataUserActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btn_data_daerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubMenuDataDaerahActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
