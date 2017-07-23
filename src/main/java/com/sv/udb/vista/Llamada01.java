/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.vista;



import com.sv.udb.recursos.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author Luis
 */
@WebServlet(name = "Llamada01", urlPatterns = {"/Llamada01"})
public class Llamada01 extends HttpServlet {

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
        
        String salida;
        ServletOutputStream out = response.getOutputStream();
        try 
        {
            Connection cn = new Conexion().getConn();
            Map params = request.getParameterMap();
            for(Object temp: params.entrySet())
            {
               System.err.println("Params"+ temp);
            }
            Map parametros = null;
            String nombre = "report1.jasper";
            byte[] bytes = null;
            String UrlRepor = getServletContext().getRealPath("/reportes/"+ nombre);
            try 
            {
               bytes =  JasperRunManager.runReportToPdf(UrlRepor, parametros,cn);
               response.setContentType("application/pdf");
               response.setHeader("content-Disposition", "inline; filename=permiso.pdf");
               response.setContentLength(bytes.length);
               out.write(bytes,0,bytes.length);
               out.flush();
               out.close();
            }
            catch (JRException e) 
            {
                StringWriter stringwriter = new StringWriter();
                PrintWriter printwriter = new PrintWriter(stringwriter);
                e.printStackTrace(printwriter);
                response.setContentType("text/plain");
                response.getOutputStream().print(stringwriter.toString());
            }
            finally
            {
                if(!cn.isClosed())
                {
                    cn.close();
                }
            }
        }
        catch (Exception e) 
        {
        
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
