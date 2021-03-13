package com.retemedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ChatData[] chatData;
    private ChatAdapter.ViewHolder holder;
    private Context context;
    private long time;
    private int day,month;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sample_message_item,parent,false);
        ChatAdapter.ViewHolder viewHolder = new ChatAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.holder=holder;
        String temp = chatData[position].getMessage();

        //akshat@rete.com\t12602134521\tyourMessage//

        time=Long.parseLong(temp.substring(temp.indexOf('\t')+1,temp.substring(temp.indexOf('\t')+1).indexOf('\t')));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        if(calendar.get(Calendar.DAY_OF_MONTH)!=day||calendar.get(Calendar.MONTH)+1!=month){
            day=calendar.get(Calendar.DAY_OF_MONTH);
            month=calendar.get(Calendar.MONTH)+1;
            holder.date_text.setText(day+"/"+month+"/"+calendar.get(Calendar.YEAR));
        }
        else holder.date.setVisibility(View.GONE);
        if(chatData[position].getMessage().substring(0,chatData[position].getMessage().indexOf('\t')).equals(chatData[position].getUsername()))
        {
            holder.received.setVisibility(View.GONE);
            //set text here
        }
        else
        {
            holder.sent.setVisibility(View.GONE);
            //set text here
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout date,sent,received;
        TextView date_text,sent_message,received_message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sent=itemView.findViewById(R.id.sent_message);
            received=itemView.findViewById(R.id.received_message);
            date=itemView.findViewById(R.id.date_layout);
            date_text=itemView.findViewById(R.id.date);
            sent_message=itemView.findViewById(R.id.sent_message_text);
            received_message=itemView.findViewById(R.id.received_message_text);
        }
    }
}
