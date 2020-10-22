package com.example.deto;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class KalibreringOgVedligehold extends AppCompatActivity {
    //create class reference
    VideoView vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalibrering_og_vedligehold);

        vid = (VideoView)findViewById(R.id.videoView);
    }
    public void playVideo(View v) {
        MediaController m = new MediaController(this);
        vid.setMediaController(m);

        String path = "/Users/susan/AndroidStudioProjects/DeTo/app/src/main/res/raw/kalibrering1.mp4";

        Uri u = Uri.parse(path);

        vid.setVideoURI(u);

        vid.start();

    }
} //Link til tutorial: https://technobyte.org/play-video-file-android-studio-using-videoview-tutorial/