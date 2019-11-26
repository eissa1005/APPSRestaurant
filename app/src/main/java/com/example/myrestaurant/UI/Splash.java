package com.example.myrestaurant.UI;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.Base.BaseActivity;

public class Splash extends BaseActivity {
    private static final String TAG = Splash.class.getSimpleName();
    private static final int delayMillis=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this,HomeActivity.class));
            }
        },delayMillis);

    }

}