/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class TimeData {
    public HashMap<String,String> getTime()
    {
        HashMap<String,String> hm=new HashMap<String,String>();
        try
        {
            
            BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\timeData\\timedatabangalore.txt"));
            String line=br.readLine();
            while(line!=null)
            {
                String[] tokens=line.split("\\$\\$\\$");
                if(!hm.containsKey(tokens[0]))
                hm.put(tokens[0], tokens[1]);
                //System.out.println(tokens[1]);
                line=br.readLine();
            }
            //System.out.println(hm);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return hm;
    }
    
}
