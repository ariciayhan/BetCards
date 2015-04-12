package com.example.ayhan.myapplication.ui.swipe;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ayhan.myapplication.R;
import com.example.ayhan.myapplication.pos.sports.Event;
import com.example.ayhan.myapplication.pos.sports.Game;
import com.example.ayhan.myapplication.pos.sports.GameGroup;
import com.example.ayhan.myapplication.util.Image.ImageLoader;

import java.util.List;

/**
 * Created by ayhan on 22/03/15.
 */
public class EventAdapter extends ArrayAdapter<Event> {
    Context context;
    int layoutResourceId;
    public ImageLoader imageLoader;


    public EventAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;

        // Create ImageLoader object to download and show image in list
        // Call ImageLoader constructor to initialize FileCache
        imageLoader = new ImageLoader(context.getApplicationContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        OptionHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new OptionHolder();
            holder.img1Icon = (ImageView)row.findViewById(R.id.img1Icon);
            holder.img2Icon = (ImageView)row.findViewById(R.id.img2Icon);
            holder.txt1Title = (TextView)row.findViewById(R.id.txt1Title);
            holder.txt2Title = (TextView)row.findViewById(R.id.txt2Title);
            holder.txtInfo = (TextView)row.findViewById(R.id.txtInfo);
            holder.txtDate = (TextView)row.findViewById(R.id.txtDate);
            holder.radioOddOne = (RadioButton)row.findViewById(R.id.radio1odd);
            holder.radioOddTwo = (RadioButton)row.findViewById(R.id.radio2odd);


            row.setTag(holder);
        }
        else
        {
            holder = (OptionHolder)row.getTag();
        }
try {
    Event option = getItem(position);
    holder.txt1Title.setText(option.getParticipants()[0]);
    holder.txt2Title.setText(option.getParticipants()[1]);
    ImageView image1 = holder.img1Icon;
    ImageView image2 = holder.img2Icon;

    //DisplayImage function from ImageLoader Class
    imageLoader.DisplayImage("http://m.kingofthemoon.org/sportclubs/icons/1_fc_passau.png", image1);
    imageLoader.DisplayImage("http://m.kingofthemoon.org/sportclubs/icons/1_fc_sand.png", image2);

    holder.txtInfo.setText(option.getRegionName() + "\\" + option.getEventShortName());
    holder.txtDate.setText(option.getStartTime().format3339(false));
    GameGroup gg = option.getGameGroups().get(0);
    Game game = option.getGameGroupByID(gg.getGroupID()).getListGames().get(0);

    if (game != null) {
        holder.radioOddOne.setText(game.getResults()[0].getFormattedOdds());
        holder.radioOddTwo.setText(game.getResults()[1].getFormattedOdds());
    }

}catch(Exception e){
    Log.e("ADAPTER", e.toString());
}
        //holder.imgIcon.setImageResource(weather.icon);

        return row;
    }

    public void pop(){
        remove(getItem(0));
    }

    public void push(Event event){
        insert(event, 0);

    }



    static class OptionHolder
    {
        ImageView img1Icon;
        ImageView img2Icon;
        TextView txt1Title;
        TextView txt2Title;
        RadioButton radioOddOne;
        RadioButton radioOddTwo;
        TextView txtInfo;
        TextView txtDate;
    }
}
