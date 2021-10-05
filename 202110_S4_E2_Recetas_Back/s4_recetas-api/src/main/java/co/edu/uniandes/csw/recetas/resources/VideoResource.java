/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.VideoDTO;
import co.edu.uniandes.csw.recetas.ejb.VideoLogic;
import co.edu.uniandes.csw.recetas.entities.VideoEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
/**
 *
 * @author Santiago Campo
 */
@Path("videos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class VideoResource {
    
    private static final Logger LOGGER = Logger.getLogger(VideoResource.class.getName());
    private static final String ELRECURSO = "El recurso /videos/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private VideoLogic videoLogic;
    
    @POST
    public VideoDTO createVideo(VideoDTO video) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "VideoResource createVideo: input: {0}", video);
        VideoEntity videoEntity = video.toEntity();
        VideoEntity nuevovideoEntity = videoLogic.createVideo(videoEntity);
        VideoDTO nuevoVideoDTO = new VideoDTO(nuevovideoEntity);
        LOGGER.log(Level.INFO, "VideoResource createVideo: output: {0}", nuevoVideoDTO);
        return nuevoVideoDTO;
    }
    
    @GET
    public List<VideoDTO> getVideos() {
        LOGGER.info("VideoResource getVideos: input: void");
        List<VideoDTO> listaVideos = listEntity2DetailDTO(videoLogic.getVideos());
        LOGGER.log(Level.INFO, "VideoResource getVideos: output: {0}", listaVideos);
        return listaVideos;
    }
    
    @GET
    @Path("{videosId: \\d+}")
    public VideoDTO getVideo(@PathParam("videosId") Long videosId) {
        LOGGER.log(Level.INFO, "VideoResource getVideo: input: {0}", videosId);
        VideoEntity videoEntity = videoLogic.getVideo(videosId);
        if (videoEntity == null) {
            throw new WebApplicationException(ELRECURSO + videosId + NOEXISTE, 404);
        }
        VideoDTO fullDTO = new VideoDTO(videoEntity);
        LOGGER.log(Level.INFO, "VideoResource getVideo: output: {0}", fullDTO);
        return fullDTO;
    }
    
    @PUT
    @Path("{videosId: \\d+}")
    public VideoDTO updateVideo(@PathParam("videosId") Long videosId, VideoDTO video) {
        LOGGER.log(Level.INFO, "VideoResource updateVideo: input: id:{0} , video: {1}", new Object[]{videosId, video});
        video.setId(videosId);
        if (videoLogic.getVideo(videosId) == null) {
            throw new WebApplicationException(ELRECURSO + videosId + NOEXISTE, 404);
        }
        VideoDTO fullDTO = new VideoDTO(videoLogic.updateVideo(videosId, video.toEntity()));
        LOGGER.log(Level.INFO, "VideoResource updateVideo: output: {0}", fullDTO);
        return fullDTO;

    }
    
    @DELETE
    @Path("{videosId: \\d+}")
    public void deleteVideo(@PathParam("videosId") Long videosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "VideoResource deleteVideo: input: {0}", videosId);
        if (videoLogic.getVideo(videosId) == null) {
            throw new WebApplicationException(ELRECURSO + videosId + NOEXISTE, 404);
        }
        videoLogic.deleteVideo(videosId);
        LOGGER.info("VideoResource deleteVideo: output: void");
    }
     

    private List<VideoDTO> listEntity2DetailDTO(List<VideoEntity> entityList) {
        List<VideoDTO> list = new ArrayList<>();
        for (VideoEntity entity : entityList) {
            list.add(new VideoDTO(entity));
        }
        return list;
    }
}
