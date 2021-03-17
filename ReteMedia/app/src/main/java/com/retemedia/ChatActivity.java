package com.retemedia;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity{

    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    private DatabaseReference databaseReference;
    private EditText editText;
    private ChatData[] chatData;
    private int message_count;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.chat_messages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));    TextView textView = findViewById(R.id.TopicText);
        textView.setText(getIntent().getStringExtra("name"));
        firestore = FirebaseFirestore.getInstance();
        documentReference = firestore.collection("Topics").document(getIntent().getStringExtra("name"));
        editText = findViewById(R.id.messageToSend);
        FirebaseDatabase.getInstance().getReference().child(getIntent().getStringExtra("name")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadMessages();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void sendMessage(View view)
    {
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
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
                databaseReference =  FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
                final String message = editText.getText().toString();
                editText.setText("");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (message.trim().length() > 0) {
                            map.put("message" + (message_count + 1), UserInfo.getUsername() + "\t" +
                                    (System.currentTimeMillis() + snapshot.getValue(Long.class)) + "\t" + message);
                        }
                        else {
                            //Toast.makeText(getApplicationContext(), "Can't send empty message", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        map.put("count", String.valueOf(message_count + 1));
                        documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                FirebaseDatabase.getInstance().getReference().child(getIntent().getStringExtra("name")).setValue(System.currentTimeMillis()+"");
                            }
                        });
                        editText.setText("");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


    private void loadMessages() {
        if(UserInfo.getUsername()==null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
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
                if(chatData.length>0) chatData[0].setDateChanged(true);
                //akshat@rete.com\t12602134521\tyourMessage//
                for(int i=0;i<chatData.length-1;i++)
                {
                    String t1,t2;
                    t1=chatData[i].getMessage();
                    t2=chatData[i+1].getMessage();
                    t1=t1.substring(t1.indexOf('\t')+1);
                    t2=t2.substring(t2.indexOf('\t')+1);
                    t1=t1.substring(0,t1.indexOf('\t'));
                    t2=t2.substring(0,t2.indexOf('\t'));
                    Calendar c1,c2;
                    c1=Calendar.getInstance();
                    c2=Calendar.getInstance();
                    c1.setTimeInMillis(Long.parseLong(t1));
                    c2.setTimeInMillis(Long.parseLong(t2));
                    if(c1.get(Calendar.DAY_OF_YEAR)!=c2.get(Calendar.DAY_OF_YEAR)
                            || c1.get(Calendar.YEAR)!=c2.get(Calendar.YEAR)) chatData[i+1].setDateChanged(true);
                }
                ChatAdapter adapter = new ChatAdapter(chatData);
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
            }
        });
    }
}