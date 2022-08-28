/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Prestamo;
import Persistencia.PrestamoJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class PrestamoDAO {
    
    private PrestamoJpaController dep;
    
    public PrestamoDAO() {
        Conexion con = Conexion.getConexion();
        dep = new PrestamoJpaController(con.getBd());
    }
    
    public void create(Prestamo departamento){
        try {
            dep.create(departamento);
        } catch (Exception ex) {
            Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Prestamo> read(){  //devuelve todos los depmnos
        return dep.findPrestamoEntities();
    }
    
    public Prestamo readPrestamo(int id){
        return dep.findPrestamo(id);
    }
    
    public void update(Prestamo d){
        try {
            dep.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id){
        
        try {
            dep.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PrestamoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
