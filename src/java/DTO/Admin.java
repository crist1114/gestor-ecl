/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "Admin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a")
    , @NamedQuery(name = "Admin.findByNombre", query = "SELECT a FROM Admin a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Admin.findByApellido", query = "SELECT a FROM Admin a WHERE a.apellido = :apellido")
    , @NamedQuery(name = "Admin.findByUrlFoto", query = "SELECT a FROM Admin a WHERE a.urlFoto = :urlFoto")
    , @NamedQuery(name = "Admin.findByCorreo", query = "SELECT a FROM Admin a WHERE a.correo = :correo")
    , @NamedQuery(name = "Admin.findByContrase\u00f1a", query = "SELECT a FROM Admin a WHERE a.contrase\u00f1a = :contrase\u00f1a")
    , @NamedQuery(name = "Admin.findByCodigo", query = "SELECT a FROM Admin a WHERE a.codigo = :codigo")})
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "url_foto")
    private String urlFoto;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "contrase\u00f1a")
    private String contraseña;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;

    public Admin() {
    }

    public Admin(Integer codigo) {
        this.codigo = codigo;
    }

    public Admin(Integer codigo, String nombre, String apellido, String urlFoto, String correo, String contraseña) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.urlFoto = urlFoto;
        this.correo = correo;
        this.contraseña = contraseña;
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

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Admin[ codigo=" + codigo + " ]";
    }
    
}
