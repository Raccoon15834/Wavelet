package com.example.wavelet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class CanvaCvActivity extends AppCompatActivity {
    DrawView dw;
    ImageButton left, right;
    int numTouches;
    boolean nextStep;
    boolean dragging;
    int moverLine; //toprec=1, botrec=2
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvacv);
        numTouches=0;
        nextStep=false;
        dragging =false;

        Bundle b = getIntent().getExtras();
        Bitmap bit = (Bitmap) b.get("data");
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        bit= Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
        dw = findViewById(R.id.canva);
        dw.mCustomImage = new BitmapDrawable(getResources(), bit);;
        dw.setOnTouchListener(new canvTouchListener(this));

        left = findViewById(R.id.backbtn);
        right = findViewById(R.id.donebtn);
        left.setImageDrawable(getResources().getDrawable(R.drawable.reset_circle_24));
        right.setImageDrawable(getResources().getDrawable(R.drawable.arrow_back_24));
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nextStep==false) {
                    if (numTouches < 4) {
                        Intent starter = new Intent(getApplicationContext(), WaterActivity.class);
                        startActivity(starter);
                    } else {
                        calculateAndScale();
                        dw.nextStep = true;
                        nextStep=true;
                        left.setImageDrawable(getResources().getDrawable(R.drawable.arrow_back_24));
                    }
                }else{
                    Intent starter = new Intent(getApplicationContext(), WaterActivity.class);
                    Bundle b = starter.getExtras();
                    b.putInt("water", (int)(dw.toprec-dw.botrec));//todo MATH
                    startActivity(starter);
                }
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nextStep==false) {
                    dw.coords = new LinkedList<DrawView.coord>();
                    numTouches = 0;
                    right.setImageDrawable(getResources().getDrawable(R.drawable.arrow_back_24));
                }else{
                    Intent starter = new Intent(getApplicationContext(), WaterActivity.class);
                    startActivity(starter);
                }
            }
        });

    }


    private void calculateAndScale() {
        Collections.sort(dw.coords, new Comparator<DrawView.coord>() {
            public int compare(DrawView.coord s1, DrawView.coord s2) {
                if(s1.y<s2.y) return -1;
                if(s1.y>s2.y) return 1;
                return 0;
            }
        });
        DrawView.coord b1 = dw.coords.get(0);
        DrawView.coord b2 = dw.coords.get(1);
        DrawView.coord t1 = dw.coords.get(2);
        DrawView.coord t2 = dw.coords.get(3);
        DrawView.coord m1 = new DrawView.coord((b1.x+b2.x)/2, (b1.y+b2.y)/2);
        DrawView.coord m2 = new DrawView.coord((t1.x+t2.x)/2, (t1.y+t2.y)/2);
        Log.i("measurecoords", b1.y+" "+b2.y+" "+t1.y+" "+t2.y+" ");
        dw.lentop = Math.sqrt(Math.pow(b1.x-b2.x,2) + Math.pow(b1.y-b2.y, 2));
        dw.lenbot = Math.sqrt(Math.pow(t1.x-t2.x,2) + Math.pow(t1.y-t2.y, 2));
        dw.lenheight = Math.sqrt(Math.pow(m1.x-m2.x,2) + Math.pow(m1.y-m2.y, 2));
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        dw.width = metrics.widthPixels;
        Log.i("measures", "lenbot "+dw.lenbot+"lentop "+dw.lentop+"lenheight"+dw.lenheight);
    }


    private class canvTouchListener implements View.OnTouchListener {
        AppCompatActivity aca;
        public canvTouchListener(AppCompatActivity aca){
            this.aca = aca;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            int e = event.getActionMasked();
            if(nextStep==false) {
                if (e == MotionEvent.ACTION_DOWN) {
                    //Log.i("nums", numTouches + "touches");
                    numTouches++;
                    if (numTouches > 4) {
                        Toast.makeText(aca, "Thats it :)", Toast.LENGTH_LONG).show();
                        return true;
                    }
                    dw.coords.add(new DrawView.coord(x, y));
                    if (numTouches == 4) {
                        right.setImageDrawable(getResources().getDrawable(R.drawable.check_24));
                    }
                }
            }else {
                if (e == MotionEvent.ACTION_DOWN) {
                    if (Math.abs(y - dw.toprec) < 20) {
                        dragging = true;
                        moverLine = 1;
                    }
                    if (Math.abs(y - dw.botrec) < 20) {
                        dragging = true;
                        moverLine = 2;
                    }
                }
                if (e == MotionEvent.ACTION_UP) {
                    dragging = false;
                }
                if(e==MotionEvent.ACTION_MOVE && dragging==true){
                    if(y<=dw.toplimit && y>dw.botdraw){
                        if(moverLine==1) dw.toprec = y;
                        else dw.botrec=y;
                    }
                }
            }
            return true;

        }

    }
}
