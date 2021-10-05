/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.recetas.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.recetas.ejb.ComentarioLogic;
import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
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
@Path("comentarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ComentarioResource {
    
    private static final Logger LOGGER = Logger.getLogger(ComentarioResource.class.getName());
    private static final String ELRECURSOR = "El recurso /comentarios/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private ComentarioLogic comentarioLogic;
    
    @POST
    public ComentarioDTO createComentario(ComentarioDTO comentario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ComentarioResource createComentario: input: {0}", comentario);
        ComentarioEntity comentarioEntity = comentario.toEntity();
        ComentarioEntity nuevoComentarioEntity = comentarioLogic.createComentario(comentarioEntity);
        ComentarioDTO nuevoComentarioDTO = new ComentarioDTO(nuevoComentarioEntity);
        LOGGER.log(Level.INFO, "ComentarioResource createComentario: output: {0}", nuevoComentarioDTO);
        return nuevoComentarioDTO;
    }
    
    @GET
    public List<ComentarioDetailDTO> getComentarios() {
        LOGGER.info("ComentarioResource getComentarios: input: void");
        List<ComentarioDetailDTO> listaComentarios = listEntity2DetailDTO(comentarioLogic.getComentarios());
        LOGGER.log(Level.INFO, "ComentarioResource getComentarios: output: {0}", listaComentarios);
        return listaComentarios;
    }
    
    @GET
    @Path("{comentariosId: \\d+}")
    public ComentarioDetailDTO getComentario(@PathParam("comentariosId") Long comentariosId){
        LOGGER.log(Level.INFO, "ComentarioResource getComentario: input: {0}", comentariosId);
        ComentarioEntity comentarioEntity = comentarioLogic.getComentario(comentariosId);
        if (comentarioEntity == null) {
            throw new WebApplicationException(ELRECURSOR + comentariosId + NOEXISTE, 404);
        }
        ComentarioDetailDTO detailDTO = new ComentarioDetailDTO(comentarioEntity);
        LOGGER.log(Level.INFO, "ComentarioResource getComentario: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @PUT
    @Path("{comentariosId: \\d+}")
    public ComentarioDetailDTO updateComentario(@PathParam("comentariosId") Long comentariosId, ComentarioDetailDTO comentario){
        LOGGER.log(Level.INFO, "ComentarioResource updateComentario: input: id:{0} , comentario: {1}", new Object[]{comentariosId, comentario});
        comentario.setId(comentariosId);
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException(ELRECURSOR + comentariosId + NOEXISTE, 404);
        }
        ComentarioDetailDTO detailDTO = new ComentarioDetailDTO(comentarioLogic.updateComentario(comentariosId, comentario.toEntity()));
        LOGGER.log(Level.INFO, "ComentarioResource updateComentario: output: {0}", detailDTO);
        return detailDTO;

    }
    
    @DELETE
    @Path("{comentariosId: \\d+}")
    public void deleteComentario(@PathParam("comentariosId") Long comentariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ComentarioResource deleteComentario: input: {0}", comentariosId);
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException(ELRECURSOR + comentariosId + NOEXISTE, 404);
        }
        comentarioLogic.deleteComentario(comentariosId);
        LOGGER.info("ComentarioResource deleteComentario: output: void");
    }
    

    private List<ComentarioDetailDTO> listEntity2DetailDTO(List<ComentarioEntity> entityList) {
        List<ComentarioDetailDTO> list = new ArrayList<>();
        for (ComentarioEntity entity : entityList) {
            list.add(new ComentarioDetailDTO(entity));
        }
        return list;
    }
}
