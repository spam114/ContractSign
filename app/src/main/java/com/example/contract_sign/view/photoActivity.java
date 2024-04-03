package com.example.contract_sign.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contract_sign.R;

public class photoActivity extends AppCompatActivity {
    public ImageView imageView;
    public Button selectContract;
    public Button addSign;
    public Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_select);

        imageView = findViewById(R.id.image);
        selectContract = findViewById(R.id.selectContract);
        addSign = findViewById(R.id.addSign);

        selectContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        addSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageView.getDrawable() == null){
                    Toast.makeText(photoActivity.this,"양식을 선택해주세여",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent SignIntent = new Intent(photoActivity.this, photoEditActivity.class);

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
                    imageView.setImageURI(uri);
                    this.uri = uri;
                }
                break;
        }
    }

    // 뒤로가기 막음 오류 무시
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}