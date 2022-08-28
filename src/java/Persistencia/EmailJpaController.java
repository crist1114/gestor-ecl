/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import DTO.Email;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Estudiante;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USUARIO
 */
public class EmailJpaController implements Serializable {

    public EmailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Email email) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante = email.getEstudiante();
            if (estudiante != null) {
                estudiante = em.getReference(estudiante.getClass(), estudiante.getEstudiantePK());
                email.setEstudiante(estudiante);
            }
            em.persist(email);
            if (estudiante != null) {
                estudiante.getEmailList().add(email);
                estudiante = em.merge(estudiante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Email email) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Email persistentEmail = em.find(Email.class, email.getId());
            Estudiante estudianteOld = persistentEmail.getEstudiante();
            Estudiante estudianteNew = email.getEstudiante();
            if (estudianteNew != null) {
                estudianteNew = em.getReference(estudianteNew.getClass(), estudianteNew.getEstudiantePK());
                email.setEstudiante(estudianteNew);
            }
            email = em.merge(email);
            if (estudianteOld != null && !estudianteOld.equals(estudianteNew)) {
                estudianteOld.getEmailList().remove(email);
                estudianteOld = em.merge(estudianteOld);
            }
            if (estudianteNew != null && !estudianteNew.equals(estudianteOld)) {
                estudianteNew.getEmailList().add(email);
                estudianteNew = em.merge(estudianteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = email.getId();
                if (findEmail(id) == null) {
                    throw new NonexistentEntityException("The email with id " + id + " no longer exists.");
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
            Email email;
            try {
                email = em.getReference(Email.class, id);
                email.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The email with id " + id + " no longer exists.", enfe);
            }
            Estudiante estudiante = email.getEstudiante();
            if (estudiante != null) {
                estudiante.getEmailList().remove(email);
                estudiante = em.merge(estudiante);
            }
            em.remove(email);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Email> findEmailEntities() {
        return findEmailEntities(true, -1, -1);
    }

    public List<Email> findEmailEntities(int maxResults, int firstResult) {
        return findEmailEntities(false, maxResults, firstResult);
    }

    private List<Email> findEmailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Email.class));
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

    public Email findEmail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Email.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Email> rt = cq.from(Email.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
