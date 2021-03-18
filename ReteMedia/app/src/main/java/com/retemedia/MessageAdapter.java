package com.retemedia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private MessageData[] messageData;
    private ViewHolder holder;
    private Context context;
    public MessageAdapter(MessageData[] messageData,Context context)
    {
        this.messageData = messageData;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_sample_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.holder = holder;
        holder.title.setText(messageData[position].getTitle());
        holder.message.setText(messageData[position].getMessage());
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(messageData[position].getTime());
        String timeString = "";
        if(calendar.get(Calendar.DAY_OF_MONTH)==day
                && calendar.get(Calendar.MONTH)==month
                && calendar.get(Calendar.YEAR)==year)
        {
            if(calendar.get(Calendar.HOUR_OF_DAY)<10) timeString=timeString+"0";
            timeString=timeString+calendar.get(Calendar.HOUR_OF_DAY)+":";
            if(calendar.get(Calendar.MINUTE)<10) timeString=timeString+"0";
            timeString=timeString+calendar.get(Calendar.MINUTE)+"";
        }
        else
        {
            timeString=calendar.get(Calendar.DAY_OF_MONTH)+"/"+(1+calendar.get(Calendar.MONTH))+"/"+calendar.get(Calendar.YEAR);
        }
        holder.time.setText(timeString);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,ChatActivity.class);
                i.putExtra("name",messageData[position].getTitle());
                i.putExtra("photo",messageData[position].getImageURL());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,message,time;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.topic_title);
            message = itemView.findViewById(R.id.message);
            image = itemView.findViewById(R.id.profile_image);
            time = itemView.findViewById(R.id.time_text);
        }
    }
}
