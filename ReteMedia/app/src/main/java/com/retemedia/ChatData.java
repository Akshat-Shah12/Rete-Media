package com.retemedia;

public class ChatData {
    private final String username;
    private final String message;
    public ChatData(String username, String message){
        this.username = username;
        this.message = message;
    }
    public String getUsername(){return username;}
    public String getMessage(){return message;}
}
