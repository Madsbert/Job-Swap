package org.example.jobswap.Model;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * class to define a message
 */
public class Message {
    private int senderID;
    private int receiverID;
    private LocalDateTime time;
    private String text;

    public Message(int sender, int receiver, String text) {
        this.senderID = sender;
        this.receiverID = receiver;
        this.text = text;
        this.time = LocalDateTime.now();
    }
    public Message(int senderID, int receiverID, String text, LocalDateTime time) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.text = text;
        this.time = time;
    }


    public int getSenderID() {
        return senderID;
    }
    public int getReceiverID() {
        return receiverID;
    }
    public LocalDateTime getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
