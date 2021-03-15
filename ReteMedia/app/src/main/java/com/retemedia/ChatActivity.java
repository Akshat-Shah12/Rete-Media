package com.retemedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    private EditText editText;
    private ChatData[] chatData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        TextView textView = findViewById(R.id.TopicText);
        textView.setText(getIntent().getStringExtra("name"));
        firestore = FirebaseFirestore.getInstance();
        documentReference = firestore.collection("Topics").document(getIntent().getStringExtra("name"));
        editText = findViewById(R.id.messageToSend);
        loadMessages();
    }

    public void sendMessage(View view)
    {
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                int message_count;
                try {
                    message_count = Integer.parseInt(documentSnapshot.get("count").toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    message_count=0;
                }
                Map<String,Object> map = new HashMap<>();
                for(int i=1;i<=message_count;i++)
                {
                    map.put("message"+i,documentSnapshot.get("message"+i).toString());
                }
                if(editText.getText().toString().trim().length()>0) map.put("message"+(message_count+1),UserInfo.getUsername()+"\t"+
                        System.currentTimeMillis()+"\t"+editText.getText().toString());
                else{
                    Toast.makeText(getApplicationContext(),"Can't send empty message",Toast.LENGTH_SHORT).show();
                    return;
                }
                map.put("count",String.valueOf(message_count+1));
                Log.println(Log.ASSERT,"message",UserInfo.getUsername()+"\t"+
                        System.currentTimeMillis()+"\t"+editText.getText().toString());
                documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadMessages();
                    }
                });
                editText.setText("");
            }
        });
    }

    private void loadMessages() {
        if(UserInfo.getUsername()==null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        RecyclerView recyclerView = findViewById(R.id.chat_messages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    chatData = new ChatData[Integer.parseInt(documentSnapshot.get("count").toString())];
                } catch (Exception e) {
                    chatData=new ChatData[0];
                }
                for(int i=0;i<chatData.length;i++)
                {
                    chatData[i] = new ChatData(UserInfo.getUsername(),documentSnapshot.get("message"+(i+1)).toString());
                }
                ChatAdapter adapter = new ChatAdapter(chatData);
                recyclerView.setAdapter(adapter);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                    }
                },200);
            }
        });
    }
}