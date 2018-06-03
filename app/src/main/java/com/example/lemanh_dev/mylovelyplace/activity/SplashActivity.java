package com.example.lemanh_dev.mylovelyplace.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lemanh_dev.mylovelyplace.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @BindView(R.id.layoutSplash)
    RelativeLayout layout;

    @BindView(R.id.imgIcon_logo)
    ImageView img_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Animation transitionAmin = AnimationUtils.loadAnimation(this,R.anim.transition_icon);
        img_icon.setAnimation(transitionAmin);
        Animation alphaAmin = AnimationUtils.loadAnimation(this,R.anim.alphe_bacground);
        layout.setAnimation(alphaAmin);
        alphaAmin.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //chuyen activity
                startActivity(new Intent(SplashActivity.this,CategoriesActivity.class));
                finish();//khi man hinh splash chay xong thi tat lun
            }
        },3000);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
