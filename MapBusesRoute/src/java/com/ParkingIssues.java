package com;

import com.ParkingInfo;
import java.util.*;
import java.io.*;

public class ParkingIssues {

    public ArrayList<ParkingInfo> getIssues() {
        ArrayList<ParkingInfo> issueList = new ArrayList<ParkingInfo>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\pmo.txt"));
            String text = br.readLine();
            StringTokenizer st = new StringTokenizer(text, "$$$$");
            while (st.hasMoreTokens()) {
                String date;
                String name;
                String userLink;
                String message;
                String picLink="#";
                String data = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(data, "###");
                date=st1.nextToken();
                name=st1.nextToken();
                userLink=st1.nextToken();
                message=st1.nextToken();
                String temp=st1.nextToken();
                if(temp!=null)
                picLink=temp;
                ParkingInfo p=new ParkingInfo(date, name, userLink, message, picLink);
                issueList.add(p);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return issueList;
    }
}
