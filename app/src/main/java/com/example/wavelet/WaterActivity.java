package com.example.wavelet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WaterActivity extends AppCompatActivity {
    ImageButton addbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waterlayout);

        ImageButton wb = findViewById(R.id.waterBtn);
        navbar.setUpNavBar(this,wb);
        navbar.selected(wb, R.drawable.watervector, getApplicationContext());

        addbtn = findViewById(R.id.addbutton);
        addbtn.setOnClickListener(new mClickListener(this));



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bundle b = data.getExtras();
        Intent starter = new Intent(getApplicationContext(), CanvaCvActivity.class);
        starter.putExtras(b);
        startActivity(starter);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class mClickListener implements View.OnClickListener {
        AppCompatActivity aca;
        public mClickListener(AppCompatActivity aca){
            this.aca = aca;
        }
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ActivityCompat.requestPermissions(aca, new String[] {android.Manifest.permission.CAMERA}, 1);
            startActivityIfNeeded(intent, 1);
//            if (intent.resolveActivity(getPackageManager())!=null){
//                startActivityIfNeeded(intent, 1);
//            }else{
//                Toast.makeText(WaterActivity.this, "Check that your camera is working and camera permission is on:)", Toast.LENGTH_SHORT).show();
//                //todo FILL THIS IN
//            }

//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
//                    == PackageManager.PERMISSION_DENIED)
        }
    }
}
