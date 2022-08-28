/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Libro;
import Persistencia.LibroJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class LibroDAO {
    
    private LibroJpaController dep;
    
    public LibroDAO() {
        Conexion con = Conexion.getConexion();
        dep = new LibroJpaController(con.getBd());
    }
    
    public void create(Libro departamento){
        try {
            dep.create(departamento);
        } catch (Exception ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Libro> read(){  //devuelve todos los depmnos
        return dep.findLibroEntities();
    }
    
    public Libro readLibro(int id){
        return dep.findLibro(id);
    }
    
    public void update(Libro d){
        try {
            dep.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id){
        
        try {
            dep.destroy(id);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
