package com.example.hikingapp.CALENDAR;

import android.os.Bundle;
//import packageName.CustomCalendarView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hikingapp.R;

public class Calendar extends AppCompatActivity {

    CustomCalendarView customCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        customCalendarView=(CustomCalendarView)findViewById(R.id.custom_calendar_view);
        customCalendarView.setUpCalendar();
    }

}
