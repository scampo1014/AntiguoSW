/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.VideoEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.VideoPersistence;
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
public class VideoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(VideoLogic.class.getName());
    
    @Inject
    VideoPersistence persistence;
    
    public VideoEntity createVideo(VideoEntity video) throws BusinessLogicException {
        
        //Regla de negocio: Dirección es única
        if (persistence.findByDireccion(video.getDireccion()) != null) {
            throw new BusinessLogicException("Ya existe un video en el sistema con la misma dirección.");
        }
        //RN: Dirección no vacía
        if (video.getDireccion().equals("")) {
            throw new BusinessLogicException("La direccion de el video no puede estar vacía");
        }
        //RN: Tamaño no puede ser negativo o 0
        if (video.getTamanio() <= 0) {
            throw new BusinessLogicException("El tamaño de el video no puede ser 0 o menos");
        }
        //RN: Tamaño no puede ser vacío
        if (video.getTamanio() == null) {
            throw new BusinessLogicException("El video tiene que tener tamaño");
        }  
        //RN: Tamaño no puede superar 600MB
        if (video.getTamanio() > 600000000) {
            throw new BusinessLogicException("El tamaño de el video no puede ser mayor a 600MB");
        } 
        //RN: Duración no puede ser negativo o 0
        if (video.getDuracion() <= 0) {
            throw new BusinessLogicException("La duración de el video no puede ser 0 o menos");
        }
        //RN: Duración no puede ser negativo o 0
        if (video.getDuracion() > 600) {
            throw new BusinessLogicException("La duración de el video no puede mayor a 10 min");
        }
        
        video = persistence.create(video);
        return video;
    }
    
    public List<VideoEntity> getVideos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los videos");        
        List<VideoEntity> videos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los videos");
        return videos;
    }

    public VideoEntity getVideo(Long videoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el video con id = {0}", videoId);        
        VideoEntity videoEntity = persistence.find(videoId);
        if (videoEntity == null) {
            LOGGER.log(Level.SEVERE, "El video con el id = {0} no existe", videoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el video con id = {0}", videoId);
        return videoEntity;
    }

    public VideoEntity updateVideo(Long videoId, VideoEntity videoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el video con id = {0}", videoId);        
        VideoEntity newEntity = persistence.update(videoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el video con id = {0}", videoEntity.getId());
        return newEntity;
    }

    public void deleteVideo(Long videoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el video con id = {0}", videoId);        
        persistence.delete(videoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el video con id = {0}", videoId);
    }
}
