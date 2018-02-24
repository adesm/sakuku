package com.example.android.iak3_bproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.iak3_bproject.R;
import com.example.android.iak3_bproject.entity.Cuaca;
import com.example.android.iak3_bproject.entity.Cuaca2;

import java.util.List;

/**
 * Created by adesm on 03/02/18.
 */

public class CuacaAdapter extends ArrayAdapter<Cuaca2> {

    private final AppCompatActivity context;
//    private final  Cuaca[] cuacas;
    private final List<Cuaca2> cuaca2s;


    public CuacaAdapter(AppCompatActivity context1, List<Cuaca2> cuaca2s) {
        super(context1, R.layout.list_test,cuaca2s);
        this.context = context1;
        this.cuaca2s = cuaca2s;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.list_test,null,true);
        TextView name = (TextView)rowView.findViewById(R.id.name);
        TextView desc = (TextView)rowView.findViewById(R.id.desc);
        TextView desc2 = (TextView)rowView.findViewById(R.id.desc2);
        name.setText(cuaca2s.get(position).getDate());
        desc.setText(cuaca2s.get(position).getTemp());
        desc2.setText(cuaca2s.get(position).getWeather());
        return rowView;
    }
}
