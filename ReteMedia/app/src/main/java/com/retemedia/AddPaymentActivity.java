package com.retemedia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AddPaymentActivity extends AppCompatActivity {

    private Calendar calendar;
    String DATE;
    EditText amount,pay;
    TextView date1;
    String final_amount;
    String final_pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        date1 = findViewById(R.id.dateText);
        amount=findViewById(R.id.amount);
        pay=findViewById(R.id.pay);
    }

    public void handleDateButton(View view){

        int year1=calendar.get(Calendar.YEAR);
        int month1=calendar.get(Calendar.MONTH);
        int day1=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date1.setText(dayOfMonth+"-" + (month+1)+"-"+year);
                DATE=dayOfMonth+"-" + (month+1)+"-"+year;
            }
        },year1,month1,day1);
        datePickerDialog.show();
    }

    public void submitBtn(View view){
        final_amount= amount.getText().toString();
        final_pay=pay.getText().toString();

    }
}