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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
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
        calendar=Calendar.getInstance();
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
        final_pay=pay.getText().toString();//Date
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference reference =  firestore.collection("Payments").document("Pizza Hut");
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String[] payment;
                try {
                    payment = new String[Integer.parseInt(documentSnapshot.getString("transaction"))];
                }
                catch (Exception e){
                    payment=new String[0];
                }
                for(int i=0;i<payment.length;i++){
                    payment[i]=documentSnapshot.getString("trans"+(i+1));
                }
                HashMap<String,Object> pay = new HashMap<>();
                for(int i=0;i<payment.length;i++){
                    pay.put("trans"+(i+1),payment[i]);
                }
                pay.put("trans"+(payment.length+1),final_amount+"\t"+final_pay);
                pay.put("transaction",(payment.length+1)+"");
                reference.set(pay);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.side_zoom_out);
    }
}