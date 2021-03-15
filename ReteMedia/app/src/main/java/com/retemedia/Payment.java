package com.retemedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Payment extends Fragment {
    private Context context;

    public Payment(Context context)
    {
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_frag,container,false);
        CardView card = view.findViewById(R.id.addPayment) ;
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,AddPaymentActivity.class);
                startActivity(i);
            }
        });
        PaymentData data[] = new PaymentData[10];
        for(int i=0;i<10;i++)
        {
            data[i] = new PaymentData("Google Pay","05/06/20",i*1000);
        }
        RecyclerView recyclerView = view.findViewById(R.id.allPayments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        PaymentAdapter adapter = new PaymentAdapter(data);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
