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
@WebServlet(name = "possiblemoves", urlPatterns = {"/possiblemoves"})
public class possiblemoves extends HttpServlet {
    
    private static String possMoves = "";
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
            out.println("<title>Servlet possiblemoves</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet possiblemoves at " + request.getContextPath() + "</h1>");
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
        // Get session
        HttpSession session = request.getSession(false);
        response.setContentType("text/plain;charset=UTF-8");
        // Check if session is active
        if (session == null) {
            response.setStatus(404);
            try (PrintWriter out = response.getWriter()) {
                out.println("No active game found");
            }        
        } else {
            // Get game state from session
            String[][] gameState = (String[][]) session.getAttribute("Game");
            try (PrintWriter out = response.getWriter()) {
                // calculate possible moves
                out.println(calculateMoves(gameState));
            }
        }
    }
    
    /**
     * Calculates possible moves from game state.
     *
     * @param gameState state of the game
     * @return empty spaces.
     */
    public String calculateMoves(String[][] gameState) {
        String moves = "";
        // Loop through game and see which spaces are empty
        for (int i = 0; i<gameState.length; i++) {
            for (int j = 0;j<gameState[i].length; j++) { 
                if ("".equals(gameState[i][j])) {
                    moves += i + "," + j + "\n";  
                }              
            }
        }
        return moves;
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
