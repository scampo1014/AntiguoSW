/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.VideoDTO;
import co.edu.uniandes.csw.recetas.ejb.RecetaVideosLogic;
import co.edu.uniandes.csw.recetas.ejb.VideoLogic;
import co.edu.uniandes.csw.recetas.entities.VideoEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author Santiago Campo
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecetaVideosResource {
    
    private static final Logger LOGGER = Logger.getLogger(RecetaVideosResource.class.getName());
    private static final String ELRECURSOR = "El recurso /recetas/";
    private static final String R = "/videos/";
    private static final String NOEXISTE = " no existe.";
    
    @Inject
    private RecetaVideosLogic recetaVideosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @POST
    public VideoDTO createVideo(@PathParam("recetasId") Long recetasId, VideoDTO video) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaVideosResource createVideo: input: recetasID: {0} , videosId: {1}", new Object[]{recetasId, video.getId()});
        
        VideoDTO videoDTO = new VideoDTO(recetaVideosLogic.createVideo(recetasId, video.toEntity()));
        LOGGER.log(Level.INFO, "RecetaVideosResource addVideo: output: {0}", videoDTO);
        return videoDTO;
    }
    
    @GET
    public List<VideoDTO> getVideos(@PathParam("recetasId") Long recetasId) {
        LOGGER.log(Level.INFO, "RecetaVideosResource getVideos: input: {0}", recetasId);
        List<VideoDTO> listaDTOs = videosListEntity2DTO(recetaVideosLogic.getVideos(recetasId));
        LOGGER.log(Level.INFO, "RecetaVideosResource getVideos: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    @GET
    @Path("{videosId: \\d+}")
    public VideoDTO getVideo(@PathParam("recetasId") Long recetasId, @PathParam("videosId") Long videosId){
        LOGGER.log(Level.INFO, "RecetaVideosResource getVideos: input: recetasID: {0} , videosId: {1}", new Object[]{recetasId, videosId});
        VideoEntity entity=recetaVideosLogic.getVideo(recetasId,videosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + "/fotos/" + videosId + NOEXISTE, 404);
        }
        VideoDTO videoDTO = new VideoDTO(entity);
        LOGGER.log(Level.INFO, "RecetaVideosResource getVideos: output: {0}", videoDTO);
        return videoDTO;
    }
    
    @PUT
    @Path("{videosId: \\d+}")
    public VideoDTO updateVideo(@PathParam("recetasId") Long recetasId, @PathParam("videosId") Long videosId, VideoDTO video) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaVideosResource updateVideo: input: recetasId: {0} , videosId: {1}, video:{2}", new Object[]{recetasId, videosId, video});
        if (videosId.equals(video.getId())) {
            throw new BusinessLogicException("Los ids del video no coinciden.");
        }
        VideoEntity entity = recetaVideosLogic.getVideo(recetasId,videosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + videosId + NOEXISTE, 404);

        }
        VideoDTO videoDTO = new VideoDTO(recetaVideosLogic.updateVideo(recetasId,videosId,video.toEntity()));
        LOGGER.log(Level.INFO, "RecetaVideosResource updateVideo: output: {0}", videoDTO);
        return videoDTO;
    }
    
    @DELETE
    @Path("{videosId: \\d+}")
    public void deleteVideo(@PathParam("recetasId") Long recetasId, @PathParam("videosId") Long videosId) throws BusinessLogicException {
        VideoEntity entity = recetaVideosLogic.getVideo(recetasId, videosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + videosId + NOEXISTE, 404);
        }
        recetaVideosLogic.deleteVideo(recetasId, videosId);
    }
    
    private List<VideoDTO> videosListEntity2DTO(List<VideoEntity> entityList) {
        List<VideoDTO> list = new ArrayList<VideoDTO>();
        for (VideoEntity entity : entityList) {
            list.add(new VideoDTO(entity));
        }
        return list;
    }

}
