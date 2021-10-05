/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.FotoEntity;
import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.FotoPersistence;
import co.edu.uniandes.csw.recetas.persistence.RecetaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ejb.Stateless;

/**
 *
 * @author Santiago Campo
 */
@Stateless
public class RecetaFotosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(RecetaFotosLogic.class.getName());

    @Inject
    FotoPersistence persistence;
    
    @Inject
    private RecetaPersistence recetaPersistence;
    
    public void validarRN(FotoEntity fotoEntity) throws BusinessLogicException
    {
        //validar reglas de negocio
         //Regla de negocio: Dirección es única
        if (persistence.findByDireccion(fotoEntity.getDireccion()) != null) {
            throw new BusinessLogicException("Ya existe una foto en el sistema con la misma dirección.");
        }
        //RN: Dirección no vacía
        if (fotoEntity.getDireccion().equals("")) {
            throw new BusinessLogicException("La direccion de la foto no puede estar vacía");
        }
        //RN: Tamaño no puede ser negativo o 0
        if (fotoEntity.getTamanio() <= 0) {
            throw new BusinessLogicException("El tamaño de la foto no puede ser 0 o menos");
        }
        //RN: Tamaño no puede ser vacío
        if (fotoEntity.getTamanio() == null) {
            throw new BusinessLogicException("La foto tiene que tener tamaño");
        }  
        //RN: Tamaño no puede superar 15MB
        if (fotoEntity.getTamanio() > 15000000) {
            throw new BusinessLogicException("El tamaño de la foto no puede ser mayor a 15MB");
        }
    }
    
     public FotoEntity createFoto(Long recetasId, FotoEntity fotoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear foto");
        RecetaEntity receta = recetaPersistence.find(recetasId);
        //validar reglas de negocio
        validarRN(fotoEntity);
        fotoEntity.setReceta(receta);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la foto");
        FotoEntity newFotoEntity= persistence.create(fotoEntity);
        return newFotoEntity;
    }
        /**
     * Actualiza la información de una instancia de Foto.
     *
     * @param fotoEntity Instancia de FotoEntity con los nuevos datos.
     * @param recetasId id del Receta el cual sera padre del fotoEntity actualizado.
     * @return Instancia de fotoEntity con los datos actualizados.
     *
     */
    public FotoEntity updateFoto(Long recetasId, Long fotosId, FotoEntity fotoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la foto con id = {0} de la receta con id = {1}" ,new Object[]{fotoEntity.getId(),recetasId});
        RecetaEntity recetaEntity = recetaPersistence.find(recetasId);
        FotoEntity fotoOriginal = persistence.find(fotosId);
        validarRN(fotoEntity);
        
        fotoOriginal.setReceta(recetaEntity);
        fotoOriginal.setDireccion(fotoEntity.getDireccion());
        fotoOriginal.setFormato(fotoEntity.getFormato());
        fotoOriginal.setTamanio(fotoEntity.getTamanio());
        FotoEntity newEntity = persistence.update(fotoOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la foto con id = {0} de la receta con id = {1}" ,new Object[]{fotoEntity.getId(),recetasId});
        return newEntity;
    }
    
        /**
     * Obtiene la lista de los registros de Foto que pertenecen a un Receta.
     *
     * @param recetasId id del Receta el cual es padre de las Fotos.
     * @return Colección de objetos de FotoEntity.
     */
    public List<FotoEntity> getFotos(Long recetasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las Fotos asociados al receta con id = {0}", recetasId);
        RecetaEntity recetaEntity = recetaPersistence.find(recetasId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar las Fotos asociados al receta con id = {0}", recetasId);
        return recetaEntity.getFotos();
    }
    
    
    public FotoEntity getFoto(Long recetasId, Long fotosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la foto con id = {0} del libro con id = {1}" ,new Object[]{fotosId,recetasId});
        return persistence.findByReceta(recetasId, fotosId);
    }
    

    /**
     * Elimina una instancia de Foto de la base de datos.
     *
     * @param fotosId Identificador de la instancia a eliminar.
     * @param recetasId id del Receta el cual es padre del Foto.
     * @throws BusinessLogicException Si la foto no esta asociada a la receta.
     *
     */
    public void deleteFoto(Long recetasId, Long fotosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la foto con id = {0} de la receta con id = {1}" ,new Object[]{fotosId,recetasId});
        FotoEntity old = getFoto(recetasId, fotosId);
        if (old == null) {
            throw new BusinessLogicException("El recurso /recetas/"+recetasId+"/fotos/"+fotosId+" no existe.");
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la foto con id = {0} de la receta con id = {1}" ,new Object[]{fotosId,recetasId});
    }
}
