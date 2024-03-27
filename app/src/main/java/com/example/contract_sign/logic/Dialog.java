package com.example.contract_sign.logic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.contract_sign.view.MainActivity;

public class Dialog {
    public void showDialog(String Title, String Content,String YesContext, String NoContext, Context context){
        AlertDialog.Builder msBuilder = new AlertDialog.Builder(context)
                .setTitle(Title)
                .setMessage(Content)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,YesContext,Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,NoContext,Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog msDialog = msBuilder.create();
        msDialog.show();
    };
}
