/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Admin;
import Persistencia.AdminJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class AdminDAO {
    
    private AdminJpaController dep;
    
    public AdminDAO() {
        Conexion con = Conexion.getConexion();
        dep = new AdminJpaController(con.getBd());
    }
    
    public void create(Admin departamento){
        try {
            dep.create(departamento);
        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Admin> read(){  //devuelve todos los depmnos
        return dep.findAdminEntities();
    }
    
    public Admin readAdmin(int id){
        return dep.findAdmin(id);
    }
    
    public void update(Admin d){
        try {
            dep.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id){
        
        try {
            dep.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Admin buscarAdmin(String correo){
        
        return dep.buscar(correo);
    }
    
    
    
}
