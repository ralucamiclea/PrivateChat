package msg;

import java.sql.Timestamp;

/**
 * Created by raluca.miclea on 12/13/2017.
 */

public class Message {
    private String fromUsername;
    private String toUsername;
    private String content;
    private Timestamp timestamp;

    public Message(String fromUsername, String toUsername, String content){
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.content = content;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getContent() {
        return content;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }
}

