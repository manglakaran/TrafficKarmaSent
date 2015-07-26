
package com;

public class TrafficInfo {
     String name;
     double lat;
     double lng;
     String time;
     String source;
     String sentimentScore;
     String state;
     String timeinfo;

    public TrafficInfo(String name, double lat, double lng,String time, String source,String sentimentScore,String state,String timeinfo) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.time=time;
        this.source=source;
        this.sentimentScore=sentimentScore;
        this.state=state;
        this.timeinfo=timeinfo;
        
    }

    public String getTimeinfo() {
        return timeinfo;
    }

    public void setTimeinfo(String timeinfo) {
        this.timeinfo = timeinfo;
    }

    public String getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(String sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
    
    
}
