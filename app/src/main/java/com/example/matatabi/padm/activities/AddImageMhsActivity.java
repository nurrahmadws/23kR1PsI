package com.example.matatabi.padm.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.matatabi.padm.R;
import com.example.matatabi.padm.api.RetrofitClient;
import com.example.matatabi.padm.model.Value;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddImageMhsActivity extends AppCompatActivity {

    //    FOR IMAGE
    private static final int PICK_FROM_GALLERY = 1;
    private ImageView img;
    private static final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    String imagePath;
    public static final String mypreference = "mypref";
    public static final String USERNAME = "username";
    SharedPreferences sharedPreferences;
    private EditText edtUsernameAddImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_mhs);

        img = findViewById(R.id.imgView_photo);

        edtUsernameAddImg = findViewById(R.id.edtUsernameAddImg);

        sharedPreferences = AddImageMhsActivity.this.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(USERNAME)){
            edtUsernameAddImg.setText(sharedPreferences.getString(USERNAME, ""));
        }

        Button btn_select_photo = findViewById(R.id.btn_select_photo);
        btn_select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        Button btn_simpan_photo = findViewById(R.id.btn_simpan_photo);
        btn_simpan_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanFoto();
            }
        });
    }

    //  FOR IMAGE
    private void selectImage(){
        if (ActivityCompat.checkSelfPermission(AddImageMhsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddImageMhsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
        }else {
//            Intent intent = new Intent();
//            intent.setType("image/");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(intent, IMG_REQUEST);
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PICK_FROM_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                }else {
                    Toast.makeText(this, "Anda Belum Mendapatkan Izin untuk Mengakses Galeri ini", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMG_REQUEST && resultCode==RESULT_OK &&data!=null){
            Uri path = data.getData();
            imagePath = getRealPathFromUri(path);

        }
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private String getRealPathFromUri(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_id = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_id);
        cursor.close();
        return result;
    }
//    END FOR IMAGE

    private void simpanFoto(){
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        String username = edtUsernameAddImg.getText().toString();

        RequestBody usernamer = RequestBody.create(MediaType.parse("text/plain"), username);

        Call<Value> call = RetrofitClient.getmInstance().getApi().InsertImage(usernamer, image);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMessage();
                switch (value){
                    case "1":
                        Toast.makeText(AddImageMhsActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "0":
                        Toast.makeText(AddImageMhsActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "2":
                        Toast.makeText(AddImageMhsActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "3":
                        Toast.makeText(AddImageMhsActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "4":
                        Toast.makeText(AddImageMhsActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "5":
                        Toast.makeText(AddImageMhsActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                Toast.makeText(AddImageMhsActivity.this, "Gagal Meresponn", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
