/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.AdminDAO;
import DAO.CategoriaDAO;
import DAO.EstudianteDAO;
import DAO.LibroDAO;
import DAO.ReservaDAO;
import DTO.Admin;
import DTO.Categoria;
import DTO.Estudiante;
import DTO.Libro;
import DTO.Notificacion;
import DTO.Prestamo;
import DTO.Reserva;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class Biblioteca {
    
    

    
    public Estudiante validarUsuario(String correo, String contrasenia) {

        EstudianteDAO e = new EstudianteDAO();
        Estudiante es = e.buscarEstudiante(correo);

        if (es != null && es.getContraseña().equals(contrasenia)) {
            return es;
        } else {
            return null;
        }
    }
     public Admin validarAdmin(String correo, String contrasenia) {

        AdminDAO e = new AdminDAO();
        Admin es = e.buscarAdmin(correo);

        if (es != null && es.getContraseña().equals(contrasenia)) {
            return es;
        } else {
            return null;
        }
    }
     
    public String getLibrosAdmin() {
        LibroDAO c = new LibroDAO();
        List<Libro> libros = c.read();
        return getLibrosAdmin(libros);
    }

    public String getLibros() {
        LibroDAO c = new LibroDAO();
        List<Libro> libros = c.read();
        return getLibros(libros);
    }
    
    private String getLibros(List<Libro> libros){
        String rta="";
        for (Libro lib : libros) {

            rta += "<div class=\"col-5\">\n"
                    + "                <div class=\"card mb-3\" style=\"max-width: 540px;\">\n"
                    + "                    <div class=\"row g-0\">\n"
                    + "                        <div class=\"col-md-4\">\n"
                    + "                            <img src='" + lib.getUrlImagen() + "' alt=\"...\"\n"
                    + "                                style=\"width: 100%; height: 100%;\">\n"
                    + "                        </div>\n"
                    + "                        <div class=\"col-md-8\">\n"
                    + "                            <div class=\"card-body\">"
                    + "<form action='CargarLibroEstudiante.do'>\n"
                    + "                                <h5 class=\"card-title\">" + lib.getTitulo() + "</h5>\n"
                    + "                                <p class=\"card-text\">" + lib.getDescripcion().substring(0, 120) + "...</p>\n"
                    + "                                <p class=\"card-text\"></p>"
                    + "         <input hidden name=\"idLibro\" value=\""+lib.getId()+"\"></input>\n"
                    + "                                <button type=\"submit\" class=\"btn btn-danger\">Ver Libro</button>\n"
                    + " </form></div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n";
        }
        return rta;
    }
    
    private String getLibrosAdmin(List<Libro> libros){
        String rta="";
        for (Libro lib : libros) {

            rta += "<div class=\"col-5\">\n"
                    + "                <div class=\"card mb-3\" style=\"max-width: 540px;\">\n"
                    + "                    <div class=\"row g-0\">\n"
                    + "                        <div class=\"col-md-4\">\n"
                    + "                            <img src='" + lib.getUrlImagen() + "' alt=\"...\"\n"
                    + "                                style=\"width: 100%; height: 100%;\">\n"
                    + "                        </div>\n"
                    + "                        <div class=\"col-md-8\">\n"
                    + "                            <div class=\"card-body\">"
                    + "<form action='CargarLibroAdmin.do'>\n"
                    + "                                <h5 class=\"card-title\">" + lib.getTitulo() + "</h5>\n"
                    + "                                <p class=\"card-text\">" + lib.getDescripcion().substring(0, 120) + "...</p>\n"
                    + "                                <p class=\"card-text\"></p>"
                    + "         <input hidden name=\"idLibro\" value=\""+lib.getId()+"\"></input>\n"
                    + "                                <button type=\"submit\" class=\"btn btn-danger\">Ver Libro</button>\n"
                    + " </form></div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n";
        }
        return rta;
    }
    
    public String getLibrosCategoria(int id_categoria){
        CategoriaDAO c = new CategoriaDAO();
        Categoria ca = c.readCategoria(id_categoria);
        
        List<Libro> libros = ca.getLibroList();
        
        return getLibros(libros);
    }
    
    public String getLibrosCategoriaAdmin(int id_categoria){
        CategoriaDAO c = new CategoriaDAO();
        Categoria ca = c.readCategoria(id_categoria);
        
        List<Libro> libros = ca.getLibroList();
        
        return getLibrosAdmin(libros);
    }

    public String getCategorias() {

        String cat = "";
        CategoriaDAO c = new CategoriaDAO();
        List<Categoria> ca = c.read();
        for (Categoria cate : ca) {
            cat += "<option value=" + cate.getId() + ">" + cate.getNombre() + "</option>";
        }
        return cat;
    }
    
    public String getNotificaciones(List<Notificacion> n){
    
        String rta = "";
        for (Notificacion no : n) {
            rta+="<div class=\"row border b-2 p-3 m-2\">\n" +
            "<p>"+no.getContenido()+"</p>\n" +
            "</div>";
        }
        return rta;
    }
    
    public String buscarLibrosAdmin(String nombre){
    
        LibroDAO li = new LibroDAO();
        List<Libro> libros = li.read();
        ArrayList<Libro> rta = new ArrayList<>();
        for (Libro l : libros) {
            
            if(l.getTitulo().toLowerCase().contains(nombre.toLowerCase())){
                rta.add(l);
            }
        }
        return getLibrosAdmin(rta);
    }
    
    public String buscarLibros(String nombre){
    
        LibroDAO li = new LibroDAO();
        List<Libro> libros = li.read();
        ArrayList<Libro> rta = new ArrayList<>();
        for (Libro l : libros) {
            
            if(l.getTitulo().toLowerCase().contains(nombre.toLowerCase())){
                rta.add(l);
            }
        }
        return getLibros(rta);
    }
    
    
    public String readReservas(String rta){
    
        String msg="";
 
        ReservaDAO reDAO = new ReservaDAO();
        
        
        List<Reserva> reservas = reDAO.read();
        
        for(Reserva r : reservas){
            
            
            String [] fecha  = r.getFecha().toString().split(" ");
            
                msg+= "<div class='row d-flex justify-content-center m-2'>"
        + "<div class='card w-75'>"
          + "<div class='card-body row'>"
             + "<div class='col-2'><img src=" + r.getEstudiante().getUrlFoto() + " class='rounded-circle' style='width: 100px; ' alt='Reserva'></div>"
             + "<div class='col-10'>"
              + "<h5 class='card-title'>" + r.getEstudiante().getNombre() + " " + r.getEstudiante().getApellido() + "</h5>"
             + "<p class='card-text'>" + "Solicitud Realizada el : " +  fecha[2] + "-" + fecha[1] +  "-" + fecha[5]+ "</p>"
             +  "<p class='card-text'>" + "Libro : " + r.getIdLibro().getTitulo()  + "</p>"
                        + "<div class='row'>"
              +  "<form action='./buscarReservas.do' method='get' class=' col-2 d-flex p-3'>"
                        
                        + "<input type='text' name='idReserva' value="+r.getId() +" hidden>"
                       + "<button class='btn btn-outline-light bg-success' type='submit'>Aceptar</button>"
                        
                    + "</form>"
                        
                        +  "<form action='./EliminarReserva.do' method='get' class=' col-1 d-flex p-3'>"
                        
                        + "<input type='text' name='idReserva' value="+r.getId() +" hidden>"
                        + "<button class='btn btn-outline-light bg-danger' type='submit'>Eliminar</button>"
                    + "</form>"
                        
                        + "<div class='col-3'> "
                        + "<p>" + rta +"</p>"
                        + "</div>"
                             + "</div>"
                        
           + "</div>"
        +  "</div>"
        + "</div>"
      + "</div>";
        }

        return msg;
    }
    
    public String buscarReservas( int codigo, String rta){
    
        String msg="";
 
        ReservaDAO reDAO = new ReservaDAO();
        
        
        ArrayList<Reserva> reservas = reDAO.buscarReservaCodigo(codigo);
        
        for(Reserva r : reservas){
            
            
            String [] fecha  = r.getFecha().toString().split(" ");
            
                msg+= "<div class='row d-flex justify-content-center m-2'>"
        + "<div class='card w-75'>"
          + "<div class='card-body row'>"
             + "<div class='col-2'><img src=" + r.getEstudiante().getUrlFoto() + " class='rounded-circle' style='width: 100px; ' alt='Reserva'></div>"
             + "<div class='col-10'>"
              + "<h5 class='card-title'>" + r.getEstudiante().getNombre() + " " + r.getEstudiante().getApellido() + "</h5>"
             + "<p class='card-text'>" + "Solicitud Realizada el : " +  fecha[2] + "-" + fecha[1] +  "-" + fecha[5]+ "</p>"
             +  "<p class='card-text'>" + "Libro : " + r.getIdLibro().getTitulo()  + "</p>"
           + "<div class='row'>"
              +  "<form action='./buscarReservas.do' method='get' class=' col-2 d-flex p-3'>"
                        + "<input type='text' name='idReserva' value="+r.getId() +" hidden>"
                       + "<button class='btn btn-outline-light bg-success' type='submit'>Aceptar</button>"
                        
                    + "</form>"
                        
                        +  "<form action='./EliminarReservaBusqueda.do' method='get' class=' col-1 d-flex p-3'>"
                        
                        + "<input type='text' name='codigo' value="+codigo +" hidden>"
                       + "<input type='text' name='idReserva' value="+r.getId() +" hidden>"
                        + "<button class='btn btn-outline-light bg-danger' type='submit'>Eliminar</button>"
                    + "</form>"
                         + "<div class='col-3'> "
                        + "<p>" + rta +"</p>"
                        + "</div>"
                             + "</div>"
           + "</div>"
        +  "</div>"
        + "</div>"
      + "</div>";
        }

        return msg;
    }
    
    public Libro readLibro(int id){
        return new LibroDAO().readLibro(id);
    }
    
    public boolean crearReserva(Date fecha, Libro l, Estudiante e){
        
    if(l.getDisponibilidad()>0){
    
        ReservaDAO rDAO = new ReservaDAO();
        Reserva r = new Reserva((short)0, fecha);
        r.setIdLibro(l);
        r.setEstudiante(e);
        rDAO.create(r);
        return true;
        
    }
    
    return false;
    }
    
    public void eliminarReserva(int idReserva){
        
        ReservaDAO rDAO = new ReservaDAO();
        rDAO.delete(idReserva);
    }
    
    
    public String tablaHistorial(Estudiante e){
        List<Prestamo> prestamos = e.getPrestamoList();
        String rta = "";
        if(prestamos == null){
            rta += "<tr><td>" + ""+ "</td>" + "<td>" + "" + "</td>" 
                        + "<td>" + "" + "</td>"+ "<td>"
                        + "" + "</td>"  +  "<td>" + "" + "</td>"+"</tr>";
        } else {
            for (Prestamo r:prestamos) {
                if(r.getIdMulta() == null){
                    rta += "<tr><td>" + r.getIdLibro().getTitulo()+ "</td>" + "<td>" + r.getFechaInicio() + "</td>" 
                        + "<td>" + r.getFechaFin() + "</td>"+ "<td>"
                        + r.getFinalizado() + "</td>"  +  "<td>" + "No aplica" + "</td>"+"</tr>";
                } else {
                    rta += "<tr><td>" + r.getIdLibro().getTitulo()+ "</td>" + "<td>" + r.getFechaInicio() + "</td>" 
                        + "<td>" + r.getFechaFin() + "</td>"+ "<td>"
                        + r.getFinalizado() + "</td>"  +  "<td>" + r.getIdMulta().getDescripcion() + "</td>"+"</tr>";
                }
             
        }
        }
        
        System.out.println("RTA :::: " + rta);
        return rta;
    }
}
