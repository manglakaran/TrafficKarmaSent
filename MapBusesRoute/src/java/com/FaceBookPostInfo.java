
package com;

/**
 *
 * @author Server
 */
public class FaceBookPostInfo {
    String time;
    String message;
    String id;
    String from;
    String fromId; 

    public FaceBookPostInfo(String time, String message, String id, String from, String fromId) {
        this.time = time;
        this.message = message;
        this.id = id;
        this.from = from;
        this.fromId = fromId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
    
}
