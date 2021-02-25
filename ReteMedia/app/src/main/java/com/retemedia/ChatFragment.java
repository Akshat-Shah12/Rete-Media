package com.retemedia;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends Fragment {
    private Context context;
    public ChatFragment(Context context)
    {
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat,container,false);
        MessageData data[] = new MessageData[10];
        for(int i=0;i<10;i++)
        {
            data[i] = new MessageData(null,"Topic "+(i+1),"Do it ASAP");
        }
        RecyclerView recyclerView = view.findViewById(R.id.chat_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MessageAdapter adapter = new MessageAdapter(data);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
