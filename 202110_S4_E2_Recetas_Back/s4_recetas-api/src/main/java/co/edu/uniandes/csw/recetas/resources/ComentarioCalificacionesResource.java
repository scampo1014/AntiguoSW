/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.CalificacionDTO;
import co.edu.uniandes.csw.recetas.ejb.CalificacionLogic;
import co.edu.uniandes.csw.recetas.ejb.ComentarioCalificacionesLogic;
import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ComentarioCalificacionesResource {
    private static final Logger LOGGER = Logger.getLogger(ComentarioCalificacionesResource.class.getName());
    private static final String ELRECURSOR = "El recurso /comentarios/";
    private static final String R = "/calificaciones/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private ComentarioCalificacionesLogic comentarioCalificacionesLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @POST
    public CalificacionDTO createCalificacion(@PathParam("comentariosId") Long comentariosId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ComentarioCalificacionesResource createCalificacion: input: comentariosID: {0} , calificacionesId: {1}", new Object[]{comentariosId, calificacion.getId()});

        CalificacionDTO calificacionDTO = new CalificacionDTO(comentarioCalificacionesLogic.createCalificacion(comentariosId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "ComentarioCalificacionesResource addCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }

    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("comentariosId") Long comentariosId) {
        LOGGER.log(Level.INFO, "ComentarioCalificacionesResource getCalificaciones: input: {0}", comentariosId);
        List<CalificacionDTO> listaDTOs = calificacionesListEntity2DTO(comentarioCalificacionesLogic.getCalificaciones(comentariosId));
        LOGGER.log(Level.INFO, "ComentarioCalificacionesResource getCalificaciones: output: {0}", listaDTOs);
        return listaDTOs;
    }

    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("comentariosId") Long comentariosId, @PathParam("calificacionId") Long calificacionesId) {
        LOGGER.log(Level.INFO, "ComentarioCalificacionesResource getCalificacion: input: comentariosID: {0} , calificacionId: {1}", new Object[]{comentariosId, calificacionesId});
        CalificacionEntity entity = comentarioCalificacionesLogic.getCalificacion(comentariosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + comentariosId + R + calificacionesId + NOEXISTE, 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(entity);
        LOGGER.log(Level.INFO, "ComentarioCalificacionesResource getCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }

    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("comentariosId") Long comentariosId, @PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ComentarioCalificacionesResource updateCalificacion: input: comentariosId: {0} , calificacionesId: {1}, calificacion:{2}", new Object[]{comentariosId, calificacionesId, calificacion});
        if (calificacionesId.equals(calificacion.getId())) {
            throw new BusinessLogicException("Los ids del Calificacion no coinciden.");
        }
        CalificacionEntity entity = comentarioCalificacionesLogic.getCalificacion(comentariosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + comentariosId + R + calificacionesId + NOEXISTE, 404);

        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(comentarioCalificacionesLogic.updateCalificacion(comentariosId, calificacionesId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "ComentarioCalificacionesResource updateCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }

    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacion(@PathParam("comentariosId") Long comentariosId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity entity = comentarioCalificacionesLogic.getCalificacion(comentariosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + comentariosId + R + calificacionesId + NOEXISTE, 404);
        }
        comentarioCalificacionesLogic.deleteCalificacion(comentariosId, calificacionesId);
    }

    private List<CalificacionDTO> calificacionesListEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }

}
