package com.example.rtspvideostreamingapplication;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import androidx.media3.ui.PlayerView;

public class MainActivity extends AppCompatActivity {


    private EditText etRtspUrl;
    private TextView fullscreentxt;
    private Button btnPlay;
    private PlayerView playerView;
    private ExoPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        etRtspUrl = findViewById(R.id.et_rtsp_url);
        btnPlay = findViewById(R.id.btn_play);
        playerView = findViewById(R.id.player_view);



        btnPlay.setOnClickListener(v -> startStreaming());

    }

    @OptIn(markerClass = UnstableApi.class)
    private void startStreaming() {
        String rtspUrl = etRtspUrl.getText().toString().trim();
        if (rtspUrl.isEmpty()) return;

        if (player != null) {
            Toast.makeText(MainActivity.this, "Ensure a better network for uninterrupted streaming", Toast.LENGTH_SHORT).show();
            player.release();
        }

        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        playerView.setVisibility(View.VISIBLE);

        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(Uri.parse(rtspUrl))
                .setMimeType("application/x-rtsp")
                .build();

        RtspMediaSource mediaSource = new RtspMediaSource.Factory().createMediaSource(mediaItem);
        player.setMediaSource(mediaSource);
        player.prepare();
        player.play();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

}