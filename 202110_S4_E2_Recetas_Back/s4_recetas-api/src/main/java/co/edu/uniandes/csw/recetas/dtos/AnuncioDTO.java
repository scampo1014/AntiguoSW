/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.AnuncioEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class AnuncioDTO implements Serializable {

    private Long id;
    private String nombre;
    private String contenido;
    private Integer duracion;
    private Integer costo;
    private String medioDePago;
    private Integer tiempoDisponible;
    private ProveedorDTO proveedor;

    public AnuncioDTO() {

    }

    public AnuncioDTO(AnuncioEntity anuncioEntity) {
        if (anuncioEntity != null) {

            this.id = anuncioEntity.getId();
            this.nombre = anuncioEntity.getNombre();
            this.costo = anuncioEntity.getCosto();
            this.contenido = anuncioEntity.getContenido();
            this.duracion = anuncioEntity.getDuracion();
            this.medioDePago = anuncioEntity.getMedioDePago();
            this.tiempoDisponible = anuncioEntity.getTiempoDisponible();
            if (anuncioEntity.getProveedor() != null) {
                this.proveedor = new ProveedorDTO(anuncioEntity.getProveedor());
            } else {
                this.proveedor = null;
            }
        }
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
     * @return the contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
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

    /**
     * @return the costo
     */
    public Integer getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    /**
     * @return the medioDePago
     */
    public String getMedioDePago() {
        return medioDePago;
    }

    /**
     * @param medioDePago the medioDePago to set
     */
    public void setMedioDePago(String medioDePago) {
        this.medioDePago = medioDePago;
    }

    /**
     * @return the tiempoDisponible
     */
    public Integer getTiempoDisponible() {
        return tiempoDisponible;
    }

    /**
     * @param tiempoDisponible the tiempoDisponible to set
     */
    public void setTiempoDisponible(Integer tiempoDisponible) {
        this.tiempoDisponible = tiempoDisponible;
    }

    /**
     * @return the proveedor
     */
    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    public AnuncioEntity toEntity() {
        AnuncioEntity anuncioEntity = new AnuncioEntity();
        anuncioEntity.setContenido(this.getContenido());
        anuncioEntity.setCosto(this.getCosto());
        anuncioEntity.setMedioDePago(this.getMedioDePago());
        anuncioEntity.setNombre(this.getNombre());
        anuncioEntity.setTiempoDisponible(this.getTiempoDisponible());
        if (this.getProveedor() != null) {
            anuncioEntity.setProveedor(this.getProveedor().toEntity());
        }
        return anuncioEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);

    }
}
