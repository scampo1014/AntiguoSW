/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.AnuncioDTO;
import co.edu.uniandes.csw.recetas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.recetas.ejb.AnuncioLogic;
import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import co.edu.uniandes.csw.recetas.entities.AnuncioEntity;
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
@Path("proveedores/{proveedoresId: \\d+}/anuncios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class AnuncioResource {
    
    private static final Logger LOGGER = Logger.getLogger(AnuncioResource.class.getName());
    
    private static final String ELRECURSOP = "El recurso /proveedores/";
    private static final String ANUNOEXISTE = "/anuncios no existe.";
    private static final String ANU = "/anuncios/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private AnuncioLogic anuncioLogic;
    
    @Inject
    private ProveedorLogic proveedorLogic;

    /**
     * Crea una nueva anuncio con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param proveedoresId El ID del proveedor del cual se le agrega la anuncio
     * @param anuncio {@link AnuncioDTO} - La anuncio que se desea guardar.
     * @return JSON {@link AnuncioDTO} - La anuncio guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la anuncio.
     */
    @POST
    public AnuncioDTO createAnuncio(@PathParam("proveedoresId") Long proveedoresId, AnuncioDTO anuncio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "AnuncioResource createAnuncio: input: {0}", anuncio);
        ProveedorEntity entityR = proveedorLogic.getProveedor(proveedoresId);
        if (entityR == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + ANUNOEXISTE, 404);
        }
        AnuncioDTO nuevoAnuncioDTO = new AnuncioDTO(anuncioLogic.createAnuncioProveedor(proveedoresId, anuncio.toEntity()));
        LOGGER.log(Level.INFO, "AnuncioResource createAnuncio: output: {0}", nuevoAnuncioDTO);
        return nuevoAnuncioDTO;
    }

    /**
     * Busca y devuelve todas las anuncios que existen en un proveedor.
     *
     * @param proveedoresId El ID del proveedor del cual se buscan las anuncios
     * @return JSONArray {@link AnuncioDTO} - Las anuncios encontradas en el
     * proveedor. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<AnuncioDTO> getAnuncios(@PathParam("proveedoresId") Long proveedoresId) {
        LOGGER.log(Level.INFO, "AnuncioResource getAnuncios: input: {0}", proveedoresId);
        ProveedorEntity entityR = proveedorLogic.getProveedor(proveedoresId);
        if (entityR == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + ANUNOEXISTE, 404);
        }
        List<AnuncioDTO> listaDTOs = listEntity2DTO(anuncioLogic.getAnunciosProveedor(proveedoresId));
        LOGGER.log(Level.INFO, "AnuncioResource getAnuncios: output: {0}", listaDTOs);
        return listaDTOs;
    }

    /**
     * Busca y devuelve la anuncio con el ID recibido en la URL, relativa a un
     * proveedor.
     *
     * @param proveedoresId El ID del proveedor del cual se buscan las anuncios
     * @param anunciosId El ID de la anuncio que se busca
     * @return {@link AnuncioDTO} - La anuncio encontradas en el proveedor.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proveedor.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la anuncio.
     */
    @GET
    @Path("{anunciosId: \\d+}")
    public AnuncioDTO getAnuncio(@PathParam("proveedoresId") Long proveedoresId, @PathParam("anunciosId") Long anunciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "AnuncioResource getAnuncio: input: {0}", anunciosId);
        ProveedorEntity entityR = proveedorLogic.getProveedor(proveedoresId);
        if (entityR == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + ANUNOEXISTE, 404);
        }
        AnuncioEntity entity = anuncioLogic.getAnuncioByProveedor(proveedoresId, anunciosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + ANU + anunciosId + NOEXISTE, 404);
        }
        AnuncioDTO anuncioDTO = new AnuncioDTO(entity);
        LOGGER.log(Level.INFO, "AnuncioResource getAnuncio: output: {0}", anuncioDTO);
        return anuncioDTO;
    }

    /**
     * Actualiza una anuncio con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param proveedoresId El ID del proveedor del cual se guarda la anuncio
     * @param anunciosId El ID de la anuncio que se va a actualizar
     * @param anuncio {@link AnuncioDTO} - La anuncio que se desea guardar.
     * @return JSON {@link AnuncioDTO} - La anuncio actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la anuncio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la anuncio.
     */
    @PUT
    @Path("{anunciosId: \\d+}")
    public AnuncioDTO updateAnuncio(@PathParam("proveedoresId") Long proveedoresId, @PathParam("anunciosId") Long anunciosId, AnuncioDTO anuncio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "AnuncioResource updateAnuncio: input: proveedoresId: {0} , anunciosId: {1} , anuncio:{2}", new Object[]{proveedoresId, anunciosId, anuncio});
        if (anunciosId.equals(anuncio.getId())) {
            throw new BusinessLogicException("Los ids del Anuncio no coinciden.");
        }
        ProveedorEntity entityR = proveedorLogic.getProveedor(proveedoresId);
        if (entityR == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + ANUNOEXISTE, 404);
        }
        AnuncioEntity entity = anuncioLogic.getAnuncioByProveedor(proveedoresId, anunciosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + ANU + anunciosId + NOEXISTE, 404);

        }
        AnuncioDTO anuncioDTO = new AnuncioDTO(anuncioLogic.updateAnuncioProveedor(proveedoresId,anunciosId ,anuncio.toEntity()));
        LOGGER.log(Level.INFO, "AnuncioResource updateAnuncio: output:{0}", anuncioDTO);
        return anuncioDTO;

    }

    /**
     * Borra la anuncio con el id asociado recibido en la URL.
     *
     * @param proveedoresId El ID del proveedor del cual se va a eliminar la anuncio.
     * @param anunciosId El ID de la anuncio que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la anuncio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la anuncio.
     */
    @DELETE
    @Path("{anunciosId: \\d+}")
    public void deleteAnuncio(@PathParam("proveedoresId") Long proveedoresId, @PathParam("anunciosId") Long anunciosId) throws BusinessLogicException {
        ProveedorEntity entityR = proveedorLogic.getProveedor(proveedoresId);
        if (entityR == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + ANUNOEXISTE, 404);
        }
        AnuncioEntity entity = anuncioLogic.getAnuncioByProveedor(proveedoresId, anunciosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOP + proveedoresId + ANU + anunciosId + NOEXISTE, 404);
        }
        anuncioLogic.deleteAnuncioProveedor(proveedoresId, anunciosId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrizeEntity a una lista de
     * objetos AnuncioDTO (json)
     *
     * @param entityList corresponde a la lista de anuncios de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de anuncios en forma DTO (json)
     */
    private List<AnuncioDTO> listEntity2DTO(List<AnuncioEntity> entityList) {
        List<AnuncioDTO> list = new ArrayList<>();
        for (AnuncioEntity entity : entityList) {
            list.add(new AnuncioDTO(entity));
        }
        return list;
    }  
}
