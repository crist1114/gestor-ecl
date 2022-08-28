/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Email;
import Persistencia.EmailJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class EmailDAO {
    
    private EmailJpaController dep;
    
    public EmailDAO() {
        Conexion con = Conexion.getConexion();
        dep = new EmailJpaController(con.getBd());
    }
    
    public void create(Email departamento){
        try {
            dep.create(departamento);
        } catch (Exception ex) {
            Logger.getLogger(EmailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Email> read(){  //devuelve todos los depmnos
        return dep.findEmailEntities();
    }
    
    public Email readEmail(int id){
        return dep.findEmail(id);
    }
    
    public void update(Email d){
        try {
            dep.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(EmailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id){
        
        try {
            dep.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EmailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
