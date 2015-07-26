
package com;

public class ParkingInfo {
    String date;
    String name;
    String userLink;
    String message;
    String picLink;

    public ParkingInfo(String date, String name, String userLink, String message, String picLink) {
        this.date = date;
        this.name = name;
        this.userLink = userLink;
        this.message = message;
        this.picLink = picLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserLink() {
        return userLink;
    }

    public void setUserLink(String userLink) {
        this.userLink = userLink;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }
    
    
}
