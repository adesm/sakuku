package com.example.android.iak3_bproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

public class DetailPatunganActivity extends AppCompatActivity {

    private PersonAdapter adapter;
    private Patungan patungan;
    private TextView txtPrice;
    private TextView txtNum;
    private TextView txtTitle, txtDesc;
    int idx;
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_patungan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        patungan = (Patungan) intent.getSerializableExtra("patungan");
        idx = intent.getIntExtra("index",-1);

        txtTitle = (TextView)findViewById(R.id.no_list_title);
        txtDesc = (TextView)findViewById(R.id.no_list_desc);

        if(patungan.getPersons().size()>=1) {
            txtTitle.setVisibility(View.GONE);
            txtDesc.setVisibility(View.GONE);
        }
        String name = patungan.getName();
        int num = patungan.getNumParticipants();
        int price = patungan.getTotal();
        final int onePay = patungan.getOnePersonPay();
        int remain = patungan.getRemain();
        Calendar dueDate = patungan.getDueDate();

        TextView txtName = (TextView) findViewById(R.id.txt_name);
        txtNum = (TextView)findViewById(R.id.txt_num);
        txtPrice = (TextView)findViewById(R.id.txt_price);
        TextView txtOnePay = (TextView) findViewById(R.id.txt_one_pay);
        TextView txtDate = (TextView) findViewById(R.id.txt_date);

        txtName.setText(name);
        String txtSet = MainActivity.idr.format(remain)+"/"+MainActivity.idr.format(price);
        txtPrice.setText(txtSet);
        txtSet = getResources().getString(R.string.txt_participant)+": "+patungan.getPersons().size()+"/"+num;
        txtNum.setText(txtSet);
        txtSet = getResources().getString(R.string.txt_per_person)+": "+MainActivity.idr.format(onePay);
        txtOnePay.setText(txtSet);
        txtSet = getResources().getString(R.string.txt_duedate)+": "+MainActivity.updateInput(dueDate);
        txtDate.setText(txtSet);


        adapter = new PersonAdapter(this, patungan.getPersons());
        ListView listView = (ListView) findViewById(R.id.person_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = patungan.getPersons().get(position);
                if(person.getNumberOfPayment()<onePay) {
                    LayoutInflater layoutInflater = LayoutInflater.from(DetailPatunganActivity.this);
                    View mView = layoutInflater.inflate(R.layout.send_person_email, null);
                    AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(DetailPatunganActivity.this);
                    alertDialogBuilderUserInput.setView(mView);

                    final TextView txtName = (TextView) mView.findViewById(R.id.name_dialog);
                    final TextView txtPay = (TextView) mView.findViewById(R.id.price_dialog);
                    final EditText txtMsg = (EditText) mView.findViewById(R.id.msg_input_dialog);
                    txtName.setText(person.getName());
                    txtPay.setText(MainActivity.idr.format(person.getNumberOfPayment()));
                    alertDialogBuilderUserInput
                            .setCancelable(false)
                            .setPositiveButton(R.string.txt_btn_person_send, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    try {
                                        String msg = txtMsg.getText().toString();
                                        Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.setType("text/plain");
                                        intent.putExtra(Intent.EXTRA_TEXT, msg);
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        Log.e("Exception: ", e.getMessage());
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
            }
        });
    }

    public void addPerson(View view) {
        int sizePerson = patungan.getPersons().size();
        if(sizePerson<patungan.getNumParticipants()) {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View mView = layoutInflater.inflate(R.layout.add_person, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
            alertDialogBuilderUserInput.setView(mView);


            final EditText inputName = (EditText) mView.findViewById(R.id.name_input_dialog);
            final EditText inputPay = (EditText) mView.findViewById(R.id.pay_input_dialog);
            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton(R.string.txt_btn_person_add, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            try {
                                String name = inputName.getText().toString();
                                int payment = Integer.parseInt(inputPay.getText().toString());
                                int newRemain = patungan.getRemain() + payment;
                                MainActivity.patungans.get(idx).setRemain(newRemain);
                                MainActivity.patungans.get(idx).addPerson(name, payment);
                                patungan.setRemain(newRemain);
                                patungan.addPerson(name, payment);
                                String txtSet = MainActivity.idr.format(patungan.getRemain()) + "/" + MainActivity.idr.format(patungan.getTotal());
                                txtPrice.setText(txtSet);
                                txtSet = getResources().getString(R.string.txt_participant)+": "+patungan.getPersons().size() + "/" + patungan.getNumParticipants();
                                txtNum.setText(txtSet);
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
    }
}
