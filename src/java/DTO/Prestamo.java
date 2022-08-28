/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "Prestamo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prestamo.findAll", query = "SELECT p FROM Prestamo p")
    , @NamedQuery(name = "Prestamo.findById", query = "SELECT p FROM Prestamo p WHERE p.id = :id")
    , @NamedQuery(name = "Prestamo.findByFechaInicio", query = "SELECT p FROM Prestamo p WHERE p.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Prestamo.findByFechaFin", query = "SELECT p FROM Prestamo p WHERE p.fechaFin = :fechaFin")
    , @NamedQuery(name = "Prestamo.findByFinalizado", query = "SELECT p FROM Prestamo p WHERE p.finalizado = :finalizado")})
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @Column(name = "finalizado")
    private short finalizado;
    @JoinColumns({
        @JoinColumn(name = "cod_estudiante", referencedColumnName = "codigo")
        , @JoinColumn(name = "email_estudiante", referencedColumnName = "email")})
    @ManyToOne(optional = false)
    private Estudiante estudiante;
    @JoinColumn(name = "id_libro", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Libro idLibro;
    @JoinColumn(name = "id_multa", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Multa idMulta;

    public Prestamo() {
    }

    public Prestamo(Integer id) {
        this.id = id;
    }

    public Prestamo(Integer id, Date fechaInicio, Date fechaFin, short finalizado) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.finalizado = finalizado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public short getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(short finalizado) {
        this.finalizado = finalizado;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Libro getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Libro idLibro) {
        this.idLibro = idLibro;
    }

    public Multa getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(Multa idMulta) {
        this.idMulta = idMulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prestamo)) {
            return false;
        }
        Prestamo other = (Prestamo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Prestamo[ id=" + id + " ]";
    }
    
}
