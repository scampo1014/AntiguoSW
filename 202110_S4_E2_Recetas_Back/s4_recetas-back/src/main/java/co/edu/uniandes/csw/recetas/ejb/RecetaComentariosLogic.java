/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.recetas.persistence.RecetaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Maria Valentina Garcia
 */
@Stateless
public class RecetaComentariosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(RecetaComentariosLogic.class.getName());

    @Inject
    private ComentarioPersistence persistence;
    
    @Inject
    private RecetaPersistence recetaPersistence;
    
    public void validarRN(ComentarioEntity comentarioEntity) throws BusinessLogicException {
        //Regla de negocio: Un comentario no puede estar vacío.
        if (comentarioEntity.getComentario().equals("")) {
            throw new BusinessLogicException("El comentario no puede estar vacío.");
        }
    }
    
     public ComentarioEntity createComentario(Long recetasId, ComentarioEntity comentarioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear comentario");
        RecetaEntity receta = recetaPersistence.find(recetasId);
        //validar reglas de negocio
        validarRN(comentarioEntity);
        comentarioEntity.setReceta(receta);
        LOGGER.log(Level.INFO, "Termina proceso de creación del comentario");
        ComentarioEntity newComentarioEntity= persistence.create(comentarioEntity);
        return newComentarioEntity;
    }
        /**
     * Actualiza la información de una instancia de Comentario.
     *
     * @param comentarioEntity Instancia de ComentarioEntity con los nuevos datos.
     * @param recetasId id del Receta el cual sera padre del Comentario actualizado.
     * @return Instancia de ComentarioEntity con los datos actualizados.
     *
     */
    public ComentarioEntity updateComentario(Long recetasId, Long comentariosId, ComentarioEntity comentarioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el comentario con id = {0} de la receta con id = {1}" + comentarioEntity.getId(),recetasId);
        RecetaEntity recetaEntity = recetaPersistence.find(recetasId);
        ComentarioEntity comentarioOriginal = persistence.find(comentariosId);
        validarRN(comentarioEntity);
        comentarioOriginal.setReceta(recetaEntity);
        comentarioOriginal.setAprobado(comentarioEntity.getAprobado());
        comentarioOriginal.setComentario(comentarioEntity.getComentario());
        comentarioOriginal.setPositivo(comentarioEntity.getPositivo());
        
        ComentarioEntity newComentarioEntity = persistence.update(comentarioOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el comentario con id = {0} del libro con id = {1}" + comentarioEntity.getId(),recetasId);
        return newComentarioEntity;
    }
    
        /**
     * Obtiene la lista de los registros de Comentario que pertenecen a un Receta.
     *
     * @param recetasId id del Receta el cual es padre de los Comentarios.
     * @return Colección de objetos de ComentarioEntity.
     */
    public List<ComentarioEntity> getComentarios(Long recetasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los comentarios asociados al receta con id = {0}", recetasId);
        RecetaEntity recetaEntity = recetaPersistence.find(recetasId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los comentarios asociados al receta con id = {0}", recetasId);
        return recetaEntity.getComentarios();
    }
    
    
    public ComentarioEntity getComentario(Long recetasId, Long comentariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el comentario con id = {0} del libro con id = " + recetasId, comentariosId);
        return persistence.findByReceta(recetasId, comentariosId);
    }
    

    /**
     * Elimina una instancia de Comentario de la base de datos.
     *
     * @param comentariosId Identificador de la instancia a eliminar.
     * @param recetasId id del Receta el cual es padre del Comentario.
     * @throws BusinessLogicException Si la reseña no esta asociada al libro.
     *
     */
    public void deleteComentario(Long recetasId, Long comentariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el comentario con id = {0} del libro con id = " + recetasId, comentariosId);
        ComentarioEntity old = getComentario(recetasId, comentariosId);
        if (old == null) {
            throw new BusinessLogicException("El recurso /recetas/"+recetasId+"/comentarios/"+comentariosId+" no existe.");
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el comentario con id = {0} del libro con id = " + recetasId, comentariosId);
    }
    
}
