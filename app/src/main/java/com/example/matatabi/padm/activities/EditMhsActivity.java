package com.example.matatabi.padm.activities;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.matatabi.padm.R;

public class EditMhsActivity extends AppCompatActivity {
    private CardView cardeEditPribadiAk, cardEditAsalDa, cardEditStatAl, CardEditFoto;
    private EditText edtGetNim, edtGetUsername, edtGetNik, edtGetNama, edtGetTempatLahir, edtGetTglLahir, edtGetNoHp, edtGetEmail, edtGetFakultas, edtGetProdi, edtGetProvinsi;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mhs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardeEditPribadiAk = findViewById(R.id.cardeEditPribadiAk);
        cardEditAsalDa = findViewById(R.id.cardEditAsalDa);
        cardEditStatAl = findViewById(R.id.cardEditStatAl);
        CardEditFoto = findViewById(R.id.CardEditFoto);

        edtGetNim = findViewById(R.id.edtGetNim);
        edtGetUsername = findViewById(R.id.edtGetUsername);
        edtGetNik = findViewById(R.id.edtGetNik);
        edtGetNama = findViewById(R.id.edtGetNama);
        edtGetTempatLahir = findViewById(R.id.edtGetTempatLahir);
        edtGetTglLahir = findViewById(R.id.edtGetTglLahir);
        edtGetNoHp = findViewById(R.id.edtGetNoHp);
        edtGetEmail = findViewById(R.id.edtGetEmail);
        edtGetFakultas = findViewById(R.id.edtGetFakultas);
        edtGetProdi = findViewById(R.id.edtGetProdi);
        edtGetProvinsi = findViewById(R.id.edtGetProvinsi);

        Intent intent = getIntent();
        final String nim = intent.getStringExtra("nim");
        edtGetNim.setText(nim);

        final String username = intent.getStringExtra("username");
        edtGetUsername.setText(username);

        final String nik = intent.getStringExtra("nik");
        edtGetNik.setText(nik);

        final String nama = intent.getStringExtra("nama");
        edtGetNama.setText(nama);

        final String tempat_lahir = intent.getStringExtra("tempat_lahir");
        edtGetTempatLahir.setText(tempat_lahir);

        final String tgl_lahir = intent.getStringExtra("tgl_lahir");
        edtGetTglLahir.setText(tgl_lahir);

        final String no_hp = intent.getStringExtra("no_hp");
        edtGetNoHp.setText(no_hp);

        final String email = intent.getStringExtra("email");
        edtGetEmail.setText(email);

        final String fakultas = intent.getStringExtra("fakultas");
        edtGetFakultas.setText(fakultas);

        final String prodi = intent.getStringExtra("prodi");
        edtGetProdi.setText(prodi);

        final String provinsi = intent.getStringExtra("provinsi");
        edtGetProvinsi.setText(provinsi);

        cardeEditPribadiAk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMhsActivity.this, EditMhsDataPribadiActivity.class);
                intent.putExtra("nim", nim);
                intent.putExtra("username", username);
                intent.putExtra("nik", nik);
                intent.putExtra("nama", nama);
                intent.putExtra("tempat_lahir", tempat_lahir);
                intent.putExtra("tgl_lahir", tgl_lahir);
                intent.putExtra("no_hp", no_hp);
                intent.putExtra("email", email);
                intent.putExtra("fakultas", fakultas);
                intent.putExtra("prodi", prodi);
                intent.putExtra("provinsi", provinsi);
                startActivity(intent);
            }
        });

        cardEditAsalDa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMhsActivity.this, EditMhsAsalDaerahActivity.class);
                intent.putExtra("nim", nim);
                startActivity(intent);
            }
        });

        cardEditStatAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMhsActivity.this, GetCoordinateManuallyEditActivity.class);
                intent.putExtra("nim", nim);
                startActivity(intent);
            }
        });

        CardEditFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EditMhsActivity.this, EditMhsPhotoActivity.class);
                intent1.putExtra("nim", nim);
                startActivity(intent1);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
