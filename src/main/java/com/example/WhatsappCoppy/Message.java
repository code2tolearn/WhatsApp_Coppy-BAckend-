package com.example.WhatsappCoppy;
import java.util.Date;

public class Message {
    private int id;
    private String content;
    private Date timestamp;

    public Message(int id) {
        this.id = id;
        this.timestamp = new Date();
    }

    public Message(int id, String content) {
        this.id = id;
        this.content = content;
        this.timestamp = new Date();
    }

    public Message(int id, String content, Date timestamp) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}