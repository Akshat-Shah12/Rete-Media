package com.retemedia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.logging.Handler;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ChatData[] chatData;
    private ChatAdapter.ViewHolder holder;
    private Context context;
    private long time;
<<<<<<< HEAD
    private int day,month;
    private int position;
=======
    private int day,month,year;
>>>>>>> fb092ed506f93245844933c7b74d73be966843b0
    public ChatAdapter(ChatData[] chatData){this.chatData=chatData;}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sample_message_item,parent,false);
        ChatAdapter.ViewHolder viewHolder = new ChatAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        this.holder=holder;
        holder.setIsRecyclable(false);
        this.position=position;
        String temp = chatData[position].getMessage();
<<<<<<< HEAD
        //akshat@rete.com\t12602134521\tyourMessage//
=======

        //akshat@rete.com126021\t34521\tyourMessage//
>>>>>>> fb092ed506f93245844933c7b74d73be966843b0

        String timeString = temp.substring(temp.indexOf('\t')+1);
        timeString = timeString.substring(0,timeString.indexOf('\t'));
        time=Long.parseLong(timeString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
<<<<<<< HEAD
        if(calendar.get(Calendar.DAY_OF_MONTH)!=day||calendar.get(Calendar.MONTH)+1!=month||position==0){
            day=calendar.get(Calendar.DAY_OF_MONTH);
            month=calendar.get(Calendar.MONTH)+1;
            String date_to_set = day+"/"+month+"/"+calendar.get(Calendar.YEAR);
            holder.date_text.setText(date_to_set);
            holder.date.setVisibility(View.VISIBLE);
=======
        if(calendar.get(Calendar.DAY_OF_MONTH)!=day||calendar.get(Calendar.MONTH)+1!=month|| calendar.get(Calendar.YEAR)!=year){
            day=calendar.get(Calendar.DAY_OF_MONTH);
            month=calendar.get(Calendar.MONTH)+1;
            year=calendar.get(Calendar.YEAR);
            holder.date_text.setText(day+"/"+month+"/"+calendar.get(Calendar.YEAR));
>>>>>>> fb092ed506f93245844933c7b74d73be966843b0
        }
        String text = chatData[position].getMessage();
        text=text.substring(text.indexOf('\t')+1);
        text=text.substring(text.indexOf('\t')+1);
        String message_time = "";
        if(calendar.get(Calendar.HOUR_OF_DAY)<10) message_time=message_time+"0";
        message_time=message_time+calendar.get(Calendar.HOUR_OF_DAY)+":";
        if(calendar.get(Calendar.MINUTE)<10) message_time=message_time+"0";
        message_time=message_time+calendar.get(Calendar.MINUTE);
        temp = chatData[position].getMessage();
        temp=temp.substring(0,temp.indexOf('\t'));
        if(text.length()<3) text=text+"   ";
        if(temp.equals(chatData[position].getUsername()))
        {
            holder.received.setVisibility(View.GONE);
            holder.sent_message.setText(text);
            holder.sent_time.setText(message_time);
        }
        else
        {
            holder.sent.setVisibility(View.GONE);
            String name = chatData[position].getMessage();
            name=name.substring(0,name.indexOf('@'));
            name=name.substring(0,1).toUpperCase()+name.substring(1);
            holder.sender.setText(name);
            holder.received_message.setText(text);
            holder.received_time.setText(message_time);
        }
    }

    @Override
    public int getItemCount() {
        return chatData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout date,sent,received;
        TextView date_text,sent_message,received_message,sender,received_time,sent_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sent=itemView.findViewById(R.id.sent_message);
            received=itemView.findViewById(R.id.received_message);
            date=itemView.findViewById(R.id.date_layout);
            date_text=itemView.findViewById(R.id.date);
            sender=itemView.findViewById(R.id.sender_text);
            sent_message=itemView.findViewById(R.id.sent_message_text);
            received_message=itemView.findViewById(R.id.received_message_text);
            sent_time=itemView.findViewById(R.id.sent_time);
            received_time=itemView.findViewById(R.id.received_time);
        }
    }
}
