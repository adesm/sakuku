package com.example.android.iak3_bproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DetailCuaca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cuaca);

        TextView title = (TextView)findViewById(R.id.title_cuaca);
        TextView desc = (TextView)findViewById(R.id.desc_cuaca);
        TextView desc2 = (TextView)findViewById(R.id.desc2_cuaca);

        if(getIntent() != null){
            Bundle bundle = getIntent().getExtras();
            title.setText(bundle.getString("title"));
            desc.setText(bundle.getString("desc"));
            desc2.setText(bundle.getString("desc2"));
        }else{
            Toast.makeText(this,"Data tidak ada",Toast.LENGTH_LONG).show();
        }
    }
}
