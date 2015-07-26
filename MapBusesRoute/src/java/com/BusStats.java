/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Dibyendu1363
 */
public class BusStats {
    
    public ArrayList<String> delhiBusStats(ArrayList<String> Routes, ArrayList<String> names) throws FileNotFoundException, NullPointerException, IOException, ParseException {

        ArrayList<String> routes = new ArrayList<String>();
        for(int i=0; i<Routes.size(); i++){
            String parts[] = Routes.get(i).split("-");
            routes.add(parts[0].trim()+"--"+parts[1].trim());
        }
        
        // time slots
        
        ArrayList<String> time_slots = new ArrayList<String>();
        String myTime = "00:00:00";
        for (int t = 0; t < 24; t++) {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date d = df.parse(myTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.SECOND, 3599);
            
            String newTime = df.format(cal.getTime());
            time_slots.add(myTime + "-" + newTime);
            d = df.parse(newTime);
            cal.setTime(d);
            cal.add(Calendar.SECOND, 1);
            myTime = df.format(cal.getTime());
        }
        
        Date date = new Date();
        String part[] = date.toString().split(" ");
        DateFormat sd = new SimpleDateFormat("HH:mm:ss");
        Date curr_time = sd.parse(part[3]);
        
        int index = 0;
        for(int i=0; i<time_slots.size(); i++){
            String divider[] = time_slots.get(i).split("-");
            DateFormat sd1 = new SimpleDateFormat("HH:mm:ss");
            Date slot_start = sd1.parse(divider[0]);
            DateFormat sd2 = new SimpleDateFormat("HH:mm:ss");
            Date slot_end = sd2.parse(divider[1]);
            
            if((slot_start.before(curr_time) || slot_start.equals(curr_time)) && (slot_end.after(curr_time) || slot_end.equals(curr_time))){
                index = i;
            }
        }
        
        ArrayList<String> points = new ArrayList<String>();
        String pp[] = routes.get(0).split("--");
        points.add(pp[0]);
        points.add(pp[1]);
        for (int i = 1; i < routes.size(); i++) {
            String parts[] = routes.get(i).split("--");
            points.add(parts[1]);
        }
        
        String final_arr[] = new String[points.size()];
        
         FileReader frt = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\statsFolder\\bus_stats_82_routeids.txt");
        BufferedReader brt = new BufferedReader(frt);
        String line="";
        while ((line = brt.readLine()) != null) {
            String parts[] = line.split("\t");
            for (int i = 0; i < points.size(); i++) {
                if (points.get(i).equals(parts[0])) {
                    if(index == 0){
                        index = 24;
                    }
                    System.out.println(index);
                    String stats = parts[index+1];
                    String splitter[] = stats.split(",");
                    String fina_color="#0000ff";
                    if(splitter[1].equals("Heavy"))
                        fina_color = "#ff0000";
                    if(splitter[1].equals("Medium"))
                        fina_color = "#ffff00";
                    if(splitter[1].equals("Mild"))
                        fina_color = "#00ff00";
                    final_arr[i] = splitter[1]+","+fina_color;
                }
            }
        }
        
        // forcibly giving unknown values
        for(int i=0; i<final_arr.length; i++){
            if(final_arr[i] == null){
               final_arr[i] = "Unknown,#0000ff";
            }
        }
        
        
        
        ArrayList<String> all_bus_stops = new ArrayList<String>();
        BufferedReader brr = new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\statsFolder\\stop_names_freq.txt"));
        String st = "";
        while ((st = brr.readLine()) != null) {
            all_bus_stops.add(st);
        }
        ArrayList<Integer> names_indices = new ArrayList<Integer>();
        for (int i = 0; i < names.size(); i++) {
            for (int j = 0; j < all_bus_stops.size(); j++) {
                if (names.get(i).equals(all_bus_stops.get(j))) {
                    names_indices.add(j);
                }
            }
        }
        
        String _parts[] = part[3].split(":");
        int curr_time_slot = Integer.parseInt(_parts[0]);
        
        BufferedReader bb = new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\statsFolder\\final_freq_1hr_equal.txt"));
        String ss = "";
        String matrix[] = new String[2397];
        int row = 0;
        while ((ss = bb.readLine()) != null) {
            String parts[] = ss.split("\\s\\s");
            matrix[row] = parts[curr_time_slot];
            row++;
        }
        
        ArrayList<String> result = new ArrayList<String>();
        result.add(names.get(0) + "," + points.get(0) + "," + final_arr[0] + "," + matrix[names_indices.get(0)]);
        for (int i = 1; i < points.size(); i++) {
            result.add(names.get(i) + "," + points.get(i) + "," + final_arr[i - 1] + "," + matrix[names_indices.get(i)]);
        }
        
        return result;
    }
}
