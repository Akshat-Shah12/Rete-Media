package com.retemedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddGroupActivity extends AppCompatActivity {
    private EditText groupName,groupParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        groupName = findViewById(R.id.group_name);
        groupParticipants = findViewById(R.id.group_participants);
    }

    public void createNewGroup(View view) {
        String name = groupName.getText().toString();
        String participants = groupParticipants.getText().toString().replace('\n',',').trim()+",";
        if(name.trim().length()==0|| participants.trim().length()==0)
        {
            Toast.makeText(getApplicationContext(),"Invalid data",Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i=0;i<31;i++){
            char c = (char) i;
            if(name.contains(String.valueOf((char) i))){
                Toast.makeText(getApplicationContext(),"Group Name cannot contain special characters",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        do
        {
            String temp = participants;
            if(participants.contains(",")) {
                temp = participants.substring(0, participants.indexOf(','));
                try {
                    participants = participants.substring(participants.indexOf(',') + 1);
                } catch (Exception exception) {
                    participants="";
                    continue;
                }
            }
            final String userId = temp;
            if(temp==null||temp.length()==0) continue;
            FirebaseFirestore.getInstance().collection("Group Data").document(temp)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    HashMap<String,Object> map = new HashMap<>();
                    int count = 0,i;
                    if(documentSnapshot.getString("count")!=null) count=Integer.parseInt(documentSnapshot.getString("count"));
                    for(i=1;i<=count;i++) map.put("group"+i,documentSnapshot.getString("group"+i));
                    map.put("group"+(count+1),name);
                    map.put("count",(count+1)+"");
                    FirebaseFirestore.getInstance().collection("Group Data").document(userId).set(map);
                }
            });
        }while (participants.length()>0);
        Toast.makeText(getApplicationContext(),"Group Added Successfully",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.side_zoom_out);
    }
}