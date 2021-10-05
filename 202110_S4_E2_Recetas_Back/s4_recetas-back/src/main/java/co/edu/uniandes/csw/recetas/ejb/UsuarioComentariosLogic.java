/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import co.edu.uniandes.csw.recetas.entities.UsuarioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.recetas.persistence.UsuarioPersistence;
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
public class UsuarioComentariosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioComentariosLogic.class.getName());

    @Inject
    private ComentarioPersistence persistence;
    
    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    public void validarRN(ComentarioEntity comentarioEntity) throws BusinessLogicException {
        //Regla de negocio: Un comentario no puede estar vacío.
        if (comentarioEntity.getComentario().equals("")) {
            throw new BusinessLogicException("El comentario no puede estar vacío.");
        }
    }
    
     public ComentarioEntity createComentario(Long usuariosId, ComentarioEntity comentarioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear comentario");
        UsuarioEntity usuario = usuarioPersistence.find(usuariosId);
        //validar reglas de negocio
        validarRN(comentarioEntity);
        comentarioEntity.setUsuario(usuario);
        LOGGER.log(Level.INFO, "Termina proceso de creación del comentario");
        ComentarioEntity newComentarioEntity= persistence.create(comentarioEntity);
        return newComentarioEntity;
    }
        /**
     * Actualiza la información de una instancia de Comentario.
     *
     * @param comentarioEntity Instancia de ComentarioEntity con los nuevos datos.
     * @param usuariosId id del Usuario el cual sera padre del Comentario actualizado.
     * @return Instancia de ComentarioEntity con los datos actualizados.
     *
     */
    public ComentarioEntity updateComentario(Long usuariosId, Long comentariosId, ComentarioEntity comentarioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el comentario con id = {0} de la usuario con id = {1}" + comentarioEntity.getId(),usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        ComentarioEntity comentarioOriginal = persistence.find(comentariosId);
        validarRN(comentarioEntity);
        comentarioOriginal.setUsuario(usuarioEntity);
        comentarioOriginal.setAprobado(comentarioEntity.getAprobado());
        comentarioOriginal.setComentario(comentarioEntity.getComentario());
        comentarioOriginal.setPositivo(comentarioEntity.getPositivo());
        
        ComentarioEntity newComentarioEntity = persistence.update(comentarioOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el comentario con id = {0} del libro con id = {1}" + comentarioEntity.getId(),usuariosId);
        return newComentarioEntity;
    }
    
        /**
     * Obtiene la lista de los registros de Comentario que pertenecen a un Usuario.
     *
     * @param usuariosId id del Usuario el cual es padre de los Comentarios.
     * @return Colección de objetos de ComentarioEntity.
     */
    public List<ComentarioEntity> getComentarios(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los comentarios asociados al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los comentarios asociados al usuario con id = {0}", usuariosId);
        return (List<ComentarioEntity>) usuarioEntity.getComentarios();
    }
    
    
    public ComentarioEntity getComentario(Long usuariosId, Long comentariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el comentario con id = {0} del libro con id = " + usuariosId, comentariosId);
        return persistence.findByUsuario(usuariosId, comentariosId);
    }
    

    /**
     * Elimina una instancia de Comentario de la base de datos.
     *
     * @param comentariosId Identificador de la instancia a eliminar.
     * @param usuariosId id del Usuario el cual es padre del Comentario.
     * @throws BusinessLogicException Si la reseña no esta asociada al libro.
     *
     */
    public void deleteComentario(Long usuariosId, Long comentariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el comentario con id = {0} del libro con id = " + usuariosId, comentariosId);
        ComentarioEntity old = getComentario(usuariosId, comentariosId);
        if (old == null) {
            throw new BusinessLogicException("El recurso /usuarios/"+usuariosId+"/comentarios/"+comentariosId+" no existe.");
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el comentario con id = {0} del libro con id = " + usuariosId, comentariosId);
    }
    
}
