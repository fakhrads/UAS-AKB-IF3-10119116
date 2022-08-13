package com.fakhrads.uasakbif3101191116;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Notes implements Serializable {
    private Date date;
    private String text, category, title;
    private boolean fullDisplayed;
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");

    public Notes(){
        this.date = new Date();
    }
    public Notes(long time, String text, String category, String title){
        this.date = new Date(time);
        this.text = text;
        this.category = category;
        this.title = title;
    }
    public String getDate(){
        return dateFormat.format(date);
    }
    public long getTime(){
        return date.getTime();
    }
    public void setTime(long time){
        this.date = new Date(time);
    }
    public void setText(String text){
        this.text = text;
    }
    public void setTtitle(String title){
        this.title = title;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getText(){
        return this.text;
    }
    public String getTtitle(){
        return this.title;
    }
    public String getCategory(){
        return this.category;
    }
    public String getShortText(){
        String temp = text.replaceAll("/n", " ");
        if (temp.length() > 50){
            return temp.substring(0, 50) + "...";
        }else{
            return temp;
        }
    }
    public void setFullDisplayed(boolean fullDisplayed){
        this.fullDisplayed = fullDisplayed;
    }

    public boolean isFullDisplayed() {
        return this.fullDisplayed;
    }

    @NonNull
    @Override
    public String toString() {
        return this.text;
    }
}
