package com.development.mobile.heasoft.heasoft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener{
    private SplashScreenClass splashScreenObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView splashImage = (ImageView) findViewById(R.id.splash_image);
        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_alpha);
        splashImage.startAnimation(splashAnim);

        splashScreenObject = new SplashScreenClass(this);
        splashScreenObject.start();
    }

    @Override
    public void onClick(View view) {
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        startActivity(goToMainActivity);
        splashScreenObject.interrupt();
    }

    class SplashScreenClass extends Thread {
        private SplashScreen context;

        private SplashScreenClass(SplashScreen context){
            this.context = context;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent goToMainActivity = new Intent(context, MainActivity.class);
            startActivity(goToMainActivity);
        }
    }
}
