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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatsFragment extends Fragment{

    FloatingActionButton fab;
    ListView chatList;
    List<String> list;
    ArrayAdapter adapter;
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
        fab = (FloatingActionButton) view.findViewById(R.id.fab_write_msg);

        //TODO: get usernames from the server
        String[] values = {"user1", "user2", "user3", "user4", "user5", "user6", "user7", "user8", "user9", "user10", "user11", "user12", "user13"};
        list = new ArrayList<String>();
        Collections.addAll(list,values);

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        chatList.setAdapter(adapter);

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: open conversation when tapped
                Toast.makeText(view.getContext(), "This Conversation should open.", Toast.LENGTH_LONG).show();
            }
        });

        chatList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Do you want to delete conversation?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        //TODO: delete conversation
                                        Object toRemove = adapter.getItem(position);
                                        adapter.remove(toRemove);
                                        Toast.makeText(getContext(),"Conversation deleted",Toast.LENGTH_LONG).show();
                                    }
                                });

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

        //start a conversastion
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Conversation.class));
            }
        });

        return view;
    }

}
