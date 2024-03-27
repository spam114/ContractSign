package com.example.contract_sign.logic;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;

import com.example.contract_sign.view.SignActivity;

public class RectTouch extends Activity {
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Intent intent = new Intent(getApplicationContext(), SignActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
