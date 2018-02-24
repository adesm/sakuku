package com.example.android.iak3_bproject;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.iak3_bproject.adapter.CuacaAdapter;
import com.example.android.iak3_bproject.entity.Cuaca;
import com.example.android.iak3_bproject.entity.Cuaca2;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ListView tesList;
    private Cuaca[] cuacas;
    private List<Cuaca2> cuaca2s;
    private AlertDialog.Builder alert;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String JSON_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Bandung&APPID=2939b4f9a70e7dd25e181b06ab14bc5d&mode=json&units=metric&cnt=17";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);
//        initData();
        tesList = (ListView)findViewById(R.id.tes_list_view);
        alert = new AlertDialog.Builder(this);
        cuaca2s = new ArrayList<Cuaca2>();

        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)Main2Activity.this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                cuaca2s.clear();
                RequestParams params = new RequestParams();
                requestWeather(params);
            }
        });

        CuacaAdapter cuacaAdapter = new CuacaAdapter(this,cuaca2s);
        tesList.setAdapter(cuacaAdapter);

        tesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main2Activity.this,DetailCuaca.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",cuaca2s.get(position).getDate());
                bundle.putString("desc",cuaca2s.get(position).getTemp());
                bundle.putString("desc2",cuaca2s.get(position).getWeather());

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    private void initData(){
        cuacas = new Cuaca[]{
                new Cuaca("hujan","hujan hujan datang"),
                new Cuaca("salju","salju hujan datang"),
                new Cuaca("petir","petir hujan datang"),
                new Cuaca("panas","panas hujan datang"),
        };
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        cuaca2s.clear();
        RequestParams params = new RequestParams();
        requestWeather(params);
    }

    public String convertDateTime(long datetime){
        Date date = new Date(datetime * 1000);
        Format dateFormat = new SimpleDateFormat("EEE, dd, MM");

        return dateFormat.format(date);
    }

    private void requestWeather(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();

        swipeRefreshLayout.setRefreshing(true);
        client.get(JSON_URL,params,new AsyncHttpResponseHandler(){
          public  void onSuccess(String response){
              swipeRefreshLayout.setRefreshing(false);
              try {
                  JSONObject weather = new JSONObject(response);
                  JSONArray listWeather = weather.getJSONArray("list");

                  for (int i=0; i< listWeather.length(); i++){
                      JSONObject wdata = listWeather.getJSONObject(i);
                      String dt = convertDateTime(wdata.getLong("dt"));

                      JSONObject temp = wdata.getJSONObject("temp");
                      String tp = String.valueOf(temp.getInt("day"));

                      JSONArray main = wdata.getJSONArray("weather");
                      String w = main.getJSONObject(0).getString("main");
                      cuaca2s.add(new Cuaca2(dt,tp,w));
                  }

                  CuacaAdapter adapter = new CuacaAdapter(Main2Activity.this,cuaca2s);
                  tesList.setAdapter(adapter);
              }catch (Exception e){
                  alert.setTitle("Error woy");
                  alert.setIcon(android.R.drawable.ic_dialog_alert);
                  alert.setMessage("Ini error bos, coba lagi");
                  alert.show();
              }
          }
          public void onFailure(int statusCode, Throwable error, String content){
              swipeRefreshLayout.setRefreshing(false);

              alert.setTitle("Error");
              alert.setIcon(android.R.drawable.ic_dialog_alert);
              alert.setMessage("Ini error bro, coba lagi");
              alert.show();
          }
        });
    }
}
