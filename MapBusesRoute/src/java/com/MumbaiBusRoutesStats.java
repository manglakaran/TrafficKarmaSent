/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 *
 * @author Server
 */
public class MumbaiBusRoutesStats {
    
    String name;
    double lat;
    double lng;
    String state;
    String color;
    String freq;

    public MumbaiBusRoutesStats(String name, double lat, double lng, String state, String color, String freq) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.state = state;
        this.color = color;
        this.freq = freq;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }
    
    
}
