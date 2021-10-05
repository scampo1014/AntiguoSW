/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Maria Valentina Garcia
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable {

    private String nombre;

    private String login;

    private String correo;

    private String contrasenia;
    
    private String foto;

    @PodamExclude
    @javax.persistence.OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true ,
            fetch = javax.persistence.FetchType.LAZY
    )
    private Collection<RecetaEntity> recetas;
    
    @PodamExclude
//    @javax.persistence.OneToMany(
//            mappedBy = "usuario",
//            cascade = CascadeType.PERSIST,
//            fetch = FetchType.EAGER,
//            orphanRemoval = true
//    )
    @OneToMany (mappedBy = "usuario",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private Collection<CalificacionEntity> calificaciones;
    
    @PodamExclude
//    @javax.persistence.OneToMany(
//            mappedBy = "usuario"
//            cascade = CascadeType.PERSIST,
//            fetch = FetchType.EAGER,
//            orphanRemoval = true
//    )
   @OneToMany (mappedBy = "usuario",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private Collection<ComentarioEntity>  comentarios;
    


    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the recetas
     */
    public Collection<RecetaEntity> getRecetas() {
        return recetas;
    }

    /**
     * @param recetas the recetas to set
     */
    public void setRecetas(Collection<RecetaEntity> recetas) {
        this.recetas = recetas;
    }

    /**
     * @return the calificaciones
     */
    public Collection<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(Collection<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return the comentarios
     */
    public Collection<ComentarioEntity> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(Collection<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    

}
