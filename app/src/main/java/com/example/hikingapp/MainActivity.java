package com.example.hikingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hikingapp.CALENDAR.Calendar;
import com.example.hikingapp.CALENDAR.CustomCalendarView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    private Button kontakt;
    private Button raspored;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=findViewById(R.id.viewPager);
        ImageAdapter adapter=new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new myTimerTask(), 2000,4000);

        kontakt=findViewById(R.id.kontakt);
        kontakt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactActivity();
            }
        });

        raspored=findViewById(R.id.raspored);
        raspored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRasporedActivity();
            }
        });
    }

    public void  openContactActivity(){
        Intent intent=new Intent(this,Contact.class);
        startActivity(intent);
    }

    public void  openRasporedActivity(){
        Intent intent=new Intent(this, Calendar.class);
        startActivity(intent);
    }


    public class myTimerTask extends TimerTask{

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() ==0){
                        viewPager.setCurrentItem(1);
                    }else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }else if(viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(3);
                    }else if(viewPager.getCurrentItem() == 3){
                        viewPager.setCurrentItem(4);
                    }else if(viewPager.getCurrentItem() == 4){
                        viewPager.setCurrentItem(5);
                    }else if(viewPager.getCurrentItem() == 5){
                        viewPager.setCurrentItem(6);
                    }else if(viewPager.getCurrentItem() == 6){
                        viewPager.setCurrentItem(7);
                    }else if(viewPager.getCurrentItem() == 7){
                        viewPager.setCurrentItem(8);
                    }else if(viewPager.getCurrentItem() == 8){
                        viewPager.setCurrentItem(9);
                    }else if(viewPager.getCurrentItem() == 9){
                        viewPager.setCurrentItem(10);
                    }
                }
            });
        }
    }
}
