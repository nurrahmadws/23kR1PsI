package com.example.matatabi.padm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.AddKabupatenActivity;
import com.example.matatabi.padm.activities.AddKecamatanActivity;
import com.example.matatabi.padm.activities.AddKelurahanActivity;
import com.example.matatabi.padm.activities.AddLatlngActivity;
import com.example.matatabi.padm.activities.AddUserActivity;

public class AdminQuickMenuFragment extends Fragment {
    private CardView cardAddUser, cardAddKab, cardAddKecl, CardAddKel, cardAddLatLng;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_quick_menu_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardAddUser = view.findViewById(R.id.cardAddUser);
        cardAddKab = view.findViewById(R.id.cardAddKab);
        cardAddKecl = view.findViewById(R.id.cardAddKec);
        CardAddKel = view.findViewById(R.id.CardAddKel);
        cardAddLatLng = view.findViewById(R.id.cardAddLatLng);

        cardAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddUserActivity.class);
                getActivity().startActivity(intent);
            }
        });

        cardAddKab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddKabupatenActivity.class);
                getActivity().startActivity(intent);
            }
        });

        cardAddKecl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddKecamatanActivity.class);
                getActivity().startActivity(intent);
            }
        });

        CardAddKel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddKelurahanActivity.class);
                getActivity().startActivity(intent);
            }
        });

        cardAddLatLng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddLatlngActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
