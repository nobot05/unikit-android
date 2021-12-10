package com.nhk.unikit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class listViewDetails extends ArrayAdapter {
    private String[] itemNames;
    private String[] itemDesc;
    private Integer[] imageid;
    private Activity context;

    public listViewDetails(Activity context, String[] itemNames, String[] itemDesc, Integer[] imageid) {
        super(context, R.layout.listview_details, itemNames);
        this.context = context;
        this.itemNames = itemNames;
        this.itemDesc = itemDesc;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.listview_details, null, true);
        TextView textViewName = (TextView) row.findViewById(R.id.textViewCountry);
        TextView textViewDesc = (TextView) row.findViewById(R.id.textViewCapital);
        ImageView imageItem = (ImageView) row.findViewById(R.id.imageViewFlag);

        textViewName.setText(itemNames[position]);
        textViewDesc.setText(itemDesc[position]);
        imageItem.setImageResource(imageid[position]);
        return  row;
    }
}