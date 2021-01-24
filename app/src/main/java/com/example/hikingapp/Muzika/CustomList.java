package com.example.hikingapp.Muzika;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hikingapp.R;

public class CustomList extends ArrayAdapter<String> {
    private String[] songNames;
    private String[] ArtistNames;
    private Integer[] imageid;
    private Activity context;

    public CustomList(Activity context, String[] countryNames, String[] capitalNames, Integer[] imageid) {
        super(context, R.layout.row_item, countryNames);
        this.context = context;
        this.songNames = countryNames;
        this.ArtistNames = capitalNames;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item, null, true);
        TextView textViewSong  = (TextView) row.findViewById(R.id.textViewSong);
        TextView textViewArtist = (TextView) row.findViewById(R.id.textViewArtist);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.imageViewFlag);

        textViewSong.setText(songNames[position]);
        textViewArtist.setText(ArtistNames[position]);
        imageFlag.setImageResource(imageid[position]);
        return  row;
    }
}
