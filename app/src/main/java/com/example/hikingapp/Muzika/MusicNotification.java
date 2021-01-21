package com.example.hikingapp;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MusicNotification {

    public static final String CHANNEL_ID = "channel1";
    public static final String ACTION_PREVIUOS = "actionprev";
    public static final String CHANNEL_PLAY = "channelplay";
    public static final String CHANNEL_NEXT = "channelnext";

    public static Notification notification;

    public static void createNotification(Context context, Track track, int playbtn, int pos, int size) {

        Log.d("Angela","vlaga vo createNot");

        if (Build.VERSION.SDK_INT >= 26) {
            Log.d("Angela","vlaga i tuka");

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),track.getImage());
            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_music_note)
                    .setContentTitle(track.getTitle())
                    .setContentText(track.getArtist())
                    .setLargeIcon(icon)
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

            notificationManagerCompat.notify(3,notification);
            Log.d("Angela","kreirase notifikacija");
        }
        //Log.d("Angela","ne vleze vo if-ot");
    }

}
