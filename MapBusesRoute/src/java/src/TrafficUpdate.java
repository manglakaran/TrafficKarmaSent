/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src;

import com.CitySelect;
import com.FaceBookPostDisplay;
import com.FaceBookPostInfo;
import com.GetTrafficLocation;
import com.TrafficInfo;
import com.TweetDisplay;
import com.TweetInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kanu
 */
@WebServlet(name = "TrafficUpdate", urlPatterns = {"/TrafficUpdate"})
public class TrafficUpdate extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double clat=0.0;
        double clng=0.0;
        
        //System.out.println(CitySelect.city);
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
        TweetDisplay tl=new TweetDisplay();
        FaceBookPostDisplay fbd=new FaceBookPostDisplay();
        ArrayList<TweetInfo> tweetListSend=tl.getTweetDetail();
        ArrayList<FaceBookPostInfo> postListSend=fbd.getPostDetail();
        String tweetHourlyCount=tl.getHourlyCount();
        String postHourlyCount=fbd.getHourlyCount();
        request.setAttribute("tweetHourlyCount", tweetHourlyCount);
        request.setAttribute("postHourlyCount", postHourlyCount);
        request.setAttribute("clat", clat);
        request.setAttribute("clng", clng);
        request.setAttribute("tweetListSend", tweetListSend);
        request.setAttribute("postListSend", postListSend);
        request.setAttribute("city", CitySelect.city);
        if(request.getParameter("daydata")!=null)
        {
        int dayCount=Integer.parseInt(request.getParameter("daydata"));
        System.out.println(dayCount);
        response.setContentType("text/html;charset=UTF-8");
        GetTrafficLocation gtl=new GetTrafficLocation();
        ArrayList<TrafficInfo> atl=gtl.getLoc(dayCount);
        
        request.setAttribute("atl", atl);
        request.getRequestDispatcher("Mainpage.jsp").forward(request, response);
        }
        else
        {
            int dayCount=0;
            //System.out.println(dayCount);
              response.setContentType("text/html;charset=UTF-8");
              GetTrafficLocation gtl=new GetTrafficLocation();
              ArrayList<TrafficInfo> atl=gtl.getLoc(dayCount);
              request.setAttribute("atl", atl);
              request.getRequestDispatcher("Mainpage.jsp").forward(request, response);
              //request.getRequestDispatcher("GetRoute").forward(request, response);
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
