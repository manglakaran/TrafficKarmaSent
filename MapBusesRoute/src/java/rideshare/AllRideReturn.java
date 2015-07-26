/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rideshare;

//import databases_rideshare.RideReturnInfo;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class AllRideReturn {
    public String getJson(ArrayList<ArrayList<String>> al)
    {
        String ret="";
        JSONObject finalObj = new JSONObject();
        JSONArray list = new JSONArray();
        //int noOfRoute=Integer.parseInt(al.get(0));
        int count=0, j=0;
        for(int i=0;i<al.size();i++)
        {   
            //j=j+2;
            //int x1=Integer.parseInt(al.get(j));
            JSONObject usersObject = new JSONObject();
            JSONArray userList = new JSONArray();
            
                
                JSONObject userObject = new JSONObject();
                //RideReturnInfo rri=new RideReturnInfo();
                ArrayList<String> userInfo=al.get(i);
                
                userObject.put("rideId", userInfo.get(0));
                userObject.put("timestamp", userInfo.get(1));
                userObject.put("source", userInfo.get(2));
                userObject.put("destination", userInfo.get(3));
                userObject.put("sll", userInfo.get(4));
                userObject.put("dll", userInfo.get(5));
                userObject.put("pref", userInfo.get(6));
                
                userList.add(userObject);
                
            
            usersObject.put(i, userList);
            list.add(usersObject);
        }
        finalObj.put("data",list );
        System.out.println(finalObj);
        return finalObj.toString();
    }
    
}