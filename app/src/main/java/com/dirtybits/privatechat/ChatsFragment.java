package com.dirtybits.privatechat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import auth.User;
import internalstorage.ContactsInternalStorage;
import internalstorage.CurrentUserInternalStorage;
import internalstorage.MessagesInternalStorage;
import uiadapters.Chat;
import uiadapters.ChatAdapter;

public class ChatsFragment extends Fragment{

    User loggeduser;
    FloatingActionButton fab;
    ListView chatList;
    List<Chat> list;
    ChatAdapter adapter;
    EditText filterText;
    List<String> contactUsernames = null;
    String contact;

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

        loggeduser = CurrentUserInternalStorage.loadUserFromFile(getContext());
        if (loggeduser != null) {
            Log.v("LoggedUserChats", loggeduser.getUsername());

        /*Get conversation details from storage*/
        list = new ArrayList<Chat>();
        if (ContactsInternalStorage.loadContactsFromFile(getContext(), loggeduser) != null)
            contactUsernames = new ArrayList<>(ContactsInternalStorage.loadContactsFromFile(getContext(), loggeduser));
        if (contactUsernames != null) {
            for (String contact : contactUsernames) {
                //looks for contacts with whom the user had a conversation
                if (MessagesInternalStorage.loadMsgFromFile(getContext(), contact, loggeduser).isEmpty() == false) {
                    Log.v("ImportedContacts", MessagesInternalStorage.loadMsgFromFile(getContext(), contact, loggeduser).toString());
                    list.add(new Chat(contact, R.drawable.ic_user));
                }
            }
        }


        /*Set the custom adapter for the conversations list.*/
        adapter = new ChatAdapter(getActivity(), R.layout.item_chat, list);
        chatList.setAdapter(adapter);


        /*Open this specific conversation*/
        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chat ctc = (Chat) parent.getItemAtPosition(position);
                contact = ctc.getName();
                Log.v("DataClicked", "Data clicked = " + contact);
                Intent intent = new Intent(getActivity(), Conversation.class);
                intent.putExtra("contactID", contact);
                startActivity(intent);
            }
        });


        /*Delete specific conversation from the list*/
        chatList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Chat ctc = (Chat) parent.getItemAtPosition(position);
                contact = ctc.getName();
                Log.v("DataClicked", "Data clicked = " + contact);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Do you want to delete conversation?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //delete conversation from storage
                                MessagesInternalStorage.deleteMsgFile(getContext(), contact, loggeduser);
                                //delete from ListView
                                Chat toRemove = adapter.getItem(position);
                                adapter.remove(toRemove);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Conversation deleted", Toast.LENGTH_LONG).show();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
    }

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
