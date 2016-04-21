package com.sjrnr.hamza.flowbyte;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Hamza Fetuga on 4/20/2016.
 */

public class HomeArrayAdapter extends ArrayAdapter<String> {
    Typeface face;

    public HomeArrayAdapter(Context context, int resource, String[] items, Typeface face) {
        super(context, resource, items);
        this.face = face;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(face);
        view.setTextSize(20);
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(face);
        return view;
    }


}
