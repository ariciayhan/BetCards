package com.example.ayhan.myapplication.ui.swipe;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayhan.myapplication.R;
import com.example.ayhan.myapplication.pos.sports.Event;

import java.util.List;

/**
 * Created by ayhan on 22/03/15.
 */
public class EventAdapter extends ArrayAdapter<Event> {
    Context context;
    int layoutResourceId;


    public EventAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
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
            holder.img1Icon = (ImageView)row.findViewById(R.id.img2Icon);
            holder.txt1Title = (TextView)row.findViewById(R.id.txt1Title);
            holder.txt2Title = (TextView)row.findViewById(R.id.txt2Title);

            row.setTag(holder);
        }
        else
        {
            holder = (OptionHolder)row.getTag();
        }

        Event option = getItem(position);
        holder.txt1Title.setText(option.getParticipants()[0]);
        holder.txt2Title.setText(option.getParticipants()[1]);
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
    }
}
