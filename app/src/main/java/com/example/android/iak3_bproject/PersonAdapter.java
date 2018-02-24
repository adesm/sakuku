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

public class PersonAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Person> personData;

    public PersonAdapter(Context context, ArrayList<Person> personData) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.personData = personData;
    }

    @Override
    public int getCount() {
        return personData.size();
    }

    @Override
    public Object getItem(int position) {
        return personData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.list_item_person,parent,false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.person_list_name);
        TextView priceTextView = (TextView) rowView.findViewById(R.id.person_list_price);
        TextView dateTextView = (TextView) rowView.findViewById(R.id.person_list_date);
        Person person = (Person) getItem(position);
        titleTextView.setText(person.getName());
        priceTextView.setText(MainActivity.idr.format(person.getNumberOfPayment()));
        dateTextView.setText(MainActivity.updateInput(person.getPaymentDate()));
        return rowView;
    }
}
