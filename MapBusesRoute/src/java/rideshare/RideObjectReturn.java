/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rideshare;

import databases_rideshare.RideReturnInfo;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class RideObjectReturn {
    public String getJson(ArrayList<String> al)
    {
        String ret="";
        JSONObject finalObj = new JSONObject();
        JSONArray list = new JSONArray();
        int noOfRoute=Integer.parseInt(al.get(0));
        int count=0, j=0;
        for(int i=0;i<noOfRoute;i++)
        {   
            j=j+2;
            int x1=Integer.parseInt(al.get(j));
            JSONObject usersObject = new JSONObject();
            JSONArray userList = new JSONArray();
            for (int k=0; k<x1; k++)
            {
                int rideId=Integer.parseInt(al.get(++j));
                double oow=Double.parseDouble(al.get(++j));
                JSONObject userObject = new JSONObject();
                RideReturnInfo rri=new RideReturnInfo();
                ArrayList<String> userInfo=rri.getUserInfo(rideId);
                userObject.put("userName", userInfo.get(1));
                userObject.put("sex", userInfo.get(0));
                userObject.put("age", userInfo.get(4));
                userObject.put("source", userInfo.get(2));
                userObject.put("destination", userInfo.get(3));
                userObject.put("rideId", rideId);
                userObject.put("outOfWay", oow);
                userObject.put("sll",userInfo.get(5));
                userObject.put("dll",userInfo.get(6));
                userObject.put("cn",userInfo.get(7));
                userList.add(userObject);
                
            }
            usersObject.put(i, userList);
            list.add(usersObject);
        }
        finalObj.put("data",list );
        System.out.println(finalObj);
        return finalObj.toString();
    }
    
}