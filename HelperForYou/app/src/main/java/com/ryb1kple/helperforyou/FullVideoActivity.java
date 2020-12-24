package com.ryb1kple.helperforyou;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;

public class FullVideoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_video);
        set_settings();
    }

    public void set_settings () {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        VideoView video = (VideoView) findViewById(R.id.video_full);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vid);
        video.setVideoURI(uri);
        video.setMediaController(new MediaController(this));
        Intent intent = getIntent();
        int ms = intent.getIntExtra("ms", 0);
        video.seekTo(ms);
        video.pause();
    }

    public void close_video (View view) {
        finish();
    }

}
