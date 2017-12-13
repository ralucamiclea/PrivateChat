package com.dirtybits.privatechat;

import android.app.AlertDialog;
import android.content.Context;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ui.Contact;
import ui.ContactsAdapter;

public class ContactsFragment extends Fragment{

    ListView friendList;
    List<Contact> list;
    String user;
    ContactsAdapter adapter;
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


        /*Get conversation details from storage*/
        list = new ArrayList<Contact>();
        List<String> contactUsernames= new ArrayList<>(loadContactsFromFile(getContext()));
        for (String contact:contactUsernames
             ) {
            list.add(new Contact(contact, R.drawable.ic_user));
        }


        /*Set the custom adapter for the contact list.*/
        adapter = new ContactsAdapter(getActivity(), R.layout.item_contact, list);
        friendList.setAdapter(adapter);


        /*Open a conversation with this specific contact*/
        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO: open conversation when tapped
            Toast.makeText(view.getContext(), "This Conversation should open.", Toast.LENGTH_LONG).show();
            }
        });


        /*Delete specific contact from the list*/
        friendList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Do you want to delete contact?");
            alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    //TODO: delete conversation from storage
                    Contact toRemove = adapter.getItem(position);
                    list.remove(toRemove);
                    adapter.remove(toRemove); //TODO: fix this doube list issue
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"Contact deleted.",Toast.LENGTH_LONG).show();
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


        /*Search through the list for a contacts for a specific friend*/
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


    /*Control the shared fab. Add a new friend*/
    public void shareFab(FloatingActionButton sfab) {
        if (sfab == null) { // When the FAB is shared to another Fragment
            if (fab != null) {
                fab.setOnClickListener(null);
            }
            fab = null;
        }
        else {
            fab = sfab;
            fab.setImageResource(R.drawable.ic_add_friend);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user = filterText.getText().toString();
                    //TODO: verify that the username is valid
                    Contact obj = new Contact(user,R.drawable.ic_user);
                    saveContactsToFile(getContext(), user); //save contact in phone storage
                    list.add(obj);
                    adapter.add(obj);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(view.getContext(), "New friend added!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fab = null; // To avoid keeping/leaking the reference of the FAB
    }

    /*Save contacts in binary files on the phone*/
    public void saveContactsToFile(Context context, String contactUsername) {
        String fileName = contactUsername;

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(contactUsername);
            os.close();
            fos.close();
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to store contact.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /*Load contacts from binary files in the phone*/
    public List<String> loadContactsFromFile(Context context) {
        try {
            List<String> contacts = new ArrayList<>();

            for (File file : context.getFilesDir().listFiles()) {
                FileInputStream fis = context.openFileInput(file.getName());
                ObjectInputStream is = new ObjectInputStream(fis);
                String contact = (String) is.readObject();
                contacts.add(contact);
                is.close();
                fis.close();
            }

            return contacts;
        } catch (IOException e) {
            Toast.makeText(context, "Cannot access local data to load contact.", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
