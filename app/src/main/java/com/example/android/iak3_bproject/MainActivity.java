package com.example.android.iak3_bproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    static ArrayList<Patungan> patungans;
    private String name = "";
    private int num = 0, price = 0;
    private ListView listView;
    private PatunganAdapter adapter;
    private EditText inputName, inputNum, inputPrice, inputDate;
    private TextView txtTitle, txtDesc;
    private Calendar calendar;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    Gson gson;
    static DecimalFormat idr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTitle = (TextView)findViewById(R.id.no_list_title);
        txtDesc = (TextView)findViewById(R.id.no_list_desc);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPrefs.edit();
        gson = new Gson();

        patungans = readStorage();

        if(patungans==null) {
            patungans = new ArrayList<Patungan>();
        }
        if(patungans.size()>0){
            txtTitle.setVisibility(View.GONE);
            txtDesc.setVisibility(View.GONE);
        }

        idr = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols rupiah = new DecimalFormatSymbols();

        rupiah.setCurrencySymbol("Rp. ");
        rupiah.setMonetaryDecimalSeparator(',');
        idr.setDecimalFormatSymbols(rupiah);
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new PatunganAdapter(this, patungans);
        listView = (ListView)findViewById(R.id.patungan_list_view);
        listView.setAdapter(adapter);
        final Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patungan patungan = patungans.get(position);
                Intent detail = new Intent(context, DetailPatunganActivity.class);
                detail.putExtra("patungan",patungan);
                detail.putExtra("index",position);
                startActivity(detail);
            }
        });
    }

    public void addPatungan(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View mView = layoutInflater.inflate(R.layout.activity_add_patungan, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(mView);

        inputName = (EditText)mView.findViewById(R.id.input_name);
        inputNum = (EditText)mView.findViewById(R.id.input_num);
        inputPrice = (EditText)mView.findViewById(R.id.input_price);
        inputDate = (EditText)mView.findViewById(R.id.input_date);

        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                inputDate.setText(updateInput(calendar));
            }
        };

        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(R.string.txt_btn_person_add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        try {
                            String name = inputName.getText().toString();
                            int num = Integer.parseInt(inputNum.getText().toString());
                            int price = Integer.parseInt(inputPrice.getText().toString());
                            Patungan patungan = new Patungan(name, num, price);
                            setInfoPatungan(patungan);
                            MainActivity.patungans.add(patungan);
                            adapter.notifyDataSetChanged();
                            txtTitle.setVisibility(View.GONE);
                            txtDesc.setVisibility(View.GONE);
                        }catch (Exception e){
                            Log.e("Exception: ",e.getMessage());
                        }
                    }
                })
                .setNegativeButton(R.string.txt_btn_person_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toStorage(patungans);
    }

    public static String updateInput(Calendar calendar){
        String format = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(calendar.getTime());
    }

    public void setInfoPatungan(Patungan patungan){
        int num = patungan.getNumParticipants();
        int onePay = patungan.getOnePersonPay();
        patungan.setTotal(num*onePay);
        patungan.setDueDate(calendar);
    }

    public void toStorage(ArrayList<Patungan> patungan){
        String json = gson.toJson(patungan);
        editor.putString("patungan", json);
        editor.commit();
    }

    public ArrayList<Patungan> readStorage(){
        String json = sharedPrefs.getString("patungan", null);
        Type type = new TypeToken<ArrayList<Patungan>>() {}.getType();
        ArrayList<Patungan> arrayList = gson.fromJson(json, type);
        return  arrayList;
    }

}
