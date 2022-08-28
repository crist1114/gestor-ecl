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
import DTO.Categoria;
import DTO.Libro;
import DTO.Reserva;
import java.util.ArrayList;
import java.util.List;
import DTO.Prestamo;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USUARIO
 */
public class LibroJpaController implements Serializable {

    public LibroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Libro libro) {
        if (libro.getReservaList() == null) {
            libro.setReservaList(new ArrayList<Reserva>());
        }
        if (libro.getPrestamoList() == null) {
            libro.setPrestamoList(new ArrayList<Prestamo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoriaId = libro.getCategoriaId();
            if (categoriaId != null) {
                categoriaId = em.getReference(categoriaId.getClass(), categoriaId.getId());
                libro.setCategoriaId(categoriaId);
            }
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : libro.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getId());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            libro.setReservaList(attachedReservaList);
            List<Prestamo> attachedPrestamoList = new ArrayList<Prestamo>();
            for (Prestamo prestamoListPrestamoToAttach : libro.getPrestamoList()) {
                prestamoListPrestamoToAttach = em.getReference(prestamoListPrestamoToAttach.getClass(), prestamoListPrestamoToAttach.getId());
                attachedPrestamoList.add(prestamoListPrestamoToAttach);
            }
            libro.setPrestamoList(attachedPrestamoList);
            em.persist(libro);
            if (categoriaId != null) {
                categoriaId.getLibroList().add(libro);
                categoriaId = em.merge(categoriaId);
            }
            for (Reserva reservaListReserva : libro.getReservaList()) {
                Libro oldIdLibroOfReservaListReserva = reservaListReserva.getIdLibro();
                reservaListReserva.setIdLibro(libro);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldIdLibroOfReservaListReserva != null) {
                    oldIdLibroOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldIdLibroOfReservaListReserva = em.merge(oldIdLibroOfReservaListReserva);
                }
            }
            for (Prestamo prestamoListPrestamo : libro.getPrestamoList()) {
                Libro oldIdLibroOfPrestamoListPrestamo = prestamoListPrestamo.getIdLibro();
                prestamoListPrestamo.setIdLibro(libro);
                prestamoListPrestamo = em.merge(prestamoListPrestamo);
                if (oldIdLibroOfPrestamoListPrestamo != null) {
                    oldIdLibroOfPrestamoListPrestamo.getPrestamoList().remove(prestamoListPrestamo);
                    oldIdLibroOfPrestamoListPrestamo = em.merge(oldIdLibroOfPrestamoListPrestamo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Libro libro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Libro persistentLibro = em.find(Libro.class, libro.getId());
            Categoria categoriaIdOld = persistentLibro.getCategoriaId();
            Categoria categoriaIdNew = libro.getCategoriaId();
            List<Reserva> reservaListOld = persistentLibro.getReservaList();
            List<Reserva> reservaListNew = libro.getReservaList();
            List<Prestamo> prestamoListOld = persistentLibro.getPrestamoList();
            List<Prestamo> prestamoListNew = libro.getPrestamoList();
            List<String> illegalOrphanMessages = null;
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaListOldReserva + " since its idLibro field is not nullable.");
                }
            }
            for (Prestamo prestamoListOldPrestamo : prestamoListOld) {
                if (!prestamoListNew.contains(prestamoListOldPrestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prestamo " + prestamoListOldPrestamo + " since its idLibro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoriaIdNew != null) {
                categoriaIdNew = em.getReference(categoriaIdNew.getClass(), categoriaIdNew.getId());
                libro.setCategoriaId(categoriaIdNew);
            }
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getId());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            libro.setReservaList(reservaListNew);
            List<Prestamo> attachedPrestamoListNew = new ArrayList<Prestamo>();
            for (Prestamo prestamoListNewPrestamoToAttach : prestamoListNew) {
                prestamoListNewPrestamoToAttach = em.getReference(prestamoListNewPrestamoToAttach.getClass(), prestamoListNewPrestamoToAttach.getId());
                attachedPrestamoListNew.add(prestamoListNewPrestamoToAttach);
            }
            prestamoListNew = attachedPrestamoListNew;
            libro.setPrestamoList(prestamoListNew);
            libro = em.merge(libro);
            if (categoriaIdOld != null && !categoriaIdOld.equals(categoriaIdNew)) {
                categoriaIdOld.getLibroList().remove(libro);
                categoriaIdOld = em.merge(categoriaIdOld);
            }
            if (categoriaIdNew != null && !categoriaIdNew.equals(categoriaIdOld)) {
                categoriaIdNew.getLibroList().add(libro);
                categoriaIdNew = em.merge(categoriaIdNew);
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    Libro oldIdLibroOfReservaListNewReserva = reservaListNewReserva.getIdLibro();
                    reservaListNewReserva.setIdLibro(libro);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldIdLibroOfReservaListNewReserva != null && !oldIdLibroOfReservaListNewReserva.equals(libro)) {
                        oldIdLibroOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldIdLibroOfReservaListNewReserva = em.merge(oldIdLibroOfReservaListNewReserva);
                    }
                }
            }
            for (Prestamo prestamoListNewPrestamo : prestamoListNew) {
                if (!prestamoListOld.contains(prestamoListNewPrestamo)) {
                    Libro oldIdLibroOfPrestamoListNewPrestamo = prestamoListNewPrestamo.getIdLibro();
                    prestamoListNewPrestamo.setIdLibro(libro);
                    prestamoListNewPrestamo = em.merge(prestamoListNewPrestamo);
                    if (oldIdLibroOfPrestamoListNewPrestamo != null && !oldIdLibroOfPrestamoListNewPrestamo.equals(libro)) {
                        oldIdLibroOfPrestamoListNewPrestamo.getPrestamoList().remove(prestamoListNewPrestamo);
                        oldIdLibroOfPrestamoListNewPrestamo = em.merge(oldIdLibroOfPrestamoListNewPrestamo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = libro.getId();
                if (findLibro(id) == null) {
                    throw new NonexistentEntityException("The libro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Libro libro;
            try {
                libro = em.getReference(Libro.class, id);
                libro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The libro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Reserva> reservaListOrphanCheck = libro.getReservaList();
            for (Reserva reservaListOrphanCheckReserva : reservaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Libro (" + libro + ") cannot be destroyed since the Reserva " + reservaListOrphanCheckReserva + " in its reservaList field has a non-nullable idLibro field.");
            }
            List<Prestamo> prestamoListOrphanCheck = libro.getPrestamoList();
            for (Prestamo prestamoListOrphanCheckPrestamo : prestamoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Libro (" + libro + ") cannot be destroyed since the Prestamo " + prestamoListOrphanCheckPrestamo + " in its prestamoList field has a non-nullable idLibro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria categoriaId = libro.getCategoriaId();
            if (categoriaId != null) {
                categoriaId.getLibroList().remove(libro);
                categoriaId = em.merge(categoriaId);
            }
            em.remove(libro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Libro> findLibroEntities() {
        return findLibroEntities(true, -1, -1);
    }

    public List<Libro> findLibroEntities(int maxResults, int firstResult) {
        return findLibroEntities(false, maxResults, firstResult);
    }

    private List<Libro> findLibroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Libro.class));
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

    public Libro findLibro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Libro.class, id);
        } finally {
            em.close();
        }
    }

    public int getLibroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Libro> rt = cq.from(Libro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
