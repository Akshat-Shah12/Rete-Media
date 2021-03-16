package com.retemedia;

public class ChatData {
    private final String username;
    private final String message;
    private boolean dateChanged;
    public ChatData(String username, String message){
        this.username = username;
        this.message = message;
        dateChanged=false;
    }

    public boolean isDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(boolean dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getUsername(){return username;}
    public String getMessage(){return message;}
}
