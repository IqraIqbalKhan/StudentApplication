package com.example.iqrakhan.studentapplication;

import android.content.Intent;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                PreferenceManager.getDefaultSharedPreferences(SplashActivity.this)
                        .edit()
                        .putBoolean("isFirstTime", false)
                        .apply();
                startActivity(new Intent(SplashActivity.this,NewsFeedActivity.class));
                finish();
            }
        }.start();
    }


    @Override
    protected void onStart() {
        super.onStart();

        ImageView imageView = (ImageView) findViewById(R.id.image_logo);

        assert imageView != null;
        imageView.setVisibility(View.VISIBLE);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_in);


        imageView.startAnimation(fadeInAnimation);


    }
}
