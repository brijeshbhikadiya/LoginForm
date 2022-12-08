package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

        ImageView imageView;

        SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        imageView=findViewById(R.id.splash_image);
        sp=getSharedPreferences(ConsatntUrl.PREF,MODE_PRIVATE);

        AlphaAnimation animation=new AlphaAnimation(0,1);
        animation.setDuration(2700);
        //animation.setRepeatCount(2);  animation ne repeat karva
        imageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sp.getString(ConsatntUrl.ID,"").equalsIgnoreCase("")) {
                    new CommonMethod(SplashActivity.this, JasonLoginActivity.class);
                    finish();
                }
                else
                {
                    new CommonMethod(SplashActivity.this,JsonProfileActivity.class);
                    finish();
                }
            }
        },3000);


    }
}