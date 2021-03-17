package com.retemedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateUserActivity extends AppCompatActivity {
    private Dialog dialog;
    private String username,password,usertype;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        dialog=new Dialog(this);
        auth=FirebaseAuth.getInstance();
    }

    public void userCreation(View view) {
        username=((EditText)findViewById(R.id.username)).getText().toString();
        password=((EditText)findViewById(R.id.password)).getText().toString();
        usertype=((TextView)findViewById(R.id.usertype)).getText().toString();
        if(username.trim().length()==0||password.trim().length()<6||usertype.equals("Select a type"))
            return;
        auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User Added",Toast.LENGTH_SHORT).show();
                    DocumentReference documentReference=FirebaseFirestore.getInstance().collection("Users").document(username);
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            HashMap<String,Object> map=new HashMap<String, Object>();
                            map.put("Type",usertype);
                            documentReference.set(map);
                        }
                    });
                    finish();
                }
                else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(getApplicationContext(),"User Already Exists",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showUserTypes(View view) {
        dialog.setContentView(R.layout.user_type);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        ListView listView=dialog.findViewById(R.id.usertype_list);
        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add("Client");
        arrayList.add("Employee");
        arrayList.add("Manager");
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView=findViewById(R.id.usertype);
                textView.setText(arrayList.get(position));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}