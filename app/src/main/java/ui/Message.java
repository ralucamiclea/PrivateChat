package ui;

/**
 * Created by raluca.miclea on 12/11/2017.
 */

public class Message {

    private String username;
    private int image;

    public Message(String username, int image) {
        this.username = username;
        this.image = image;
    }

    public String getName() {
        return username;
    }
    public int getImage() { return image; }
}
