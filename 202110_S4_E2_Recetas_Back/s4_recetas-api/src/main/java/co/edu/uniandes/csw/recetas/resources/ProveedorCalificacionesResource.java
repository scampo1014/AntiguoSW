/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.CalificacionDTO;
import co.edu.uniandes.csw.recetas.ejb.CalificacionLogic;
import co.edu.uniandes.csw.recetas.ejb.ProveedorCalificacionesLogic;
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
 * @author Maria Valentina Garcia
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProveedorCalificacionesResource {

    private static final Logger LOGGER = Logger.getLogger(ProveedorCalificacionesResource.class.getName());
    private static final String ELRECURSOR = "El recurso /proveedores/";
    private static final String R = "/calificaciones/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private ProveedorCalificacionesLogic proveedorCalificacionesLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private CalificacionLogic calificacionLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @POST
    public CalificacionDTO createCalificacion(@PathParam("proveedoresId") Long proveedoresId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProveedorCalificacionesResource createCalificacion: input: proveedoresID: {0} , calificacionesId: {1}", new Object[]{proveedoresId, calificacion.getId()});

        CalificacionDTO calificacionDTO = new CalificacionDTO(proveedorCalificacionesLogic.createCalificacion(proveedoresId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "ProveedorCalificacionesResource addCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }

    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("proveedoresId") Long proveedoresId) {
        LOGGER.log(Level.INFO, "ProveedorCalificacionesResource getCalificaciones: input: {0}", proveedoresId);
        List<CalificacionDTO> listaDTOs = calificacionesListEntity2DTO( proveedorCalificacionesLogic.getCalificaciones(proveedoresId));
        LOGGER.log(Level.INFO, "ProveedorCalificacionesResource getCalificaciones: output: {0}", listaDTOs);
        return listaDTOs;
    }

    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("proveedoresId") Long proveedoresId, @PathParam("calificacionId") Long calificacionesId){
        LOGGER.log(Level.INFO, "ProveedorCalificacionesResource getCalificacion: input: proveedoresID: {0} , calificacionId: {1}", new Object[]{proveedoresId, calificacionesId});
        CalificacionEntity entity = proveedorCalificacionesLogic.getCalificacion(proveedoresId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + R + calificacionesId + NOEXISTE, 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(entity);
        LOGGER.log(Level.INFO, "ProveedorCalificacionesResource getCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }

    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("proveedoresId") Long proveedoresId, @PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProveedorCalificacionesResource updateCalificacion: input: proveedoresId: {0} , calificacionesId: {1}, calificacion:{2}", new Object[]{proveedoresId, calificacionesId, calificacion});
        if (calificacionesId.equals(calificacion.getId())) {
            throw new BusinessLogicException("Los ids del Calificacion no coinciden.");
        }
        CalificacionEntity entity = proveedorCalificacionesLogic.getCalificacion(proveedoresId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + R + calificacionesId + NOEXISTE, 404);

        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(proveedorCalificacionesLogic.updateCalificacion(proveedoresId, calificacionesId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "ProveedorCalificacionesResource updateCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }

    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacion(@PathParam("proveedoresId") Long proveedoresId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity entity = proveedorCalificacionesLogic.getCalificacion(proveedoresId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + R + calificacionesId + NOEXISTE, 404);
        }
        proveedorCalificacionesLogic.deleteCalificacion(proveedoresId, calificacionesId);
    }

    private List<CalificacionDTO> calificacionesListEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }

}
