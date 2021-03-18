package com.retemedia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private Context context;
    private Activity activity;
    public ChatFragment(){}
    public ChatFragment(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat, container, false);
        if(UserInfo.getUsername()==null) return view;
        FirebaseFirestore.getInstance().collection("Group Data").document(UserInfo.getUsername())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String[] group = new String[Integer.parseInt(documentSnapshot.getString("count"))];
                for(int i=0;i< group.length;i++)
                {
                    group[i] = documentSnapshot.getString("group"+(i+1));
                }
                MessageData[] data = new MessageData[group.length];
                for(int i=0;i<data.length;i++)
                {
                    data[i] = new MessageData("abc",group[i],"Do it Asap");
                }
                RecyclerView recyclerView = view.findViewById(R.id.chat_recycler);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                MessageAdapter adapter = new MessageAdapter(data,context);
                recyclerView.setAdapter(adapter);
                view.findViewById(R.id.add_group).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context,AddGroupActivity.class));
                        activity.overridePendingTransition(R.anim.side_zoom_in,R.anim.no_anim);
                    }
                });
            }
        });
        return view;
    }
}
