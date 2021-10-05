/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Santiago Campo
 */
@Entity
public class FotoEntity extends BaseEntity implements Serializable {
    
    private String formato;
    private String direccion;
    private Double tamanio;
    
    @PodamExclude
    @ManyToOne
    private RecetaEntity receta;    //o será solo Receta?

    /**
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the tamanio
     */
    public Double getTamanio() {
        return tamanio;
    }

    /**
     * @param tamanio the tamanio to set
     */
    public void setTamanio(Double tamanio) {
        this.tamanio = tamanio;
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
}
