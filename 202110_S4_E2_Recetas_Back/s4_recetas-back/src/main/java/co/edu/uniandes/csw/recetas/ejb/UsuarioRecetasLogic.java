/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.entities.UsuarioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.RecetaPersistence;
import co.edu.uniandes.csw.recetas.persistence.UsuarioPersistence;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Maria Valentina Garcia
 */

@Stateless
public class UsuarioRecetasLogic {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioRecetasLogic.class.getName());
    
    @Inject
    private RecetaPersistence recetaPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    public RecetaEntity createReceta(Long usuariosId, RecetaEntity recetaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear receta");
        UsuarioEntity usuario = usuarioPersistence.find(usuariosId);
        //validar reglas de negocio
        if(recetaEntity.getCantIngredientes()<=0)
            throw new BusinessLogicException("La receta debe tener mínimo un ingrediente.");
        if(recetaEntity.getCalorias()<=0)
            throw new BusinessLogicException("La cantidad de calorías debe ser un número mayor a 0.");
        for (RecetaEntity r: recetaPersistence.findAll())
        {
            if(r.getNombre().equals(recetaEntity.getNombre()))
                throw new BusinessLogicException("No puede haber dos recetas con el mismo nombre.");
        }
        recetaEntity.setUsuario(usuario);
        LOGGER.log(Level.INFO, "Termina proceso de creación del receta");
        RecetaEntity newRecetaEntity= recetaPersistence.create(recetaEntity);
        return newRecetaEntity;
    }

    public Collection<RecetaEntity> getRecetas(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los recetas asociados a la usuario con id = {0}", usuariosId);
        return usuarioPersistence.find(usuariosId).getRecetas();
    }
    
    public RecetaEntity getReceta(Long usuariosId, Long recetasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el receta con id = {0} del usuario con id = " + usuariosId, recetasId);
        return recetaPersistence.findByUsuario(usuariosId, recetasId);
    }
    
    public RecetaEntity updateReceta(Long usuariosId, Long recetasId, RecetaEntity recetaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el recetae con id = {0} de la usuario con id = {1}" + recetaEntity.getId(),usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        RecetaEntity recetaOriginal = recetaPersistence.find(recetasId);
        //validar reglas de negocio
        if(recetaEntity.getCantIngredientes()<=0)
            throw new BusinessLogicException("La receta debe tener mínimo un ingrediente.");
        if(recetaEntity.getCalorias()<=0)
            throw new BusinessLogicException("La cantidad de calorías debe ser un número mayor a 0.");
        for (RecetaEntity r: recetaPersistence.findAll())
        {
            if(r.getNombre().equals(recetaEntity.getNombre()))
                throw new BusinessLogicException("No puede haber dos recetas con el mismo nombre.");
        }
        recetaOriginal.setUsuario(usuarioEntity);
        recetaOriginal.setCalorias(recetaEntity.getCalorias());
        recetaOriginal.setCantIngredientes(recetaEntity.getCantIngredientes());
        recetaOriginal.setDescripcion(recetaEntity.getDescripcion());
        recetaOriginal.setNombre(recetaEntity.getNombre());
        recetaOriginal.setTiempoPrep(recetaEntity.getTiempoPrep());
        recetaOriginal.setDificultad(recetaEntity.getDificultad());
        recetaOriginal.setPopular(recetaEntity.getPopular());
        
        RecetaEntity newRecetaEntity = recetaPersistence.update(recetaOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el receta con id = {0} del libro con id = {1}" + recetaEntity.getId(),usuariosId);
        return newRecetaEntity;
    }
    
    public void deleteReceta(Long usuariosId, Long recetasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el receta con id = {0} del usuario con id = " + usuariosId, recetasId);
        RecetaEntity old = getReceta(usuariosId, recetasId);
        if (old == null) {
            throw new BusinessLogicException("El recurso /usuarios/"+usuariosId+"/recetas/"+recetasId+" no existe.");
        }
        recetaPersistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el receta con id = {0} del usuario con id = " + usuariosId, recetasId);
    }
    
}
