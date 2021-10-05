/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.CalificacionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Ingrith Barbosa
 */
@Stateless
public class CalificacionLogic 
{
    private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());
    
    @Inject
    private CalificacionPersistence persistence;
    
    public CalificacionEntity create(CalificacionEntity calificacion) throws BusinessLogicException
    {
        //Validar reglas de negocio
        if(calificacion.getPuntos()<1.0 || calificacion.getPuntos()>5.0)
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        return persistence.create(calificacion);
    }
    
    public List<CalificacionEntity> getCalificaciones() {
        List<CalificacionEntity> calificacion = persistence.findAll();
        return calificacion;
    }
    public CalificacionEntity getCalificacion(Long calificacionId) {
        CalificacionEntity calificacion = persistence.find(calificacionId);
        if (calificacion == null) 
        {
            LOGGER.log(Level.SEVERE, "La calificacion con el id = {0} no existe", calificacionId);
        }
        return calificacion;
    }
    public CalificacionEntity updateCalificacion(CalificacionEntity calificacion) throws BusinessLogicException {
        if(calificacion.getPuntos()<1 || calificacion.getPuntos()>5)
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        CalificacionEntity newEntity = persistence.update(calificacion);
        return newEntity;
    }
    public void deleteCalificacion(Long calificacionId) throws BusinessLogicException {
        persistence.delete(calificacionId);
    }
}

    