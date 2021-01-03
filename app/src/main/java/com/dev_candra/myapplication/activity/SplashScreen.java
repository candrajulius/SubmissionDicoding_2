package com.dev_candra.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dev_candra.myapplication.R;

public class SplashScreen extends AppCompatActivity {

    Animation bottomAnimation,topAnimation;
    ImageView gambar1;
    TextView text1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        gambar1 = findViewById(R.id.gambarBanner1);
        text1 = findViewById(R.id.textBanner1);

        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        gambar1.setAnimation(topAnimation);
        text1.setAnimation(bottomAnimation);


        final int SplashScreen1 = 5000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
            }
        },SplashScreen1);
    }


}
