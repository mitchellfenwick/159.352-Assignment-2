/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_19037806.server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mitch
 */
@WebServlet(name = "state", urlPatterns = {"/state"})
public class state extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet state</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet state at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        // Get session object
        HttpSession session = request.getSession(false);
        // If session is not active
        if (session == null) {
            response.setStatus(404);
            try (PrintWriter out = response.getWriter()) {
                out.println("No active game found");
            }        
        } else {
            // Get game state
            String[][] gameState = (String[][]) session.getAttribute("Game");
            // Swap empty spaces with underscores so table appears nicer in response
            for (int i=0; i<gameState.length; i++){
                for (int j=0; j<gameState[i].length; j++) {
                    if (gameState[i][j].equals("")) {
                        gameState[i][j] = "_";
                    }
                }
            }
            // If client request png format
            if (request.getParameter("format").equals( "png")) {
                String contentType = request.getParameter("format");
                if (contentType.equals("png")){
                    response.setContentType("text/html;charset=UTF-8");
                    // Create table with necessary attributes for jsp page
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<table class=\"table\" id=\"game\"><tr><td id=\"x1y1\" onclick=\"box1()\">"+gameState[0][0]+"</td><td id=\"x2y1\" class=\"leftright\" onclick=\"box2()\">"
                                +gameState[0][1]+"</td><td id=\"x3y1\" onclick=\"box3()\">"+gameState[0][2]+"</td></tr>");
                        out.println("<tr><td id=\"x1y2\" class=\"topdown\" onclick=\"box4()\">"+gameState[1][0]+"</td><td id=\"x2y2\" class=\"leftright topdown\" onclick=\"box5()\">"
                                +gameState[1][1]+"</td><td id=\"x3y2\" class=\"topdown\" onclick=\"box6()\">"+gameState[1][2]+"</td></tr>");
                        out.println("<tr><td id=\"x1y3\" onclick=\"box7()\">"+gameState[2][0]+"</td><td id=\"x2y3\" class=\"leftright\" onclick=\"box8()\">"
                                +gameState[2][1]+"</td><td id=\"x3y3\" onclick=\"box9()\">"+gameState[2][2]+"</td></tr></table>");
                    }
                } 
            // If client request txt format or does not request a format at all
            } else {
                response.setContentType("text/plain;charset=UTF-8");
                // Output plain txt string
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println(gameState[0][0] + gameState[0][1] + gameState[0][2] + "\n");
                    out.println(gameState[1][0] + gameState[1][1] + gameState[1][2] + "\n");
                    out.println(gameState[2][0] + gameState[2][1] + gameState[2][2] + "\n");
                }  
            }
            // Set spaces with underscore back to empty
            for (int i=0; i<gameState.length; i++){
                for (int j=0; j<gameState[i].length; j++) {
                    if (gameState[i][j].equals("_")) {
                        gameState[i][j] = "";
                    }
                }
            }   
        }   
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
