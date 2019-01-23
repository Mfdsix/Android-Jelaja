package com.example.wdd_vip.jelaja.UserView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.wdd_vip.jelaja.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        img = (ImageView) findViewById(R.id.logo);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        img.startAnimation(myAnim);
        final Intent i = new Intent(this, DrawerActivity.class);
        Thread timer = new Thread(){
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();

    }
}
