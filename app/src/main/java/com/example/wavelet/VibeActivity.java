package com.example.wavelet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class VibeActivity extends AppCompatActivity {
    private Vibrator vibrator;
    private ImageView start_stop;
    public int mode;

    public void Show(View start_stop) {
        start_stop.setVisibility(View.VISIBLE);
    }

    public void Hide(View start_stop) {
        start_stop.setVisibility(View.INVISIBLE);
    }

    ImageButton check;
    AppCompatActivity aca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vibelayout);
        ImageButton vb = findViewById(R.id.vibeBtn);
        navbar.setUpNavBar(this, vb);
        navbar.selected(vb, R.drawable.vibevector, getApplicationContext());
        aca = this;

        mode = 0;
        start_stop = (ImageView) findViewById(R.id.start_stop_button);
        start_stop.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_circle_24));
        VibrationMenu vbm = new VibrationMenu(this);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        check = findViewById(R.id.checkbutton);
        Hide(check);

        start_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.main_activity, vbm).commit();
                mode=1;
                Show(check);
                Hide(start_stop);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(View view) {
                        if (vbm.interval == 0) {
                            Toast.makeText(aca, "Please select something", Toast.LENGTH_LONG);
                        } else {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.remove(vbm).commit();
                            Hide(check);
                            Show(start_stop);
                            start_stop.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_circle_24));
                            vibrate(vbm.interval, vbm.gap);
                            start_stop.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    long[] pattern = {1, 1};
                                    vibrator.vibrate(pattern, -1);
                                    Intent starter = new Intent(aca.getApplicationContext(), VibeActivity.class);
                                    aca.startActivity(starter);
                                }
                            });
                        }
                    }
                });
            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void vibrate(int interval, int gap) {
        long[] pattern = {gap, interval};
        vibrator.vibrate(pattern, 0);
    }
}