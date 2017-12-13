package com.dirtybits.privatechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

import uiadapters.Message;
import uiadapters.ConversationAdapter;

public class Conversation extends AppCompatActivity {

    private EditText msgEditText;
    private ImageButton sendButton;
    private ListView msgListView;
    private String user1 = "user1", user2 = "user2";
    private Random random;
    public static ArrayList<Message> list;
    public static ConversationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        random = new Random();
        msgEditText = (EditText) findViewById(R.id.msgEditText);
        msgListView = (ListView) findViewById(R.id.msgListView);
        sendButton= (ImageButton) findViewById(R.id.sendMessageButton);

        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        list = new ArrayList<Message>();
        adapter = new ConversationAdapter(Conversation.this, list);
        msgListView.setAdapter(adapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageButton:
                sendTextMessage(v);
        }
    }

    public void sendTextMessage(View v) {
        String message = msgEditText.getEditableText().toString();

        if (!message.equalsIgnoreCase("")) {
            Message chatMessage = new Message(user1, user2, message, "" + random.nextInt(1000), true);
            chatMessage.setMsgID();
            msgEditText.setText("");
            adapter.add(chatMessage);
            adapter.notifyDataSetChanged();
        }
    }
}
