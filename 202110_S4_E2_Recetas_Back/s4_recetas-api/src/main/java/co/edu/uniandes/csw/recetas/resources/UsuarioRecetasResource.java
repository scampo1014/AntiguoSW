/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.RecetaDTO;
import co.edu.uniandes.csw.recetas.dtos.RecetaDetailDTO;
import co.edu.uniandes.csw.recetas.ejb.UsuarioRecetasLogic;
import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
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
public class UsuarioRecetasResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioRecetasResource.class.getName());
    private static final String ELRECURSOR = "El recurso /usuarios/";
    private static final String R = "/recetas/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private UsuarioRecetasLogic usuarioRecetasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @POST
    public RecetaDTO createReceta(@PathParam("usuariosId") Long usuariosId, RecetaDTO receta) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioRecetasResource createReceta: input: usuariosID: {0} , recetaId: {1}", new Object[]{usuariosId, receta.getId()});

        RecetaDTO recetaDTO = new RecetaDTO(usuarioRecetasLogic.createReceta(usuariosId, receta.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioRecetasResource addReceta: output: {0}", recetaDTO);
        return recetaDTO;
    }

    @GET
    public List<RecetaDetailDTO> getRecetas(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioRecetasResource getRecetas: input: {0}", usuariosId);
        List<RecetaDetailDTO> listaDTOs = recetasListEntity2DTO((List<RecetaEntity>) usuarioRecetasLogic.getRecetas(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioRecetasResource getRecetas: output: {0}", listaDTOs);
        return listaDTOs;
    }

    @GET
    @Path("{recetaId: \\d+}")
    public RecetaDetailDTO getReceta(@PathParam("usuariosId") Long usuariosId, @PathParam("recetaId") Long recetasId){
        LOGGER.log(Level.INFO, "UsuarioRecetasResource getReceta: input: usuariosID: {0} , recetaId: {1}", new Object[]{usuariosId, recetasId});
        RecetaEntity entity = usuarioRecetasLogic.getReceta(usuariosId, recetasId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + recetasId + NOEXISTE, 404);
        }
        RecetaDetailDTO recetaDTO = new RecetaDetailDTO(entity);
        LOGGER.log(Level.INFO, "UsuarioRecetasResource getReceta: output: {0}", recetaDTO);
        return recetaDTO;
    }

    @PUT
    @Path("{recetasId: \\d+}")
    public RecetaDTO updateReceta(@PathParam("usuariosId") Long usuariosId, @PathParam("recetasId") Long recetasId, RecetaDTO receta) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioRecetasResource updateReceta: input: usuariosId: {0} , recetasId: {1}, receta:{2}", new Object[]{usuariosId, recetasId, receta});
        if (recetasId.equals(receta.getId())) {
            throw new BusinessLogicException("Los ids del Receta no coinciden.");
        }
        RecetaEntity entity = usuarioRecetasLogic.getReceta(usuariosId, recetasId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + recetasId + NOEXISTE, 404);

        }
        RecetaDTO recetaDTO = new RecetaDTO(usuarioRecetasLogic.updateReceta(usuariosId, recetasId, receta.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioRecetasResource updateReceta: output: {0}", recetaDTO);
        return recetaDTO;
    }

    @DELETE
    @Path("{recetasId: \\d+}")
    public void deleteReceta(@PathParam("usuariosId") Long usuariosId, @PathParam("recetasId") Long recetasId) throws BusinessLogicException {
        RecetaEntity entity = usuarioRecetasLogic.getReceta(usuariosId, recetasId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + recetasId + NOEXISTE, 404);
        }
        usuarioRecetasLogic.deleteReceta(usuariosId, recetasId);
    }
    
    @Path("{recetasId: \\d+}/ingredientes")
    public Class<IngredienteResource> getIngredientesResource(@PathParam("usuariosId") Long usuariosId,@PathParam("recetasId") Long recetasId) {
        if (usuarioRecetasLogic.getReceta(usuariosId,recetasId) == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + recetasId + NOEXISTE, 404);
        }
        return IngredienteResource.class;
    }
    
    @Path("{recetasId: \\d+}/utensilios")
    public Class<UtensilioResource> getUtensiliosResource(@PathParam("usuariosId") Long usuariosId,@PathParam("recetasId") Long recetasId) {
        if (usuarioRecetasLogic.getReceta(usuariosId,recetasId) == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + R + recetasId + NOEXISTE, 404);
        }
        return UtensilioResource.class;
    }

    private List<RecetaDetailDTO> recetasListEntity2DTO(List<RecetaEntity> entityList) {
        List<RecetaDetailDTO> list = new ArrayList<>();
        for (RecetaEntity entity : entityList) {
            list.add(new RecetaDetailDTO(entity));
        }
        return list;
    }

}
