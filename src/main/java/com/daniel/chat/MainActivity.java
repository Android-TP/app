package com.daniel.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private Button user1;
    private Button user2;
    private SharedPreferences username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init_element();
        this.event();
    }
    private void init_element(){
        this.user1 = (Button)findViewById(R.id.user1);
        this.user2 = (Button)findViewById(R.id.user2);
    }
    private  void event(){
        user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(await_intent(ChatActivity.class));
                Toast.makeText(MainActivity.this, "demarrage", Toast.LENGTH_SHORT).show();
            }
        });
        user2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(await_intent(ChatActivity.class));
            }
        });
    }

    private Intent await_intent(Class context){

        return new Intent(MainActivity.this, ChatActivity.class);
    }
}