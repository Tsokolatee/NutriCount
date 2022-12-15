package com.example.nutricount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.content.Intent;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Dashboard extends AppCompatActivity {
    private ImageView btnScan;
    private Button btnProgress;
    private Button btnPlanner;
    private Button btnProfile;
    private YouTubePlayerView youTubePlayerView001;
    private YouTubePlayerView youTubePlayerView002;
    private YouTubePlayerView youTubePlayerView003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnScan = (ImageView) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Camera.class);
            startActivity(intent);
            finish();
        });

        btnProgress = (Button) findViewById(R.id.btnProgress);
        btnProgress.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Progress.class);
            startActivity(intent);
            finish();
        });

        btnPlanner = (Button) findViewById(R.id.btnPlanner);
        btnPlanner.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Planner.class);
            startActivity(intent);
            finish();
        });

        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Profile.class);
            startActivity(intent);
        });



        youTubePlayerView001 = findViewById(R.id.youtubeplayer001);
        getLifecycle().addObserver(youTubePlayerView001);

        youTubePlayerView001.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayerView001.enterFullScreen();
                youTubePlayerView001.exitFullScreen();
                youTubePlayerView001.isFullScreen();
                youTubePlayerView001.toggleFullScreen();
            }
        });

        youTubePlayerView002 = findViewById(R.id.youtubeplayer002);
        getLifecycle().addObserver(youTubePlayerView002);

        youTubePlayerView002.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayerView002.enterFullScreen();
                youTubePlayerView002.exitFullScreen();
                youTubePlayerView002.isFullScreen();
                youTubePlayerView002.toggleFullScreen();
            }
        });

        youTubePlayerView003 = findViewById(R.id.youtubeplayer003);
        getLifecycle().addObserver(youTubePlayerView003);

        youTubePlayerView003.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayerView003.enterFullScreen();
                youTubePlayerView003.exitFullScreen();
                youTubePlayerView003.isFullScreen();
                youTubePlayerView003.toggleFullScreen();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView001.release();
        youTubePlayerView002.release();
        youTubePlayerView003.release();
    }
}