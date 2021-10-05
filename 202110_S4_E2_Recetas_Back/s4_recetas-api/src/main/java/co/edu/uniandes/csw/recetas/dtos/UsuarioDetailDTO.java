/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maria Valentina Garcia
 */
public class UsuarioDetailDTO extends UsuarioDTO implements Serializable{
   
    private List<RecetaDTO> recetas;
     
    private List<CalificacionDTO> calificaciones;
    
    private List<ComentarioDTO> comentarios;
    
    public UsuarioDetailDTO() {
        super();
    }
    
    public UsuarioDetailDTO(UsuarioEntity usuarioEntity) {
        super(usuarioEntity);
        if (usuarioEntity != null) {
            if (usuarioEntity.getRecetas() != null) {
                recetas = new ArrayList<>();
                for (RecetaEntity entityReceta : usuarioEntity.getRecetas()) {
                    recetas.add(new RecetaDTO(entityReceta));
                }
            }
            if (usuarioEntity.getCalificaciones() != null) {
                calificaciones = new ArrayList<>();
                for (CalificacionEntity entityCalificacion : usuarioEntity.getCalificaciones()) {
                    calificaciones.add(new CalificacionDTO(entityCalificacion));
                }
            }
            if (usuarioEntity.getComentarios()!= null) {
                comentarios = new ArrayList<>();
                for (ComentarioEntity entityComentario : usuarioEntity.getComentarios()) {
                    comentarios.add(new ComentarioDTO(entityComentario));
                }
            }
        }
    }

    /**
     * @return the recetas
     */
    public List<RecetaDTO> getRecetas() {
        return recetas;
    }

    /**
     * @param recetas the recetas to set
     */
    public void setRecetas(List<RecetaDTO> recetas) {
        this.recetas = recetas;
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
    
    @Override
    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = super.toEntity();
        if (getRecetas() != null) {
            List<RecetaEntity> recetasEntity = new ArrayList<>();
            for (RecetaDTO dtoReceta : getRecetas()) {
                recetasEntity.add(dtoReceta.toEntity());
            }
            usuarioEntity.setRecetas(recetasEntity);
        }
        if (getCalificaciones() != null) {
            List<CalificacionEntity> calificacionesEntity = new ArrayList<>();
            for (CalificacionDTO dtoCalificacion : getCalificaciones()) {
                calificacionesEntity.add(dtoCalificacion.toEntity());
            }
            usuarioEntity.setCalificaciones(calificacionesEntity);
        }
        
        if (getComentarios() != null) {
            List<ComentarioEntity> comentariosEntity = new ArrayList<>();
            for (ComentarioDTO dtoComentario : getComentarios()) {
                comentariosEntity.add(dtoComentario.toEntity());
            }
            usuarioEntity.setComentarios(comentariosEntity);
        }
        
        return usuarioEntity;
    }

    
}
