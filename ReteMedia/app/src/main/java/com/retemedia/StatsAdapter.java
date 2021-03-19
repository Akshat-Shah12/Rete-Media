package com.retemedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {
    private ViewHolder holder;
    private StatsData[] data;
    private Context context;
    public StatsAdapter(StatsData[] data,Context context){
        this.data=data;
        this.context=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_sample_item,parent,false);
        StatsAdapter.ViewHolder viewHolder = new StatsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.holder=holder;
        holder.name.setText(data[position].getName());
        holder.jobTitle.setText(data[position].getJobTitle());
        holder.time.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,jobTitle,imageUrl,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.topic_title);
            jobTitle=itemView.findViewById(R.id.message);
            time=itemView.findViewById(R.id.time_text);
            //imageUrl=itemView.findViewById(R.id.profile_image);
        }
    }
}
