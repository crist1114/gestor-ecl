/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Categoria;
import Persistencia.CategoriaJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class CategoriaDAO {
    
    private CategoriaJpaController dep;
    
    public CategoriaDAO() {
        Conexion con = Conexion.getConexion();
        dep = new CategoriaJpaController(con.getBd());
    }
    
    public void create(Categoria departamento){
        try {
            dep.create(departamento);
        } catch (Exception ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Categoria> read(){  //devuelve todos los depmnos
        return dep.findCategoriaEntities();
    }
    
    public Categoria readCategoria(int id){
        return dep.findCategoria(id);
    }
    
    public void update(Categoria d){
        try {
            dep.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id){
        
        try {
            dep.destroy(id);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }   
}
