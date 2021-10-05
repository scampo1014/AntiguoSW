/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.CalificacionDTO;
import co.edu.uniandes.csw.recetas.ejb.CalificacionLogic;
import co.edu.uniandes.csw.recetas.ejb.UsuarioCalificacionesLogic;
import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
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
public class UsuarioCalificacionesResource {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioCalificacionesResource.class.getName());
    private static final String ELRECURSOR = "El recurso /usuarios/";
    private static final String R = "/calificaciones/";
    private static final String NOEXISTE = " no existe.";
    
    @Inject
    private UsuarioCalificacionesLogic usuarioCalificacionesLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private CalificacionLogic calificacionLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @POST
    public CalificacionDTO createCalificacion(@PathParam("usuariosId") Long usuariosId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource createCalificacion: input: usuariosID: {0} , calificacionesId: {1}", new Object[]{usuariosId, calificacion.getId()});
        
        CalificacionDTO calificacionDTO = new CalificacionDTO(usuarioCalificacionesLogic.createCalificacion(usuariosId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource addCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource getCalificaciones: input: {0}", usuariosId);
        List<CalificacionDTO> listaDTOs = calificacionesListEntity2DTO( usuarioCalificacionesLogic.getCalificaciones(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource getCalificaciones: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("usuariosId") Long usuariosId, @PathParam("calificacionesId") Long calificacionesId) {
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource getCalificaciones: input: usuariosID: {0} , calificacionesId: {1}", new Object[]{usuariosId, calificacionesId});
        CalificacionEntity entity=usuarioCalificacionesLogic.getCalificacion(usuariosId,calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + calificacionesId + NOEXISTE, 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(entity);
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource getCalificaciones: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    
    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("usuariosId") Long usuariosId, @PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource updateCalificacion: input: usuariosId: {0} , calificacionesId: {1}, calificacion:{2}", new Object[]{usuariosId, calificacionesId, calificacion});
        if (calificacionesId.equals(calificacion.getId())) {
            throw new BusinessLogicException("Los ids del Calificacion no coinciden.");
        }
        CalificacionEntity entity = usuarioCalificacionesLogic.getCalificacion(usuariosId,calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + calificacionesId + NOEXISTE, 404);

        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(usuarioCalificacionesLogic.updateCalificacion(usuariosId,calificacionesId,calificacion.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource updateCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    
    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacion(@PathParam("usuariosId") Long usuariosId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity entity = usuarioCalificacionesLogic.getCalificacion(usuariosId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + calificacionesId + NOEXISTE, 404);
        }
        usuarioCalificacionesLogic.deleteCalificacion(usuariosId, calificacionesId);
    }
    
    private List<CalificacionDTO> calificacionesListEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
    
}
