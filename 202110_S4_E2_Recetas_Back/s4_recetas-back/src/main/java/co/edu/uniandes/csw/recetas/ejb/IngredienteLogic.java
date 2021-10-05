/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.IngredienteEntity;
import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.IngredientePersistence;
import co.edu.uniandes.csw.recetas.persistence.RecetaPersistence;
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
public class IngredienteLogic {
    
    private static final Logger LOGGER = Logger.getLogger(IngredienteLogic.class.getName());

    @Inject
    IngredientePersistence persistence;
    
    @Inject
    private RecetaPersistence recetaPersistence;
    
    public void validarRN(IngredienteEntity ingrediente) throws BusinessLogicException {
        //El precio no puede ser negativo
        if(ingrediente.getPrecio()<0)
        {
            throw new BusinessLogicException("El precio no puede ser negativo");
        }
        //El precio no puede ser mayor a 50000
        if(ingrediente.getPrecio()>50000)
        {
            throw new BusinessLogicException("El precio del ingrediente es muy alto");
        }
    }
    
    public void validarRNReceta(RecetaEntity receta, IngredienteEntity ingredienteEntity) throws BusinessLogicException
    {
        for (IngredienteEntity ingrediente: receta.getIngredientes())
        {
            if(ingrediente.getNombre().equals(ingredienteEntity.getNombre()))
            {
                throw new BusinessLogicException("Una receta no puede tener dos ingredientes con el mismo nombre");
            }
        }
    }
    
    public IngredienteEntity createIngrediente(IngredienteEntity ingrediente) throws BusinessLogicException {
        
        //validar reglas de negocio
        LOGGER.log(Level.INFO, "Inicia proceso de creación del ingrediente");  
        validarRN(ingrediente);
        LOGGER.log(Level.INFO, "Termina proceso de creación del ingrediente");
        IngredienteEntity newIngredienteEntity= persistence.create(ingrediente);
        return newIngredienteEntity;
    }
    
     public IngredienteEntity createIngredienteReceta(Long recetasId, IngredienteEntity ingredienteEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear ingrediente");
        RecetaEntity receta = recetaPersistence.find(recetasId);
        //validar reglas de negocio
        validarRNReceta(receta, ingredienteEntity);     
        validarRN(ingredienteEntity);
        ingredienteEntity.setReceta(receta);
        LOGGER.log(Level.INFO, "Termina proceso de creación del ingrediente");
        return persistence.create(ingredienteEntity);
    }
    
    public IngredienteEntity updateIngrediente(Long id,IngredienteEntity ingrediente)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el ingrediente con id = {0}", id);
        IngredienteEntity newIngredienteEntity = persistence.update(ingrediente);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el ingrediente con id = {0}", id);
        return newIngredienteEntity;
    }
    
        /**
     * Actualiza la información de una instancia de Ingrediente.
     *
     * @param ingredienteEntity Instancia de IngredienteEntity con los nuevos datos.
     * @param recetasId id del Receta el cual sera padre del Ingrediente actualizado.
     * @return Instancia de IngredienteEntity con los datos actualizados.
     * ,new Object[]{ingredienteEntity.getId(),recetasId}
     */
    public IngredienteEntity updateIngredienteReceta(Long recetasId, Long ingredientesId, IngredienteEntity ingredienteEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el ingrediente con id = {0} de la receta con id = {1}" ,new Object[]{ingredienteEntity.getId(),recetasId});
        RecetaEntity recetaEntity = recetaPersistence.find(recetasId);
        IngredienteEntity ingredienteOriginal = persistence.find(ingredientesId);
        validarRNReceta(recetaEntity, ingredienteEntity);         
        validarRN(ingredienteEntity);
        ingredienteOriginal.setReceta(recetaEntity);
        ingredienteOriginal.setNombre(ingredienteEntity.getNombre());
        ingredienteOriginal.setPrecio(ingredienteEntity.getPrecio());
        ingredienteOriginal.setCantidad(ingredienteEntity.getCantidad());
        
        IngredienteEntity newIngredienteEntity = persistence.update(ingredienteOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el ingrediente con id = {0} del libro con id = {1}" ,new Object[]{ingredienteEntity.getId(),recetasId});
        return newIngredienteEntity;
    }
    
    public List<IngredienteEntity> getIngredientes() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los ingredientes");
        List<IngredienteEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los ingredientes");
        return lista;
    }
    
        /**
     * Obtiene la lista de los registros de Ingrediente que pertenecen a un Receta.
     *
     * @param recetasId id del Receta el cual es padre de los Ingredientes.
     * @return Colección de objetos de IngredienteEntity.
     */
    public List<IngredienteEntity> getIngredientesReceta(Long recetasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los ingredientes asociados al receta con id = {0}", recetasId);
        RecetaEntity recetaEntity = recetaPersistence.find(recetasId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los ingredientes asociados al receta con id = {0}", recetasId);
        return recetaEntity.getIngredientes();
    }
    
    public IngredienteEntity getIngrediente(Long ingredienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el ingrediente con id = {0}", ingredienteId);
        IngredienteEntity ingredienteEntity = persistence.find(ingredienteId);
        if (ingredienteEntity == null) {
            LOGGER.log(Level.SEVERE, "El ingrediente con el id = {0} no existe", ingredienteId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el ingrediente con id = {0}", ingredienteId);
        return ingredienteEntity;
    }
    
    
    public IngredienteEntity getIngredienteByReceta(Long recetasId, Long ingredientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el ingrediente con id = {0} del libro con id = {1}" ,new Object[]{ingredientesId,recetasId});
        return persistence.findByReceta(recetasId, ingredientesId);
    }
    
    
    public void deleteIngrediente(Long ingredienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el ingrediente con id = {0}", ingredienteId);
        persistence.delete(ingredienteId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el ingrediente con id = {0}", ingredienteId);
    }

    /**
     * Elimina una instancia de Ingrediente de la base de datos.
     *
     * @param ingredientesId Identificador de la instancia a eliminar.
     * @param recetasId id del Receta el cual es padre del Ingrediente.
     * @throws BusinessLogicException Si la reseña no esta asociada al libro.
     *
     */
    public void deleteIngredienteReceta(Long recetasId, Long ingredientesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el ingrediente con id = {0} del libro con id = {1}" ,new Object[]{ingredientesId,recetasId});
        IngredienteEntity old = getIngredienteByReceta(recetasId, ingredientesId);
        if (old == null) {
            throw new BusinessLogicException("El ingrediente con id = " + ingredientesId + " no esta asociado a la receta con id = " + recetasId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el ingrediente con id = {0} del libro con id = {1}" ,new Object[]{ingredientesId,recetasId});
    }
    
}
