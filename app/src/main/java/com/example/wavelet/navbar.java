package com.example.wavelet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

public class navbar {
    public static void setUpNavBar(AppCompatActivity aca, ImageView im){
        AppCompatImageButton hb = aca.findViewById(R.id.homeBtn);
        AppCompatImageButton wb = aca.findViewById(R.id.waterBtn);
        AppCompatImageButton vb = aca.findViewById(R.id.vibeBtn);
        Log.i("blah", "setupnavbar");
        hb.setOnClickListener(new myNavClickListener(aca, MainActivity.class, hb));
        wb.setOnClickListener(new myNavClickListener(aca, WaterActivity.class, wb));
        vb.setOnClickListener(new myNavClickListener(aca, VibeActivity.class, vb));
    }

    private static class myNavClickListener implements View.OnClickListener {
        AppCompatActivity aca;
        Class cl;
        ImageView image;
        public myNavClickListener(AppCompatActivity ac, Class c, ImageView im){
            aca = ac;
            cl=c;
            image = im;
        }
        @Override
        public void onClick(View view) {
            Log.i("blah", "heey onClick");
            animateReset(image, aca, cl);
        }
    }
    public static void animateReset(ImageView v, AppCompatActivity aca, Class cl) {
        Animation in = AnimationUtils.loadAnimation(aca.getApplicationContext(), R.anim.navanim);
        Animation out = AnimationUtils.loadAnimation(aca.getApplicationContext(), R.anim.navanim2);
        out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {

                Log.i("blah", "starting next one");
                v.startAnimation(in);
            }
        });
        in.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                Log.i("blah", "new activity come");
                Intent starter = new Intent(aca.getApplicationContext(), cl);
                aca.startActivity(starter);
            }
        });
        v.startAnimation(out);

    }

    public static void selected(ImageButton btn, int curr, Context ctx){
        VectorChildFinder vector = new VectorChildFinder(ctx, curr, btn);
        VectorDrawableCompat.VFullPath path1 = vector.findPathByName("top");
        path1.setFillColor(ctx.getResources().getColor(R.color.lgrey));
        VectorDrawableCompat.VFullPath path2 = vector.findPathByName("bot");
        path2.setFillColor(ctx.getResources().getColor(R.color.mgrey));

        VectorDrawableCompat.VFullPath path3 = vector.findPathByName("top2");
        if(path3!=null)
            path3.setFillColor(ctx.getResources().getColor(R.color.lgrey));
        VectorDrawableCompat.VFullPath path4 = vector.findPathByName("bot2");
        if(path4!=null)
            path4.setFillColor(ctx.getResources().getColor(R.color.mgrey));

    }

}
