/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.FotoEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.FotoPersistence;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Santiago Campo
 */
@Stateless
public class FotoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(FotoLogic.class.getName());
    
    @Inject
    FotoPersistence persistence;
    
    public FotoEntity createFoto(FotoEntity foto) throws BusinessLogicException {
        
        //Regla de negocio: Dirección es única
        if (persistence.findByDireccion(foto.getDireccion()) != null) {
            throw new BusinessLogicException("Ya existe una foto en el sistema con la misma dirección.");
        }
        //RN: Dirección no vacía
        if (foto.getDireccion().equals("")) {
            throw new BusinessLogicException("La direccion de la foto no puede estar vacía");
        }
        //RN: Tamaño no puede ser negativo o 0
        if (foto.getTamanio() <= 0) {
            throw new BusinessLogicException("El tamaño de la foto no puede ser 0 o menos");
        }
        //RN: Tamaño no puede ser vacío
        if (foto.getTamanio() == null) {
            throw new BusinessLogicException("La foto tiene que tener tamaño");
        }  
        //RN: Tamaño no puede superar 15MB
        if (foto.getTamanio() > 15000000) {
            throw new BusinessLogicException("El tamaño de la foto no puede ser mayor a 15MB");
        } 
        
        foto = persistence.create(foto);
        return foto;
    }
    
    public List<FotoEntity> getFotos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las fotos");        
        List<FotoEntity> fotos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las fotos");
        return fotos;
    }

    public FotoEntity getFoto(Long fotoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la foto con id = {0}", fotoId);        
        FotoEntity fotoEntity = persistence.find(fotoId);
        if (fotoEntity == null) {
            LOGGER.log(Level.SEVERE, "La foto con el id = {0} no existe", fotoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la foto con id = {0}", fotoId);
        return fotoEntity;
    }

    public FotoEntity updateFoto(Long fotoId, FotoEntity fotoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la foto con id = {0}", fotoId);        
        FotoEntity newEntity = persistence.update(fotoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la foto con id = {0}", fotoEntity.getId());
        return newEntity;
    }

    public void deleteFoto(Long fotoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la foto con id = {0}", fotoId);        
        persistence.delete(fotoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la foto con id = {0}", fotoId);
    }
}
