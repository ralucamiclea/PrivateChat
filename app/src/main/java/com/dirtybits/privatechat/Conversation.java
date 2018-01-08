package com.dirtybits.privatechat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import internalstorage.MessagesInternalStorage;
import uiadapters.Message;
import uiadapters.ConversationAdapter;

public class Conversation extends AppCompatActivity {

    String contactID;
    private boolean who = false;
    private EditText msgEditText;
    private ImageButton sendButton;
    private ListView msgListView = null;
    private String user1 = "user1", user2 = "user2";
    private Random random;
    public static ArrayList<Message> list;
    public static ConversationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        contactID = getIntent().getStringExtra("contactID");
        random = new Random();
        msgEditText = (EditText) findViewById(R.id.msgEditText);
        msgListView = (ListView) findViewById(R.id.msgListView);
        sendButton= (ImageButton) findViewById(R.id.sendMessageButton);

        //Set autoscroll of listview when a new message arrives
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        //sent the contactID and load the proper msgs
        if(MessagesInternalStorage.loadMsgFromFile(this, contactID) != null)
            list = new ArrayList<>(MessagesInternalStorage.loadMsgFromFile(this, contactID));
        else
            list = new ArrayList<>();
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
        //TODO: actually handle a 2 ppl conversation
        who ^= true;

        if (!message.equalsIgnoreCase("")) {
            Message chatMessage = new Message(user1, user2, message, UUID.randomUUID().toString(), who);
            Log.v("Conversation", "Who = " + who);
            msgEditText.setText("");
            adapter.add(chatMessage);
            adapter.notifyDataSetChanged();
            //save msg in internal storage
            MessagesInternalStorage.saveMsgToFile(this, chatMessage, contactID);
        }
    }
}
