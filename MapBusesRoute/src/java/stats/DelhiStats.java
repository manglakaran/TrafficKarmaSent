/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stats;

import com.CitySelect;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Dibyendu1363
 */
public class DelhiStats {

    public  ArrayList<PetrolStatInfo> getStats() throws FileNotFoundException, IOException {
        String city=CitySelect.city.toLowerCase();
       

        ArrayList<PetrolStatInfo> arr = new ArrayList<PetrolStatInfo>();

        FileReader fr1 = new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\statsFolder\\petrol_"+city+"_stats.txt");
        BufferedReader br1 = new BufferedReader(fr1);
        String line1;
        while ((line1 = br1.readLine()) != null) {

            String parts[] = line1.split("\t");
            String com[] = parts[0].split(",");
            String lat = com[0];
            String lng = com[1];

            ArrayList<String> speed = new ArrayList<String>();
            for (int i = 1; i <= 15; i++) {
                String speeds[] = parts[i].split(",");
                speed.add(speeds[1]);
            }
            //System.out.println(speed);
            PetrolStatInfo psi = new PetrolStatInfo(lat, lng, speed.get(0), speed.get(1), speed.get(2), speed.get(3), speed.get(4), speed.get(5), speed.get(6), speed.get(7), speed.get(8), speed.get(9), speed.get(10), speed.get(11), speed.get(12), speed.get(13), speed.get(14));
            arr.add(psi);
        }

        return arr;

    }
}