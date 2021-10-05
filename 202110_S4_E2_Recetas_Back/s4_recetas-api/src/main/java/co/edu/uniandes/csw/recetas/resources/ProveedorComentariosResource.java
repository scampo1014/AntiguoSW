/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.recetas.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.recetas.ejb.ComentarioLogic;
import co.edu.uniandes.csw.recetas.ejb.ProveedorComentariosLogic;
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
 * @author Maria Valentina Garcia
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProveedorComentariosResource {

    private static final Logger LOGGER = Logger.getLogger(ProveedorComentariosResource.class.getName());
    private static final String ELRECURSOR = "El recurso /proveedores/";
    private static final String R = "/comentarios/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private ProveedorComentariosLogic proveedorComentariosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ComentarioLogic comentarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @POST
    public ComentarioDTO createComentario(@PathParam("proveedoresId") Long proveedoresId, ComentarioDTO comentario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProveedorComentariosResource createComentario: input: proveedoresID: {0} , comentariosId: {1}", new Object[]{proveedoresId, comentario.getId()});

        ComentarioDTO comentarioDTO = new ComentarioDTO(proveedorComentariosLogic.createComentario(proveedoresId, comentario.toEntity()));
        LOGGER.log(Level.INFO, "ProveedorComentariosResource addComentario: output: {0}", comentarioDTO);
        return comentarioDTO;
    }

    @GET
    public List<ComentarioDetailDTO> getComentarios(@PathParam("proveedoresId") Long proveedoresId) {
        LOGGER.log(Level.INFO, "ProveedorComentariosResource getComentarios: input: {0}", proveedoresId);
        List<ComentarioDetailDTO> listaDTOs = comentariosListEntity2DTO(proveedorComentariosLogic.getComentarios(proveedoresId));
        LOGGER.log(Level.INFO, "ProveedorComentariosResource getComentarios: output: {0}", listaDTOs);
        return listaDTOs;
    }

    @GET
    @Path("{comentariosId: \\d+}")
    public ComentarioDetailDTO getComentario(@PathParam("proveedoresId") Long proveedoresId, @PathParam("comentariosId") Long comentariosId){
        LOGGER.log(Level.INFO, "ProveedorComentariosResource getComentario: input: proveedoresID: {0} , comentariosId: {1}", new Object[]{proveedoresId, comentariosId});
        ComentarioEntity entity = proveedorComentariosLogic.getComentario(proveedoresId, comentariosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + R + comentariosId + NOEXISTE, 404);
        }
        ComentarioDetailDTO comentarioDTO = new ComentarioDetailDTO(entity);
        LOGGER.log(Level.INFO, "ProveedorComentariosResource getComentario: output: {0}", comentarioDTO);
        return comentarioDTO;
    }

    @PUT
    @Path("{comentariosId: \\d+}")
    public ComentarioDetailDTO updateComentario(@PathParam("proveedoresId") Long proveedoresId, @PathParam("comentariosId") Long comentariosId, ComentarioDetailDTO comentario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProveedorComentariosResource updateComentario: input: proveedoresId: {0} , comentariosId: {1}, comentario:{2}", new Object[]{proveedoresId, comentariosId, comentario});
        if (comentariosId.equals(comentario.getId())) {
            throw new BusinessLogicException("Los ids del Comentario no coinciden.");
        }
        ComentarioEntity entity = proveedorComentariosLogic.getComentario(proveedoresId, comentariosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + R + comentariosId + NOEXISTE, 404);

        }
        ComentarioDetailDTO comentarioDTO = new ComentarioDetailDTO(proveedorComentariosLogic.updateComentario(proveedoresId, comentariosId, comentario.toEntity()));
        LOGGER.log(Level.INFO, "ProveedorComentariosResource updateComentario: output: {0}", comentarioDTO);
        return comentarioDTO;
    }

    @DELETE
    @Path("{comentariosId: \\d+}")
    public void deleteComentario(@PathParam("proveedoresId") Long proveedoresId, @PathParam("comentariosId") Long comentariosId) throws BusinessLogicException {
        ComentarioEntity entity = proveedorComentariosLogic.getComentario(proveedoresId, comentariosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + R + comentariosId + NOEXISTE, 404);
        }
        proveedorComentariosLogic.deleteComentario(proveedoresId, comentariosId);
    }
    @Path("{comentariosId: \\d+}/calificaciones")
    public Class<ComentarioCalificacionesResource> getComentarioCalificacionesResource(@PathParam("comentariosId") Long comentariosId) {
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException("El recurso /comentarios/" + comentariosId + NOEXISTE, 404);
        }
        return ComentarioCalificacionesResource.class;
    }

    private List<ComentarioDetailDTO> comentariosListEntity2DTO(List<ComentarioEntity> entityList) {
        List<ComentarioDetailDTO> list = new ArrayList<>();
        for (ComentarioEntity entity : entityList) {
            list.add(new ComentarioDetailDTO(entity));
        }
        return list;
    }


}
