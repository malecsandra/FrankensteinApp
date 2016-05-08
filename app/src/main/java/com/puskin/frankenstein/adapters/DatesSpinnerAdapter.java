package com.puskin.frankenstein.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.puskin.frankenstein.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexandra on 08-May-16.
 */
public class DatesSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context context;
    private ArrayList<Date> dates;

    public DatesSpinnerAdapter(Context context, ArrayList<Date> dates) {
        this.context = context;
        this.dates = dates;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent,
                false);

        TextView date = (TextView) row.findViewById(android.R.id.text1);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        date.setText(sdf.format(dates.get(position)));

        return row;
    }


}
