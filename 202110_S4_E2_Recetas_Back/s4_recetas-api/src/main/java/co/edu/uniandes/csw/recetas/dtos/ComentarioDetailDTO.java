/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santiago Campo
 */
public class ComentarioDetailDTO extends ComentarioDTO implements Serializable {
    
    private List<CalificacionDTO> calificaciones;
    
    public ComentarioDetailDTO()
    {
        super();
    }
    
    public ComentarioDetailDTO(ComentarioEntity comentarioEntity)
    {
        super(comentarioEntity);
        if(comentarioEntity != null )
        {
            if(comentarioEntity.getCalificaciones() != null ) {
                calificaciones = new ArrayList<>();
                for(CalificacionEntity entityCalificacion : comentarioEntity.getCalificaciones())
                {
                    calificaciones.add(new CalificacionDTO(entityCalificacion));
                }
            }
           
        }
    }
    
    @Override
    public ComentarioEntity toEntity() {
        ComentarioEntity comentarioEntity = super.toEntity();
        if (getCalificaciones() != null) {
            List<CalificacionEntity> calificacionesEntity = new ArrayList<>();
            for (CalificacionDTO dtoCalificacion : getCalificaciones()) {
                calificacionesEntity.add(dtoCalificacion.toEntity());
            }
            comentarioEntity.setCalificaciones(calificacionesEntity);
        }
        return comentarioEntity;
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
    
}
