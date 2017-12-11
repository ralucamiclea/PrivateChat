package ui;

/**
 * Created by raluca.miclea on 12/11/2017.
 */
public class Contact {

    private String username;
    private int image;

    public Contact(String username, int image) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.username = username;
        this.image = image;
    }

    public String getName() {
        return username;
    }
    public int getImage() {
        return image;
    }

}