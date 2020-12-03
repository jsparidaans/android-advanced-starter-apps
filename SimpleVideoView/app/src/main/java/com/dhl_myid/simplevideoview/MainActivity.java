package com.dhl_myid.simplevideoview;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Constants
    private static final String VIDEO_SAMPLE = "https://developers.google.com/training/images/tacoma_narrows.mp4";
    private static final String PLAYBACK_TIME = "play_time";

    //Member variables
    private VideoView videoView;
    private TextView bufferingView;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.video_view);
        bufferingView = findViewById(R.id.buffering_text_view);

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(videoView);
        videoView.setMediaController(controller);

    }

    private void initializePlayer() {
        bufferingView.setVisibility(View.VISIBLE);
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(mediaPlayer -> {
            bufferingView.setVisibility(View.GONE);

            // Restore view to previous play time
            if (currentPosition > 0) {
                videoView.seekTo(currentPosition);
            } else {
                //Setting to 1 shows the first frame in stead of a black square
                videoView.seekTo(1);
            }

            videoView.start();
        });

        videoView.setOnCompletionListener(mediaPlayer -> {
            Toast.makeText(MainActivity.this, "Playback completed", Toast.LENGTH_SHORT).show();
            videoView.seekTo(1);
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PLAYBACK_TIME, videoView.getCurrentPosition());
    }

    private void releasePlayer() {
        videoView.stopPlayback();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();

        releasePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        videoView.pause();
    }

    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            return Uri.parse(mediaName);
        } else {
            return Uri.parse("android.resource://" + getPackageName() + "/raw/" + mediaName);
        }
    }
}