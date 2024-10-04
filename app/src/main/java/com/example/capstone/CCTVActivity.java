package com.example.capstone;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class CCTVActivity extends AppCompatActivity {
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        StyledPlayerView playerView = findViewById(R.id.player_view);
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        String streamUrl = getIntent().getStringExtra("STREAM_URL");
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(streamUrl));

        RtspMediaSource.Factory mediaSourceFactory = new RtspMediaSource.Factory();
        RtspMediaSource mediaSource = mediaSourceFactory.createMediaSource(mediaItem);

        player.setMediaSource(mediaSource);
        player.prepare();
        player.setPlayWhenReady(true);

        // 버튼
        Button homeButton = findViewById(R.id.button_home);
        homeButton.setOnClickListener(v -> {
            player.stop();
            player.release();
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}