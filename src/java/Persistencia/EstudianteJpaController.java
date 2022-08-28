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
import DTO.Notificacion;
import java.util.ArrayList;
import java.util.List;
import DTO.Email;
import DTO.Estudiante;
import DTO.EstudiantePK;
import DTO.Reserva;
import DTO.Prestamo;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USUARIO
 */
public class EstudianteJpaController implements Serializable {

    public EstudianteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante estudiante) throws PreexistingEntityException, Exception {
        if (estudiante.getEstudiantePK() == null) {
            estudiante.setEstudiantePK(new EstudiantePK());
        }
        if (estudiante.getNotificacionList() == null) {
            estudiante.setNotificacionList(new ArrayList<Notificacion>());
        }
        if (estudiante.getEmailList() == null) {
            estudiante.setEmailList(new ArrayList<Email>());
        }
        if (estudiante.getReservaList() == null) {
            estudiante.setReservaList(new ArrayList<Reserva>());
        }
        if (estudiante.getPrestamoList() == null) {
            estudiante.setPrestamoList(new ArrayList<Prestamo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Notificacion> attachedNotificacionList = new ArrayList<Notificacion>();
            for (Notificacion notificacionListNotificacionToAttach : estudiante.getNotificacionList()) {
                notificacionListNotificacionToAttach = em.getReference(notificacionListNotificacionToAttach.getClass(), notificacionListNotificacionToAttach.getId());
                attachedNotificacionList.add(notificacionListNotificacionToAttach);
            }
            estudiante.setNotificacionList(attachedNotificacionList);
            List<Email> attachedEmailList = new ArrayList<Email>();
            for (Email emailListEmailToAttach : estudiante.getEmailList()) {
                emailListEmailToAttach = em.getReference(emailListEmailToAttach.getClass(), emailListEmailToAttach.getId());
                attachedEmailList.add(emailListEmailToAttach);
            }
            estudiante.setEmailList(attachedEmailList);
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : estudiante.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getId());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            estudiante.setReservaList(attachedReservaList);
            List<Prestamo> attachedPrestamoList = new ArrayList<Prestamo>();
            for (Prestamo prestamoListPrestamoToAttach : estudiante.getPrestamoList()) {
                prestamoListPrestamoToAttach = em.getReference(prestamoListPrestamoToAttach.getClass(), prestamoListPrestamoToAttach.getId());
                attachedPrestamoList.add(prestamoListPrestamoToAttach);
            }
            estudiante.setPrestamoList(attachedPrestamoList);
            em.persist(estudiante);
            for (Notificacion notificacionListNotificacion : estudiante.getNotificacionList()) {
                Estudiante oldEstudianteOfNotificacionListNotificacion = notificacionListNotificacion.getEstudiante();
                notificacionListNotificacion.setEstudiante(estudiante);
                notificacionListNotificacion = em.merge(notificacionListNotificacion);
                if (oldEstudianteOfNotificacionListNotificacion != null) {
                    oldEstudianteOfNotificacionListNotificacion.getNotificacionList().remove(notificacionListNotificacion);
                    oldEstudianteOfNotificacionListNotificacion = em.merge(oldEstudianteOfNotificacionListNotificacion);
                }
            }
            for (Email emailListEmail : estudiante.getEmailList()) {
                Estudiante oldEstudianteOfEmailListEmail = emailListEmail.getEstudiante();
                emailListEmail.setEstudiante(estudiante);
                emailListEmail = em.merge(emailListEmail);
                if (oldEstudianteOfEmailListEmail != null) {
                    oldEstudianteOfEmailListEmail.getEmailList().remove(emailListEmail);
                    oldEstudianteOfEmailListEmail = em.merge(oldEstudianteOfEmailListEmail);
                }
            }
            for (Reserva reservaListReserva : estudiante.getReservaList()) {
                Estudiante oldEstudianteOfReservaListReserva = reservaListReserva.getEstudiante();
                reservaListReserva.setEstudiante(estudiante);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldEstudianteOfReservaListReserva != null) {
                    oldEstudianteOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldEstudianteOfReservaListReserva = em.merge(oldEstudianteOfReservaListReserva);
                }
            }
            for (Prestamo prestamoListPrestamo : estudiante.getPrestamoList()) {
                Estudiante oldEstudianteOfPrestamoListPrestamo = prestamoListPrestamo.getEstudiante();
                prestamoListPrestamo.setEstudiante(estudiante);
                prestamoListPrestamo = em.merge(prestamoListPrestamo);
                if (oldEstudianteOfPrestamoListPrestamo != null) {
                    oldEstudianteOfPrestamoListPrestamo.getPrestamoList().remove(prestamoListPrestamo);
                    oldEstudianteOfPrestamoListPrestamo = em.merge(oldEstudianteOfPrestamoListPrestamo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstudiante(estudiante.getEstudiantePK()) != null) {
                throw new PreexistingEntityException("Estudiante " + estudiante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante estudiante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante persistentEstudiante = em.find(Estudiante.class, estudiante.getEstudiantePK());
            List<Notificacion> notificacionListOld = persistentEstudiante.getNotificacionList();
            List<Notificacion> notificacionListNew = estudiante.getNotificacionList();
            List<Email> emailListOld = persistentEstudiante.getEmailList();
            List<Email> emailListNew = estudiante.getEmailList();
            List<Reserva> reservaListOld = persistentEstudiante.getReservaList();
            List<Reserva> reservaListNew = estudiante.getReservaList();
            List<Prestamo> prestamoListOld = persistentEstudiante.getPrestamoList();
            List<Prestamo> prestamoListNew = estudiante.getPrestamoList();
            List<String> illegalOrphanMessages = null;
            for (Notificacion notificacionListOldNotificacion : notificacionListOld) {
                if (!notificacionListNew.contains(notificacionListOldNotificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notificacion " + notificacionListOldNotificacion + " since its estudiante field is not nullable.");
                }
            }
            for (Email emailListOldEmail : emailListOld) {
                if (!emailListNew.contains(emailListOldEmail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Email " + emailListOldEmail + " since its estudiante field is not nullable.");
                }
            }
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaListOldReserva + " since its estudiante field is not nullable.");
                }
            }
            for (Prestamo prestamoListOldPrestamo : prestamoListOld) {
                if (!prestamoListNew.contains(prestamoListOldPrestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prestamo " + prestamoListOldPrestamo + " since its estudiante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Notificacion> attachedNotificacionListNew = new ArrayList<Notificacion>();
            for (Notificacion notificacionListNewNotificacionToAttach : notificacionListNew) {
                notificacionListNewNotificacionToAttach = em.getReference(notificacionListNewNotificacionToAttach.getClass(), notificacionListNewNotificacionToAttach.getId());
                attachedNotificacionListNew.add(notificacionListNewNotificacionToAttach);
            }
            notificacionListNew = attachedNotificacionListNew;
            estudiante.setNotificacionList(notificacionListNew);
            List<Email> attachedEmailListNew = new ArrayList<Email>();
            for (Email emailListNewEmailToAttach : emailListNew) {
                emailListNewEmailToAttach = em.getReference(emailListNewEmailToAttach.getClass(), emailListNewEmailToAttach.getId());
                attachedEmailListNew.add(emailListNewEmailToAttach);
            }
            emailListNew = attachedEmailListNew;
            estudiante.setEmailList(emailListNew);
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getId());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            estudiante.setReservaList(reservaListNew);
            List<Prestamo> attachedPrestamoListNew = new ArrayList<Prestamo>();
            for (Prestamo prestamoListNewPrestamoToAttach : prestamoListNew) {
                prestamoListNewPrestamoToAttach = em.getReference(prestamoListNewPrestamoToAttach.getClass(), prestamoListNewPrestamoToAttach.getId());
                attachedPrestamoListNew.add(prestamoListNewPrestamoToAttach);
            }
            prestamoListNew = attachedPrestamoListNew;
            estudiante.setPrestamoList(prestamoListNew);
            estudiante = em.merge(estudiante);
            for (Notificacion notificacionListNewNotificacion : notificacionListNew) {
                if (!notificacionListOld.contains(notificacionListNewNotificacion)) {
                    Estudiante oldEstudianteOfNotificacionListNewNotificacion = notificacionListNewNotificacion.getEstudiante();
                    notificacionListNewNotificacion.setEstudiante(estudiante);
                    notificacionListNewNotificacion = em.merge(notificacionListNewNotificacion);
                    if (oldEstudianteOfNotificacionListNewNotificacion != null && !oldEstudianteOfNotificacionListNewNotificacion.equals(estudiante)) {
                        oldEstudianteOfNotificacionListNewNotificacion.getNotificacionList().remove(notificacionListNewNotificacion);
                        oldEstudianteOfNotificacionListNewNotificacion = em.merge(oldEstudianteOfNotificacionListNewNotificacion);
                    }
                }
            }
            for (Email emailListNewEmail : emailListNew) {
                if (!emailListOld.contains(emailListNewEmail)) {
                    Estudiante oldEstudianteOfEmailListNewEmail = emailListNewEmail.getEstudiante();
                    emailListNewEmail.setEstudiante(estudiante);
                    emailListNewEmail = em.merge(emailListNewEmail);
                    if (oldEstudianteOfEmailListNewEmail != null && !oldEstudianteOfEmailListNewEmail.equals(estudiante)) {
                        oldEstudianteOfEmailListNewEmail.getEmailList().remove(emailListNewEmail);
                        oldEstudianteOfEmailListNewEmail = em.merge(oldEstudianteOfEmailListNewEmail);
                    }
                }
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    Estudiante oldEstudianteOfReservaListNewReserva = reservaListNewReserva.getEstudiante();
                    reservaListNewReserva.setEstudiante(estudiante);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldEstudianteOfReservaListNewReserva != null && !oldEstudianteOfReservaListNewReserva.equals(estudiante)) {
                        oldEstudianteOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldEstudianteOfReservaListNewReserva = em.merge(oldEstudianteOfReservaListNewReserva);
                    }
                }
            }
            for (Prestamo prestamoListNewPrestamo : prestamoListNew) {
                if (!prestamoListOld.contains(prestamoListNewPrestamo)) {
                    Estudiante oldEstudianteOfPrestamoListNewPrestamo = prestamoListNewPrestamo.getEstudiante();
                    prestamoListNewPrestamo.setEstudiante(estudiante);
                    prestamoListNewPrestamo = em.merge(prestamoListNewPrestamo);
                    if (oldEstudianteOfPrestamoListNewPrestamo != null && !oldEstudianteOfPrestamoListNewPrestamo.equals(estudiante)) {
                        oldEstudianteOfPrestamoListNewPrestamo.getPrestamoList().remove(prestamoListNewPrestamo);
                        oldEstudianteOfPrestamoListNewPrestamo = em.merge(oldEstudianteOfPrestamoListNewPrestamo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EstudiantePK id = estudiante.getEstudiantePK();
                if (findEstudiante(id) == null) {
                    throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EstudiantePK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante estudiante;
            try {
                estudiante = em.getReference(Estudiante.class, id);
                estudiante.getEstudiantePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Notificacion> notificacionListOrphanCheck = estudiante.getNotificacionList();
            for (Notificacion notificacionListOrphanCheckNotificacion : notificacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the Notificacion " + notificacionListOrphanCheckNotificacion + " in its notificacionList field has a non-nullable estudiante field.");
            }
            List<Email> emailListOrphanCheck = estudiante.getEmailList();
            for (Email emailListOrphanCheckEmail : emailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the Email " + emailListOrphanCheckEmail + " in its emailList field has a non-nullable estudiante field.");
            }
            List<Reserva> reservaListOrphanCheck = estudiante.getReservaList();
            for (Reserva reservaListOrphanCheckReserva : reservaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the Reserva " + reservaListOrphanCheckReserva + " in its reservaList field has a non-nullable estudiante field.");
            }
            List<Prestamo> prestamoListOrphanCheck = estudiante.getPrestamoList();
            for (Prestamo prestamoListOrphanCheckPrestamo : prestamoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudiante (" + estudiante + ") cannot be destroyed since the Prestamo " + prestamoListOrphanCheckPrestamo + " in its prestamoList field has a non-nullable estudiante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiante> findEstudianteEntities() {
        return findEstudianteEntities(true, -1, -1);
    }

    public List<Estudiante> findEstudianteEntities(int maxResults, int firstResult) {
        return findEstudianteEntities(false, maxResults, firstResult);
    }

    private List<Estudiante> findEstudianteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante.class));
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

    public Estudiante findEstudiante(EstudiantePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante> rt = cq.from(Estudiante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Estudiante buscar(String correo){
    
        List<Estudiante> estudiantes = this.findEstudianteEntities();
        
        
        for (Estudiante es: estudiantes) {
            
            if(es.getEstudiantePK().getEmail().equalsIgnoreCase(correo)){
            
                return es;
            }
        }
        return null;
        
    }
    
}
