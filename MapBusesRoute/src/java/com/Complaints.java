/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Dibyendu1363
 */
public class Complaints {

    public ArrayList<ArrayList<FaceBookPostInfo>> getComplaints () throws FileNotFoundException, IOException {

        ArrayList<ArrayList<FaceBookPostInfo>> arr = new ArrayList<ArrayList<FaceBookPostInfo>>();
        
        ArrayList<String> plate_parking = new ArrayList<String>();
        ArrayList<String> infra_police = new ArrayList<String>();
        ArrayList<String> line_violation = new ArrayList<String>();
        ArrayList<String> others = new ArrayList<String>();
        
        ArrayList<FaceBookPostInfo> arr1 = new ArrayList<FaceBookPostInfo>();
        ArrayList<FaceBookPostInfo> arr2 = new ArrayList<FaceBookPostInfo>();
        ArrayList<FaceBookPostInfo> arr3 = new ArrayList<FaceBookPostInfo>();
        ArrayList<FaceBookPostInfo> arr4 = new ArrayList<FaceBookPostInfo>();
        
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\facebookData\\"+CitySelect.city+"\\complaints.txt"));
        String splitter[] = br.readLine().split("\\$\\$\\$");

        for (int i = splitter.length-1; i >= 0; i--) {
            String split[] = splitter[i].split("\\#\\#\\#");
            StringTokenizer st = new StringTokenizer(split[1], " ,.");

            
            while (st.hasMoreTokens()) {
                String token = st.nextToken().toLowerCase();
                if (token.equals("number") || token.equals("plate") || token.equals("parked") || token.equals("zebra") || token.equals("helmet") || token.equals("wrong") || split[1].contains("illegal parking")) {
                    if (!plate_parking.contains(split[1])) {
                        FaceBookPostInfo f = new FaceBookPostInfo(split[0], split[1], split[2], split[3], split[4]);
                        arr1.add(f);
                        plate_parking.add(split[1]);
                    }
                }
                else if (token.equals("implement") || token.equals("red") || token.equals("signal") || token.equals("action") || token.equals("resolve") || split[1].contains("traffic police")) {
                    if (!infra_police.contains(split[1])) {
                        FaceBookPostInfo f = new FaceBookPostInfo(split[0], split[1], split[2], split[3], split[4]);
                        arr2.add(f);
                        infra_police.add(split[1]);
                    }
                }
                else if (split[1].contains("line voilation")) {
                    if (!line_violation.contains(split[1])) {
                        FaceBookPostInfo f = new FaceBookPostInfo(split[0], split[1], split[2], split[3], split[4]);
                        arr3.add(f);
                        line_violation.add(split[1]);
                    }
                } 
                else {
                    if(!others.contains(split[1])){
                        FaceBookPostInfo f = new FaceBookPostInfo(split[0], split[1], split[2], split[3], split[4]);
                        arr4.add(f);
                        others.add(split[1]);
                    }
                }
            }
        }

        arr.add(arr1);
        arr.add(arr2);
        arr.add(arr3);
        arr.add(arr4);
        
        //System.out.println(plate_parking+"\n\n\n\n\n");
        //System.out.println(infra_police + "\n\n");
        //System.out.println(line_violation+"\n\n\n\n\n");
        //System.out.println(others);
        return arr;
    }
}
