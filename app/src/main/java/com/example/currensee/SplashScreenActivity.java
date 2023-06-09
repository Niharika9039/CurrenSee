package com.example.currensee;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageView imageView;
    private ImageView zoomBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        zoomBTN = findViewById(R.id.mg_glass);
        videoView = findViewById(R.id.video_view);
        imageView = findViewById(R.id.image_view);

        // Play video in the background
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_video);
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                videoView.start();
            }
        });

        // Animate the image
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade);
        imageView.startAnimation(animation);

        // Animate the text
        Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.fade);
        TextView animatedText = findViewById(R.id.animated_text);
        animatedText.startAnimation(textAnimation);

         //Animate the glass image
        Animation animImage = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoomBTN.startAnimation(animImage);

        // Wait for the video to finish loading and the animation to complete
        long videoDuration = getVideoDuration(R.raw.splash_video);
        long animationDuration = animation.getDuration();
        long totalDuration = 6000; // Set the desired duration in milliseconds (e.g., 3000ms = 3 seconds)


        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(totalDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreenActivity.this, ClassifierActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }

    private long getVideoDuration(int videoResourceId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, videoResourceId);
        int duration = mediaPlayer.getDuration();
        mediaPlayer.release();
        return duration;
    }
}
