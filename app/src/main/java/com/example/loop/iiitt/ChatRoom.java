package com.example.loop.iiitt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

import static co.intentservice.chatui.models.ChatMessage.Type.RECEIVED;


/**
 * Created by loop on 3/9/17.
 */

public class ChatRoom extends AppCompatActivity {

    String check;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference myRef=database.getReference("message");

        final ChatView chatView = (ChatView) findViewById(R.id.chat_view);



        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
            @Override
            public boolean sendMessage(ChatMessage chatMessage){
                // perform actual message sending
                myRef.setValue(chatMessage.getMessage().toString());
                check=chatMessage.getMessage().toString();
                return true;
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value!=check)
                    chatView.addMessage(new ChatMessage(value,100,RECEIVED));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
