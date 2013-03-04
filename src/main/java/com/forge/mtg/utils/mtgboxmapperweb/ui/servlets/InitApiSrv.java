/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forge.mtg.utils.mtgboxmapperweb.ui.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author arman
 */
public class InitApiSrv extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sleeveInput = request.getParameter("sleeveInput");
        if (sleeveInput == null || sleeveInput.replaceAll("[^1-5\\-]", "").length() != 38){
            sleeveInput = (String) request.getSession().getAttribute("sleeveInput");
        }
        if (sleeveInput == null || sleeveInput.replaceAll("[^1-5\\-]", "").length() != 38){
            response.sendRedirect("index.jsp");
            //request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("sleeveInput", sleeveInput);
            request.getRequestDispatcher("guessPanel.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
