package com.dirtybits.privatechat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uiadapters.Chat;
import uiadapters.MessageAdapter;

public class ChatsFragment extends Fragment{

    FloatingActionButton fab;
    ListView chatList;
    List<Chat> list;
    MessageAdapter adapter;
    EditText filterText;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_chats, container, false);
        filterText = (EditText) view.findViewById(R.id.search_old_conv);
        chatList = (ListView) view.findViewById(R.id.chatList);


        /*Get conversation details from storage*/
        //TODO: get usernames from storage
        Chat[] values = {new Chat("friend1", R.drawable.ic_chat), new Chat("friend2", R.drawable.ic_chat), new Chat("friend3", R.drawable.ic_chat), new Chat("friend4", R.drawable.ic_chat), new Chat("friend5", R.drawable.ic_chat), new Chat("friend6", R.drawable.ic_chat),new Chat("friend7", R.drawable.ic_chat)};
        list = new ArrayList<Chat>();
        Collections.addAll(list,values);

        /*Set the custom adapter for the conversations list.*/
        adapter = new MessageAdapter(getActivity(), R.layout.item_chat, list);
        chatList.setAdapter(adapter);


        /*Open this specific conversation*/
        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO: open conversation when tapped
            Toast.makeText(view.getContext(), "This Conversation should open.", Toast.LENGTH_LONG).show();
            }
        });


        /*Delete specific conversation from the list*/
        chatList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Do you want to delete conversation?");
                alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        //TODO: delete conversation from storage
                        Chat toRemove = adapter.getItem(position);
                        list.remove(toRemove);
                        adapter.remove(toRemove); //TODO: fix this doube list issue
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(),"Conversation deleted",Toast.LENGTH_LONG).show();
                    }});

            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
            }
        });


        /*Search through the list for a conversation with a specific friend*/
        filterText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ChatsFragment.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    /*Control the shared fab. Start a new conversation.*/
    public void shareFab(FloatingActionButton sfab) {
        if (sfab == null) { // When the FAB is shared to another Fragment
            if (fab != null) {
                fab.setOnClickListener(null);
            }
            fab = null;
        }
        else {
            fab = sfab;
            fab.setImageResource(R.drawable.ic_new_conv);
            //TODO: decide how to actually start a new conversation
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                startActivity(new Intent(getActivity(), Conversation.class));
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fab = null; // To avoid keeping/leaking the reference of the FAB
    }
}
