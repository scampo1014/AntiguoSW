/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.entities.VideoEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.RecetaPersistence;
import co.edu.uniandes.csw.recetas.persistence.VideoPersistence;
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
public class RecetaVideosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(RecetaVideosLogic.class.getName());

    @Inject
    VideoPersistence persistence;
    
    @Inject
    private RecetaPersistence recetaPersistence;
    
    public void validarRN(VideoEntity videoEntity) throws BusinessLogicException
    {
        //validar reglas de negocio
        //Regla de negocio: Dirección es única
        if (persistence.findByDireccion(videoEntity.getDireccion()) != null) {
            throw new BusinessLogicException("Ya existe un video en el sistema con la misma dirección.");
        }
        //RN: Dirección no vacía
        if (videoEntity.getDireccion().equals("")) {
            throw new BusinessLogicException("La direccion de el video no puede estar vacía");
        }
        //RN: Tamaño no puede ser negativo o 0
        if (videoEntity.getTamanio() <= 0) {
            throw new BusinessLogicException("El tamaño de el video no puede ser 0 o menos");
        }
        //RN: Tamaño no puede ser vacío
        if (videoEntity.getTamanio() == null) {
            throw new BusinessLogicException("El video tiene que tener tamaño");
        }  
        //RN: Tamaño no puede superar 600MB
        if (videoEntity.getTamanio() > 600000000) {
            throw new BusinessLogicException("El tamaño de el video no puede ser mayor a 600MB");
        } 
        //RN: Duración no puede ser negativo o 0
        if (videoEntity.getDuracion() <= 0) {
            throw new BusinessLogicException("La duración de el video no puede ser 0 o menos");
        }
        //RN: Duración no puede ser negativo o 0
        if (videoEntity.getDuracion() > 600) {
            throw new BusinessLogicException("La duración de el video no puede mayor a 10 min");
        }
    }
    
     public VideoEntity createVideo(Long recetasId, VideoEntity videoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear video");
        RecetaEntity receta = recetaPersistence.find(recetasId);
        validarRN(videoEntity);
        videoEntity.setReceta(receta);
        LOGGER.log(Level.INFO, "Termina proceso de creación del video");
        return persistence.create(videoEntity);
    }
        /**
     * Actualiza la información de una instancia de Video.
     *
     * @param videoEntity Instancia de VideoEntity con los nuevos datos.
     * @param recetasId id del Receta el cual sera padre del videoEntity actualizado.
     * @return Instancia de videoEntity con los datos actualizados.
     *  new Object[]{videoEntity.getId(),recetasId}
     */
    public VideoEntity updateVideo(Long recetasId, Long videosId, VideoEntity videoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el video con id = {0} de la receta con id = {1} ",new Object[]{videoEntity.getId(),recetasId});
        RecetaEntity recetaEntity = recetaPersistence.find(recetasId);
        VideoEntity videoOriginal = persistence.find(videosId);
        validarRN(videoEntity);
        videoOriginal.setReceta(recetaEntity);
        videoOriginal.setDireccion(videoEntity.getDireccion());
        videoOriginal.setFormato(videoEntity.getFormato());
        videoOriginal.setTamanio(videoEntity.getTamanio());
        videoOriginal.setDuracion(videoEntity.getDuracion());
        VideoEntity newEntity = persistence.update(videoOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el video con id = {0} de la receta con id = {1}", new Object[]{videoEntity.getId(),recetasId});
        return newEntity;
    }
    
        /**
     * Obtiene la lista de los registros de Video que pertenecen a un Receta.
     *
     * @param recetasId id del Receta el cual es padre de los Videos.
     * @return Colección de objetos de VideoEntity.
     */
    public List<VideoEntity> getVideos(Long recetasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los Videos asociados al receta con id = {0}", recetasId);
        RecetaEntity recetaEntity = recetaPersistence.find(recetasId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los Videos asociados al receta con id = {0}", recetasId);
        return recetaEntity.getVideos();
    }
    
    
    public VideoEntity getVideo(Long recetasId, Long videosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el video con id = {0} de la receta con id = {1}", new Object[]{videosId,recetasId});
        return persistence.findByReceta(recetasId, videosId);
    }
    

    /**
     * Elimina una instancia de Video de la base de datos.
     *
     * @param videosId Identificador de la instancia a eliminar.
     * @param recetasId id del Receta el cual es padre del Foto.
     * @throws BusinessLogicException Si la foto no esta asociada a la receta.
     *
     */
    public void deleteVideo(Long recetasId, Long videosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el video con id = {0} de la receta con id = {1}", new Object[]{videosId,recetasId});
        VideoEntity old = getVideo(recetasId, videosId);
        if (old == null) {
            throw new BusinessLogicException("El recurso /recetas/"+recetasId+"/videos/"+videosId+" no existe.");
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el video con id = {0} de la receta con id = {1}", new Object[]{videosId,recetasId});
    }
}
