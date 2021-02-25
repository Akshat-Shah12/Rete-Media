package com.retemedia;

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
    public MessageAdapter(MessageData[] messageData)
    {
        this.messageData = messageData;
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
