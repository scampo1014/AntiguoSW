/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.ComentarioPersistence;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Campo
 */
@Stateless
public class ComentarioLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ComentarioLogic.class.getName());
    
    @Inject
    ComentarioPersistence persistence;
      
    public ComentarioEntity createComentario(ComentarioEntity comentario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del comentario");
        //Regla de negocio: Un comentario no puede estar vacío.
        if (comentario.getComentario().equals("")) {
            throw new BusinessLogicException("El comentario no puede estar vacío.");
        }
        
        //Invoca la persistencia para crear el comentario
        comentario = persistence.create(comentario);
        return comentario;
    }
    
    public List<ComentarioEntity> getComentarios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas los comentarios");        
        List<ComentarioEntity> comentarios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas los comentarios");
        return comentarios;
    }

    public ComentarioEntity getComentario(Long comentarioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el comentario con id = {0}", comentarioId);        
        ComentarioEntity comentarioEntity = persistence.find(comentarioId);
        if (comentarioEntity == null) {
            LOGGER.log(Level.SEVERE, "El comentario con el id = {0} no existe", comentarioId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el comentario con id = {0}", comentarioId);
        return comentarioEntity;
    }

    public ComentarioEntity updateComentario(Long comentarioId, ComentarioEntity comentarioEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el comentario con id = {0}", comentarioId);        
        ComentarioEntity newEntity = persistence.update(comentarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el comentario con id = {0}", comentarioEntity.getId());
        return newEntity;
    }

    public void deleteComentario(Long comentarioId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el comentario con id = {0}", comentarioId);        
        List<CalificacionEntity> calificaciones = (List<CalificacionEntity>) getComentario(comentarioId).getCalificaciones();
        if (calificaciones != null && !calificaciones.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el comentario con id = " + comentarioId + " porque tiene calificaciones asociadas");
        }
        persistence.delete(comentarioId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el comentario con id = {0}", comentarioId);
    }
    
}
