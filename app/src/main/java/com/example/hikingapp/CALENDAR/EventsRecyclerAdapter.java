package com.example.hikingapp.CALENDAR;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikingapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<Events> arrayList;
    DBOpenHelper dbOpenHelper;

    public EventsRecyclerAdapter(Context context, ArrayList<Events> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_rowlayout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Events events = arrayList.get(position);
        holder.Event.setText(events.getEVENT());
        holder.DateTxt.setText(events.getDATE());
        holder.Time.setText(events.getTIME());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCalendarEvent(events.getEVENT(), events.getDATE(), events.getTIME());
                Log.d("list", arrayList.toString());
                arrayList.remove(position);
                Log.d("list", arrayList.toString());
                Log.d("myTag", "pred setChanged");
                notifyDataSetChanged();
                Log.d("myTag", "posle setChanged");
            }
        });

        /*Log.d("myTag", "pred if else");
        if (isAllarmed(events.getDATE(), events.getEVENT(), events.getTIME())) {
            holder.setAlarm.setImageResource(R.drawable.ic_action_notification_on);
            Log.d("myTag", "pred");
            notifyDataSetChanged();
            Log.d("myTag", "posle");
        } else {
            holder.setAlarm.setImageResource(R.drawable.ic_action_notification_off);
            Log.d("myTag", "PRED");
            notifyDataSetChanged();
            Log.d("myTag", "POSLE");
        }*/
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(convertStringToDate(events.getDATE()));
        final int alamYear = dateCalendar.get(Calendar.YEAR);
        final int alarmMounth= dateCalendar.get(Calendar.MONTH);
        final int alamDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(convertStringToTime(events.getTIME()));
        final int alarmHour = timeCalendar.get(Calendar.HOUR_OF_DAY);
        final int alamMinute = timeCalendar.get(Calendar.MINUTE);

        holder.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllarmed(events.getDATE(), events.getEVENT(), events.getTIME())) {
                    holder.setAlarm.setImageResource(R.drawable.ic_action_notification_off);
                    cancelAlarm(getReqCode(events.getDATE(),events.getEVENT(),events.getTIME()));
                    updateEvent(events.getDATE(),events.getEVENT(),events.getTIME(),"off");
                    notifyDataSetChanged();
                } else {
                    holder.setAlarm.setImageResource(R.drawable.ic_action_notification_on);
                    Calendar alarmCalendar = Calendar.getInstance();
                    alarmCalendar.set(alamYear,alarmMounth,alamDay,alarmHour,alamMinute);
                    setAlarm(alarmCalendar,events.getEVENT(),events.getTIME(),getReqCode(events.getDATE(),events.getEVENT(),events.getTIME()));
                    updateEvent(events.getDATE(),events.getEVENT(),events.getTIME(),"on");
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView DateTxt, Event, Time;
        Button delete;
        ImageButton setAlarm;

        public MyViewHolder(View itemview) {
            super(itemview);
            DateTxt = itemview.findViewById(R.id.eventdate);
            Event = itemview.findViewById(R.id.eventnamee);
            Time = itemview.findViewById(R.id.eventime);
            delete = itemview.findViewById(R.id.delete);
            setAlarm = itemview.findViewById(R.id.alarmBtn);
        }
    }

    private void deleteCalendarEvent(String date, String event, String time) {
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        dbOpenHelper.deleteEvent(event, date, time, database);
        dbOpenHelper.close();
    }

    private boolean isAllarmed(String date, String event, String time) {
        boolean alarmed = true;
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadIDEvents(date, event, time, database);
        while (cursor.moveToNext()) {
            String notify = cursor.getString(cursor.getColumnIndex(DBStructure.Notify));
            if (notify.equals("on")) {
                alarmed = true;
            } else {
                alarmed = false;
            }
        }
        cursor.close();
        dbOpenHelper.close();
        return alarmed;
    }

    private void setAlarm(Calendar calendar, String event, String time, int RequestCode) {
        Intent intent = new Intent(context.getApplicationContext(),AlarmReceiver.class);
        intent.putExtra("event",event);
        intent.putExtra("time",time);
        intent.putExtra("id",RequestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,RequestCode,intent,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager)context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }

    private void cancelAlarm(int RequestCode) {
        Intent intent = new Intent(context.getApplicationContext(),AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,RequestCode,intent,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager)context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private int getReqCode(String date,String event,String time) {
        int code = 0;
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor= dbOpenHelper.ReadIDEvents(date,event,time,database);
        while (cursor.moveToNext()){
            code = cursor.getInt(cursor.getColumnIndex(DBStructure.ID));
        }
        cursor.close();
        dbOpenHelper.close();

        return code;
    }

    private void updateEvent(String date,String event,String time, String notify){
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.updateEvent(date,event,time,notify,database);
        dbOpenHelper.close();
    }

    private Date convertStringToTime(String eventDate){
        SimpleDateFormat format=new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        Date date=null;
        try{
            date=format.parse(eventDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    private Date convertStringToDate(String eventDate){
        SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd", Locale.ENGLISH);
        Date date=null;
        try{
            date=format.parse(eventDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }
}
