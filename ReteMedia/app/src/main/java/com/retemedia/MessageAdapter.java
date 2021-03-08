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
        TextView title,message;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.topic_title);
            message = itemView.findViewById(R.id.message);
            image = itemView.findViewById(R.id.profile_image);
        }
    }
}
