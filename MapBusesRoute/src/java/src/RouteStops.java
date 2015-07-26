

package src;

import com.CitySelect;
import com.ForMumbai;
import com.MumbaiBusInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author kanu
 */
@WebServlet(name = "RouteStops", urlPatterns = {"/RouteStops"})
public class RouteStops extends HttpServlet {

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
        ForMumbai fr = new ForMumbai();
        String routenumber = request.getParameter("Route_No");
        //System.out.println(routenumber);
        ArrayList<MumbaiBusInfo> send1 = fr.main(routenumber);
        ArrayList<com.MumbaiBusRoutesStats> send = new ArrayList<com.MumbaiBusRoutesStats>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> points = new ArrayList<String>();
        ArrayList<String> routes = new ArrayList<String>();
        for(MumbaiBusInfo m:send1){
            points.add(m.getLat()+","+m.getLng());
            names.add(m.getName());
        }
        for(int i=0; i<points.size()-1; i++){
            routes.add(points.get(i)+"-"+points.get(i+1));
        }
        
        //System.out.println(routes);
        
        com.BusStats bb = new com.BusStats();
        try{
            ArrayList<String> result = bb.delhiBusStats(routes, names);
            for(int i=0; i<result.size(); i++){
                String comma[] = result.get(i).split(",");
                com.MumbaiBusRoutesStats m = new com.MumbaiBusRoutesStats(comma[0], Double.parseDouble(comma[1]), Double.parseDouble(comma[2]), comma[3], comma[4], comma[5]);
                System.out.println(comma[0]+"#"+Double.parseDouble(comma[1])+"#"+Double.parseDouble(comma[2])+"#"+comma[3]+"#"+comma[4]+"#"+comma[5]);
                send.add(m);
            }
        }
        catch (Exception e){
            System.out.print(e);
        }
        request.setAttribute("send", send);
        request.setAttribute("city", CitySelect.city);
        request.getRequestDispatcher("BusRoutes.jsp").forward(request, response);
        
//        JSONObject last=new JSONObject();
//        last.put("response", result);
         
        
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
