package com.dirtybits.privatechat;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactsFragment extends Fragment{

    ListView friendList;
    List<String> list;
    String user;
    ArrayAdapter adapter;
    EditText filterText;
    FloatingActionButton fab;

    public ContactsFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        filterText = (EditText) view.findViewById(R.id.search_friend);
        friendList = (ListView) view.findViewById(R.id.friendList);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_add_friend);

        //TODO: get usernames from the server
        String[] values = {"friend1", "friend2", "friend3", "friend4", "friend5", "friend6", "friend7"};
        list = new ArrayList<String>();
        Collections.addAll(list,values);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = filterText.getText().toString();
                //TODO: verify that the username exists
                adapter.add(user);
                Toast.makeText(view.getContext(), "User added successfully!", Toast.LENGTH_LONG).show();
            }
        });

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        friendList.setAdapter(adapter);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make Toast when click
                Toast.makeText(view.getContext(), "Position " + position + 1, Toast.LENGTH_LONG).show();
            }
        });

        filterText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ContactsFragment.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

}
