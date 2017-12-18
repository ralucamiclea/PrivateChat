package uiadapters;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by raluca.miclea on 12/13/2017.
 */

public class Message{

    private String sender;
    private String receiver;
    private String content;
    private String msgid;
    private boolean isMine; // Did I send the message?
    private Timestamp timestamp;

    public Message(String sender, String receiver, String content, String id,  boolean isMINE){
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.msgid = id;
        this.isMine = isMINE;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getContent() {
        return content;
    }

    public String getSenderName() {
        return sender;
    }

    public String getReceiverName() {return receiver; }

    public String getMsgId() {
        return msgid;
    }

    public boolean getIsMine() {
        return isMine;
    }

    public Timestamp getTimestamp() {return timestamp; }

    /*Comparator for sorting the list by Timestamp*/
    public static Comparator<Message> MsgTimestampComparator = new Comparator<Message>() {

        public int compare(Message m1, Message m2) {
            Timestamp timestamp1 = m1.getTimestamp();
            Timestamp timestamp2 = m2.getTimestamp();
            boolean result = timestamp1.before(timestamp2); //ascending order

            if(result) return 1;
            else return 0;

        }};
}

