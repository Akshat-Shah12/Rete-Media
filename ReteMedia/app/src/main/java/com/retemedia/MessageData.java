package com.retemedia;

public class MessageData {
    private String title,message;
    private String imageURL;
    private long time;
    public MessageData(String imageURL, String title, String message, long time)
    {
        this.imageURL = imageURL;
        this.title = title;
        this.message = message;
        this.time = time;
    }
    public String getImageURL(){return imageURL;}
    public String getTitle(){return title;}
    public String getMessage(){return message;}
    public long getTime(){return time;}
}
