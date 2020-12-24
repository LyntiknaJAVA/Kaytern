package com.ryb1kple.helperforyou;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.TypeAdapterFactory;

public class Settings_activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start_animation();
        set_Settings();
        animation();
    }

    public void animation () {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.screen_scale);
        View main = findViewById(R.id.main_settings);
        main.startAnimation(anim);
    }

    public void on_back_button_click (View view) {
        finish();
    }

    Animation anim, anim_back;
    public void start_animation () {
        setContentView(R.layout.settings_layout);
        View settings = (View) findViewById(R.id.main_settings);
        anim = AnimationUtils.loadAnimation(this, R.anim.screen_scale);
        settings.startAnimation(anim);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

            }
        }, 1000);
    }

    public void set_Settings () {
        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        String color = settings.getString("bgcolor", "grey");
        View main = (View) findViewById(R.id.settings);
        switch (color) {
            case "aqua":
                main.setBackgroundColor(getResources().getColor(R.color.aqua));
                break;
            case "red":
                main.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case "brown":
                main.setBackgroundColor(getResources().getColor(R.color.brown));
                break;
            case "grey":
                main.setBackgroundColor(getResources().getColor(R.color.grey));
                break;
        }
        TextView tv = (TextView) findViewById(R.id.title);
        Typeface shrift = Typeface.createFromAsset(getAssets(), "fonts/Nunito-ExtraBold.ttf");
        tv.setTypeface(shrift);
    }
}
