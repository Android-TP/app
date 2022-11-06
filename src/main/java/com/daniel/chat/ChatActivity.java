package com.daniel.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class ChatActivity extends AppCompatActivity {
    private TextView user1;
    private TextView user2;
    private Button send;
    private EditText chat;
    private String username;
    private FirebaseDatabase database;
    private DatabaseReference bdd_ref;
    private String derniere_message;
    private String last_post;
    private DatabaseReference correspondant_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.init_element();
        this.event();
    }
    private void correspondant_reference(){
        if(this.username == "user1"){
            this.correspondant_ref = this.database.getReference("user2");
        }else{
            this.correspondant_ref = this.database.getReference("user1");
        }
        // Read from the database
        this.correspondant_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                writeView("correspondant", value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                writeView("correspondant", "impossible de communiquer avec la bdd");
            }
        });

    }
    private void getBdd(){
        this.database = FirebaseDatabase.getInstance();
        this.bdd_ref = database.getReference(this.username);

        // Read from the database
        this.bdd_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                last_post = value;
                writeView("moi", value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                writeView("moi", "impossible de communiquer avec la bdd");
            }
        });

    }
    private void init_element(){
        this.user1 = (TextView)findViewById(R.id.contentme);
        this.user2 = (TextView)findViewById(R.id.content2);
        this.send = (Button)findViewById(R.id.send);
        this.chat = (EditText)findViewById(R.id.chat);

        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        String name = pref.getString("username", null);
        if(name==null){
            this.username = "user1";
        }else{
            this.username = name;
        }
        this.getBdd();
        this.last_post = "...";
        this.correspondant_reference();
    }
    private void writeView(String user, String data){
        if(user=="moi"){

            this.user1.setText(this.last_post);

        }else{
            //other user
                this.user2.setText(data);
        }
    }
    private void event(){
        this.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bdd_ref.setValue(chat.getText());
            }
        });
    }

    private void send_message(){

    }
}