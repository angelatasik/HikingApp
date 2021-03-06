package com.example.hikingapp.CALENDAR;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.hikingapp.MainActivity;
import com.example.hikingapp.R;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("blaa","vlaga vo onRecive");
        String event = intent.getStringExtra("event");
        String time = intent.getStringExtra("time");
        int notID = intent.getIntExtra("id",0);
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,activityIntent,PendingIntent.FLAG_ONE_SHOT);
        String chanelId = "chanel_id";
        CharSequence name = "channel_name";
        String description = "description";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("angela","vlaga vo if kaj kalendarot kaj 26tata verzija ");
            NotificationChannel channel = new NotificationChannel(chanelId,name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Log.d("a","start create notification:");
        Notification notification = new NotificationCompat.Builder(context,chanelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(event)
                .setContentText(time)
                .setDeleteIntent(pendingIntent)
                .setGroup("Group_calendar_view")
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notID,notification);
        Log.d("a","kreirana not");
    }
}
