package com.movieapp.mian;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XYActivity extends AppCompatActivity {

    @BindView(R.id.rl_xy_bacl)
    RelativeLayout rlXyBacl;
    @BindView(R.id.tv_xy)
    TextView tvXy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xy);
        ButterKnife.bind(this);
        tvXy.setText(getResources().getString(R.string.pay_info));
    }

    @OnClick(R.id.rl_xy_bacl)
    public void onClick() {
        finish();
    }
}
