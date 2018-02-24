package com.example.android.iak3_bproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adesm on 10/12/17.
 */

public class PatunganAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Patungan> patunganData;

    public PatunganAdapter(Context context, ArrayList<Patungan> patunganData) {
        Context context1 = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.patunganData = patunganData;
    }

    @Override
    public int getCount() {
        return patunganData.size();
    }

    @Override
    public Object getItem(int position) {
        return patunganData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.list_item_patungan,parent,false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.patungan_list_name);
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.patungan_list_num);
        TextView detailTextView = (TextView) rowView.findViewById(R.id.patungan_list_price);
        TextView dateTxt = (TextView) rowView.findViewById(R.id.patungan_list_date);

        Patungan patungan = (Patungan) getItem(position);
        titleTextView.setText(patungan.getName());
        String txtSet = patungan.getNumParticipants()+" "+parent.getResources().getString(R.string.txt_participant);
        subtitleTextView.setText(txtSet);
        int remain = patungan.getRemain();
        int total = patungan.getTotal();
        txtSet = MainActivity.idr.format(remain)+"/"+MainActivity.idr.format(total);
        detailTextView.setText(txtSet);
        String dateString = MainActivity.updateInput(patungan.getDueDate());
        dateTxt.setText(dateString);
        return rowView;
    }
}
