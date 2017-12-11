package ui;

/**
 * Created by raluca.miclea on 12/11/2017.
 */
public class Contact {

    private String username;

    public Contact(String username) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.username = username;
    }

    public String getName() {
        return username;
    }

}