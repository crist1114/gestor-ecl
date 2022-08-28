/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Reserva;
import Persistencia.ReservaJpaController;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class ReservaDAO {
    private ReservaJpaController dep;
    
    public ReservaDAO() {
        Conexion con = Conexion.getConexion();
        dep = new ReservaJpaController(con.getBd());
    }
    
    public void create(Reserva departamento){
        try {
            dep.create(departamento);
        } catch (Exception ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Reserva> read(){  //devuelve todos los depmnos
        return dep.findReservaEntities();
    }
    
    public Reserva readReserva(int id){
        return dep.findReserva(id);
    }
    
    public void update(Reserva d){
        try {
            dep.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id){
        
        try {
            dep.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ArrayList<Reserva> buscarReservaCodigo(int codigo){
        
        return dep.findReservasCodigoEstudiante(codigo);
    }
}
