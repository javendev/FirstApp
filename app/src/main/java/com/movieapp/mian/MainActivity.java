package com.movieapp.mian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.movieapp.R;

public class MainActivity extends Activity {
    private ImageView imageView;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        imageView = (ImageView) findViewById(R.id.id_main_wel);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.wel);
                mAnimation.setFillAfter(false);
                imageView.startAnimation(mAnimation);
                mAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(mContext,MainViewPageActivity.class));
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
        },1000);
    }
}
