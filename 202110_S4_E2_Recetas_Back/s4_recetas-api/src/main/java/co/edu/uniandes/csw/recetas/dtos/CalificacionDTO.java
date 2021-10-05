/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import java.io.Serializable;

/**
 *
 * @author Ingrith Barbosa
 */
public class CalificacionDTO implements Serializable{

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

    /**
     * @return the usuario
     */
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
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

    /**
     * @return the comentario
     */
    public ComentarioDTO getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(ComentarioDTO comentario) {
        this.comentario = comentario;
    }
    
    private Long id;
    private String calificador;
    private Double puntos;
    private RecetaDTO receta;
    private UsuarioDTO usuario;
    private ProveedorDTO proveedor;
    private ComentarioDTO comentario;
    /**
     * Constructor por defecto
     */
    public CalificacionDTO()
    {
        
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param calificacionEntity: Es la entidad que se va a convertir a DTO
     */
    public CalificacionDTO(CalificacionEntity calificacionEntity) {
        if (calificacionEntity != null) {
            this.id= calificacionEntity.getId();
            this.calificador = calificacionEntity.getCalificador();
            this.puntos = calificacionEntity.getPuntos();
            if(calificacionEntity.getReceta()!=null)
                this.receta= new RecetaDTO(calificacionEntity.getReceta());
            if(calificacionEntity.getUsuario()!=null)
                this.usuario=new UsuarioDTO(calificacionEntity.getUsuario());
            if(calificacionEntity.getProveedor()!=null)
                this.proveedor=new ProveedorDTO(calificacionEntity.getProveedor());
            if(calificacionEntity.getComentario()!=null)
                this.comentario= new ComentarioDTO(calificacionEntity.getComentario());
        }
    }
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
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public CalificacionEntity toEntity() {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setId(this.getId());
        calificacionEntity.setCalificador(this.calificador);
        calificacionEntity.setPuntos(this.puntos);
        if(this.getUsuario()!=null)
            calificacionEntity.setUsuario(this.getUsuario().toEntity());
        if(this.getProveedor()!=null)
            calificacionEntity.setProveedor(this.getProveedor().toEntity());
        if(this.getComentario()!=null)
            calificacionEntity.setComentario(this.getComentario().toEntity());
        if(this.getReceta()!=null)
            calificacionEntity.setReceta(this.getReceta().toEntity());
        return calificacionEntity;
    }
}
