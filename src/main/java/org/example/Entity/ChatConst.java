package org.example.Entity;

import java.io.Serializable;
import java.util.Objects;

public class ChatConst implements Serializable {
    private String sender;
    private String recipient;
    private String content;
    private String date;
    private String time;

    public ChatConst(String sender, String recipient, String content, String date, String time) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.date = getCurrentDate();
        this.time = getCurrentTime();
    }

    // Getters
    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    private static String getCurrentDate() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new java.util.Date());
    }

    private static String getCurrentTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        return sdf.format(new java.util.Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatConst chatConst = (ChatConst) o;
        return Objects.equals(sender, chatConst.sender) && Objects.equals(recipient, chatConst.recipient) && Objects.equals(content, chatConst.content) && Objects.equals(date, chatConst.date) && Objects.equals(time, chatConst.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient, content, date, time);
    }

    @Override
    public String toString() {
        return "ChatConst{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}