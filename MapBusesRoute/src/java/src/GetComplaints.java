
package src;

import com.CitySelect;
import com.Complaints;
import com.FaceBookPostInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetComplaints extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Complaints cc=new Complaints();
        ArrayList<ArrayList<FaceBookPostInfo>> getFullComplaint=cc.getComplaints();
        ArrayList<FaceBookPostInfo> parkingComplaints=getFullComplaint.get(0);
        ArrayList<FaceBookPostInfo> infraComplaints=getFullComplaint.get(1);
        ArrayList<FaceBookPostInfo> lineViolation=getFullComplaint.get(2);
        ArrayList<FaceBookPostInfo> otherComplaints=getFullComplaint.get(3);
        request.setAttribute("city",CitySelect.city);
        request.setAttribute("parkingComplaints", parkingComplaints);
        request.setAttribute("infraComplaints", infraComplaints);
        request.setAttribute("lineViolation", lineViolation);
        request.setAttribute("otherComplaints", otherComplaints);
        request.getRequestDispatcher("CitizenComplaints.jsp").forward(request, response);
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
