package com.example.callruntimepermissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class PickGalary extends AppCompatActivity {


    private static final int IMAGE_PICK_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_galary);


        Button button = findViewById(R.id.btns);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkSelf = ActivityCompat.checkSelfPermission(PickGalary.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (checkSelf != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PickGalary.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivity(galleryIntent);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   pickImageFromGalary();
                }else {
                    Toast.makeText(this, "denied ... !", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void pickImageFromGalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK&&requestCode==IMAGE_PICK_CODE){
            ImageView imageView = findViewById(R.id.imgas);
            imageView.setImageURI(data.getData());
        }
    }
}
