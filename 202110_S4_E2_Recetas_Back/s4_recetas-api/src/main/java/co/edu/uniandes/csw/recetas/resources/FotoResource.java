/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.FotoDTO;
import co.edu.uniandes.csw.recetas.ejb.FotoLogic;
import co.edu.uniandes.csw.recetas.entities.FotoEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Santiago Campo
 */
@Path("fotos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FotoResource {
    
    private static final Logger LOGGER = Logger.getLogger(FotoResource.class.getName());
    private static final String ELRECURSO = "El recurso /fotos/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private FotoLogic fotoLogic;
    
    @POST
    public FotoDTO createFoto(FotoDTO foto) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "FotoResource createFoto: input: {0}", foto);
        FotoEntity fotoEntity = foto.toEntity();
        FotoEntity nuevoFotoEntity = fotoLogic.createFoto(fotoEntity);
        FotoDTO nuevoFotoDTO = new FotoDTO(nuevoFotoEntity);
        LOGGER.log(Level.INFO, "FotoResource createFoto: output: {0}", nuevoFotoDTO);
        return nuevoFotoDTO;
    }
    
    @GET
    public List<FotoDTO> getFotos() {
        LOGGER.info("FotoResource getFotos: input: void");
        List<FotoDTO> listaFotos = listEntity2DetailDTO(fotoLogic.getFotos());
        LOGGER.log(Level.INFO, "FotoResource getFotos: output: {0}", listaFotos);
        return listaFotos;
    }
    
    @GET
    @Path("{fotosId: \\d+}")
    public FotoDTO getFoto(@PathParam("fotosId") Long fotosId){
        LOGGER.log(Level.INFO, "FotoResource getFoto: input: {0}", fotosId);
        FotoEntity fotoEntity = fotoLogic.getFoto(fotosId);
        if (fotoEntity == null) {
            throw new WebApplicationException(ELRECURSO + fotosId + NOEXISTE, 404);
        }
        FotoDTO fullDTO = new FotoDTO(fotoEntity);
        LOGGER.log(Level.INFO, "FotoResource getFoto: output: {0}", fullDTO);
        return fullDTO;
    }
    
    @PUT
    @Path("{fotosId: \\d+}")
    public FotoDTO updateFoto(@PathParam("fotosId") Long fotosId, FotoDTO foto){
        LOGGER.log(Level.INFO, "FotoResource updateFoto: input: id:{0} , foto: {1}", new Object[]{fotosId, foto});
        foto.setId(fotosId);
        if (fotoLogic.getFoto(fotosId) == null) {
            throw new WebApplicationException(ELRECURSO + fotosId + NOEXISTE, 404);
        }
        FotoDTO fullDTO = new FotoDTO(fotoLogic.updateFoto(fotosId, foto.toEntity()));
        LOGGER.log(Level.INFO, "FotoResource updateFoto: output: {0}", fullDTO);
        return fullDTO;

    }
    
    @DELETE
    @Path("{fotosId: \\d+}")
    public void deleteFoto(@PathParam("fotosId") Long fotosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "FotoResource deleteFoto: input: {0}", fotosId);
        if (fotoLogic.getFoto(fotosId) == null) {
            throw new WebApplicationException(ELRECURSO + fotosId + NOEXISTE, 404);
        }
        fotoLogic.deleteFoto(fotosId);
        LOGGER.info("FotoResource deleteFoto: output: void");
    }
     

    private List<FotoDTO> listEntity2DetailDTO(List<FotoEntity> entityList) {
        List<FotoDTO> list = new ArrayList<>();
        for (FotoEntity entity : entityList) {
            list.add(new FotoDTO(entity));
        }
        return list;
    }
}
