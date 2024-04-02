package com.example.contract_sign.logic;

import android.graphics.Point;
import android.view.Display;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class Resize {
    public void resizePDFView(PDFView pdfView, int Width, int Height){
        int A4Width = 210;
        int A4height = 297;

        while(A4Width < Width || A4height < Height){
            if(A4Width + 210 > Width || A4height + 210 > Height){
                break;
            }
            A4Width += 210;
            A4height += 297;
        }
        ViewGroup.LayoutParams params = pdfView.getLayoutParams();
        params.width = A4Width;
        params.height = A4height;
        pdfView.setLayoutParams(params);
    }
}
