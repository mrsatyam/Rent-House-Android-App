package com.example.myself.findme.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myself.findme.R;
import com.example.myself.findme.util.PrefUtils;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startingHandler(3000);
    }


    void startingHandler(final int milliseconds) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               String value = PrefUtils.getValue(SplashActivity.this, "app_pref", "city");

                if(value!=null&&value.contains("selected")) {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(SplashActivity.this,FetchingLocationActivity.class);
                    startActivity(i);
                }
                finish();

            }
        },milliseconds);


    }




}
