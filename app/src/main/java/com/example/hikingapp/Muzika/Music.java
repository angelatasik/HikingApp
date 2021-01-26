package com.example.hikingapp.Muzika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hikingapp.R;

import java.lang.reflect.Field;

public class Music extends AppCompatActivity implements AdapterView.OnItemClickListener {
    int position;
    ImageButton stop;
    Field[] fields = R.raw.class.getFields();
    //boolean isPlaying = false;


    private String songNames[] = {
            "song1",
            "song2",
            "song3",
            "song4",
            "song5",
            "song6"
    };

    private String ArtistNames[] = {
            "Artist1",
            "Artist2",
            "Artist3",
            "Artist4",
            "Artist5",
            "Artist6"
    };


    private Integer imageid[] = {
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3,
            R.drawable.s4,
            R.drawable.s5,
            R.drawable.s6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);


        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);



        CustomList customList = new CustomList(this, songNames, ArtistNames, imageid);
        listView.setAdapter(customList);

    }

    // овој метод се повикува кога ќе се кликне било кој item од ListView
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {

        String filename = fields[index].getName();
        position = index;
        //String[] songTitles = getResources().getStringArray(R.array.song_titles);
        //String title = songTitles[index].toLowerCase().replace(" ", "");

        
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra("title", filename);
        intent.setAction(MusicService.ACTION_PLAY);
        startService(intent);
    }

    // овој метод се повикува кога ќе се кликне копчето STOP
    public void onClickStop(View view) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(MusicService.ACTION_STOP);
        startService(intent);
    }

    public void onClickNext(View view) {
        position++;
        //position->poslednata pesna od listata, za da ne mi crash-ne ja postavuvam pak na pocetok
        if(((fields.length)) == position){
            position = 0;
            String filename = fields[position].getName();
            Intent intent = new Intent(this, MusicService.class);
            intent.putExtra("title", filename);
            intent.setAction(MusicService.ACTION_NEXT);
            startService(intent);
        }else {
            String filename = fields[position].getName();
            Intent intent = new Intent(this, MusicService.class);
            intent.putExtra("title", filename);
            intent.setAction(MusicService.ACTION_NEXT);
            startService(intent);
        }
    }

    public void onClickPrev(View view) {
        position--;
        if (position == -1 ) {
            position = fields.length - 1;
            String filename = fields[position].getName();
            Intent intent = new Intent(this, MusicService.class);
            intent.putExtra("title", filename);
            intent.setAction(MusicService.ACTION_PREV);
            startService(intent);
        } else {
            String filename = fields[position].getName();
            Intent intent = new Intent(this, MusicService.class);
            intent.putExtra("title", filename);
            intent.setAction(MusicService.ACTION_PREV);
            startService(intent);
        }
    }
}
