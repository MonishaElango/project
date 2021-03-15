package com.hertzai.hevolve.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.hertzai.hevolve.R;
import com.hertzai.hevolve.MainPage;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                SharedPreferences settings = getSharedPreferences("prefName", MODE_PRIVATE);
                boolean firstLaunch = settings.getBoolean("firstRun", false);
                if (firstLaunch) {
                    Intent i1 = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i1);
                } else {
                    Intent i = new Intent(SplashActivity.this,MainPage.class);
                    startActivity(i);
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
