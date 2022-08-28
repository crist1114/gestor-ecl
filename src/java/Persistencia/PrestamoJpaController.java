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
import DTO.Estudiante;
import DTO.Libro;
import DTO.Multa;
import DTO.Prestamo;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USUARIO
 */
public class PrestamoJpaController implements Serializable {

    public PrestamoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Prestamo prestamo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante = prestamo.getEstudiante();
            if (estudiante != null) {
                estudiante = em.getReference(estudiante.getClass(), estudiante.getEstudiantePK());
                prestamo.setEstudiante(estudiante);
            }
            Libro idLibro = prestamo.getIdLibro();
            if (idLibro != null) {
                idLibro = em.getReference(idLibro.getClass(), idLibro.getId());
                prestamo.setIdLibro(idLibro);
            }
            Multa idMulta = prestamo.getIdMulta();
            if (idMulta != null) {
                idMulta = em.getReference(idMulta.getClass(), idMulta.getId());
                prestamo.setIdMulta(idMulta);
            }
            em.persist(prestamo);
            if (estudiante != null) {
                estudiante.getPrestamoList().add(prestamo);
                estudiante = em.merge(estudiante);
            }
            if (idLibro != null) {
                idLibro.getPrestamoList().add(prestamo);
                idLibro = em.merge(idLibro);
            }
            if (idMulta != null) {
                idMulta.getPrestamoList().add(prestamo);
                idMulta = em.merge(idMulta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Prestamo prestamo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prestamo persistentPrestamo = em.find(Prestamo.class, prestamo.getId());
            Estudiante estudianteOld = persistentPrestamo.getEstudiante();
            Estudiante estudianteNew = prestamo.getEstudiante();
            Libro idLibroOld = persistentPrestamo.getIdLibro();
            Libro idLibroNew = prestamo.getIdLibro();
            Multa idMultaOld = persistentPrestamo.getIdMulta();
            Multa idMultaNew = prestamo.getIdMulta();
            if (estudianteNew != null) {
                estudianteNew = em.getReference(estudianteNew.getClass(), estudianteNew.getEstudiantePK());
                prestamo.setEstudiante(estudianteNew);
            }
            if (idLibroNew != null) {
                idLibroNew = em.getReference(idLibroNew.getClass(), idLibroNew.getId());
                prestamo.setIdLibro(idLibroNew);
            }
            if (idMultaNew != null) {
                idMultaNew = em.getReference(idMultaNew.getClass(), idMultaNew.getId());
                prestamo.setIdMulta(idMultaNew);
            }
            prestamo = em.merge(prestamo);
            if (estudianteOld != null && !estudianteOld.equals(estudianteNew)) {
                estudianteOld.getPrestamoList().remove(prestamo);
                estudianteOld = em.merge(estudianteOld);
            }
            if (estudianteNew != null && !estudianteNew.equals(estudianteOld)) {
                estudianteNew.getPrestamoList().add(prestamo);
                estudianteNew = em.merge(estudianteNew);
            }
            if (idLibroOld != null && !idLibroOld.equals(idLibroNew)) {
                idLibroOld.getPrestamoList().remove(prestamo);
                idLibroOld = em.merge(idLibroOld);
            }
            if (idLibroNew != null && !idLibroNew.equals(idLibroOld)) {
                idLibroNew.getPrestamoList().add(prestamo);
                idLibroNew = em.merge(idLibroNew);
            }
            if (idMultaOld != null && !idMultaOld.equals(idMultaNew)) {
                idMultaOld.getPrestamoList().remove(prestamo);
                idMultaOld = em.merge(idMultaOld);
            }
            if (idMultaNew != null && !idMultaNew.equals(idMultaOld)) {
                idMultaNew.getPrestamoList().add(prestamo);
                idMultaNew = em.merge(idMultaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prestamo.getId();
                if (findPrestamo(id) == null) {
                    throw new NonexistentEntityException("The prestamo with id " + id + " no longer exists.");
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
            Prestamo prestamo;
            try {
                prestamo = em.getReference(Prestamo.class, id);
                prestamo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prestamo with id " + id + " no longer exists.", enfe);
            }
            Estudiante estudiante = prestamo.getEstudiante();
            if (estudiante != null) {
                estudiante.getPrestamoList().remove(prestamo);
                estudiante = em.merge(estudiante);
            }
            Libro idLibro = prestamo.getIdLibro();
            if (idLibro != null) {
                idLibro.getPrestamoList().remove(prestamo);
                idLibro = em.merge(idLibro);
            }
            Multa idMulta = prestamo.getIdMulta();
            if (idMulta != null) {
                idMulta.getPrestamoList().remove(prestamo);
                idMulta = em.merge(idMulta);
            }
            em.remove(prestamo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prestamo> findPrestamoEntities() {
        return findPrestamoEntities(true, -1, -1);
    }

    public List<Prestamo> findPrestamoEntities(int maxResults, int firstResult) {
        return findPrestamoEntities(false, maxResults, firstResult);
    }

    private List<Prestamo> findPrestamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Prestamo.class));
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

    public Prestamo findPrestamo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Prestamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrestamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Prestamo> rt = cq.from(Prestamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
