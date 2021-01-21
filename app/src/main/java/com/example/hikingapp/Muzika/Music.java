package com.example.hikingapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Music extends AppCompatActivity {

    ImageButton play;
    TextView title;
    NotificationManager notificationManager;
    List<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        play = findViewById(R.id.play);
        title = findViewById(R.id.title);

        populateTracks();

        //Build.VERSION_CODES.Q
        //.SDK_INT -> verzijata na tel kja so se testira
        if(Build.VERSION.SDK_INT >= 26) {
            Log.d("Angela","vlaga tukaaaaaaaaaaaaaa");
            createChannel();
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Angela","se klika pause");
                MusicNotification.createNotification(Music.this, tracks.get(1), R.drawable.ic_pause,
                        1,tracks.size() -1);
                Log.d("Angela","let's creat3e not");
            }
        });
    }

    private void createChannel() {

        if(Build.VERSION.SDK_INT >= 26)
        {
            Log.d("angela","kaj verzija 26");
            NotificationChannel channel = new NotificationChannel(MusicNotification.CHANNEL_ID,
                    "KOD DEV", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);

            }
        }
    }
    private void populateTracks() {
        tracks = new ArrayList<>();
        tracks.add(new Track("Track 1", "Artist 1" , R.drawable.s1));
        tracks.add(new Track("Track 2", "Artist 2" , R.drawable.s2));
        tracks.add(new Track("Track 3", "Artist 3" , R.drawable.s3));
        tracks.add(new Track("Track 4", "Artist 4" , R.drawable.s4));
    }


}
