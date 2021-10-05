/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.recetas.persistence.RecetaPersistence;
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
public class RecetaCalificacionesLogic {
    
    private static final Logger LOGGER = Logger.getLogger(RecetaCalificacionesLogic.class.getName());
    
    @Inject
    private CalificacionPersistence persistence;
    
    @Inject
    private RecetaPersistence usuarioPersistence;
    
    public void validarRN(CalificacionEntity calificacionEntity) throws  BusinessLogicException{
        //Regla de negocio: La calificación debe estar entre 1.0 y 5.0
        if(calificacionEntity.getPuntos()<1.0 || calificacionEntity.getPuntos()>5.0){
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        }
    }
    
     public CalificacionEntity createCalificacion(Long recetasId, CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear calificacione");
        RecetaEntity usuario = usuarioPersistence.find(recetasId);
        //validar reglas de negocio
        validarRN(calificacionEntity);
        calificacionEntity.setReceta(usuario);
        LOGGER.log(Level.INFO, "Termina proceso de creación del calificacione");
        CalificacionEntity newCalificacionEntity= persistence.create(calificacionEntity);
        return newCalificacionEntity;
    }
        /**
     * Actualiza la información de una instancia de Calificacion.
     *
     * @param calificacioneEntity Instancia de CalificacionEntity con los nuevos datos.
     * @param recetasId id del Receta el cual sera padre del Calificacion actualizado.
     * @return Instancia de CalificacionEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException
     *
     */
    public CalificacionEntity updateCalificacion(Long recetasId, Long calificacionesId, CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el calificacione con id = {0} de la usuario con id = {1}" ,new Object[]{calificacionEntity.getId(),recetasId});
        RecetaEntity usuarioEntity = usuarioPersistence.find(recetasId);
        CalificacionEntity calificacionOriginal = persistence.find(calificacionesId);
        validarRN(calificacionEntity);
        calificacionOriginal.setReceta(usuarioEntity);
        calificacionOriginal.setCalificador(calificacionEntity.getCalificador());
        calificacionOriginal.setPuntos(calificacionEntity.getPuntos());
        
        CalificacionEntity newCalificacionEntity = persistence.update(calificacionOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el calificacion con id = {0} del libro con id = {1}" ,new Object[]{calificacionEntity.getId(),recetasId});
        return newCalificacionEntity;
    }
    
        /**
     * Obtiene la lista de los registros de Calificacion que pertenecen a un Receta.
     *
     * @param recetasId id del Receta el cual es padre de los Calificacions.
     * @return Colección de objetos de CalificacionEntity.
     */
    public List<CalificacionEntity> getCalificaciones(Long recetasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los calificaciones asociados al usuario con id = {0}", recetasId);
        RecetaEntity usuarioEntity = usuarioPersistence.find(recetasId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los calificaciones asociados al usuario con id = {0}", recetasId);
        return (List<CalificacionEntity>) usuarioEntity.getCalificaciones();
    }
    
    
    public CalificacionEntity getCalificacion(Long recetasId, Long calificacionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el calificacione con id = {0} del libro con id = {1}" ,new Object[]{calificacionesId, recetasId});
        return persistence.findByReceta(recetasId, calificacionesId);
    }
    

    /**
     * Elimina una instancia de Calificacion de la base de datos.
     *
     * @param calificacionesId Identificador de la instancia a eliminar.
     * @param recetasId id del Receta el cual es padre del Calificacion.
     * @throws BusinessLogicException Si la reseña no esta asociada al libro.
     *
     */
    public void deleteCalificacion(Long recetasId, Long calificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacione con id = {0} del libro con id = {1}" ,new Object[]{calificacionesId, recetasId});
        CalificacionEntity old = getCalificacion(recetasId, calificacionesId);
        if (old == null) {
            throw new BusinessLogicException("El recurso /recetas/"+recetasId+"/calificaciones/"+calificacionesId+" no existe.");
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacione con id = {0} del libro con id = {1}" + recetasId, calificacionesId);
    }
}
