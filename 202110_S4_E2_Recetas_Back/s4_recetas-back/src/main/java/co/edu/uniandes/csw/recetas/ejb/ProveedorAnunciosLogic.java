/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.AnuncioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.AnuncioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ProveedorAnunciosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(AnuncioLogic.class.getName());

    @Inject
    AnuncioPersistence persistence;
    
    public AnuncioEntity crearAnuncioProveedor(AnuncioEntity anuncio) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia la creacion del Anuncio");
         if(anuncio.getDuracion()<0)
        {
            throw new BusinessLogicException("La duracion no puede ser menor a 0"+anuncio.getDuracion());
        }
        if(anuncio.getDuracion()>100000)
        {
            throw new BusinessLogicException("La duracion es muy alta"+anuncio.getDuracion());
        }
      
         LOGGER.log(Level.INFO, "FIN");
        AnuncioEntity nAnuncioEntity= persistence.create(anuncio);
        return nAnuncioEntity;    
        }
          
    public AnuncioEntity actualizarAnuncioProveedor(Long id,AnuncioEntity anuncio)
    {
        LOGGER.log(Level.INFO, "Actualizar anuncio", id);
        AnuncioEntity nAnuncioEntity = persistence.update(anuncio);
        LOGGER.log(Level.INFO, "FIN", id);
        return nAnuncioEntity;
    }
     public void deleteUsuarioProveedor(Long AnuncioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el anuncio con id = {0}", AnuncioId);
        persistence.delete(AnuncioId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el anuncio con id = {0}", AnuncioId);
    }
       public List<AnuncioEntity> getAnunciosProveedor()
    {
        LOGGER.log(Level.INFO, "tratar los Anuncios");
         List<AnuncioEntity> list = persistence.findAll();
         LOGGER.log(Level.INFO, "Fin");
         return list;
    }
       public AnuncioEntity getAnuncioProveedor(Long id)
    {
        LOGGER.log(Level.INFO, "Encontrar un Anuncio");
        AnuncioEntity nAnuncioEntity=persistence.find(id);
        LOGGER.log(Level.INFO, "Fin");
        return nAnuncioEntity;
    }
    
        
        
    
}
