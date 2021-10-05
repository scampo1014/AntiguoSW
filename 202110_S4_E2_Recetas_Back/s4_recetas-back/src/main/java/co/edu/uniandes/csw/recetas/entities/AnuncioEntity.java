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
 * @author juliantorres
 */
@Entity
public class AnuncioEntity extends BaseEntity implements Serializable{
    private String nombre;
    private Integer costo;
    private Integer duracion;
    private String contenido;
    private String medioDePago;
    private Integer tiempoDisponible;
    
    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor;
    
    public String getNombre() {
        return nombre;
    }
    
    public Integer getCosto() {
        return costo;
    }
    public Integer getDuracion() {
        return duracion;
    }
    public String getContenido() {
        return contenido;
    }
    public String getMedioDePago() {
        return medioDePago;
    }
    public Integer getTiempoDisponible() {
        return tiempoDisponible;
    }
    
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCosto(Integer costo) {
        this.costo = costo;
    }
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public void setMedioDePago(String medioDePago) {
        this.medioDePago =medioDePago;
    }
    public void setTiempoDisponible(Integer tiempoDisponibleible) {
        this.tiempoDisponible = tiempoDisponibleible;
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
}
