/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.MetodoPagoDTO;
import co.edu.uniandes.csw.recetas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.recetas.ejb.MetodoPagoLogic;
import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import co.edu.uniandes.csw.recetas.entities.MetodoPagoEntity;
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
@Path("proveedores/{proveedoresId: \\d+}/metodosPago")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MetodoDePagoResource {
    
    private static final Logger LOGGER = Logger.getLogger(MetodoDePagoResource.class.getName());
    
    private static final String ELRECURSOP = "El recurso /proveedores/";
    private static final String RNOEXISTE = "/metodosPago no existe.";
    private static final String R = "/metodosPago/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private MetodoPagoLogic metodoPagoLogic;
    
    @Inject
    private ProveedorLogic proveedorLogic;

    /**
     * Crea una nueva metodoPago con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param proveedoresId El ID del proveedor del cual se le agrega la metodoPago
     * @param metodoPago {@link MetodoPagoDTO} - La metodoPago que se desea guardar.
     * @return JSON {@link MetodoPagoDTO} - La metodoPago guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la metodoPago.
     */
    @POST
    public MetodoPagoDTO createMetodoPago(@PathParam("proveedoresId") Long proveedoresId, MetodoPagoDTO metodoPago) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MetodoPagoResource createMetodoPago: input: {0}", metodoPago);
        ProveedorEntity entityR = proveedorLogic.getProveedor(proveedoresId);
        if (entityR == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + RNOEXISTE, 404);
        }
        MetodoPagoDTO nuevoMetodoPagoDTO = new MetodoPagoDTO(metodoPagoLogic.createMetodoPagoProveedor(proveedoresId, metodoPago.toEntity()));
        LOGGER.log(Level.INFO, "MetodoPagoResource createMetodoPago: output: {0}", nuevoMetodoPagoDTO);
        return nuevoMetodoPagoDTO;
    }

    /**
     * Busca y devuelve todas las metodosPago que existen en un proveedor.
     *
     * @param proveedoresId El ID del proveedor del cual se buscan las metodosPago
     * @return JSONArray {@link MetodoPagoDTO} - Las metodosPago encontradas en el
     * proveedor. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<MetodoPagoDTO> getMetodoPagos(@PathParam("proveedoresId") Long proveedoresId) {
        LOGGER.log(Level.INFO, "MetodoPagoResource getMetodoPagos: input: {0}", proveedoresId);
        ProveedorEntity entityR = proveedorLogic.getProveedor(proveedoresId);
        if (entityR == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + RNOEXISTE, 404);
        }
        List<MetodoPagoDTO> listaDTOs = listEntity2DTO(metodoPagoLogic.getMetodosPagoProveedor(proveedoresId));
        LOGGER.log(Level.INFO, "MetodoPagoResource getMetodoPagos: output: {0}", listaDTOs);
        return listaDTOs;
    }

    /**
     * Busca y devuelve la metodoPago con el ID recibido en la URL, relativa a un
     * proveedor.
     *
     * @param proveedoresId El ID del proveedor del cual se buscan las metodosPago
     * @param metodosPagoId El ID de la metodoPago que se busca
     * @return {@link MetodoPagoDTO} - La metodoPago encontradas en el proveedor.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proveedor.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la metodoPago.
     */
    @GET
    @Path("{metodosPagoId: \\d+}")
    public MetodoPagoDTO getMetodoPago(@PathParam("proveedoresId") Long proveedoresId, @PathParam("metodosPagoId") Long metodosPagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MetodoPagoResource getMetodoPago: input: {0}", metodosPagoId);
        ProveedorEntity entityR = proveedorLogic.getProveedor(proveedoresId);
        if (entityR == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + RNOEXISTE, 404);
        }
        MetodoPagoEntity entity = metodoPagoLogic.getMetodoPagoByProveedor(proveedoresId, metodosPagoId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + R + metodosPagoId + NOEXISTE, 404);
        }
        MetodoPagoDTO metodoPagoDTO = new MetodoPagoDTO(entity);
        LOGGER.log(Level.INFO, "MetodoPagoResource getMetodoPago: output: {0}", metodoPagoDTO);
        return metodoPagoDTO;
    }

    /**
     * Actualiza una metodoPago con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param proveedoresId El ID del proveedor del cual se guarda la metodoPago
     * @param metodosPagoId El ID de la metodoPago que se va a actualizar
     * @param metodoPago {@link MetodoPagoDTO} - La metodoPago que se desea guardar.
     * @return JSON {@link MetodoPagoDTO} - La metodoPago actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la metodoPago.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la metodoPago.
     */
    @PUT
    @Path("{metodosPagoId: \\d+}")
    public MetodoPagoDTO updateMetodoPago(@PathParam("proveedoresId") Long proveedoresId, @PathParam("metodosPagoId") Long metodosPagoId, MetodoPagoDTO metodoPago) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MetodoPagoResource updateMetodoPago: input: proveedoresId: {0} , metodosPagoId: {1} , metodoPago:{2}", new Object[]{proveedoresId, metodosPagoId, metodoPago});
        if (metodosPagoId.equals(metodoPago.getId())) {
            throw new BusinessLogicException("Los ids del MetodoPago no coinciden.");
        }
        ProveedorEntity entityR = proveedorLogic.getProveedor(proveedoresId);
        if (entityR == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + RNOEXISTE, 404);
        }
        MetodoPagoEntity entity = metodoPagoLogic.getMetodoPagoByProveedor(proveedoresId, metodosPagoId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + R + metodosPagoId + NOEXISTE, 404);

        }
        MetodoPagoDTO metodoPagoDTO = new MetodoPagoDTO(metodoPagoLogic.updateMetodoPagoProveedor(proveedoresId,metodosPagoId ,metodoPago.toEntity()));
        LOGGER.log(Level.INFO, "MetodoPagoResource updateMetodoPago: output:{0}", metodoPagoDTO);
        return metodoPagoDTO;

    }

    /**
     * Borra la metodoPago con el id asociado recibido en la URL.
     *
     * @param proveedoresId El ID del proveedor del cual se va a eliminar la metodoPago.
     * @param metodosPagoId El ID de la metodoPago que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la metodoPago.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la metodoPago.
     */
    @DELETE
    @Path("{metodosPagoId: \\d+}")
    public void deleteMetodoPago(@PathParam("proveedoresId") Long proveedoresId, @PathParam("metodosPagoId") Long metodosPagoId) throws BusinessLogicException {
        ProveedorEntity entityR = proveedorLogic.getProveedor(proveedoresId);
        if (entityR == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + RNOEXISTE, 404);
        }
        MetodoPagoEntity entity = metodoPagoLogic.getMetodoPagoByProveedor(proveedoresId, metodosPagoId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + R + metodosPagoId + NOEXISTE, 404);
        }
        metodoPagoLogic.deleteMetodoPagoProveedor(proveedoresId, metodosPagoId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrizeEntity a una lista de
     * objetos MetodoPagoDTO (json)
     *
     * @param entityList corresponde a la lista de metodosPago de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de metodosPago en forma DTO (json)
     */
    private List<MetodoPagoDTO> listEntity2DTO(List<MetodoPagoEntity> entityList) {
        List<MetodoPagoDTO> list = new ArrayList<>();
        for (MetodoPagoEntity entity : entityList) {
            list.add(new MetodoPagoDTO(entity));
        }
        return list;
    }  
}
