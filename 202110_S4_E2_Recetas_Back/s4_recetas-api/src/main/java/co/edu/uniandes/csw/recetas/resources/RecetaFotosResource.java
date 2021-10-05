/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.FotoDTO;
import co.edu.uniandes.csw.recetas.ejb.FotoLogic;
import co.edu.uniandes.csw.recetas.ejb.RecetaFotosLogic;
import co.edu.uniandes.csw.recetas.entities.FotoEntity;
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
public class RecetaFotosResource {
    
    private static final Logger LOGGER = Logger.getLogger(RecetaFotosResource.class.getName());
    private static final String ELRECURSOR = "El recurso /recetas/";
    private static final String R = "/fotos/";
    private static final String NOEXISTE = " no existe.";
    
    @Inject
    private RecetaFotosLogic recetaFotosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private FotoLogic fotoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @POST
    public FotoDTO createFoto(@PathParam("recetasId") Long recetasId, FotoDTO foto) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaFotosResource createFoto: input: recetasID: {0} , fotosId: {1}", new Object[]{recetasId, foto.getId()});
        
        FotoDTO fotoDTO = new FotoDTO(recetaFotosLogic.createFoto(recetasId, foto.toEntity()));
        LOGGER.log(Level.INFO, "RecetaFotosResource addFoto: output: {0}", fotoDTO);
        return fotoDTO;
    }
    
    @GET
    public List<FotoDTO> getFotos(@PathParam("recetasId") Long recetasId) {
        LOGGER.log(Level.INFO, "RecetaFotosResource getFotos: input: {0}", recetasId);
        List<FotoDTO> listaDTOs = fotosListEntity2DTO(recetaFotosLogic.getFotos(recetasId));
        LOGGER.log(Level.INFO, "RecetaFotosResource getFotos: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    @GET
    @Path("{fotosId: \\d+}")
    public FotoDTO getFoto(@PathParam("recetasId") Long recetasId, @PathParam("fotosId") Long fotosId){
        LOGGER.log(Level.INFO, "RecetaFotosResource getFotos: input: recetasID: {0} , fotosId: {1}", new Object[]{recetasId, fotosId});
        FotoEntity entity=recetaFotosLogic.getFoto(recetasId,fotosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + fotosId + NOEXISTE, 404);
        }
        FotoDTO fotoDTO = new FotoDTO(entity);
        LOGGER.log(Level.INFO, "RecetaFotosResource getFotos: output: {0}", fotoDTO);
        return fotoDTO;
    }
    
    @PUT
    @Path("{fotosId: \\d+}")
    public FotoDTO updateFoto(@PathParam("recetasId") Long recetasId, @PathParam("fotosId") Long fotosId, FotoDTO foto) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecetaFotosResource updateFoto: input: recetasId: {0} , fotosId: {1}, foto:{2}", new Object[]{recetasId, fotosId, foto});
        if (fotosId.equals(foto.getId())) {
            throw new BusinessLogicException("Los ids del Foto no coinciden.");
        }
        FotoEntity entity = recetaFotosLogic.getFoto(recetasId,fotosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + fotosId + NOEXISTE, 404);

        }
        FotoDTO fotoDTO = new FotoDTO(recetaFotosLogic.updateFoto(recetasId,fotosId,foto.toEntity()));
        LOGGER.log(Level.INFO, "RecetaFotosResource updateFoto: output: {0}", fotoDTO);
        return fotoDTO;
    }
    
    @DELETE
    @Path("{fotosId: \\d+}")
    public void deleteFoto(@PathParam("recetasId") Long recetasId, @PathParam("fotosId") Long fotosId) throws BusinessLogicException {
        FotoEntity entity = recetaFotosLogic.getFoto(recetasId, fotosId);
        if (entity == null) {
            throw new WebApplicationException(ELRECURSOR + recetasId + R + fotosId + NOEXISTE, 404);
        }
        recetaFotosLogic.deleteFoto(recetasId, fotosId);
    }
    
    private List<FotoDTO> fotosListEntity2DTO(List<FotoEntity> entityList) {
        List<FotoDTO> list = new ArrayList<>();
        for (FotoEntity entity : entityList) {
            list.add(new FotoDTO(entity));
        }
        return list;
    }
    
}
