package com.example.contract_sign.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contract_sign.R;

public class MainActivity extends AppCompatActivity {
    //region 변수 선언
    public Button pdfSelect;
    public Button photoSelect;

    //endregion
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //region 아이템 연결
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdfSelect = findViewById(R.id.PdfSelect);
        photoSelect = findViewById(R.id.PhotoSelect);
        //endregion

        pdfSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignIntent = new Intent(MainActivity.this, SignPdfActivity.class);
                startActivity(SignIntent);
            }
        });

        photoSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignIntent = new Intent(MainActivity.this, photoActivity.class);
                startActivity(SignIntent);
            }
        });
    }

}