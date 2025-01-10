package com.example.projectscheduler;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;
    //    private static final int STORAGE_PERMISSION_CODE=101;
    Animation topanim, bottomanim;
    ImageView image;
    TextView logo;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_animation);
        image = findViewById(R.id.imageView5);
        logo = findViewById(R.id.textView34);

        image.setAnimation(topanim);
        logo.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent intent = new Intent(MainActivity.this, IpSet.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  }
                , SPLASH_SCREEN);
    }
}


