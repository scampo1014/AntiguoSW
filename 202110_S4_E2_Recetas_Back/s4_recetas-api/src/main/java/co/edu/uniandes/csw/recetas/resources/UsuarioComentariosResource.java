/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.recetas.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.recetas.ejb.ComentarioLogic;
import co.edu.uniandes.csw.recetas.ejb.UsuarioComentariosLogic;
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
public class UsuarioComentariosResource {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioComentariosResource.class.getName());
    private static final String ELRECURSOR = "El recurso /usuarios/";
    private static final String R = "/comentarios/";
    private static final String NOEXISTE = " no existe.";
    
    @Inject
    private UsuarioComentariosLogic usuarioComentariosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ComentarioLogic comentarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @POST
    public ComentarioDTO createComentario(@PathParam("usuariosId") Long usuariosId, ComentarioDTO comentario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioComentariosResource createComentario: input: usuariosID: {0} , comentariosId: {1}", new Object[]{usuariosId, comentario.getId()});
        
        ComentarioDTO comentarioDTO = new ComentarioDTO(usuarioComentariosLogic.createComentario(usuariosId, comentario.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioComentariosResource addComentario: output: {0}", comentarioDTO);
        return comentarioDTO;
    }
    
    @GET
    public List<ComentarioDetailDTO> getComentarios(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioComentariosResource getComentarios: input: {0}", usuariosId);
        List<ComentarioDetailDTO> listaDTOs = comentariosListEntity2DTO(usuarioComentariosLogic.getComentarios(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioComentariosResource getComentarios: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    @GET
    @Path("{comentariosId: \\d+}")
    public ComentarioDetailDTO getComentario(@PathParam("usuariosId") Long usuariosId, @PathParam("comentariosId") Long comentariosId) {
        LOGGER.log(Level.INFO, "UsuarioComentariosResource getComentarios: input: usuariosID: {0} , comentariosId: {1}", new Object[]{usuariosId, comentariosId});
        ComentarioEntity entity=usuarioComentariosLogic.getComentario(usuariosId,comentariosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + comentariosId + NOEXISTE, 404);
        }
        ComentarioDetailDTO comentarioDTO = new ComentarioDetailDTO(entity);
        LOGGER.log(Level.INFO, "UsuarioComentariosResource getComentarios: output: {0}", comentarioDTO);
        return comentarioDTO;
    }
    
    @PUT
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO updateComentario(@PathParam("usuariosId") Long usuariosId, @PathParam("comentariosId") Long comentariosId, ComentarioDTO comentario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioComentariosResource updateComentario: input: usuariosId: {0} , comentariosId: {1}, comentario:{2}", new Object[]{usuariosId, comentariosId, comentario});
        if (comentariosId.equals(comentario.getId())) {
            throw new BusinessLogicException("Los ids del Comentario no coinciden.");
        }
        ComentarioEntity entity = usuarioComentariosLogic.getComentario(usuariosId,comentariosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + comentariosId + NOEXISTE, 404);

        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(usuarioComentariosLogic.updateComentario(usuariosId,comentariosId,comentario.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioComentariosResource updateComentario: output: {0}", comentarioDTO);
        return comentarioDTO;
    }
    
    @DELETE
    @Path("{comentariosId: \\d+}")
    public void deleteComentario(@PathParam("usuariosId") Long usuariosId, @PathParam("comentariosId") Long comentariosId) throws BusinessLogicException {
        ComentarioEntity entity = usuarioComentariosLogic.getComentario(usuariosId, comentariosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + comentariosId + NOEXISTE, 404);
        }
        usuarioComentariosLogic.deleteComentario(usuariosId, comentariosId);
    }
    
    private List<ComentarioDetailDTO> comentariosListEntity2DTO(List<ComentarioEntity> entityList) {
        List<ComentarioDetailDTO> list = new ArrayList<>();
        for (ComentarioEntity entity : entityList) {
            list.add(new ComentarioDetailDTO(entity));
        }
        return list;
    }
    
}
