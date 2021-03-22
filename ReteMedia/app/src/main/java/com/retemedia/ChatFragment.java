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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatFragment extends Fragment {
    private Context context;
    private Activity activity;
    private MessageData[] data;
    private String[] group;
    private View layout;
    private String type="no_type";
    public ChatFragment(){}
    public ChatFragment(Context context, Activity activity, String type)
    {
        this.context = context;
        this.activity = activity;
        this.type=type;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat, container, false);
        layout = view;
        if(!type.equals("Manager")) view.findViewById(R.id.add_group).setVisibility(View.GONE);
        if(UserInfo.getUsername()==null) return view;
        FirebaseFirestore.getInstance().collection("Group Data").document(UserInfo.getUsername())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    group = new String[Integer.parseInt(documentSnapshot.getString("count"))];
                } catch (Exception e) {
                    e.printStackTrace();
                    group=new String[0];
                }
                for(int i=0;i< group.length;i++)
                {
                    group[i] = documentSnapshot.getString("group"+(i+1));
                }
                data = new MessageData[group.length];
                getMessagesFromServer(group,0);
                view.findViewById(R.id.add_group).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context,AddGroupActivity.class));
                        activity.overridePendingTransition(R.anim.side_zoom_in,R.anim.fade_out);
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(group!=null) getMessagesFromServer(group,0);
    }

    private void getMessagesFromServer(String[] string, int position){
        if(position>=string.length)
        {
            RecyclerView recyclerView = layout.findViewById(R.id.chat_recycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            for(int i=0;i<data.length-1;i++)
            {
                for(int j=0;j<data.length-1-i;j++)
                {
                    if(data[j].getTime()<data[j+1].getTime())
                    {
                        MessageData temp = data[j];
                        data[j] = data[j+1];
                        data[j+1] = temp;
                    }
                }
            }
            MessageAdapter adapter = new MessageAdapter(data,context);
            recyclerView.setAdapter(adapter);
            return;
        }
        FirebaseFirestore.getInstance().collection("Topics").document(string[position])
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String lastMessage = "No Messages";
                int count = 0;
                Map<String, Object> map = (Map<String, Object>) documentSnapshot.get("Chats");
                try{
                    count=Integer.parseInt((String)map.get("count"));
                } catch (Exception e) {
                    count=0;
                }
                if(map==null||count==0) {
                    data[position] = new MessageData("abc", string[position], "No messages yet", System.currentTimeMillis());
                    getMessagesFromServer(string, position + 1);
                    return;
                }
                lastMessage = (String) map.get("message"+count);
                String sender = lastMessage.substring(0,lastMessage.indexOf('@'));
                sender = sender.substring(0,1).toUpperCase()+sender.substring(1);
                lastMessage = lastMessage.substring(lastMessage.indexOf('\t')+1);
                String messageTime = lastMessage.substring(0,lastMessage.indexOf('\t'));
                lastMessage = lastMessage.substring(lastMessage.indexOf('\t')+1);
                if(sender.toLowerCase().equals(UserInfo.getUsername().substring(0,UserInfo.getUsername().indexOf('@')))) sender="You";
                data[position] = new MessageData("abc",
                        string[position],
                        sender+" : "+lastMessage.replace('\n',' '),
                        Long.parseLong(messageTime));
                getMessagesFromServer(string,position+1);
            }
        });
    }
}
