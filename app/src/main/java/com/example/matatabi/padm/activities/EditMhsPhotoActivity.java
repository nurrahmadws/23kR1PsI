package com.example.matatabi.padm.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Value;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMhsPhotoActivity extends AppCompatActivity {

    //    FOR IMAGE
    private static final int PICK_FROM_GALLERY = 1;
    private ImageView img;
    private static final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    Button btn_get_photo, btn_upload_photo;
//    END OF IMAGE

    EditText edtNimPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mhs_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNimPhoto = findViewById(R.id.edtNimPhoto);
        img = findViewById(R.id.imgEditMhs);

        Intent intent = getIntent();
        String nim = intent.getStringExtra("nim");
        edtNimPhoto.setText(nim);

        btn_get_photo = findViewById(R.id.btn_get_photo);
        btn_upload_photo = findViewById(R.id.btn_upload_photo);

        btn_get_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditMhsPhotoActivity.this);
                dialog.setTitle("PEMBERITAHUAN!").setMessage("Pilih Open Gallery Jika Anda Memiliki Foto \nPilih Default Foto Jika Anda belum Memiliki Foto")
                        .setCancelable(false);
                dialog.setPositiveButton("Open Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectImage();
                    }
                });
                dialog.setNegativeButton("Default Foto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isImageNull();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        btn_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData();
            }
        });

    }

    //    FOR IMAGE
    private void selectImage(){
        if (ActivityCompat.checkSelfPermission(EditMhsPhotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(EditMhsPhotoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
        }else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
        }
    }

    private void isImageNull(){
        Uri uri = Uri.parse("android.resource://"+this.getPackageName()+"/drawable/default_photo");
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            img.setImageBitmap(bitmap);
            img.setVisibility(View.VISIBLE);
            btn_get_photo.setEnabled(false);
            btn_upload_photo.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==IMG_REQUEST && resultCode==RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                img.setImageBitmap(bitmap);
                img.setVisibility(View.VISIBLE);
                btn_get_photo.setEnabled(false);
                btn_upload_photo.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] imgByt = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByt, Base64.DEFAULT);
    }
    //    END OF IMAGE

    private void editData(){
        final ProgressDialog progressDialog = new ProgressDialog(EditMhsPhotoActivity.this);
        progressDialog.setMessage("Mengubah Data...");
        progressDialog.show();

        String nim = edtNimPhoto.getText().toString();
        String image = imageToString();

        Call<Value> call = RetrofitClient.getmInstance().getApi().editMhsPhoto(nim, image);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                progressDialog.dismiss();
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")){
                    Toast.makeText(EditMhsPhotoActivity.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditMhsPhotoActivity.this, MahasiswaActivity.class));
                }else {
                    Toast.makeText(EditMhsPhotoActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditMhsPhotoActivity.this, "Gagal", Toast.LENGTH_LONG).show();
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
