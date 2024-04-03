package com.example.contract_sign.view;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contract_sign.R;
import com.github.barteksc.pdfviewer.PDFView;

public class SignPdfActivity extends AppCompatActivity {
    //region 변수 선언
    public PDFView pdfView;
    public Button selectContract;
    public Button addSign;
    public Uri uri;
    //endregion
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //region 아이템 연결
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_pdf);

        pdfView = findViewById(R.id.image);
        selectContract = findViewById(R.id.selectContract);
        addSign = findViewById(R.id.addSign);

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

        ViewGroup.LayoutParams params = pdfView.getLayoutParams();
        params.width = A4Width;
        params.height = A4height;
        pdfView.setLayoutParams(params);
        selectContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 1);
            }
        });

        addSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfView.getPageCount() == 0){
                  Toast.makeText(SignPdfActivity.this,"양식을 선택해주세여",Toast.LENGTH_LONG).show();
                return;
                }

                Intent SignIntent = new Intent(SignPdfActivity.this, SignEditActivity.class);

                System.out.println(uri.toString());

                SignIntent.putExtra("image",uri);

                startActivity(SignIntent);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    this.uri = uri;
                    showPDF();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void showPDF(){
        pdfView.fromUri(uri)
                .defaultPage(0)
                .pages(0)
                .enableAnnotationRendering(true)
                .load();
    }
}
