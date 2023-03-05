package com.example.wavelet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class DrawView extends View {
    Drawable mCustomImage;
    Paint p, p2, p3;
    LinkedList<coord> coords= new LinkedList<>();
    double lenbot, lentop, lenheight, scaleWidth, midw, toplimit;
    float botdraw = 30f;
    boolean nextStep;
    float toprec = 0, botrec=0;
    int width;
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        //setMinimumHeight(mCustomImage.getMinimumHeight());
        p = new Paint();
        p.setColor(Color.RED);
        p2 = new Paint();
        p2.setColor(Color.BLACK);
        p3 = new Paint();
        p3.setColor(Color.BLUE);
        nextStep = false;
    }
    public static class coord{
         float x, y;
        public coord(float mX, float mY){
            x=mX; y= mY;
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!nextStep) {
            Rect imageBounds = canvas.getClipBounds();
            mCustomImage.setBounds(imageBounds);
            mCustomImage.draw(canvas);
            for(coord i: coords){
                canvas.drawCircle(i.x, i.y, 20f, p);
            }
        }else{
            scaleWidth= getWidth()/2;
            midw= getWidth()/2+10;
            //Log.i("regAsswidth", width+"w");
            canvas.drawColor(Color.WHITE);
            double scale = scaleWidth/lentop;
            Path glassP = new Path();
            float left = (float)(midw-scale*lenbot/2);
            float right = (float)(midw+scale*lenbot/2);
            glassP.moveTo(left, (float)(botdraw+lenheight*scale));
            glassP.lineTo(right, (float)(botdraw+lenheight*scale));
            glassP.lineTo((float)(midw+scale*lentop/2), botdraw);
            glassP.lineTo((float)(midw-scale*lentop/2), botdraw);
            glassP.lineTo(left, botdraw);
            if(toprec==0){
                botrec = (float)(botdraw+lenheight*scale);
                toplimit = (float)(botdraw+lenheight*scale);
                toprec = botrec-30;
                Log.i("scalewidthmeasure", scaleWidth+"scalewidth");
                Log.i("scalemeasure", scale+"scale");
                Log.i("measures", toprec+" "+botrec);
            }
            canvas.drawPath(glassP, p2);
            canvas.drawRect(left, toprec, right, botrec, p3);


        }

        invalidate();
    }
}
