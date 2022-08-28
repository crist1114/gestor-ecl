/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Notificacion;
import Persistencia.NotificacionJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class NotificacionDAO {
    
    private NotificacionJpaController dep;
    
    public NotificacionDAO() {
        Conexion con = Conexion.getConexion();
        dep = new NotificacionJpaController(con.getBd());
    }
    
    public void create(Notificacion departamento){
        try {
            dep.create(departamento);
        } catch (Exception ex) {
            Logger.getLogger(NotificacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Notificacion> read(){  //devuelve todos los depmnos
        return dep.findNotificacionEntities();
    }
    
    public Notificacion readNotificacion(int id){
        return dep.findNotificacion(id);
    }
    
    public void update(Notificacion d){
        try {
            dep.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(NotificacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id){
        
        try {
            dep.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
