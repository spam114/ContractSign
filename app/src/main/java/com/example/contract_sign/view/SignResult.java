package com.example.contract_sign.view;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contract_sign.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.net.URI;

public class SignResult extends AppCompatActivity {

    public PDFView pdfView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_result);

        pdfView = findViewById(R.id.imageResult);
        Button StartMove = findViewById(R.id.startMove);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int Width = size.x;
        int Height = size.y;
        int A4Width = 210;
        int A4height = 297;

        while(true){
            if(A4Width + 210 >= Width || A4height + 297 >= Height){
                break;
            }
            A4Width += 210;
            A4height += 297;
        }

        System.out.println("3========================");
        System.out.println("display X = " + Width);
        System.out.println("display Y = " + Height);
        System.out.println("X = " + A4Width);
        System.out.println("Y = " + A4height);

        ViewGroup.LayoutParams params = pdfView.getLayoutParams();
        params.width = A4Width;
        params.height = A4height;
        pdfView.setLayoutParams(params);

        Intent getIntent = getIntent();
        String FileName = getIntent.getStringExtra("FileName");
        //uri = Uri.parse("/storage/emulated/0/" + Environment.DIRECTORY_DOWNLOADS + "/" + FileName);
        File file = new File(FileName);
        showPdfFile(file);
        StartMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryAddPic("/storage/emulated/0/" + Environment.DIRECTORY_DOWNLOADS);
                Intent intent = new Intent(SignResult.this,MainActivity.class);
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

    public void showPdfFile(File file){
        try {
            pdfView.fromFile(file)
                    .defaultPage(1)
                    .enableAnnotationRendering(true)
                    .load();
        }catch (Exception e){
            System.out.println("실패");
        }

    }
}
