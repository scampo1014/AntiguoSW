/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.UtensilioDTO;
import co.edu.uniandes.csw.recetas.ejb.UtensilioLogic;
import co.edu.uniandes.csw.recetas.entities.UtensilioEntity;
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
public class UtensilioResource {
    
    private static final Logger LOGGER = Logger.getLogger(UtensilioResource.class.getName());
    private static final String ELRECURSO = "El recurso /recetas/";
    private static final String R = "/utensilios/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private UtensilioLogic utensilioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @POST
    public UtensilioDTO createUtensilio(@PathParam("recetasId") Long recetasId, UtensilioDTO utensilio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaUtensiliosResource createUtensilio: input: recetasID: {0} , utensiliosId: {1}", new Object[]{recetasId, utensilio.getId()});
        
        UtensilioDTO utensilioDTO = new UtensilioDTO(utensilioLogic.createUtensilioReceta(recetasId, utensilio.toEntity()));
        LOGGER.log(Level.INFO, "RecetaUtensiliosResource addUtensilio: output: {0}", utensilioDTO);
        return utensilioDTO;
    }
    
    @GET
    public List<UtensilioDTO> getUtensilios(@PathParam("recetasId") Long recetasId) {
        LOGGER.log(Level.INFO, "RecetaUtensiliosResource getUtensilios: input: {0}", recetasId);
        List<UtensilioDTO> listaDTOs = utensiliosListEntity2DTO(utensilioLogic.getUtensiliosReceta(recetasId));
        LOGGER.log(Level.INFO, "RecetaUtensiliosResource getUtensilios: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    @GET
    @Path("{utensiliosId: \\d+}")
    public UtensilioDTO getUtensilio(@PathParam("recetasId") Long recetasId, @PathParam("utensiliosId") Long utensiliosId) {
        LOGGER.log(Level.INFO, "RecetaUtensiliosResource getUtensilios: input: recetasID: {0} , utensiliosId: {1}", new Object[]{recetasId, utensiliosId});
        UtensilioEntity entity=utensilioLogic.getUtensilioByReceta(recetasId,utensiliosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSO + recetasId + R + utensiliosId + NOEXISTE, 404);
        }
        UtensilioDTO utensilioDTO = new UtensilioDTO(entity);
        LOGGER.log(Level.INFO, "RecetaUtensiliosResource getUtensilios: output: {0}", utensilioDTO);
        return utensilioDTO;
    }
    
    @PUT
    @Path("{utensiliosId: \\d+}")
    public UtensilioDTO updateUtensilio(@PathParam("recetasId") Long recetasId, @PathParam("utensiliosId") Long utensiliosId, UtensilioDTO utensilio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaUtensiliosResource updateUtensilio: input: recetasId: {0} , utensiliosId: {1}, utensilio:{2}", new Object[]{recetasId, utensiliosId, utensilio});
        if (utensiliosId.equals(utensilio.getId())) {
            throw new BusinessLogicException("Los ids del Utensilio no coinciden.");
        }
        UtensilioEntity entity = utensilioLogic.getUtensilioByReceta(recetasId,utensiliosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSO + recetasId + R + utensiliosId + NOEXISTE, 404);

        }
        UtensilioDTO utensilioDTO = new UtensilioDTO(utensilioLogic.updateUtensilioReceta(recetasId,utensiliosId,utensilio.toEntity()));
        LOGGER.log(Level.INFO, "RecetaUtensiliosResource updateUtensilio: output: {0}", utensilioDTO);
        return utensilioDTO;
    }
    
    @DELETE
    @Path("{utensiliosId: \\d+}")
    public void deleteUtensilio(@PathParam("recetasId") Long recetasId, @PathParam("utensiliosId") Long utensiliosId) throws BusinessLogicException {
        UtensilioEntity entity = utensilioLogic.getUtensilioByReceta(recetasId, utensiliosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSO + recetasId + R + utensiliosId + NOEXISTE, 404);
        }
        utensilioLogic.deleteUtensilioReceta(recetasId, utensiliosId);
    }
    
    private List<UtensilioDTO> utensiliosListEntity2DTO(List<UtensilioEntity> entityList) {
        List<UtensilioDTO> list = new ArrayList<>();
        for (UtensilioEntity entity : entityList) {
            list.add(new UtensilioDTO(entity));
        }
        return list;
    }
    
}
