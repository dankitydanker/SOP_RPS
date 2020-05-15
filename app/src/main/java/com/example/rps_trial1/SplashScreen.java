package com.example.rps_trial1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {

    TextView tvSplashScreen;

    ImageView ivSplashScreen;

    Animation topAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        tvSplashScreen = findViewById(R.id.tvSplashScreen);
        ivSplashScreen = findViewById(R.id.ivSplashScreen);

        ivSplashScreen.setAnimation(topAnim);
        tvSplashScreen.setAnimation(bottomAnim);

        final Handler rpsAnim = new Handler();
        rpsAnim.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable rps = (AnimationDrawable) ivSplashScreen.getDrawable();
                rps.start();
            }
        },2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainMenu = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(mainMenu);
                finish();
            }
        },5000);

    }
}
