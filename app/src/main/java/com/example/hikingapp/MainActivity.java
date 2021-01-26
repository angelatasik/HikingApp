package com.example.hikingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.hikingapp.CALENDAR.Calendar;
import com.example.hikingapp.Muzika.Music;
import com.example.hikingapp.WeatherMap.Vreme;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    private ImageButton vreme;
    private ImageButton kontakt;
    private ImageButton raspored;
    private ImageButton  music;


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

        music=findViewById(R.id.musicButton);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMusicActivity();
            }
        });

        vreme=findViewById(R.id.button2);
        vreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeatherActivity();
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

    public void  openMusicActivity(){
        Intent intent=new Intent(this, Music.class);
        startActivity(intent);
    }

    public void  openRasporedActivity(){
        Intent intent=new Intent(this, Calendar.class);
        startActivity(intent);
    }

    public void  openWeatherActivity(){
        Intent intent=new Intent(this, Vreme.class);
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
