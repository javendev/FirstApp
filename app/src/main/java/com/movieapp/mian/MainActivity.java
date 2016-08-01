package com.movieapp.mian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.movieapp.R;

public class MainActivity extends AppCompatActivity {
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


    /**
     * startActivity屏蔽物理返回按钮
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
