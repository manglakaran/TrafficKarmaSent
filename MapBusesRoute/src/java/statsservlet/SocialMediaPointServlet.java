/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statsservlet;

import com.CitySelect;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import stats.FaceBookStats;

/**
 *
 * @author Server
 */
public class SocialMediaPointServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        FaceBookStats fb=new FaceBookStats();
        ArrayList<ArrayList<String>> send=fb.getSocialStats();
        ArrayList<String> count=send.get(0);
        String tweetCount=count.get(0); 
        String facebookCount=count.get(1); 
        ArrayList<String> slotData=send.get(1);
        ArrayList<String> topCongested=send.get(2);
        request.setAttribute("city", CitySelect.city);
        request.setAttribute("tweetCount", tweetCount);
        request.setAttribute("facebookCount", facebookCount);
        request.setAttribute("slotData", slotData);
        request.setAttribute("topCongested", topCongested);
        request.getRequestDispatcher("socialmediastats.jsp").forward(request, response);
        
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
