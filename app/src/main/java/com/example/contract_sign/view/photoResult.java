package com.example.contract_sign.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.contract_sign.R;

import java.io.File;

public class photoResult extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_result);

        ImageView imageResult = findViewById(R.id.imageResult);
        Button StartMove = findViewById(R.id.startMove);

        Intent getIntent = getIntent();
        String FileName = getIntent.getStringExtra("FileName");

        Uri uri = Uri.parse("/storage/emulated/0/" + Environment.DIRECTORY_DOWNLOADS + "/" + FileName);
        System.out.println(uri.toString());

        imageResult.setImageURI(uri);

        StartMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryAddPic("/storage/emulated/0/" + Environment.DIRECTORY_DOWNLOADS);
                Intent intent = new Intent(photoResult.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void galleryAddPic(String currentPhotoPath){
        Intent mediaScan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File("/storage/emulated/0/" + Environment.DIRECTORY_DOWNLOADS);
        Uri connectUri = Uri.fromFile(f);
        mediaScan.setData(connectUri);
        this.sendBroadcast(mediaScan);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
