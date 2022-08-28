/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorVistas;

import DTO.Estudiante;
import DTO.EstudiantePK;
import DTO.Libro;
import Negocio.Biblioteca;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author USUARIO
 */
public class Reservar extends HttpServlet {

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
        
         Libro l = (Libro)request.getSession().getAttribute("libro");
         String [] fecha = request.getParameter("fechaEntrega").toString().split("-");
         //System.out.println("FECHA :" + Integer.parseInt(fecha[0]) +" " + fecha[1]);
         
         Date f = new Date(Integer.parseInt(fecha[0]) - 1900, Integer.parseInt(fecha[1])-1, Integer.parseInt(fecha[2]));
         //Date f = (Date)request.getParameter("fechaEntrega");
        Estudiante e = (Estudiante)request.getSession().getAttribute("usuario");
        System.out.println("FECHA :" +  f.toString()) ;
       // Estudiante e = new Estudiante(new EstudiantePK(1151835, "jefersonrr@ufps.edu.co"), "Jeferson", "Rodriguez", 30035666, "1151835", "");
         
         Biblioteca b = new Biblioteca();
         
         boolean reservar = b.crearReserva(f, l, e);
         
         if(reservar){
         
             request.getSession().setAttribute("mensaje", "<p style='color:green;'>Reserva Realizada con Exito</p>");
             
         }else{
         
             request.getSession().setAttribute("mensaje", "<p style='color:red;'>No se pudo realizar la reserva, no disponibilidad</p>");
         }
         
         request.getRequestDispatcher("./jsp/libroEstudiante.jsp").forward(request, response);
         
         
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
