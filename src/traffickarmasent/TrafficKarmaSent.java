/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffickarmasent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import static traffickarmasent.spellcheck.postagging;


/**
 *
 * @author karan
 */
public class TrafficKarmaSent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
        // TODO code application logic here
        
       String a = "this is it" ;
       a  = postagging(a);

        
    }

    
    
}
