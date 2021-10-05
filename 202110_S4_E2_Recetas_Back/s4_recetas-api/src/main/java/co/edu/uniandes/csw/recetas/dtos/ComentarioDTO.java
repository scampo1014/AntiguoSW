/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import java.io.Serializable;

/**
 *
 * @author Santiago Campo
 */
public class ComentarioDTO implements Serializable {
    
    private Long id;
    private Boolean aprobado;
    private String comentario;
    private Boolean positivo;
    private UsuarioDTO usuario;
    private RecetaDTO receta;
    private ProveedorDTO proveedor;
    
    public ComentarioDTO()
    {
        
    }
    
    public ComentarioDTO(ComentarioEntity comentarioEntity)
    {
        if(comentarioEntity != null)
        {
            this.id = comentarioEntity.getId();
            this.aprobado = comentarioEntity.getAprobado();
            this.comentario = comentarioEntity.getComentario();
            this.positivo = comentarioEntity.getPositivo();
            if(comentarioEntity.getUsuario()!= null)
                this.usuario = new UsuarioDTO(comentarioEntity.getUsuario());
        }
    }
    
    public ComentarioEntity toEntity()
    {
        ComentarioEntity comentarioEntity = new ComentarioEntity();
        comentarioEntity.setId(this.id);
        comentarioEntity.setAprobado(this.getAprobado());
        comentarioEntity.setComentario(this.getComentario());
        comentarioEntity.setPositivo(this.getPositivo());
        if(this.getUsuario() != null)
            comentarioEntity.setUsuario(this.getUsuario().toEntity());
        if(this.getReceta() != null)
            comentarioEntity.setReceta(this.getReceta().toEntity());
        if(this.getProveedor() != null)
            comentarioEntity.setProveedor(this.getProveedor().toEntity());
        return comentarioEntity;
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
     * @return the aprobado
     */
    public Boolean getAprobado() {
        return aprobado;
    }

    /**
     * @param aprobado the aprobado to set
     */
    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the positivo
     */
    public Boolean getPositivo() {
        return positivo;
    }

    /**
     * @param positivo the positivo to set
     */
    public void setPositivo(Boolean positivo) {
        this.positivo = positivo;
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
    
}
