package com.example.wavelet;

import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

public class MainActivity extends AppCompatActivity {
    ImageView wave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);

        wave = findViewById(R.id.wave);
        wave.setBackgroundResource(R.drawable.avd_anim);
        AnimatedVectorDrawable anim = (AnimatedVectorDrawable) wave.getBackground();
        anim.start();

        ImageButton hb = findViewById(R.id.homeBtn);
        navbar.setUpNavBar(this,hb);
        navbar.selected(hb, R.drawable.homevector, getApplicationContext());

    }

}


