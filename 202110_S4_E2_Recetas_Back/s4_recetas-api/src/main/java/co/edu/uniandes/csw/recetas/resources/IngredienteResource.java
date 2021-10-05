/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.IngredienteDTO;
import co.edu.uniandes.csw.recetas.ejb.IngredienteLogic;
import co.edu.uniandes.csw.recetas.entities.IngredienteEntity;
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
public class IngredienteResource {
    
    private static final Logger LOGGER = Logger.getLogger(IngredienteResource.class.getName());
    private static final String ELRECURSO = "El recurso /recetas/";
    private static final String R = "/ingredientes/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private IngredienteLogic ingredienteLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @POST
    public IngredienteDTO createIngrediente(@PathParam("recetasId") Long recetasId, IngredienteDTO ingrediente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaIngredientesResource createIngrediente: input: recetasID: {0} , ingredientesId: {1}", new Object[]{recetasId, ingrediente.getId()});
        
        IngredienteDTO ingredienteDTO = new IngredienteDTO(ingredienteLogic.createIngredienteReceta(recetasId, ingrediente.toEntity()));
        LOGGER.log(Level.INFO, "RecetaIngredientesResource addIngrediente: output: {0}", ingredienteDTO);
        return ingredienteDTO;
    }
    
    @GET
    public List<IngredienteDTO> getIngredientes(@PathParam("recetasId") Long recetasId) {
        LOGGER.log(Level.INFO, "RecetaIngredientesResource getIngredientes: input: {0}", recetasId);
        List<IngredienteDTO> listaDTOs = ingredientesListEntity2DTO(ingredienteLogic.getIngredientesReceta(recetasId));
        LOGGER.log(Level.INFO, "RecetaIngredientesResource getIngredientes: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    @GET
    @Path("{ingredientesId: \\d+}")
    public IngredienteDTO getIngrediente(@PathParam("recetasId") Long recetasId, @PathParam("ingredientesId") Long ingredientesId){
        LOGGER.log(Level.INFO, "RecetaIngredientesResource getIngredientes: input: recetasID: {0} , ingredientesId: {1}", new Object[]{recetasId, ingredientesId});
        IngredienteEntity entity=ingredienteLogic.getIngredienteByReceta(recetasId,ingredientesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSO + recetasId + R + ingredientesId + NOEXISTE, 404);
        }
        IngredienteDTO ingredienteDTO = new IngredienteDTO(entity);
        LOGGER.log(Level.INFO, "RecetaIngredientesResource getIngredientes: output: {0}", ingredienteDTO);
        return ingredienteDTO;
    }
    
    @PUT
    @Path("{ingredientesId: \\d+}")
    public IngredienteDTO updateIngrediente(@PathParam("recetasId") Long recetasId, @PathParam("ingredientesId") Long ingredientesId, IngredienteDTO ingrediente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaIngredientesResource updateIngrediente: input: recetasId: {0} , ingredientesId: {1}, ingrediente:{2}", new Object[]{recetasId, ingredientesId, ingrediente});
        if (ingredientesId.equals(ingrediente.getId())) {
            throw new BusinessLogicException("Los ids del Ingrediente no coinciden.");
        }
        IngredienteEntity entity = ingredienteLogic.getIngredienteByReceta(recetasId,ingredientesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSO + recetasId + R + ingredientesId + NOEXISTE, 404);

        }
        IngredienteDTO ingredienteDTO = new IngredienteDTO(ingredienteLogic.updateIngredienteReceta(recetasId,ingredientesId,ingrediente.toEntity()));
        LOGGER.log(Level.INFO, "RecetaIngredientesResource updateIngrediente: output: {0}", ingredienteDTO);
        return ingredienteDTO;
    }
    
    @DELETE
    @Path("{ingredientesId: \\d+}")
    public void deleteIngrediente(@PathParam("recetasId") Long recetasId, @PathParam("ingredientesId") Long ingredientesId) throws BusinessLogicException {
        IngredienteEntity entity = ingredienteLogic.getIngredienteByReceta(recetasId, ingredientesId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSO + recetasId + R + ingredientesId + NOEXISTE, 404);
        }
        ingredienteLogic.deleteIngredienteReceta(recetasId, ingredientesId);
    }
    
    private List<IngredienteDTO> ingredientesListEntity2DTO(List<IngredienteEntity> entityList) {
        List<IngredienteDTO> list = new ArrayList<>();
        for (IngredienteEntity entity : entityList) {
            list.add(new IngredienteDTO(entity));
        }
        return list;
    }
    
}
