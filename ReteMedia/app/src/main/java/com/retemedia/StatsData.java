package com.retemedia;

public class StatsData {
    private String name,imageid;
    private String jobTitle;
    public StatsData(String name, String imageid, String jobTitle)
    {
        this.name = name;
        this.imageid = imageid;
        this.jobTitle = jobTitle;
    }
    public String getName(){return name;}
    public String getJobTitle(){return jobTitle;}
    public String getImageid(){return imageid;}
}
