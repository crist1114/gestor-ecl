/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Multa;
import Persistencia.MultaJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class MultaDAO {
    
    private MultaJpaController dep;
    
    public MultaDAO() {
        Conexion con = Conexion.getConexion();
        dep = new MultaJpaController(con.getBd());
    }
    
    public void create(Multa departamento){
        try {
            dep.create(departamento);
        } catch (Exception ex) {
            Logger.getLogger(MultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Multa> read(){  //devuelve todos los depmnos
        return dep.findMultaEntities();
    }
    
    public Multa readMulta(int id){
        return dep.findMulta(id);
    }
    
    public void update(Multa d){
        try {
            dep.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(MultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id){
        
        try {
            dep.destroy(id);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(MultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
