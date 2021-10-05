/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.recetas.persistence.ComentarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ComentarioCalificacionesLogic {
    private static final Logger LOGGER = Logger.getLogger(ComentarioCalificacionesLogic.class.getName());
    
    @Inject
    private CalificacionPersistence persistence;
    
    @Inject
    private ComentarioPersistence comentarioPersistence;
    
    public void validarRN(CalificacionEntity calificacionEntity) throws  BusinessLogicException{
        //Regla de negocio: La calificación debe estar entre 1.0 y 5.0
        if(calificacionEntity.getPuntos()<1.0 || calificacionEntity.getPuntos()>5.0){
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        }
    }
    
     public CalificacionEntity createCalificacion(Long comentariosId, CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear calificacione");
        ComentarioEntity comentario = comentarioPersistence.find(comentariosId);
        //validar reglas de negocio
        validarRN(calificacionEntity);
        calificacionEntity.setComentario(comentario);
        LOGGER.log(Level.INFO, "Termina proceso de creación del calificacione");
        CalificacionEntity newCalificacionEntity= persistence.create(calificacionEntity);
        return newCalificacionEntity;
    }
        /**
     * Actualiza la información de una instancia de Calificacion.
     *
     * @param calificacioneEntity Instancia de CalificacionEntity con los nuevos datos.
     * @param comentariosId id del Comentario el cual sera padre del Calificacion actualizado.
     * @return Instancia de CalificacionEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException
     *
     */
    public CalificacionEntity updateCalificacion(Long comentariosId, Long calificacionesId, CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el calificacione con id = {0} de la comentario con id = {1}" + calificacionEntity.getId(),comentariosId);
        ComentarioEntity comentarioEntity = comentarioPersistence.find(comentariosId);
        CalificacionEntity calificacionOriginal = persistence.find(calificacionesId);
        validarRN(calificacionEntity);
        calificacionOriginal.setComentario(comentarioEntity);
        calificacionOriginal.setCalificador(calificacionEntity.getCalificador());
        calificacionOriginal.setPuntos(calificacionEntity.getPuntos());
        
        CalificacionEntity newCalificacionEntity = persistence.update(calificacionOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el calificacion con id = {0} del libro con id = {1}" + calificacionEntity.getId(),comentariosId);
        return newCalificacionEntity;
    }
    
        /**
     * Obtiene la lista de los registros de Calificacion que pertenecen a un Comentario.
     *
     * @param comentariosId id del Comentario el cual es padre de los Calificacions.
     * @return Colección de objetos de CalificacionEntity.
     */
    public List<CalificacionEntity> getCalificaciones(Long comentariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los calificaciones asociados al comentario con id = {0}", comentariosId);
        ComentarioEntity comentarioEntity = comentarioPersistence.find(comentariosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los calificaciones asociados al comentario con id = {0}", comentariosId);
        return (List<CalificacionEntity>) comentarioEntity.getCalificaciones();
    }
    
    
    public CalificacionEntity getCalificacion(Long comentariosId, Long calificacionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el calificacione con id = {0} del libro con id = " + comentariosId, calificacionesId);
        return persistence.findByComentario(comentariosId, calificacionesId);
    }
    

    /**
     * Elimina una instancia de Calificacion de la base de datos.
     *
     * @param calificacionesId Identificador de la instancia a eliminar.
     * @param comentariosId id del Comentario el cual es padre del Calificacion.
     * @throws BusinessLogicException Si la reseña no esta asociada al libro.
     *
     */
    public void deleteCalificacion(Long comentariosId, Long calificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacione con id = {0} del libro con id = " + comentariosId, calificacionesId);
        CalificacionEntity old = getCalificacion(comentariosId, calificacionesId);
        if (old == null) {
            throw new BusinessLogicException("El recurso /comentarios/"+comentariosId+"/calificaciones/"+calificacionesId+" no existe.");
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacione con id = {0} del libro con id = " + comentariosId, calificacionesId);
    }
}
