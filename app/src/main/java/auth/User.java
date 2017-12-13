package auth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raluca.miclea on 12/12/2017.
 */

public class User {

    private String username;
    private List<String> contacts;
    private String password;

    public User(String user, String pass){
        this.username = user;
        this.password = pass;
        this.contacts = new ArrayList<String>();
    }

    public String getUsername(){
        return username;
    }

    public List<String> getContacts(){
        return new ArrayList(contacts);
    }
    public void addContact(String contactUsername){
        contacts.add(contactUsername);
    }
}
