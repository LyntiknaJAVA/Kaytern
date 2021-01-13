package com.ryb1kple.helperforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        start_img();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void start_img () {
        ImageView img = (ImageView) findViewById(R.id.imageView);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.screen_scale);
        img.startAnimation(anim);
    }

}
