/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forge.mtg.utils.mtgboxmapperweb.ui.servlets;

import com.forge.mtg.utils.mtgboxmapper.BoxMappingApi;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author arman
 */
public class guessSrv extends HttpServlet {
    
    private BoxMappingApi api = null;

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session != null){
            String sleeveInput = (String) session.getAttribute("sleeveInput");
            String openedPacks = request.getParameter("openedPacks");
            if (sleeveInput == null || sleeveInput.replaceAll("[^1-5\\-]", "").length() != 38){
                writeResponse(response, "{\"error\" : \"selectSleeves\"}");
                return;
            } else {
                try {
                    api = new BoxMappingApi("GTC");
                } catch (Throwable ex) {
                    writeResponse(response, "{\"error\" : \"setIsNotRecognised\"}");
                    return;
                }
                api.setSleeves(sleeveInput);
                //api.setSleeves("123452534123-315241452315-534123241534");
            }
            
            if(openedPacks != null && !openedPacks.isEmpty()){
                String[] packs = openedPacks.split("\n");
                for (int i = 0; i < packs.length; i++) {
                    boolean hasNext = !(i == packs.length- 1);
                    String[] packInfo = packs[i].split("-");
                    if (packInfo.length != 2) {
                        System.out.println("Invalid opened pack info. Discarding " + packs[i] + ", example: L03-SoulRansom");
                    } else {
                        try{
                            api.selectCard(packInfo[0], packInfo[1], hasNext);
                        } catch (Exception e){
                            writeResponse(response, "{\"error\" : \"invalidEntryOrWrongRow\"}");
                            return;
                        }
                    }
                }
            }
            
            writeResponse(response, api.jsonShowAll());
        }
    }
    private void writeResponse(HttpServletResponse response, String message) throws IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            out.println(message);
        } finally {            
            out.close();
        }
    }
}
