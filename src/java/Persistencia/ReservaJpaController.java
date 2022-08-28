/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Libro;
import DTO.Estudiante;
import DTO.Reserva;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USUARIO
 */
public class ReservaJpaController implements Serializable {

    public ReservaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reserva reserva) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Libro idLibro = reserva.getIdLibro();
            if (idLibro != null) {
                idLibro = em.getReference(idLibro.getClass(), idLibro.getId());
                reserva.setIdLibro(idLibro);
            }
            Estudiante estudiante = reserva.getEstudiante();
            if (estudiante != null) {
                estudiante = em.getReference(estudiante.getClass(), estudiante.getEstudiantePK());
                reserva.setEstudiante(estudiante);
            }
            em.persist(reserva);
            if (idLibro != null) {
                idLibro.getReservaList().add(reserva);
                idLibro = em.merge(idLibro);
            }
            if (estudiante != null) {
                estudiante.getReservaList().add(reserva);
                estudiante = em.merge(estudiante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reserva reserva) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva persistentReserva = em.find(Reserva.class, reserva.getId());
            Libro idLibroOld = persistentReserva.getIdLibro();
            Libro idLibroNew = reserva.getIdLibro();
            Estudiante estudianteOld = persistentReserva.getEstudiante();
            Estudiante estudianteNew = reserva.getEstudiante();
            if (idLibroNew != null) {
                idLibroNew = em.getReference(idLibroNew.getClass(), idLibroNew.getId());
                reserva.setIdLibro(idLibroNew);
            }
            if (estudianteNew != null) {
                estudianteNew = em.getReference(estudianteNew.getClass(), estudianteNew.getEstudiantePK());
                reserva.setEstudiante(estudianteNew);
            }
            reserva = em.merge(reserva);
            if (idLibroOld != null && !idLibroOld.equals(idLibroNew)) {
                idLibroOld.getReservaList().remove(reserva);
                idLibroOld = em.merge(idLibroOld);
            }
            if (idLibroNew != null && !idLibroNew.equals(idLibroOld)) {
                idLibroNew.getReservaList().add(reserva);
                idLibroNew = em.merge(idLibroNew);
            }
            if (estudianteOld != null && !estudianteOld.equals(estudianteNew)) {
                estudianteOld.getReservaList().remove(reserva);
                estudianteOld = em.merge(estudianteOld);
            }
            if (estudianteNew != null && !estudianteNew.equals(estudianteOld)) {
                estudianteNew.getReservaList().add(reserva);
                estudianteNew = em.merge(estudianteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reserva.getId();
                if (findReserva(id) == null) {
                    throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva reserva;
            try {
                reserva = em.getReference(Reserva.class, id);
                reserva.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.", enfe);
            }
            Libro idLibro = reserva.getIdLibro();
            if (idLibro != null) {
                idLibro.getReservaList().remove(reserva);
                idLibro = em.merge(idLibro);
            }
            Estudiante estudiante = reserva.getEstudiante();
            if (estudiante != null) {
                estudiante.getReservaList().remove(reserva);
                estudiante = em.merge(estudiante);
            }
            em.remove(reserva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reserva> findReservaEntities() {
        return findReservaEntities(true, -1, -1);
    }

    public List<Reserva> findReservaEntities(int maxResults, int firstResult) {
        return findReservaEntities(false, maxResults, firstResult);
    }

    private List<Reserva> findReservaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reserva.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Reserva findReserva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reserva.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reserva> rt = cq.from(Reserva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public ArrayList<Reserva> findReservasCodigoEstudiante(int codigo){
        
        List<Reserva> reservas = findReservaEntities();
        ArrayList<Reserva> rta = new ArrayList<>();
        
        for(Reserva r: reservas){
            
            if(r.getEstudiante().getEstudiantePK().getCodigo() == codigo){
                rta.add(r);
            }
        
        }
        
        return rta;
    }
    
}
