/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.RecetaDTO;
import co.edu.uniandes.csw.recetas.dtos.RecetaDetailDTO;
import co.edu.uniandes.csw.recetas.ejb.RecetaLogic;
import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
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
@Path ("recetas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class RecetaResource {
    @Inject
    RecetaLogic recetaLogic;
    private static final Logger LOGGER = Logger.getLogger(RecetaResource.class.getName());
    private static final String ELRECURSOR = "El recurso /recetas/";
    private static final String NOEXISTE = " no existe.";
    
    /**
     * Crea una nueva receta con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param receta {@link RecetaDTO} - La receta que se desea
     * guardar.
     * @return JSON {@link RecetaDTO} - La receta guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la receta.
     */
    @POST
    public RecetaDTO createReceta(RecetaDTO receta) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaResource createReceta: input: {0}", receta);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        RecetaEntity recetaEntity = receta.toEntity();
        // Invoca la lógica para crear la receta nueva
        RecetaEntity nuevoRecetaEntity = recetaLogic.create(recetaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        RecetaDTO nuevoRecetaDTO = new RecetaDTO(nuevoRecetaEntity);
        LOGGER.log(Level.INFO, "RecetaResource createReceta: output: {0}", nuevoRecetaDTO);
        return nuevoRecetaDTO;
    }

    /**
     * Busca y devuelve todas las recetaes que existen en la aplicacion.
     *
     * @return JSONArray {@link RecetaDetailDTO} - Las recetaes
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<RecetaDetailDTO> getRecetas() {
        LOGGER.info("RecetaResource getRecetas: input: void");
        List<RecetaDetailDTO> listaRecetas = listEntity2DetailDTO(recetaLogic.getRecetas());
        LOGGER.log(Level.INFO, "RecetaResource getRecetas: output: {0}", listaRecetas);
        return listaRecetas;
    }

    /**
     * Busca la receta con el id asociado recibido en la URL y la devuelve.
     *
     * @param recetasId Identificador de la receta que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link RecetaDetailDTO} - La receta buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la receta.
     */
    @GET
    @Path("{recetasId: \\d+}")
    public RecetaDetailDTO getReceta(@PathParam("recetasId") Long recetasId) {
        LOGGER.log(Level.INFO, "RecetaResource getReceta: input: {0}", recetasId);
        RecetaEntity recetaEntity = recetaLogic.getReceta(recetasId);
        if (recetaEntity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + NOEXISTE, 404);
        }
        RecetaDetailDTO detailDTO = new RecetaDetailDTO(recetaEntity);
        LOGGER.log(Level.INFO, "RecetaResource getReceta: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la receta con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param recetasId Identificador de la receta que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param receta {@link RecetaDetailDTO} La receta que se desea
     * guardar.
     * @return JSON {@link RecetaDetailDTO} - La receta guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la receta a
     * actualizar.
     */
    @PUT
    @Path("{recetasId: \\d+}")
    public RecetaDetailDTO updateReceta(@PathParam("recetasId") Long recetasId, RecetaDetailDTO receta) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaResource updateReceta: input: id:{0} , receta: {1}", new Object[]{recetasId, receta});
        receta.setId(recetasId);
        if (recetaLogic.getReceta(recetasId) == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + NOEXISTE, 404);
        }
        RecetaDetailDTO detailDTO = new RecetaDetailDTO(recetaLogic.updateReceta(receta.toEntity()));
        LOGGER.log(Level.INFO, "RecetaResource updateReceta: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la receta con el id asociado recibido en la URL.
     *
     * @param recetasId Identificador de la receta que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la receta.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la receta.
     */
    @DELETE
    @Path("{recetasId: \\d+}")
    public void deleteReceta(@PathParam("recetasId") Long recetasId){
        LOGGER.log(Level.INFO, "RecetaResource deleteReceta: input: {0}", recetasId);
        if (recetaLogic.getReceta(recetasId) == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + NOEXISTE, 404);
        }
        recetaLogic.deleteReceta(recetasId);
        LOGGER.info("RecetaResource deleteReceta: output: void");
    }
    
    @Path("{recetasId: \\d+}/comentarios")
    public Class<RecetaComentariosResource> getRecetaComentariosResource(@PathParam("recetasId") Long recetasId) {
        if (recetaLogic.getReceta(recetasId) == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + NOEXISTE, 404);
        }
        return RecetaComentariosResource.class;
    }
    @Path("{recetasId: \\d+}/calificaciones")
    public Class<RecetaCalificacionesResource> getRecetaCalificacionesResource(@PathParam("recetasId") Long recetasId) {
        if (recetaLogic.getReceta(recetasId) == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + NOEXISTE, 404);
        }
        return RecetaCalificacionesResource.class;
    }
    
    @Path("{recetasId: \\d+}/fotos")
    public Class<RecetaFotosResource> getRecetaFotosResource(@PathParam("recetasId") Long recetasId) {
        if (recetaLogic.getReceta(recetasId) == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + NOEXISTE, 404);
        }
        return RecetaFotosResource.class;
    }
    
    @Path("{recetasId: \\d+}/videos")
    public Class<RecetaVideosResource> getRecetaVideosResource(@PathParam("recetasId") Long recetasId) {
        if (recetaLogic.getReceta(recetasId) == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + NOEXISTE, 404);
        }
        return RecetaVideosResource.class;
    }
    
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos RecetaEntity a una lista de
     * objetos RecetaDetailDTO (json)
     *
     * @param entityList corresponde a la lista de recetaes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de recetaes en forma DTO (json)
     */
    private List<RecetaDetailDTO> listEntity2DetailDTO(List<RecetaEntity> entityList) {
        List<RecetaDetailDTO> list = new ArrayList<>();
        for (RecetaEntity entity : entityList) {
            list.add(new RecetaDetailDTO(entity));
        }
        return list;
    }
}
