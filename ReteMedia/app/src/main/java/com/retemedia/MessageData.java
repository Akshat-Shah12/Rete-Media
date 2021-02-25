package com.retemedia;

public class MessageData {
    private String title,message;
    private String imageURL;
    public MessageData(String imageURL, String title, String message)
    {
        this.imageURL = imageURL;
        this.title = title;
        this.message = message;
    }
    public String getImageURL(){return imageURL;}
    public String getTitle(){return title;}
    public String getMessage(){return message;}
}
