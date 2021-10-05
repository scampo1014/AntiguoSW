/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.UtensilioEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Maria Valentina Garcia
 */
public class UtensilioDTO implements Serializable {
    
    private Long id;
    
    private String nombre;
    
    private Integer precio;
    
    private String descripcion;
    
    private RecetaDTO receta;
    
    public UtensilioDTO(){
        
    }
    
    public UtensilioDTO(UtensilioEntity utensilioEntity)
    {
        if (utensilioEntity != null) {
            this.id = utensilioEntity.getId();
            this.nombre = utensilioEntity.getNombre();
            this.precio = utensilioEntity.getPrecio();
            this.descripcion = utensilioEntity.getDescripcion();
            if (utensilioEntity.getReceta() != null) {
                this.receta = new RecetaDTO(utensilioEntity.getReceta());
            } else {
                this.receta = null;
            }
        }
    }
    
    public UtensilioEntity toEntity() {
        UtensilioEntity utensilioEntity = new UtensilioEntity();
        utensilioEntity.setId(this.getId());
        utensilioEntity.setNombre(this.getNombre());
        utensilioEntity.setPrecio(this.getPrecio());
        utensilioEntity.setDescripcion(this.getDescripcion());
        if (this.getReceta() != null) {
            utensilioEntity.setReceta(this.getReceta().toEntity());
        }
        return utensilioEntity;
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * @return the receta
     */
    public RecetaDTO getReceta() {
        return receta;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
