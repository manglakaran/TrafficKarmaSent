
package com;

public class TweetInfo {
    String time;
    String screenName;
    String profileURL;    
    String userId;
    String statusId;
    String name;
    String textData;

    public TweetInfo(String time, String screenName, String profileURL, String userId, String statusId, String name, String textData) {
        this.time = time;
        this.screenName = screenName;
        this.profileURL = profileURL;
        this.userId = userId;
        this.statusId = statusId;
        this.name = name;
        this.textData = textData;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

}