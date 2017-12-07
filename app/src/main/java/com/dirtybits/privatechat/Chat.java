package com.dirtybits.privatechat;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chat extends AppCompatActivity {

    FloatingActionButton fab;
    ListView chatList;
    List<String> list;
    ArrayAdapter adapter;
    EditText filterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        filterText = (EditText) findViewById(R.id.search_old_conv);
        chatList = (ListView) findViewById(R.id.chatList);
        fab = (FloatingActionButton) findViewById(R.id.fab_write_msg);

        //TODO: get usernames from the server
        String[] values = {"user1", "user2", "user3", "user4", "user5", "user6", "user7", "user8", "user9", "user10", "user11", "user12", "user13"};
        list = new ArrayList<String>();
        Collections.addAll(list,values);

        adapter = new ArrayAdapter(Chat.this, android.R.layout.simple_list_item_1, list);
        chatList.setAdapter(adapter);

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make Toast when click
                Toast.makeText(getApplicationContext(), "Position " + position + 1, Toast.LENGTH_LONG).show();
            }
        });

        filterText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Chat.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //go to NewConv Activity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Chat.this, NewConv.class));
            }
        });
    }
}
