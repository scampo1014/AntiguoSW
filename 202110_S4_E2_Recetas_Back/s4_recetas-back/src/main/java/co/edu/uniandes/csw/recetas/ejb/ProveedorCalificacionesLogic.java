/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.recetas.persistence.ProveedorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Julian
 */
@Stateless
public class ProveedorCalificacionesLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ProveedorCalificacionesLogic.class.getName());
    
    @Inject
    private CalificacionPersistence persistence;
    
    @Inject
    private ProveedorPersistence proveedorPersistence;
    
    public void validarRN(CalificacionEntity calificacionEntity) throws  BusinessLogicException{
        //Regla de negocio: La calificación debe estar entre 1.0 y 5.0
        if(calificacionEntity.getPuntos()<1.0 || calificacionEntity.getPuntos()>5.0){
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        }
    }
    
     public CalificacionEntity createCalificacion(Long proveedoresId, CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear calificacione");
        ProveedorEntity proveedor = proveedorPersistence.find(proveedoresId);
        //validar reglas de negocio
        validarRN(calificacionEntity);
        calificacionEntity.setProveedor(proveedor);
        LOGGER.log(Level.INFO, "Termina proceso de creación del calificacione");
        CalificacionEntity newCalificacionEntity= persistence.create(calificacionEntity);
        return newCalificacionEntity;
    }
        /**
     * Actualiza la información de una instancia de Calificacion.
     *
     * @param calificacioneEntity Instancia de CalificacionEntity con los nuevos datos.
     * @param proveedoresId id del Proveedor el cual sera padre del Calificacion actualizado.
     * @return Instancia de CalificacionEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException
     *
     */
    public CalificacionEntity updateCalificacion(Long proveedoresId, Long calificacionesId, CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el calificacione con id = {0} de la proveedor con id = {1}" ,new Object[]{calificacionEntity.getId(),proveedoresId});
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedoresId);
        CalificacionEntity calificacionOriginal = persistence.find(calificacionesId);
        validarRN(calificacionEntity);
        calificacionOriginal.setProveedor(proveedorEntity);
        calificacionOriginal.setCalificador(calificacionEntity.getCalificador());
        calificacionOriginal.setPuntos(calificacionEntity.getPuntos());
        
        CalificacionEntity newCalificacionEntity = persistence.update(calificacionOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el calificacion con id = {0} del libro con id = {1}" ,new Object[]{calificacionEntity.getId(),proveedoresId});
        return newCalificacionEntity;
    }
    
        /**
     * Obtiene la lista de los registros de Calificacion que pertenecen a un Proveedor.
     *
     * @param proveedoresId id del Proveedor el cual es padre de los Calificacions.
     * @return Colección de objetos de CalificacionEntity.
     */
    public List<CalificacionEntity> getCalificaciones(Long proveedoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los calificaciones asociados al proveedor con id = {0}", proveedoresId);
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedoresId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los calificaciones asociados al proveedor con id = {0}", proveedoresId);
        return (List<CalificacionEntity>) proveedorEntity.getCalificaciones();
    }
    
    
    public CalificacionEntity getCalificacion(Long proveedoresId, Long calificacionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el calificacione con id = {0} del libro con id = {1}" ,new Object[]{proveedoresId, calificacionesId});
        return persistence.findByProveedor(proveedoresId, calificacionesId);
    }
    

    /**
     * Elimina una instancia de Calificacion de la base de datos.
     *
     * @param calificacionesId Identificador de la instancia a eliminar.
     * @param proveedoresId id del Proveedor el cual es padre del Calificacion.
     * @throws BusinessLogicException Si la reseña no esta asociada al libro.
     *
     */
    public void deleteCalificacion(Long proveedoresId, Long calificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el calificacione con id = {0} del libro con id = {1}" ,new Object[]{proveedoresId, calificacionesId});
        CalificacionEntity old = getCalificacion(proveedoresId, calificacionesId);
        if (old == null) {
            throw new BusinessLogicException("El recurso /proveedores/"+proveedoresId+"/calificaciones/"+calificacionesId+" no existe.");
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el calificacione con id = {0} del libro con id = {1}" ,new Object[]{proveedoresId, calificacionesId});
    }
    
}
