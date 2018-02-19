package com.example.myself.findme.activity;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.example.myself.findme.R;
import com.example.myself.findme.util.CircleProgress;

import com.example.myself.findme.util.PrefUtils;
import com.nineoldandroids.animation.ValueAnimator;

public class FetchingLocationActivity extends AppCompatActivity {

    private CircleProgress mProgressView;
    private View mStartBtn;
    private View mStopBtn;
    private View mResetBtn;
     CircularProgressButton  circularButton1=null;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        textView = (TextView)findViewById(R.id.text);

        mProgressView = (CircleProgress) findViewById(R.id.progress);
        mProgressView.startAnim();

      mProgressView.setVisibility(View.INVISIBLE);



        circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);

        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       simulateSuccessProgress(circularButton1);
                if(circularButton1.getProgress()==100)
                {
                    PrefUtils.setValue(FetchingLocationActivity.this, "app_pref", "cityname","Dehradun");
                    Intent intent = new Intent(FetchingLocationActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    circularButton1.setVisibility(View.INVISIBLE);
                    mProgressView.setVisibility(View.VISIBLE);
                }
            }
        });

        circularButton1.performClick();
    }






    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(4000);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();

                if(value==100) {
                    mProgressView.stopAnim();

                    circularButton1.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.INVISIBLE);
                    button.setProgress(value);
                    textView.setText("Click On Dehradun To Proceed");
                }
            }
        });
        widthAnimation.start();
    }
}