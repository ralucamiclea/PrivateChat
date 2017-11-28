package com.dirtybits.privatechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    ListView chatList;
    List list = new ArrayList();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatList = (ListView) findViewById(R.id.chatList);

        //TODO: get old user conversations from the server
        list.add("user1");
        list.add("user2");
        list.add("user3");
        list.add("user4");
        list.add("user5");
        list.add("user6");
        list.add("user7");
        list.add("user8");
        list.add("user9");
        list.add("user10");
        list.add("user11");
        list.add("user12");
        list.add("user13");
        list.add("user14");
        list.add("user15");

        adapter = new ArrayAdapter(Chat.this, android.R.layout.simple_list_item_1, list);
        chatList.setAdapter(adapter);
    }
}
