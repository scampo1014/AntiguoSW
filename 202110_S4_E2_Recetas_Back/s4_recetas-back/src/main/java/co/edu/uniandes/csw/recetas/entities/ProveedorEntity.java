/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author juliantorres
 */
@Entity
public class ProveedorEntity extends BaseEntity implements Serializable {

    private String nombre;
    private String login;
    private String correo;
    private String contrasenia;

    @PodamExclude
    @OneToMany(mappedBy = "proveedor",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private List<CalificacionEntity> calificaciones = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "proveedor",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private List<ComentarioEntity> comentarios = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "proveedor",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private List<MetodoPagoEntity> metodosPago = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "proveedor",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private List<AnuncioEntity> anuncios = new ArrayList<>();

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
     * @return the calificaciones
     */
    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return the comentarios
     */
    public List<ComentarioEntity> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the metodosPago
     */
    public List<MetodoPagoEntity> getMetodosPago() {
        return metodosPago;
    }

    /**
     * @param metodosPago the metodosPago to set
     */
    public void setMetodosPago(List<MetodoPagoEntity> metodosPago) {
        this.metodosPago = metodosPago;
    }

    /**
     * @return the anuncios
     */
    public List<AnuncioEntity> getAnuncios() {
        return anuncios;
    }

    /**
     * @param anuncios the anuncios to set
     */
    public void setAnuncios(List<AnuncioEntity> anuncios) {
        this.anuncios = anuncios;
    }

}
