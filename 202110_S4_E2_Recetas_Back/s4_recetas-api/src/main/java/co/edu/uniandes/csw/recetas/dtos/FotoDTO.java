/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.FotoEntity;
import java.io.Serializable;

/**
 *
 * @author Santiago Campo
 */
public class FotoDTO implements Serializable {

    private Long id;    
    private String formato;
    private String direccion;
    private Double tamanio;
    
    public FotoDTO() 
    {
        
    }
    
    public FotoDTO(FotoEntity fotoEntity)
    {
        if (fotoEntity != null) 
        {
            this.id = fotoEntity.getId();
            this.formato = fotoEntity.getFormato();
            this.direccion = fotoEntity.getDireccion();
            this.tamanio = fotoEntity.getTamanio();
        }
    }
    
    public FotoEntity toEntity()
    {
        FotoEntity fotoEntity = new FotoEntity();
        fotoEntity.setId(this.id);
        fotoEntity.setFormato(this.formato);
        fotoEntity.setDireccion(this.direccion);
        fotoEntity.setTamanio(this.tamanio);
        return fotoEntity;
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

    
}
