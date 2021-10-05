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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Campo
 */
@Entity
public class ComentarioEntity extends BaseEntity implements Serializable {
    
    private Boolean aprobado;
    private String comentario;
    private Boolean positivo;
    
    @PodamExclude
    @ManyToOne
    private RecetaEntity receta;    
    
    @PodamExclude
    @OneToMany(mappedBy = "comentario",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private Collection<CalificacionEntity> calificaciones;

    @PodamExclude
    @ManyToOne
    private UsuarioEntity usuario;
    
    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor;
    
    /**
     * @return the aprobado
     */
    public Boolean getAprobado() {
        return aprobado;
    }

    /**
     * @param aprobado the aprobado to set
     */
    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the positivo
     */
    public Boolean getPositivo() {
        return positivo;
    }

    /**
     * @param positivo the positivo to set
     */
    public void setPositivo(Boolean positivo) {
        this.positivo = positivo;
    }

    /**
     * @return the receta
     */
    public RecetaEntity getReceta() {
        return receta;
    }

    /**
     * @param receta the receta to set
     */
    public void setReceta(RecetaEntity receta) {
        this.receta = receta;
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
     * @return the usuario
     */
    public UsuarioEntity getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
     public ProveedorEntity getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the usuario to set
     */
    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }
    
}
