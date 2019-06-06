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
@WebServlet(name = "won", urlPatterns = {"/won"})
public class won extends HttpServlet {

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
            out.println("<title>Servlet won</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet won at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);
        response.setContentType("text/plain;charset=UTF-8");
        if (session == null) {
            response.setStatus(404);
            try (PrintWriter out = response.getWriter()) {
                out.println("404 No Game Found");
            }        
        } else {
            String[][] gameState = (String[][]) session.getAttribute("Game");
            String winner = checkWin(gameState);
            if (!"none".equals(winner)) {
                session.setAttribute("gameStarted", false);
            }
            try (PrintWriter out = response.getWriter()) {
                out.println(winner);
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
    
    public static String checkWin(String[][] gameState) {
        String winner = "";

        // Check rows for win
        winner = checkRows(gameState, winner);

        // Check columns for win
        if (winner.equals("")) {
            winner = checkColumns(gameState, winner);
        }
        

        // Check diagonal from left to right for win
        if (winner.equals("")) {
            winner = checkDiagonalsLeftRight(gameState, winner);
        }
           
        // Check diagonal from right to left for win
        if (winner.equals("")) {
            winner = checkDiagonalsRightLeft(gameState, winner);
        }
        
        // Check for draw
        if (winner.equals("")) {
            winner = checkDraw(gameState, winner);
        }

        // If there is still no winner then set winner to none
        if (winner.equals("")) {
            winner = "none";
        }
        
        return winner;
    }
    
    private static String checkRows(String[][] gameState, String winner) {
        for (int i=0; i<gameState.length; i++) {
            // Set counters for number of "x" and "o"
            int crossCount = 0;
            int noughtCount = 0;
            // Loop through game state and count "x" and "o"
            for (int j=0; j<gameState[i].length; j++) {
                if (gameState[i][j].equals("x")) {
                    crossCount += 1;
                } else if (gameState[i][j].equals("o")) {
                    noughtCount += 1;
                }
            }
            // If either cross count or nought count = 3 then the board has a winner
            if (crossCount == 3) {
                winner = "user";
                break;
            } else if (noughtCount == 3) {
                winner = "computer";
                break;
            }
        }
        return winner;
    }
    
    private static String checkColumns(String[][] gameState, String winner){
        for (int i=0; i<gameState.length; i++) {
            // Set counters for number of "x" and "o"
            int crossCount = 0;
            int noughtCount = 0;
            // Loop through game state and count "x" and "o"
            for (int j=0; j<gameState[i].length; j++) {
                if (gameState[j][i].equals("x")) {
                    crossCount += 1;
                } else if (gameState[j][i].equals("o")) {
                    noughtCount += 1;
                }
            }
            // If either cross count or nought count = 3 then the board has a winner
            if (crossCount == 3) {
                winner = "user";
                break;
            } else if (noughtCount == 3) {
                winner = "computer";
                break;
            }
        }
        return winner;
    }
    
    private static String checkDiagonalsLeftRight(String[][] gameState, String winner) {
        // Set counters for number of "x" and "o"
        int crossCount = 0;
        int noughtCount = 0;
        // Loop through game state and count "x" and "o"
        for (int i=0; i<gameState.length; i++) {
            int j = i;
            if (gameState[i][j].equals("x")) {
                crossCount += 1;
            } else if (gameState[i][j].equals("o")) {
                noughtCount += 1;
            }
        }
        // If either cross count or nought count = 3 then the board has a winner
        if (crossCount == 3) {
            winner = "user";
        } else if (noughtCount == 3) {
            winner = "computer";
        }
        return winner;
    }
    
    private static String checkDiagonalsRightLeft(String[][] gameState, String winner) {
        // Set counters for number of "x" and "o"
        int crossCount = 0;
        int noughtCount = 0;
        // Loop through game state and count "x" and "o"
        for (int i=0; i<gameState.length; i++) {
            int j = 2 - i;
            if (gameState[j][i].equals("x")) {
                crossCount += 1;
            } else if (gameState[j][i].equals("o")) {
                noughtCount += 1;
            }
        }
        // If either cross count or nought count = 3 then the board has a winner
        if (crossCount == 3) {
            winner = "user";
        } else if (noughtCount == 3) {
            winner = "computer";
        }
        return winner;
    }
    
    private static String checkDraw(String[][] gameState, String winner) {
        // Loop through game state and if a empty space is found, return from method
        for (int i=0; i<gameState.length; i++) {
            for (int j=0; j<gameState[i].length; j++) {
                if (gameState[i][j].equals("_") || gameState[i][j].equals("")) {
                    return winner;
                }
            }
        }
        // If no empty space has been found in the board then the game is in draw
        winner = "draw";
        return winner;
    }
}
