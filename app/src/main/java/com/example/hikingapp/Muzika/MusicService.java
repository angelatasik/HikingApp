package com.example.hikingapp.Muzika;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.hikingapp.R;

import java.lang.reflect.Field;

public class MusicService extends Service {
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_STOP = "stop";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREV = "prev";


    MediaPlayer player;
    Field[] fields = R.raw.class.getFields();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();


        if (action.equals(ACTION_PLAY)) {

            String title = intent.getStringExtra("title");
            int id = getResources().getIdentifier(title, "raw", getPackageName());


            if (player != null) {
                player.stop();
                player.reset();
            }

            player = MediaPlayer.create(this, id);
            player.setLooping(true);
            player.start();
        }else if (action.equals(ACTION_STOP)) {


            if (player != null) {
                player.stop();
                player.reset();
            }
        }else if (action.equals(ACTION_NEXT)){
            String title = intent.getStringExtra("title");
            int id = getResources().getIdentifier(title, "raw", getPackageName());
            if (player != null) {
                player.stop();
                player.reset();
            }

            player = MediaPlayer.create(this, id);
            player.setLooping(true);
            player.start();

        }else if (action.equals(ACTION_PREV)){
            String title = intent.getStringExtra("title");
            int id = getResources().getIdentifier(title, "raw", getPackageName());
            if (player != null) {
                player.stop();
                player.reset();
            }

            player = MediaPlayer.create(this, id);
            player.setLooping(true);
            player.start();
        }


        return START_STICKY;
    }

    public MusicService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
