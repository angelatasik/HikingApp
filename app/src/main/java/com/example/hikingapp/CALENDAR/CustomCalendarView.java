package com.example.hikingapp.CALENDAR;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikingapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CustomCalendarView extends LinearLayout {
    ImageButton nextButton,PreviosButton;
    TextView CurrentDate;
    GridView gridView;

    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
    SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy",Locale.ENGLISH);
    SimpleDateFormat eventDateFormat=new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
    AlertDialog alertDialog;
    MyGridAdapter gridAdapter;
    //androidx.appcompat.app.AlertDialog alertDialog;
    List<Date> dates=new ArrayList<>();
    List<Events> eventsList=new ArrayList<>();
    DBOpenHelper dbOpenHelper;
    int alarmYear,alarmMounth,alarmHour,alarmMinute,alarmDay;

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        IntializeLayout();
        setUpCalendar();

        PreviosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                setUpCalendar();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                setUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setCancelable(true);
                //final View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_layout,parent,false);
                View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_layout,null);
                final EditText EventName = addView.findViewById(R.id.eventname);
                final TextView EventTime = addView.findViewById(R.id.eventtime);
                ImageButton SetTime = addView.findViewById(R.id.seteventtime);
                final CheckBox alarmMe = addView.findViewById(R.id.alarmMe);
                Calendar dateCalenadr = Calendar.getInstance();
                dateCalenadr.setTime(dates.get(position));
                alarmYear = dateCalenadr.get(Calendar.YEAR);
                alarmMounth = dateCalenadr.get(Calendar.MONTH);
                alarmDay = dateCalenadr.get(Calendar.DAY_OF_MONTH);

                Button AddEvent = addView.findViewById(R.id.addevent);

                SetTime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int minuts = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), R.style.Theme_AppCompat_Dialog,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        Calendar c = Calendar.getInstance();
                                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                        c.set(Calendar.MINUTE,minute);
                                        c.setTimeZone(TimeZone.getDefault());
                                        SimpleDateFormat hformate = new SimpleDateFormat("K:mm a",Locale.ENGLISH);
                                        String event_Time = hformate.format(c.getTime());
                                        EventTime.setText(event_Time);
                                        alarmHour = c.get(Calendar.HOUR_OF_DAY);
                                        alarmMinute = c.get(Calendar.MINUTE);
                                    }
                                },hours,minuts,false);
                                timePickerDialog.show();
                    }
                });
                final String date = eventDateFormat.format(dates.get(position));
                final String month=monthFormat.format(dates.get(position));
                final String year=yearFormat.format(dates.get(position));

                AddEvent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (alarmMe.isChecked()) {
                            SaveEvent(EventName.getText().toString(),EventTime.getText().toString(),date,month,year,"on");
                            //SaveEvent("test","12:22",date,month,year);
                            setUpCalendar();
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(alarmYear,alarmMounth,alarmDay,alarmHour,alarmMinute);
                            setAlarm(calendar,EventName.getText().toString(),EventTime.getText().toString(),getReqCode(date,EventName.getText().toString(),EventTime.getText().toString()));
                            alertDialog.dismiss();
                        }else {
                            SaveEvent(EventName.getText().toString(),EventTime.getText().toString(),date,month,year,"off");
                            //SaveEvent("test","12:22",date,month,year);
                            setUpCalendar();
                            alertDialog.dismiss();
                        }

                    }
                });
                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String date = eventDateFormat.format(dates.get(position));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View showView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_layout,null);
                RecyclerView recyclerView = showView.findViewById(R.id.EventsRV);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                EventsRecyclerAdapter eventsRecyclerAdapter = new EventsRecyclerAdapter(showView.getContext(),CollectEventsByDate(date));
                recyclerView.setAdapter(eventsRecyclerAdapter);
                eventsRecyclerAdapter.notifyDataSetChanged();

                builder.setView(showView);
                alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        setUpCalendar();
                    }
                });
                return true;
            }
        });
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
    private void setAlarm(Calendar calendar, String event, String time,int RequestCode) {
        Intent intent = new Intent(context.getApplicationContext(),AlarmReceiver.class);
        intent.putExtra("event",event);
        intent.putExtra("time",time);
        intent.putExtra("id",RequestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,RequestCode,intent,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager)context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }
    private ArrayList<Events> CollectEventsByDate(String date){
        ArrayList<Events>arrayList = new ArrayList<>();
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor= dbOpenHelper.ReadEvents(date,database);
        while (cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String Date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events events= new Events(event,time,Date,month,Year);
            arrayList.add(events);
        }
        cursor.close();
        dbOpenHelper.close();

        return arrayList;
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void SaveEvent(String event,String time,String date, String month, String year,String notify){
        dbOpenHelper=new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        //dbOpenHelper.SaveEvent(event,time,date,month,year,database);
        dbOpenHelper.SaveEvent(event,time,date,month,year,notify,database);
        dbOpenHelper.close();
        Toast.makeText(context,"Event Saved",Toast.LENGTH_SHORT).show();
    }

    private void IntializeLayout(){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_calendar,this);
        nextButton = view.findViewById(R.id.next);
        PreviosButton = view.findViewById(R.id.previous);
        CurrentDate = view.findViewById(R.id.currentDateLabel);
        gridView = view.findViewById(R.id.gridview);
    }

    public void setUpCalendar(){

        String currentDate = SimpleDateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDate);
        dates.clear();
        Calendar monthCalendar = (Calendar)calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayOfMonth= monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstDayOfMonth);

        CollectEventsPerMounth(monthFormat.format(calendar.getTime()),yearFormat.format(calendar.getTime()));

        while(dates.size() < MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        gridAdapter = new MyGridAdapter(context,dates,calendar,eventsList);
        gridView.setAdapter(gridAdapter);
    }

    private void CollectEventsPerMounth(String Month,String year){
        eventsList.clear();
        dbOpenHelper=new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        //SQLiteDatabase database=dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEventsPerMonth(Month,year,database);
        //CollectEventsPerMounth(monthFormat.format(calendar.getTime()),yearFormat.format(calendar.getTime()));

        while (cursor.moveToNext()) {
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events events=new Events(event,time,date,month,Year);
            eventsList.add(events);
        }
        cursor.close();
        dbOpenHelper.close();
    }

    private Date convertStrinToDate(String dateInString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormat.parse(dateInString);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }
}
