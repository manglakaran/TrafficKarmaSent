package com;

import java.io.*;
import java.net.*;
import java.util.*;
public class GetEdgeDetails {
    public ArrayList<EdgeInfo> getDetail()
    {
        ArrayList<EdgeInfo> al=null;
        try
        {
            al=new ArrayList<EdgeInfo>();
            HashMap<String,String> hm=new HashMap<String,String>();
            BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\points_delhi2.txt"));
            String line=br.readLine();
            StringTokenizer st=new StringTokenizer(line);
            while(line!=null)
            {
                st=new StringTokenizer(line,"-");
                hm.put(st.nextToken(), st.nextToken());
                line=br.readLine();
            }
            BufferedReader br1=new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\routes_delhi1.txt"));
            String line1=br1.readLine();
            while(line1!=null)
            {
                st=new StringTokenizer(line1,"-");
                String startName=st.nextToken();
                String endName=st.nextToken();
                String[] parts1=hm.get(startName).split(",");
                String[] parts2=hm.get(endName).split(",");
                double startlat=Double.parseDouble(parts1[0]);
                double startlng=Double.parseDouble(parts1[1]);
                double endlat=Double.parseDouble(parts2[0]);
                double endlng=Double.parseDouble(parts2[1]);
                EdgeInfo ef=new EdgeInfo(startName, endName, startlat, startlng, endlat, endlng);
                al.add(ef);
                System.out.println(startName+" "+endName+" "+startlat+" "+startlng+" "+endlat+" "+endlng);
                line1=br1.readLine();
            }
            
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return al;
    }
    
}
