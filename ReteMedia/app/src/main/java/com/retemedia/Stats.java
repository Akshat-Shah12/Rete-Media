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

public class Stats extends Fragment {
    private Context context;
    public Stats(Context context)
    {
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats,container,false);
        StatsData data[] = new StatsData[10];
        for(int i=0;i<10;i++)
        {
            data[i] = new StatsData("Akshat Shah",null,"Senior Developer");
        }
        RecyclerView recyclerView = view.findViewById(R.id.statsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        StatsAdapter adapter = new StatsAdapter(data,context);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
