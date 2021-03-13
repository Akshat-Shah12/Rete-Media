package com.retemedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        TextView textView = findViewById(R.id.TopicText);
        textView.setText(getIntent().getStringExtra("name"));
    }

    /*public void sendMessage(View view)
    {
        FirebaseFirestore
    }*/
}