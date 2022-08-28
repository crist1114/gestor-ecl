/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Estudiante;
import DTO.EstudiantePK;
import Persistencia.EstudianteJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class EstudianteDAO {
    
    private EstudianteJpaController dep;
    
    public EstudianteDAO() {
        Conexion con = Conexion.getConexion();
        dep = new EstudianteJpaController(con.getBd());
    }
    
    public void create(Estudiante departamento){
        try {
            dep.create(departamento);
        } catch (Exception ex) {
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Estudiante> read(){  //devuelve todos los depmnos
        return dep.findEstudianteEntities();
    }
    
    public Estudiante readEstudiante(EstudiantePK id){
        return dep.findEstudiante(id);
    }
    
    public void update(Estudiante d){
        try {
            dep.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(EstudiantePK id){
        
        try {
            dep.destroy(id);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Estudiante buscarEstudiante(String correo){
        
        return dep.buscar(correo);
    }
}
