package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Metadata {

    private String author;

    private String name;

    @JsonIgnore
    private Link link;

    @JsonIgnore
    private String time;

    @JsonIgnore
    private String desc;


    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Link getLink ()
    {
        return link;
    }

    public void setLink (Link link)
    {
        this.link = link;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getDesc ()
    {
        return desc;
    }

    public void setDesc (String desc)
    {
        this.desc = desc;
    }



}
