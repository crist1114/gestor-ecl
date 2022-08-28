/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "Libro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Libro.findAll", query = "SELECT l FROM Libro l")
    , @NamedQuery(name = "Libro.findById", query = "SELECT l FROM Libro l WHERE l.id = :id")
    , @NamedQuery(name = "Libro.findByTitulo", query = "SELECT l FROM Libro l WHERE l.titulo = :titulo")
    , @NamedQuery(name = "Libro.findByAutor", query = "SELECT l FROM Libro l WHERE l.autor = :autor")
    , @NamedQuery(name = "Libro.findByFechaPublicaciones", query = "SELECT l FROM Libro l WHERE l.fechaPublicaciones = :fechaPublicaciones")
    , @NamedQuery(name = "Libro.findByEditoria", query = "SELECT l FROM Libro l WHERE l.editoria = :editoria")
    , @NamedQuery(name = "Libro.findByNumPag", query = "SELECT l FROM Libro l WHERE l.numPag = :numPag")
    , @NamedQuery(name = "Libro.findByUrlImagen", query = "SELECT l FROM Libro l WHERE l.urlImagen = :urlImagen")
    , @NamedQuery(name = "Libro.findByDisponibilidad", query = "SELECT l FROM Libro l WHERE l.disponibilidad = :disponibilidad")})
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "autor")
    private String autor;
    @Basic(optional = false)
    @Column(name = "fecha_publicaciones")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicaciones;
    @Basic(optional = false)
    @Column(name = "editoria")
    private String editoria;
    @Basic(optional = false)
    @Column(name = "num_pag")
    private int numPag;
    @Basic(optional = false)
    @Column(name = "url_imagen")
    private String urlImagen;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "disponibilidad")
    private int disponibilidad;
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categoria categoriaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLibro")
    private List<Reserva> reservaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLibro")
    private List<Prestamo> prestamoList;

    public Libro() {
    }

    public Libro(Integer id) {
        this.id = id;
    }

    public Libro(Integer id, String titulo, String autor, Date fechaPublicaciones, String editoria, int numPag, String urlImagen, String descripcion, int disponibilidad) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPublicaciones = fechaPublicaciones;
        this.editoria = editoria;
        this.numPag = numPag;
        this.urlImagen = urlImagen;
        this.descripcion = descripcion;
        this.disponibilidad = disponibilidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getFechaPublicaciones() {
        return fechaPublicaciones;
    }

    public void setFechaPublicaciones(Date fechaPublicaciones) {
        this.fechaPublicaciones = fechaPublicaciones;
    }

    public String getEditoria() {
        return editoria;
    }

    public void setEditoria(String editoria) {
        this.editoria = editoria;
    }

    public int getNumPag() {
        return numPag;
    }

    public void setNumPag(int numPag) {
        this.numPag = numPag;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Categoria getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Categoria categoriaId) {
        this.categoriaId = categoriaId;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Libro)) {
            return false;
        }
        Libro other = (Libro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Libro[ id=" + id + " ]";
    }
    
}
