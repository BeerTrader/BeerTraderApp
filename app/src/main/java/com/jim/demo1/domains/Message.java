package com.jim.demo1.domains;

/**
 * Created by jasekurasz on 6/1/15.
 */
public class Message {
    public String user;
    public String content;
    public String date;

    public Message(String user, String content, String date) {
        this.user = user;
        this.content = content;
        this.date = date;
    }

    @Override
    public String toString(){
        return this.user + ":" + this.content;
    }
}
