/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.AnuncioEntity;
import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import co.edu.uniandes.csw.recetas.entities.MetodoPagoEntity;
import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class ProveedorDetailDTO extends ProveedorDTO implements Serializable {

    private List<CalificacionDTO> calificaciones;
    private List<MetodoPagoDTO> metodosDePago;
    private List<ComentarioDTO> comentarios;
    private List<AnuncioDTO> anuncios;

    public ProveedorDetailDTO() {
        super();
    }

    public ProveedorDetailDTO(ProveedorEntity proveedorEntity) {
        super(proveedorEntity);
        if (proveedorEntity != null) {
            if (proveedorEntity.getCalificaciones() != null) {
                calificaciones = new ArrayList<>();
                for (CalificacionEntity entityCalificacion : proveedorEntity.getCalificaciones()) {
                    calificaciones.add(new CalificacionDTO(entityCalificacion));
                }
            }
            if (proveedorEntity.getMetodosPago() != null) {
                metodosDePago = new ArrayList<>();
                for (MetodoPagoEntity metodoPago : proveedorEntity.getMetodosPago()) {
                    metodosDePago.add(new MetodoPagoDTO(metodoPago));
                }
            }
            if (proveedorEntity.getComentarios() != null) {
                comentarios = new ArrayList<>();
                for (ComentarioEntity cometario : proveedorEntity.getComentarios()) {
                    comentarios.add(new ComentarioDTO(cometario));
                }
            }
            if (proveedorEntity.getAnuncios() != null) {
                anuncios = new ArrayList<>();
                for (AnuncioEntity anuncio : proveedorEntity.getAnuncios()) {
                    anuncios.add(new AnuncioDTO(anuncio));
                }
            }
        }
    }

    @Override
    public ProveedorEntity toEntity() {
        ProveedorEntity proveedor = super.toEntity();
        if (calificaciones != null) {
            List<CalificacionEntity> calificacionesEntity = new ArrayList<>();
            for (CalificacionDTO calificacion : getCalificaciones()) {
                calificacionesEntity.add(calificacion.toEntity());
            }
            proveedor.setCalificaciones(calificacionesEntity);
        }
        if (metodosDePago != null) {
            List<MetodoPagoEntity> metodosPagoEntity = new ArrayList<>();
            for (MetodoPagoDTO metodoPago : getMetodosDePago()) {
                metodosPagoEntity.add(metodoPago.toEntity());
            }
            proveedor.setMetodosPago(metodosPagoEntity);
        }
        if (comentarios != null) {
            List<ComentarioEntity> comentariosEntity = new ArrayList<>();
            for (ComentarioDTO comentario : getComentarios()) {
                comentariosEntity.add(comentario.toEntity());
            }
            proveedor.setComentarios(comentariosEntity);
        }
        if (anuncios != null) {
            List<AnuncioEntity> anunciosEntity = new ArrayList<>();
            for (AnuncioDTO anuncio : getAnuncios()) {
                anunciosEntity.add(anuncio.toEntity());
            }
            proveedor.setAnuncios(anunciosEntity);
        }
        return proveedor;
    }

    /**
     * @return the calificaciones
     */
    public List<CalificacionDTO> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(List<CalificacionDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return the metodosDePago
     */
    public List<MetodoPagoDTO> getMetodosDePago() {
        return metodosDePago;
    }

    /**
     * @param metodosDePago the metodosDePago to set
     */
    public void setMetodosDePago(List<MetodoPagoDTO> metodosDePago) {
        this.metodosDePago = metodosDePago;
    }

    /**
     * @return the comentarios
     */
    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the anuncios
     */
    public List<AnuncioDTO> getAnuncios() {
        return anuncios;
    }

    /**
     * @param anuncios the anuncios to set
     */
    public void setAnuncios(List<AnuncioDTO> anuncios) {
        this.anuncios = anuncios;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
