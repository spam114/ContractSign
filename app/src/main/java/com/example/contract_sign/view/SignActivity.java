package com.example.contract_sign.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contract_sign.R;
import com.example.contract_sign.logic.Dialog;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignActivity extends AppCompatActivity {

//region 버튼 및 아이템 선언
    public SimpleDrawingView simpleDrawingView;
    public Button saveSign;
    public Button clearSign;
    public FrameLayout frame;
    public ImageView BackImage;
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

        Uri uri = getIntent().getParcelableExtra("image");
        BackImage.setImageURI(uri);

        // 저장
        saveSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder msBuilder = new AlertDialog.Builder(SignActivity.this);
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
                        FileOutputStream fos;
                        try {
                            fos = new FileOutputStream(new File(Dir,FileName));
                            captureView.compress(Bitmap.CompressFormat.PNG,100,fos);
                            fos.close();

                            Toast.makeText(getApplicationContext(),"저장되었습니다.", Toast.LENGTH_LONG).show();

                            Intent SignIntent = new Intent(SignActivity.this, SignResult.class);

                            SignIntent.putExtra("FileName",FileName);

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
                AlertDialog.Builder msBuilder = new AlertDialog.Builder(SignActivity.this);
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
}
