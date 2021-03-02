package com.retemedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private String username,password;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private DocumentReference document;
    private String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestore = FirebaseFirestore.getInstance();

        //findViewById(R.id.submitBtn).setBackgroundColor(Color.parseColor("#1E88E5"));
    }
    public void forgotPassword(View view){

    }
    public void submitBtn(View view){
        editText=findViewById(R.id.LoginId);
        username = editText.getText().toString();
        editText=findViewById(R.id.Password);
        password= editText.getText().toString();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(username.trim().length()==0||password.trim().length()<6)
        {
            Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
            return;
        }
        document = firestore.collection("Users").document(username);
        //auth.signInWithEmailAndPassword(username,password);
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    if (password.equals(documentSnapshot.getString("Password")))
                    {
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                        intent.putExtra("type",userType);
                        intent.putExtra("id",username);
                        startActivity(intent);
                    }
                    else Toast.makeText(getApplicationContext(),"Incorrect Password",Toast.LENGTH_SHORT).show();
                } catch (Exception exception) {
                    Toast.makeText(getApplicationContext(),"User doesn't exist",Toast.LENGTH_SHORT).show();
                    exception.printStackTrace();
                }
            }
        });
        Toast.makeText(getApplicationContext(),"Password : "+password,Toast.LENGTH_SHORT).show();
    }
}