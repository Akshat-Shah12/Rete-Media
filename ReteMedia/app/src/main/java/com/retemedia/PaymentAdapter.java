package com.retemedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private PaymentData[] data;
    public PaymentAdapter(PaymentData[] data){ this.data = data;}
    private ViewHolder holder;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sample_payment,parent,false);
        PaymentAdapter.ViewHolder viewHolder = new PaymentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.holder=holder;
        holder.method.setText(data[position].getMethod());
        holder.date.setText(data[position].getDate());
        holder.amount.setText(Long.toString(data[position].getAmount()));
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView method,date, amount;
        public ViewHolder(View view) {
            super(view);
            method = view.findViewById(R.id.payMet);
            date=view.findViewById(R.id.dateOfPay);
            amount=view.findViewById(R.id.money);
        }
    }
}
