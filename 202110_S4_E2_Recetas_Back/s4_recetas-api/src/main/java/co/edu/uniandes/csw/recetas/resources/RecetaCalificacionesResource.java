/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.CalificacionDTO;
import co.edu.uniandes.csw.recetas.ejb.CalificacionLogic;
import co.edu.uniandes.csw.recetas.ejb.RecetaCalificacionesLogic;
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
public class RecetaCalificacionesResource {
    
    private static final Logger LOGGER = Logger.getLogger(RecetaCalificacionesResource.class.getName());
    private static final String ELRECURSOR = "El recurso /recetas/";
    private static final String R = "/calificaciones/";
    private static final String NOEXISTE = " no existe.";
    
    @Inject
    private RecetaCalificacionesLogic recetaCalificacionesLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private CalificacionLogic calificacionLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @POST
    public CalificacionDTO createCalificacion(@PathParam("recetasId") Long recetasId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaCalificacionesResource createCalificacion: input: recetasID: {0} , calificacionesId: {1}", new Object[]{recetasId, calificacion.getId()});
        
        CalificacionDTO calificacionDTO = new CalificacionDTO(recetaCalificacionesLogic.createCalificacion(recetasId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "RecetaCalificacionesResource addCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("recetasId") Long recetasId) {
        LOGGER.log(Level.INFO, "RecetaCalificacionesResource getCalificaciones: input: {0}", recetasId);
        List<CalificacionDTO> listaDTOs = calificacionesListEntity2DTO(recetaCalificacionesLogic.getCalificaciones(recetasId));
        LOGGER.log(Level.INFO, "RecetaCalificacionesResource getCalificaciones: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("recetasId") Long recetasId, @PathParam("calificacionesId") Long calificacionesId){
        LOGGER.log(Level.INFO, "RecetaCalificacionesResource getCalificaciones: input: recetasID: {0} , calificacionesId: {1}", new Object[]{recetasId, calificacionesId});
        CalificacionEntity entity=recetaCalificacionesLogic.getCalificacion(recetasId,calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + calificacionesId + NOEXISTE, 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(entity);
        LOGGER.log(Level.INFO, "RecetaCalificacionesResource getCalificaciones: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    
    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("recetasId") Long recetasId, @PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaCalificacionesResource updateCalificacion: input: recetasId: {0} , calificacionesId: {1}, calificacion:{2}", new Object[]{recetasId, calificacionesId, calificacion});
        if (calificacionesId.equals(calificacion.getId())) {
            throw new BusinessLogicException("Los ids del Calificacion no coinciden.");
        }
        CalificacionEntity entity = recetaCalificacionesLogic.getCalificacion(recetasId,calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + calificacionesId + NOEXISTE, 404);

        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(recetaCalificacionesLogic.updateCalificacion(recetasId,calificacionesId,calificacion.toEntity()));
        LOGGER.log(Level.INFO, "RecetaCalificacionesResource updateCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    
    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacion(@PathParam("recetasId") Long recetasId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity entity = recetaCalificacionesLogic.getCalificacion(recetasId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + calificacionesId + NOEXISTE, 404);
        }
        recetaCalificacionesLogic.deleteCalificacion(recetasId, calificacionesId);
    }
    
    private List<CalificacionDTO> calificacionesListEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
    
}
