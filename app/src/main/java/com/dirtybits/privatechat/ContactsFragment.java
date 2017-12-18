package com.dirtybits.privatechat;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.List;

import internalstorage.ContactsInternalStorage;
import uiadapters.Contact;
import uiadapters.ContactsAdapter;

public class ContactsFragment extends Fragment{

    ListView friendList;
    List<String> contactUsernames = null;
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
        if(ContactsInternalStorage.loadContactsFromFile(getContext()) != null)
            contactUsernames= new ArrayList<>(ContactsInternalStorage.loadContactsFromFile(getContext()));
        if(contactUsernames != null)
            for (String contact:contactUsernames) {
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
                    Contact toRemove = adapter.getItem(position);
                    //delete from internal storage
                    ContactsInternalStorage.deleteContactFromFile(getContext(),toRemove.getName());
                    //delete from the ListView
                    adapter.remove(toRemove);
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
                    //check for empty username
                    if(user.equals(""))
                        Toast.makeText(view.getContext(), "Please enter a username!", Toast.LENGTH_LONG).show();
                    else {
                        //verify that the username is not already a contact
                        if(ContactsInternalStorage.loadContactsFromFile(getContext())!=null && ContactsInternalStorage.loadContactsFromFile(getContext()).contains(user))
                            Toast.makeText(view.getContext(), user + " is already a friend!", Toast.LENGTH_LONG).show();
                        else {
                            //TODO: verify that the username exists @DATABASE
                            Contact obj = new Contact(user, R.drawable.ic_user);
                            //add in internal storage
                            ContactsInternalStorage.saveContactsToFile(getContext(), user); //save contact in phone storage
                            //add in ListView
                            adapter.add(obj);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(view.getContext(), "New friend added!", Toast.LENGTH_LONG).show();
                        }
                    }
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
