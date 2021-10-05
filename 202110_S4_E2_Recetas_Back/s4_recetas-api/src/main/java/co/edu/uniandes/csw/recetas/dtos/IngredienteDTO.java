/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.IngredienteEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Maria Valentina Garcia
 */
public class IngredienteDTO implements Serializable {

    private Long id;

    private String nombre;

    private Integer precio;

    private String cantidad;

    private RecetaDTO receta;

    public IngredienteDTO() {

    }

    public IngredienteDTO(IngredienteEntity ingredienteEntity) {
        if (ingredienteEntity != null) {
            this.id = ingredienteEntity.getId();
            this.nombre = ingredienteEntity.getNombre();
            this.precio = ingredienteEntity.getPrecio();
            this.cantidad = ingredienteEntity.getCantidad();
            if (ingredienteEntity.getReceta() != null) {
                this.receta = new RecetaDTO(ingredienteEntity.getReceta());
            } else {
                this.receta = null;
            }
        }
    }

    public IngredienteEntity toEntity() {
        IngredienteEntity ingredienteEntity = new IngredienteEntity();
        ingredienteEntity.setId(this.getId());
        ingredienteEntity.setNombre(this.getNombre());
        ingredienteEntity.setPrecio(this.getPrecio());
        ingredienteEntity.setCantidad(this.getCantidad());
        if (this.getReceta() != null) {
            ingredienteEntity.setReceta(this.getReceta().toEntity());
        }
        return ingredienteEntity;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * @return the precio
     */
    public Integer getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    /**
     * @return the cantidad
     */
    public String getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the receta
     */
    public RecetaDTO getReceta() {
        return receta;
    }

    /**
     * @param receta the receta to set
     */
    public void setReceta(RecetaDTO receta) {
        this.receta = receta;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
