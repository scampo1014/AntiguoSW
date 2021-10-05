/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.CalificacionDTO;
import co.edu.uniandes.csw.recetas.ejb.CalificacionLogic;
import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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

/**
 *
 * @author Ingrith Barbosa
 */
@Path ("calificaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource {
    @Inject
    CalificacionLogic calificacionLogic;
    
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());
    
    private static final String ELRECURSO = "El recurso /calificaciones/";
    private static final String NOEXISTE = " no existe.";
    
    /**
     * Crea una nueva calificacion con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param calificacion {@link CalificacionDTO} - La calificacion que se desea
     * guardar.
     * @return JSON {@link CalificacionDTO} - La calificacion guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la calificacion.
     */
    @POST
    public CalificacionDTO createCalificacion(CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0}", calificacion);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CalificacionEntity calificacionEntity = calificacion.toEntity();
        // Invoca la lógica para crear la calificacion nueva
        CalificacionEntity nuevoCalificacionEntity = calificacionLogic.create(calificacionEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CalificacionDTO nuevoCalificacionDTO = new CalificacionDTO(nuevoCalificacionEntity);
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", nuevoCalificacionDTO);
        return nuevoCalificacionDTO;
    }

    /**
     * Busca y devuelve todas las calificaciones que existen en la aplicacion.
     *
     * @return JSONArray {@link CalificacionDetailDTO} - Las calificaciones
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CalificacionDTO> getCalificacions() {
        LOGGER.info("CalificacionResource getCalificacions: input: void");
        List<CalificacionDTO> listaCalificacions = listEntity2DTO(calificacionLogic.getCalificaciones());
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacions: output: {0}", listaCalificacions);
        return listaCalificacions;
    }

    /**
     * Busca la calificacion con el id asociado recibido en la URL y la devuelve.
     *
     * @param calificacionsId Identificador de la calificacion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CalificacionDetailDTO} - La calificacion buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @GET
    @Path("{calificacionsId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("calificacionsId") Long calificacionsId) {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionsId);
        CalificacionEntity calificacionEntity = calificacionLogic.getCalificacion(calificacionsId);
        if (calificacionEntity == null) {
            throw new WebApplicationException(ELRECURSO + calificacionsId + NOEXISTE, 404);
        }
        CalificacionDTO detailDTO = new CalificacionDTO(calificacionEntity);
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la calificacion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param calificacionsId Identificador de la calificacion que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param calificacion {@link CalificacionDetailDTO} La calificacion que se desea
     * guardar.
     * @return JSON {@link CalificacionDetailDTO} - La calificacion guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion a
     * actualizar.
     */
    @PUT
    @Path("{calificacionsId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("calificacionsId") Long calificacionsId, CalificacionDTO calificacion)throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: id:{0} , calificacion: {1}", new Object[]{calificacionsId, calificacion});
        calificacion.setId(calificacionsId);
        if (calificacionLogic.getCalificacion(calificacionsId) == null) {
            throw new WebApplicationException(ELRECURSO + calificacionsId + NOEXISTE, 404);
        }
        CalificacionDTO detailDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la calificacion con el id asociado recibido en la URL.
     *
     * @param calificacionsId Identificador de la calificacion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la calificacion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @DELETE
    @Path("{calificacionsId: \\d+}")
    public void deleteCalificacion(@PathParam("calificacionsId") Long calificacionsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource deleteCalificacion: input: {0}", calificacionsId);
        if (calificacionLogic.getCalificacion(calificacionsId) == null) {
            throw new WebApplicationException(ELRECURSO + calificacionsId + NOEXISTE, 404);
        }
        calificacionLogic.deleteCalificacion(calificacionsId);
        LOGGER.info("CalificacionResource deleteCalificacion: output: void");
    }
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CalificacionEntity a una lista de
     * objetos CalificacionDetailDTO (json)
     *
     * @param entityList corresponde a la lista de calificaciones de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de calificaciones en forma DTO (json)
     */
    private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
}
