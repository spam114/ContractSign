package com.example.contract_sign.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
                .defaultPage(1)
                .pages(1)
                .enableAnnotationRendering(true)
                .load();
    }
}
