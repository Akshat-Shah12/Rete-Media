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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        try {
            FileInputStream inputStream=getApplicationContext().openFileInput("user.txt");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            username=bufferedReader.readLine();
            password=bufferedReader.readLine();
            editText=findViewById(R.id.LoginId);
            editText.setText(username);
            editText=findViewById(R.id.Password);
            editText.setText(password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //findViewById(R.id.submitBtn).setBackgroundColor(Color.parseColor("#1E88E5"));
    }
    public void forgotPassword(View view){
        FirebaseAuth.getInstance().sendPasswordResetEmail(username).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Email sent",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Check your username",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void submitBtn(View view){
        editText=findViewById(R.id.LoginId);
        username = editText.getText().toString();
        editText=findViewById(R.id.Password);
        password= editText.getText().toString();
        ////
        /*if(username.length()==0 && password.length()==0){
            Intent intent = new Intent(getApplicationContext(), AddPaymentActivity.class);
            startActivity(intent);
        }*/
        ////
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(username.trim().length()==0||password.trim().length()<6)
        {
            Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
            return;
        }


        document = firestore.collection("Users").document(username);
        auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login details verified", Toast.LENGTH_SHORT).show();
                    user = auth.getCurrentUser();
                    document = firestore.collection("Users").document(username);
                    /*document.get().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Map<String, Object> info = new HashMap<>();
                            info.put("Type", userType);
                            document.set(info);
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            intent.putExtra("type", "user1");
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_start,R.anim.right_end);
                            finish();
                        }
                    });*/
                    document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            try {
                                Toast.makeText(getApplicationContext(), documentSnapshot.getString("Type"), Toast.LENGTH_SHORT);
                                userType = documentSnapshot.getString("Type");
                                Toast.makeText(getApplicationContext(), userType, Toast.LENGTH_SHORT).show();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                userType = "Employee";
                            }
                            Map<String, Object> info = new HashMap<>();
                            if(userType==null||userType.equals("null")) userType="Employee";
                            info.put("Type", userType);
                            document.set(info);
                            UserInfo.setUsername(username);
                            File details=new File(getApplicationContext().getFilesDir(),"user.txt");
                            if(!details.exists()){
                                try{
                                    details.createNewFile();
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                FileOutputStream outputStream=getApplicationContext().openFileOutput("user.txt",MODE_PRIVATE);
                                outputStream.write((username+"\n"+password+"\n").getBytes());
                                outputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            intent.putExtra("type", "user1");
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_start,R.anim.right_end);
                            finish();
                        }
                    });
                }
                else if(task.isCanceled()){
                    Toast.makeText(getApplicationContext(), "Login ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Incorrect password",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}