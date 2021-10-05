/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.recetas.persistence.ProveedorPersistence;
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
public class ProveedorComentariosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ProveedorComentariosLogic.class.getName());

    @Inject
    private ComentarioPersistence persistence;
    
    @Inject
    private ProveedorPersistence proveedorPersistence;
    
    
    public void validarRN(ComentarioEntity comentarioEntity) throws BusinessLogicException {
        //Regla de negocio: Un comentario no puede estar vacío.
        if (comentarioEntity.getComentario().equals("")) {
            throw new BusinessLogicException("El comentario no puede estar vacío.");
        }
    }
    
     public ComentarioEntity createComentario(Long proveedoresId, ComentarioEntity comentarioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear comentario");
        ProveedorEntity proveedor = proveedorPersistence.find(proveedoresId);
        //validar reglas de negocio
        validarRN(comentarioEntity);
        comentarioEntity.setProveedor(proveedor);
        LOGGER.log(Level.INFO, "Termina proceso de creación del comentario");
        ComentarioEntity newComentarioEntity= persistence.create(comentarioEntity);
        return newComentarioEntity;
    }
        /**
     * Actualiza la información de una instancia de Comentario.
     *
     * @param comentarioEntity Instancia de ComentarioEntity con los nuevos datos.
     * @param proveedoresId id del Proveedor el cual sera padre del Comentario actualizado.
     * @return Instancia de ComentarioEntity con los datos actualizados.
     *
     */
    public ComentarioEntity updateComentario(Long proveedoresId, Long comentariosId, ComentarioEntity comentarioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el comentario con id = {0} de la proveedor con id = {1}" + comentarioEntity.getId(),proveedoresId);
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedoresId);
        ComentarioEntity comentarioOriginal = persistence.find(comentariosId);
        //Regla de negocio: Un comentario no puede estar vacío.
        validarRN(comentarioEntity);
        comentarioOriginal.setProveedor(proveedorEntity);
        comentarioOriginal.setAprobado(comentarioEntity.getAprobado());
        comentarioOriginal.setComentario(comentarioEntity.getComentario());
        comentarioOriginal.setPositivo(comentarioEntity.getPositivo());
        
        ComentarioEntity newComentarioEntity = persistence.update(comentarioOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el comentario con id = {0} del libro con id = {1}" + comentarioEntity.getId(),proveedoresId);
        return newComentarioEntity;
    }
    
        /**
     * Obtiene la lista de los registros de Comentario que pertenecen a un Proveedor.
     *
     * @param proveedoresId id del Proveedor el cual es padre de los Comentarios.
     * @return Colección de objetos de ComentarioEntity.
     */
    public List<ComentarioEntity> getComentarios(Long proveedoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los comentarios asociados al proveedor con id = {0}", proveedoresId);
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedoresId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los comentarios asociados al proveedor con id = {0}", proveedoresId);
        return (List<ComentarioEntity>) proveedorEntity.getComentarios();
    }
    
    
    public ComentarioEntity getComentario(Long proveedoresId, Long comentariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el comentario con id = {0} del libro con id = " + proveedoresId, comentariosId);
        return persistence.findByProveedor(proveedoresId, comentariosId);
    }
    

    /**
     * Elimina una instancia de Comentario de la base de datos.
     *
     * @param comentariosId Identificador de la instancia a eliminar.
     * @param proveedoresId id del Proveedor el cual es padre del Comentario.
     * @throws BusinessLogicException Si la reseña no esta asociada al libro.
     *
     */
    public void deleteComentario(Long proveedoresId, Long comentariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el comentario con id = {0} del libro con id = " + proveedoresId, comentariosId);
        ComentarioEntity old = getComentario(proveedoresId, comentariosId);
        if (old == null) {
            throw new BusinessLogicException("El recurso /proveedores/"+proveedoresId+"/comentarios/"+comentariosId+" no existe.");
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el comentario con id = {0} del libro con id = " + proveedoresId, comentariosId);
    }
    
}
