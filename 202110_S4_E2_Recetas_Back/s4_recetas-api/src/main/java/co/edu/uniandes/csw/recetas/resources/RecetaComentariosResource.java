/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.recetas.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.recetas.ejb.ComentarioLogic;
import co.edu.uniandes.csw.recetas.ejb.RecetaComentariosLogic;
import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
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
public class RecetaComentariosResource {
    
    private static final Logger LOGGER = Logger.getLogger(RecetaComentariosResource.class.getName());
    private static final String ELRECURSOR = "El recurso /recetas/";
    private static final String R = "/comentarios/";
    private static final String NOEXISTE = " no existe.";
    
    @Inject
    private RecetaComentariosLogic recetaComentariosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ComentarioLogic comentarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @POST
    public ComentarioDTO createComentario(@PathParam("recetasId") Long recetasId, ComentarioDTO comentario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaComentariosResource createComentario: input: recetasID: {0} , comentariosId: {1}", new Object[]{recetasId, comentario.getId()});
        
        ComentarioDTO comentarioDTO = new ComentarioDTO(recetaComentariosLogic.createComentario(recetasId, comentario.toEntity()));
        LOGGER.log(Level.INFO, "RecetaComentariosResource addComentario: output: {0}", comentarioDTO);
        return comentarioDTO;
    }
    
    @GET
    public List<ComentarioDetailDTO> getComentarios(@PathParam("recetasId") Long recetasId) {
        LOGGER.log(Level.INFO, "RecetaComentariosResource getComentarios: input: {0}", recetasId);
        List<ComentarioDetailDTO> listaDTOs = comentariosListEntity2DTO( recetaComentariosLogic.getComentarios(recetasId));
        LOGGER.log(Level.INFO, "RecetaComentariosResource getComentarios: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    @GET
    @Path("{comentariosId: \\d+}")
    public ComentarioDetailDTO getComentario(@PathParam("recetasId") Long recetasId, @PathParam("comentariosId") Long comentariosId) {
        LOGGER.log(Level.INFO, "RecetaComentariosResource getComentarios: input: recetasID: {0} , comentariosId: {1}", new Object[]{recetasId, comentariosId});
        ComentarioEntity entity=recetaComentariosLogic.getComentario(recetasId,comentariosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + comentariosId + NOEXISTE, 404);
        }
        ComentarioDetailDTO comentarioDetailDTO = new ComentarioDetailDTO(entity);
        LOGGER.log(Level.INFO, "RecetaComentariosResource getComentarios: output: {0}", comentarioDetailDTO);
        return comentarioDetailDTO;
    }
    
    @PUT
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO updateComentario(@PathParam("recetasId") Long recetasId, @PathParam("comentariosId") Long comentariosId, ComentarioDTO comentario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaComentariosResource updateComentario: input: recetasId: {0} , comentariosId: {1}, comentario:{2}", new Object[]{recetasId, comentariosId, comentario});
        if (comentariosId.equals(comentario.getId())) {
            throw new BusinessLogicException("Los ids del Comentario no coinciden.");
        }
        ComentarioEntity entity = recetaComentariosLogic.getComentario(recetasId,comentariosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + comentariosId + NOEXISTE, 404);

        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(recetaComentariosLogic.updateComentario(recetasId,comentariosId,comentario.toEntity()));
        LOGGER.log(Level.INFO, "RecetaComentariosResource updateComentario: output: {0}", comentarioDTO);
        return comentarioDTO;
    }
    
    @DELETE
    @Path("{comentariosId: \\d+}")
    public void deleteComentario(@PathParam("recetasId") Long recetasId, @PathParam("comentariosId") Long comentariosId) throws BusinessLogicException {
        ComentarioEntity entity = recetaComentariosLogic.getComentario(recetasId, comentariosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + comentariosId + NOEXISTE, 404);
        }
        recetaComentariosLogic.deleteComentario(recetasId, comentariosId);
    }
    
    private List<ComentarioDetailDTO> comentariosListEntity2DTO(List<ComentarioEntity> entityList) {
        List<ComentarioDetailDTO> list = new ArrayList<>();
        for (ComentarioEntity entity : entityList) {
            list.add(new ComentarioDetailDTO(entity));
        }
        return list;
    }
    
}
