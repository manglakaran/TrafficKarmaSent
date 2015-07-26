/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Kireet1337
 */
public class GetStats extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException, ParseException {
        int edge1=Integer.parseInt(request.getParameter("edge1"));
        int edge2=Integer.parseInt(request.getParameter("edge2"));
        int edge3=Integer.parseInt(request.getParameter("edge3"));
        ArrayList<String> route=new ArrayList<String>();
        route.add("EF-CD");route.add("CD-AA");route.add("AA-BB");route.add("EF-FG");route.add("FG-VV");route.add("VV-BB");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\estimated values.txt"));
        String line=br.readLine();
        HashMap<String,String> hm=new HashMap<String,String>();
        while(line!=null)
        {
            String tokens[]=line.split(",");
            hm.put(tokens[3], tokens[1]);
            line=br.readLine();
        }
        //System.out.println(edge1);
        String result=route.get(edge1)+": "+hm.get(route.get(edge1))+"<br/>"+route.get(edge2)+": "+hm.get(route.get(edge2))+"<br/>"+route.get(edge3)+": "+hm.get(route.get(edge3))+"<br/> ";
        
//        ReverseGeocoderBing rgb = new ReverseGeocoderBing();
//        String result1 = rgb.reverse(edge);
//        
//        StringTokenizer st = new StringTokenizer(result1, "$$$");
//        String parts[] = result1.split("$$$");
//        String a = st.nextToken();
//        String b = st.nextToken();
//        System.out.println(a);
//        System.out.println(b);
//        
//        String result = "";
//        Functions f=new Functions();
//        if(Integer.parseInt(item)==0){
//            result = f.getMean(edge);
//        }
//        if(Integer.parseInt(item)==1){
//            result = f.getMedian(edge);
//        }
//        if(Integer.parseInt(item)==2){
//            result = f.getVariance(edge);
//        }
//        if(Integer.parseInt(item)==3){
//            result = f.getSD(edge);
//        }
//        if(Integer.parseInt(item)==4){
//            result = f.getMeanDay(edge, date);
//        }
//        if(Integer.parseInt(item)==5){
//            result = f.getMedianDay(edge, date);
//        }
//        if(Integer.parseInt(item)==6){
//            result = f.getVarianceDay(edge, date);
//        }
//        if(Integer.parseInt(item)==7){
//            result = f.getSDDay(edge, date);
//        }
//        if(Integer.parseInt(item)==8){
//            result = f.getMeanTime(edge, time);
//        }
//        if(Integer.parseInt(item)==9){
//            result = f.getMedianTime(edge, time);
//        }
//        if(Integer.parseInt(item)==10){
//            result = f.getVarianceTime(edge, time);
//        }
//        if(Integer.parseInt(item)==11){
//            result = f.getSDTime(edge, time);
//        }
//        if(Integer.parseInt(item)==12){
//            result = f.getState(edge, date, time);
//        }
        out.write(" "+result);
        //System.out.println(result);
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(GetStats.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(GetStats.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
