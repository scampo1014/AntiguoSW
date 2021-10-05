/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.ProveedorDTO;
import co.edu.uniandes.csw.recetas.dtos.ProveedorDetailDTO;
import co.edu.uniandes.csw.recetas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
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
 * @author Maria Valentina Garcia
 */
@Path("proveedores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ProveedorResource {

    private static final Logger LOGGER = Logger.getLogger(ProveedorResource.class.getName());
    private static final String ELRECURSOR = "El recurso /proveedores/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private ProveedorLogic proveedorLogic;

    @POST
    public ProveedorDTO createProveedor(ProveedorDTO proveedor) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProveedorResource createProveedor: input: {0}", proveedor);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ProveedorEntity proveedorEntity = proveedor.toEntity();
        // Invoca la lógica para crear el proveedor nuevo
        ProveedorEntity nuevoProveedorEntity = proveedorLogic.createProveedor(proveedorEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ProveedorDTO nuevoProveedorDTO = new ProveedorDTO(nuevoProveedorEntity);
        LOGGER.log(Level.INFO, "ProveedorResource createProveedor: output: {0}", nuevoProveedorDTO);
        return nuevoProveedorDTO;
    }

    @GET
    public List<ProveedorDetailDTO> getProveedores() {
        LOGGER.info("ProveedorResource getProveedors: input: void");
        List<ProveedorDetailDTO> listaProveedors = listEntity2DetailDTO(proveedorLogic.getProveedores());
        LOGGER.log(Level.INFO, "ProveedorResource getProveedors: output: {0}", listaProveedors);
        return listaProveedors;
    }

    /**
     * Busca la proveedor con el id asociado recibido en la URL y la devuelve.
     *
     * @param proveedoresId Identificador de la proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ProveedorDetailDTO} - La proveedor buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la proveedor.
     */
    @GET
    @Path("{proveedoresId: \\d+}")
    public ProveedorDetailDTO getProveedor(@PathParam("proveedoresId") Long proveedoresId) {
        LOGGER.log(Level.INFO, "ProveedorResource getProveedor: input: {0}", proveedoresId);
        ProveedorEntity proveedorEntity = proveedorLogic.getProveedor(proveedoresId);
        if (proveedorEntity == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + NOEXISTE, 404);
        }
        ProveedorDetailDTO detailDTO = new ProveedorDetailDTO(proveedorEntity);
        LOGGER.log(Level.INFO, "ProveedorResource getProveedor: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la proveedor con el id recibido en la URL con la informacion que
     * se recibe en el cuerpo de la petición.
     *
     * @param proveedoresId Identificador de la proveedor que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @param proveedor {@link ProveedorDetailDTO} La proveedor que se desea guardar.
     * @return JSON {@link ProveedorDetailDTO} - La proveedor guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la proveedor a
     * actualizar.
     */
    @PUT
    @Path("{proveedoresId: \\d+}")
    public ProveedorDetailDTO updateProveedor(@PathParam("proveedoresId") Long proveedoresId, ProveedorDetailDTO proveedor) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProveedorResource updateProveedor: input: id:{0} , proveedor: {1}", new Object[]{proveedoresId, proveedor});
        proveedor.setId(proveedoresId);
        if (proveedorLogic.getProveedor(proveedoresId) == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + NOEXISTE, 404);
        }
        ProveedorDetailDTO detailDTO = new ProveedorDetailDTO(proveedorLogic.updateProveedor(proveedoresId, proveedor.toEntity()));
        LOGGER.log(Level.INFO, "ProveedorResource updateProveedor: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la proveedor con el id asociado recibido en la URL.
     *
     * @param proveedoresId Identificador de la proveedor que se desea borrar. Este
     * debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la proveedor.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la proveedor.
     */
    @DELETE
    @Path("{proveedoresId: \\d+}")
    public void deleteProveedor(@PathParam("proveedoresId") Long proveedoresId){
        LOGGER.log(Level.INFO, "ProveedorResource deleteProveedor: input: {0}", proveedoresId);
        if (proveedorLogic.getProveedor(proveedoresId) == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + NOEXISTE, 404);
        }
        proveedorLogic.deleteProveedor(proveedoresId);
        LOGGER.info("ProveedorResource deleteProveedor: output: void");
    }

    /**
     * Conexión con el servicio de libros para una proveedor.
     * {@link ProveedorBooksResource}
     *
     * Este método conecta la ruta de /proveedores con las rutas de /books que
     * dependen de la proveedor, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los libros de una proveedor.
     *
     * @param proveedoresId El ID de la proveedor con respecto a la cual se accede al
     * servicio.
     * @return El servicio de libros para esta proveedor en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la proveedor.
     */

    @Path("{proveedoresId: \\d+}/calificaciones")
    public Class<ProveedorCalificacionesResource> getProveedorCalificacionesResource(@PathParam("proveedoresId") Long proveedoresId) {
        if (proveedorLogic.getProveedor(proveedoresId) == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + NOEXISTE, 404);
        }
        return ProveedorCalificacionesResource.class;
    }

    @Path("{proveedoresId: \\d+}/comentarios")
    public Class<ProveedorComentariosResource> getProveedorComentariosResource(@PathParam("proveedoresId") Long proveedoresId) {
        if (proveedorLogic.getProveedor(proveedoresId) == null) {
            throw new WebApplicationException(ELRECURSOR + proveedoresId + NOEXISTE, 404);
        }
        return ProveedorComentariosResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ProveedorEntity a una lista de
     * objetos ProveedorDetailDTO (json)
     *
     * @param entityList corresponde a la lista de proveedores de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de proveedores en forma DTO (json)
     */
    private List<ProveedorDetailDTO> listEntity2DetailDTO(List<ProveedorEntity> entityList) {
        List<ProveedorDetailDTO> list = new ArrayList<>();
        for (ProveedorEntity entity : entityList) {
            list.add(new ProveedorDetailDTO(entity));
        }
        return list;
    }
}
