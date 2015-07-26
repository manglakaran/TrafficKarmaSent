/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rideshare;

import databases_rideshare.RideReturnInfo;
import databases_rideshare.RideShareDBManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Server@TrafficKarma
 */
public class RideSave extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        String source = request.getParameter("source");
        String destination = request.getParameter("destination");
        String timestamp = request.getParameter("timestamp");
        String userid = request.getParameter("userid");
        String type = request.getParameter("type");
        String schedule = request.getParameter("schedule");
        String sourcell = request.getParameter("sourcell");
        String destinationll = request.getParameter("destinationll");
        String genderPre = request.getParameter("pref");
        String gender = request.getParameter("gender");
        System.out.println(sourcell + " " + destinationll+" "+timestamp+"jj");
        String param = request.getParameter("param");
        int rideId;
        if (param.equals("0"))
        {
            RideShareDBManager db = new RideShareDBManager();
        RideReturnInfo rri = new RideReturnInfo();
        rideId = rri.getRideId();
        //System.out.println(rideId);
        
        int i = db.saveRideDetail(source, destination, timestamp, userid, type, schedule, sourcell, destinationll,genderPre);
        //String str = "C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\src\\java\\rideshare\\find_distance_output.py " + rideId + " " + sourcell + " " + destinationll + " " + gender + " " + genderPre;

        }
        else
        {
            rideId = Integer.parseInt(request.getParameter("rideid"));
        }
                //System.out.println(str);

        Process pr = Runtime.getRuntime().exec("C:\\Python27\\python C:\\Users\\Server\\Documents\\NetBeansProjects\\MapBusesRoute\\src\\java\\rideshare\\find_distance_output_n.py " + userid + " " + sourcell + " " + destinationll + " " + gender + " " + genderPre+" "+rideId+" "+timestamp);
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        ArrayList<String> routeData = new ArrayList<String>();
        String line = in.readLine();
        
        //response.getWriter().print(line);
        while (line != null) {
            //System.out.println ("Here");
            routeData.add(line);
            line = in.readLine();
        }
        line = routeData.get(0);
        if (!line.equals("No one matches")) {
            RideObjectReturn_bm ror = new RideObjectReturn_bm();
            String jsonData = ror.getJson(routeData);
            //System.out.println(routeData);
            response.getWriter().println(jsonData);
        } else {
            response.getWriter().println("No one matches");
            System.out.println("No one matches");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
