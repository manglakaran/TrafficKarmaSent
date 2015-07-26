/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsservlet;

import com.CitySelect;
import com.GetTrafficLocation;
import com.TrafficInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import stats.DelhiStats;
import stats.PetrolStatInfo;
import stats.TrafficSignalStats;
import stats.TrafficStatInfo;

/**
 *
 * @author Server
 */
public class GetTrafficSignalServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        double clat=0.0;
        double clng=0.0;
        if(CitySelect.city.equals("Delhi"))
        {
            clat=28.6139;
            clng=77.2089;
        }
        if(CitySelect.city.equals("Bangalore"))
        {
            clat=12.9667;
            clng=77.5667;
        }
        if(CitySelect.city.equals("Mumbai"))
        {
            clat=18.9750;
            clng=72.8258;
        }
        response.setContentType("text/html;charset=UTF-8");
        GetTrafficLocation gtl=new GetTrafficLocation();
        ArrayList<TrafficInfo> atl=gtl.getLoc(0);
        TrafficSignalStats ds=new TrafficSignalStats();        
        ArrayList<TrafficStatInfo> send=ds.getStats();
        request.setAttribute("city", CitySelect.city);
        request.setAttribute("send", send);
        request.setAttribute("atl", atl);
        request.setAttribute("clat", clat);
        request.setAttribute("clng", clng);
        request.getRequestDispatcher("TrafficSignalStats.jsp").forward(request, response);
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
