package com.dirtybits.privatechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewConv extends AppCompatActivity {

    ListView friendList;
    List<String> list;
    String user;
    ArrayAdapter adapter;
    EditText filterText;
    Button addFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_conv);

        filterText = (EditText) findViewById(R.id.search_friend);
        friendList = (ListView) findViewById(R.id.friendList);
        addFriend = (Button) findViewById(R.id.addFriendButton);

        //TODO: get usernames from the server
        String[] values = {"friend1", "friend2", "friend3", "friend4", "friend5", "friend6", "friend7"};
        list = new ArrayList<String>();
        Collections.addAll(list,values);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = filterText.getText().toString();
                //TODO: verify that the username exists
                adapter.add(user);
            }
        });

        adapter = new ArrayAdapter(NewConv.this, android.R.layout.simple_list_item_1, list);
        friendList.setAdapter(adapter);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
                NewConv.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}

