package com.example.wavelet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class VibrationMenu extends Fragment {
    public AppCompatActivity c;
    public int interval = 0;
    public int gap=0;
    public VibrationMenu(AppCompatActivity c) {
        interval = 0;
        this.c = c;
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        c.setContentView(R.layout.vibration_menu);

        Button anteater = (Button) c.findViewById(R.id.anteater);
        anteater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    public int getGap(){
        return gap;
    }
    public int getInterval(){
        return interval;
    }

}