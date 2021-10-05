/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Ingrith Barbosa
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable{
    
    private String calificador;
    private Double puntos;
    
    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor;
    
    @PodamExclude
    @ManyToOne
    private RecetaEntity receta; 
    
    @PodamExclude
    @ManyToOne
    private UsuarioEntity usuario;
    
    @PodamExclude
    @ManyToOne
    private ComentarioEntity comentario;

    /**
     * @return the calificador
     */
    public String getCalificador() {
        return calificador;
    }

    /**
     * @param calificador the calificador to set
     */
    public void setCalificador(String calificador) {
        this.calificador = calificador;
    }

    /**
     * @return the puntos
     */
    public Double getPuntos() {
        return puntos;
    }

    /**
     * @param puntos the puntos to set
     */
    public void setPuntos(Double puntos) {
        this.puntos = puntos;
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

    /**
     * @return the comentario
     */
    public ComentarioEntity getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(ComentarioEntity comentario) {
        this.comentario = comentario;
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
     * @return the proveedor
     */
    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }
    
    @Override
    public boolean equals(Object obj) {
    if (! super.equals(obj)) {
      return false;
    }
    CalificacionEntity fobj = (CalificacionEntity) obj;
    return calificador.equals(fobj.getCalificador()) && puntos.equals(fobj.getPuntos());
  }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.calificador);
        hash = 11 * hash + Objects.hashCode(this.puntos);
        return hash;
    }
}
