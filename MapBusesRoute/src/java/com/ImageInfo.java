package com;


public class ImageInfo {
    String imageURL;
    String text;
    String time;
    String userName;
    String profileImage;

    public ImageInfo(String imageURL, String text, String time, String userName, String profileImage) {
        this.imageURL = imageURL;
        this.text = text;
        this.time = time;
        this.userName = userName;
        this.profileImage = profileImage;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
  
    
}
