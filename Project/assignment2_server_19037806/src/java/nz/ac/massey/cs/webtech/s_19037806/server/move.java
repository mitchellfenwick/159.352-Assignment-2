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
@WebServlet(name = "move", urlPatterns = {"/move/*"})
public class move extends HttpServlet {

    private static boolean gameOver;
    private won gameWin = new won();
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
            out.println("<title>Servlet move</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet move at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/plain;charset=UTF-8");
        HttpSession session = request.getSession(false);
        // Check if a session exist, if not give 404 error
        if (session == null) {
            response.setStatus(404);
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Tic Tac Toe</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>404 Game Not Found</h1>");
                out.println("</html>"); 
            }
        } else {
            // Only check move if a game is in session
            if (!gameOver) {
                String position = request.getPathInfo();
                // Move player
                movePlayer(position, session, response);  
            }
        } 
    }
    
    /**
     * Moves the player calls for computer to move.
     *
     * @param session session object
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    private void movePlayer(String position, HttpSession session, HttpServletResponse response) throws IOException {
        String coord = "xy";
        char x = coord.charAt(0);
        char y = coord.charAt(1);
        // Get game state from session
        String[][] gameState = (String[][]) session.getAttribute("Game");
        // Check for any bad request
        if (position.length() > 5 || position.length() < 4 || position.charAt(1) != x || position.charAt(3) != y || Character.getNumericValue(position.charAt(2)) > 3
            || Character.getNumericValue(position.charAt(4)) > 3 || Character.getNumericValue(position.charAt(4)) < 1 || Character.getNumericValue(position.charAt(2)) < 1) {
            badMove(response);
        }
        else 
        {
            // Extract move position
            int xPosition = Character.getNumericValue(position.charAt(2)) - 1;
            int yPosition = Character.getNumericValue(position.charAt(4)) - 1;
            // Check if spot has already been filled
            if (!("".equals(gameState[yPosition][xPosition]) || "_".equals(gameState[yPosition][xPosition]))) {
                badMove(response);
            } else {
                // Set x at position
                gameState[yPosition][xPosition] = "x";
                // Check if player has won
                if (!won.checkWin(gameState).equals("none")) {
                    gameOver = true;
                } else {
                    // Place computer o
                    moveComputer(gameState);
                    // Check if Computer has won
                    if (!won.checkWin(gameState).equals("none")) {
                        gameOver = true;
                    }
                }
            }
        }
    }
    
    /**
     * Moves the computer.
     *
     * @param gameState game state.
     */
    private void moveComputer(String[][] gameState) {
        // Get possible moves from game state
        possiblemoves possibleMoves = new possiblemoves();
        String moves = possibleMoves.calculateMoves(gameState);
        String[] arrMoves = moves.split("\\r?\\n");
        int randIndex = (int) (Math.random() * arrMoves.length);
        // Choose one of the possible moves at random
        String choosenSpace = arrMoves[randIndex];
        int xPosition = Character.getNumericValue(choosenSpace.charAt(0));
        int yPosition = Character.getNumericValue(choosenSpace.charAt(2));
        // Set computer o
        gameState[xPosition][yPosition] = "o";
    }
    
    /**
     * Sends back 400 status code to client
     *
     * @param response servlet response.
     */
    private void badMove(HttpServletResponse response) throws IOException {
        // Handle a bad request
        response.setStatus(400);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Tic Tac Toe</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>400 Bad Request</h1>");
            out.println("</html>"); 
        }
    }
    
    public void setGameOver(boolean game){
        gameOver = game;
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
