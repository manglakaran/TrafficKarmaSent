
package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mtis.CreateDisplayJson;
import mtis.CreateJson;

/**
 *
 * @author Server
 */
public class JsonReturn extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categeory= request.getParameter("cat").toLowerCase().trim();
        String duration= request.getParameter("dur").toLowerCase().trim();
        String city= request.getParameter("city").toLowerCase().trim();
        //System.out.println(categeory);
        //System.out.println(duration);
        //System.out.println(city);
        response.setContentType("application/json");
        String send="";
        if(categeory.equals("loc"))
        {
            CreateJson cj=new CreateJson();
            send=cj.getJsonRes(city,duration);
        }
        if(categeory.equals("display"))
        {
            CreateDisplayJson cdj=new CreateDisplayJson();
            try
            {
                send=cdj.getJson(city, duration);
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        
       

    response.getWriter().println( send );
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
