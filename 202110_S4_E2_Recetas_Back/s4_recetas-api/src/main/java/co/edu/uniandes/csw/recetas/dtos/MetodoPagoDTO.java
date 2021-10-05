/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.MetodoPagoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author juliantorres
 */
public class MetodoPagoDTO implements Serializable {

    private Long id;
    private String metodoPago;
    private ProveedorDTO proveedor;

    public MetodoPagoDTO() {

    }

    public MetodoPagoDTO(MetodoPagoEntity metodoPagoEntity) {
        if (metodoPagoEntity != null) {
            this.id = metodoPagoEntity.getId();
            this.metodoPago = metodoPagoEntity.getMetodoPago();
            if (metodoPagoEntity.getProveedor() != null) {
                this.proveedor = new ProveedorDTO(metodoPagoEntity.getProveedor());
            } else {
                this.proveedor = null;
            }
        }
    }

    public MetodoPagoEntity toEntity() {
        MetodoPagoEntity metodoDePagoEntity = new MetodoPagoEntity();
        metodoDePagoEntity.setId(this.getId());
        metodoDePagoEntity.setMetodoPago(this.getMetodoPago());
        if (this.getProveedor() != null) {
            metodoDePagoEntity.setProveedor(this.getProveedor().toEntity());
        }
        return metodoDePagoEntity;
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
     * @return the metodoPago
     */
    public String getMetodoPago() {
        return metodoPago;
    }

    /**
     * @param metodoPago the metodoPago to set
     */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
