/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.VideoEntity;
import java.io.Serializable;

/**
 *
 * @author Santiago Campo
 */
public class VideoDTO implements Serializable{
    
    private Long id;
    private String formato;
    private String direccion;
    private Double tamanio;
    private Integer duracion;
    
    public VideoDTO()
    {
        
    }
    
    public VideoDTO(VideoEntity videoEntity)
    {
        if(videoEntity != null)
        {
            this.id = videoEntity.getId();
            this.formato = videoEntity.getFormato();
            this.direccion = videoEntity.getDireccion();
            this.tamanio = videoEntity.getTamanio();
            this.duracion = videoEntity.getDuracion();
        }
    }
    
    public VideoEntity toEntity()
    {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(this.getId());
        videoEntity.setFormato(this.getFormato());
        videoEntity.setDireccion(this.getDireccion());
        videoEntity.setTamanio(this.getTamanio());
        videoEntity.setDuracion(this.getDuracion());
        return videoEntity;
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

    /**
     * @return the duracion
     */
    public Integer getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
    
}
