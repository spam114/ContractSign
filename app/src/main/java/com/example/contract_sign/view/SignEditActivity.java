package com.example.contract_sign.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contract_sign.R;
import com.example.contract_sign.logic.SaveFile;
import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignEditActivity extends AppCompatActivity {

//region 버튼 및 아이템 선언
    public SimpleDrawingView simpleDrawingView;
    public Button saveSign;
    public Button clearSign;
    public FrameLayout frame;
    public PDFView BackImage;
    public SaveFile saveFile;
//endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_view);

//region 버튼 및 아이템 연결
        saveSign    = findViewById(R.id.saveSign);
        clearSign   = findViewById(R.id.clearSign);
        simpleDrawingView = findViewById(R.id.drawView);
        BackImage         = findViewById(R.id.bakImage);
        frame             = findViewById(R.id.Frame);
//endregion

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
        System.out.println("2========================");
        System.out.println("display X = " + Width);
        System.out.println("display Y = " + Height);
        System.out.println("X = " + A4Width);
        System.out.println("Y = " + A4height);

        ViewGroup.LayoutParams params = BackImage.getLayoutParams();
        params.width = A4Width;
        params.height = A4height;
        simpleDrawingView.setLayoutParams(params);
        BackImage.setLayoutParams(params);

        Uri uri = getIntent().getParcelableExtra("image");
        showPDF(uri);
        // 저장
        saveSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder msBuilder = new AlertDialog.Builder(SignEditActivity.this);
                msBuilder.setTitle("저장 확인");
                msBuilder.setMessage("저장 확인");

                msBuilder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        frame.setDrawingCacheEnabled(true);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = new Date();

                        String FileName = sdf.format(date) + ".png";

                        Bitmap captureView = Bitmap.createBitmap(frame.getDrawingCache());
                        frame.setDrawingCacheEnabled(false);

                        File Dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                        if(!Dir.exists()){
                            Dir.mkdirs();
                        }
                        String output = Dir.getPath() + "/" + sdf.format(date) + ".pdf";

                        try {
                            saveFile.PdfSave(Dir,captureView,FileName,output);

                            Toast.makeText(getApplicationContext(),"저장되었습니다.", Toast.LENGTH_LONG).show();

                            Intent SignIntent = new Intent(SignEditActivity.this, SignResult.class);

                            SignIntent.putExtra("FileName",output);

                            startActivity(SignIntent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                msBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소하였습니다.",Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = msBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });

        // 그린거 초기화
        clearSign.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msBuilder = new AlertDialog.Builder(SignEditActivity.this);
                msBuilder.setTitle("초기화");
                msBuilder.setMessage("화면을 지우시겠습니까?");

                msBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        simpleDrawingView.onClear();
                        simpleDrawingView.refreshDrawableState();
                        simpleDrawingView.invalidate();
                    }
                });

                msBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소하였습니다.",Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = msBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

            }
        });
    }
    public void showPDF(Uri uri){
        BackImage.fromUri(uri)
                .defaultPage(0)
                .pages(0)
                .enableAnnotationRendering(true)
                .load();
    }
}
