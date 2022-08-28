/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "Estudiante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e")
    , @NamedQuery(name = "Estudiante.findByCodigo", query = "SELECT e FROM Estudiante e WHERE e.estudiantePK.codigo = :codigo")
    , @NamedQuery(name = "Estudiante.findByEmail", query = "SELECT e FROM Estudiante e WHERE e.estudiantePK.email = :email")
    , @NamedQuery(name = "Estudiante.findByNombre", query = "SELECT e FROM Estudiante e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Estudiante.findByApellido", query = "SELECT e FROM Estudiante e WHERE e.apellido = :apellido")
    , @NamedQuery(name = "Estudiante.findByTelefono", query = "SELECT e FROM Estudiante e WHERE e.telefono = :telefono")
    , @NamedQuery(name = "Estudiante.findByContrase\u00f1a", query = "SELECT e FROM Estudiante e WHERE e.contrase\u00f1a = :contrase\u00f1a")
    , @NamedQuery(name = "Estudiante.findByUrlFoto", query = "SELECT e FROM Estudiante e WHERE e.urlFoto = :urlFoto")})
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EstudiantePK estudiantePK;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "telefono")
    private int telefono;
    @Basic(optional = false)
    @Column(name = "contrase\u00f1a")
    private String contraseña;
    @Basic(optional = false)
    @Column(name = "url_foto")
    private String urlFoto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiante")
    private List<Notificacion> notificacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiante")
    private List<Email> emailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiante")
    private List<Reserva> reservaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiante")
    private List<Prestamo> prestamoList;

    public Estudiante() {
    }

    public Estudiante(EstudiantePK estudiantePK) {
        this.estudiantePK = estudiantePK;
    }

    public Estudiante(EstudiantePK estudiantePK, String nombre, String apellido, int telefono, String contraseña, String urlFoto) {
        this.estudiantePK = estudiantePK;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.urlFoto = urlFoto;
    }

    public Estudiante(int codigo, String email) {
        this.estudiantePK = new EstudiantePK(codigo, email);
    }

    public EstudiantePK getEstudiantePK() {
        return estudiantePK;
    }

    public void setEstudiantePK(EstudiantePK estudiantePK) {
        this.estudiantePK = estudiantePK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @XmlTransient
    public List<Notificacion> getNotificacionList() {
        return notificacionList;
    }

    public void setNotificacionList(List<Notificacion> notificacionList) {
        this.notificacionList = notificacionList;
    }

    @XmlTransient
    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @XmlTransient
    public List<Prestamo> getPrestamoList() {
        return prestamoList;
    }

    public void setPrestamoList(List<Prestamo> prestamoList) {
        this.prestamoList = prestamoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estudiantePK != null ? estudiantePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.estudiantePK == null && other.estudiantePK != null) || (this.estudiantePK != null && !this.estudiantePK.equals(other.estudiantePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Estudiante[ estudiantePK=" + estudiantePK + " ]";
    }
    
}
