package uiadapters;

import java.sql.Timestamp;
import java.util.Random;

/**
 * Created by raluca.miclea on 12/13/2017.
 */

public class Message {

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

    public void setMsgID() {
        msgid += "-" + String.format("%02d", new Random().nextInt(100));
    }
}

