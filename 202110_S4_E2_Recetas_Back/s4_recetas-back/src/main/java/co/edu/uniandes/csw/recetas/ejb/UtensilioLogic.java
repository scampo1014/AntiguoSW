/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.entities.UtensilioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.RecetaPersistence;
import co.edu.uniandes.csw.recetas.persistence.UtensilioPersistence;
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
public class UtensilioLogic {
    
    private static final Logger LOGGER = Logger.getLogger(UtensilioLogic.class.getName());

    @Inject
    UtensilioPersistence persistence;
    
    @Inject
    private RecetaPersistence recetaPersistence;
    
    public void validarRN(UtensilioEntity utensilio) throws BusinessLogicException {
        //El precio no puede ser negativo
        if(utensilio.getPrecio()<0)
        {
            throw new BusinessLogicException("El precio no puede ser negativo");
        }
        //El precio no puede ser mayor a 500000
        if(utensilio.getPrecio()>500000)
        {
            throw new BusinessLogicException("El precio del utensilio es muy alto");
        }
    }
    
    public void validarRNReceta(RecetaEntity receta, UtensilioEntity utensilioEntity) throws BusinessLogicException
    {
        //Una receta no puede tener dos utensilios con el mismo nombre
        for (UtensilioEntity utensilio: receta.getUtensilios())
        {
            if(utensilio.getNombre().equals(utensilioEntity.getNombre()))
            {
                throw new BusinessLogicException("Una receta no puede tener dos utensilios con el mismo nombre");
            }
        }
    }
    
    public UtensilioEntity createUtensilio(UtensilioEntity utensilio) throws BusinessLogicException {
        
        //validar reglas de negocio
        LOGGER.log(Level.INFO, "Inicia proceso de creación del utensilio");
        //Una receta no puede tener dos utensilios con el mismo nombre
        
        validarRN(utensilio);

        LOGGER.log(Level.INFO, "Termina proceso de creación del utensilio");
        return persistence.create(utensilio);
    }
    
    public UtensilioEntity createUtensilioReceta(Long recetasId, UtensilioEntity utensilioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear utensilio");
        RecetaEntity receta = recetaPersistence.find(recetasId);
        //validar reglas de negocio
        validarRNReceta(receta, utensilioEntity);        
        validarRN(utensilioEntity);
        utensilioEntity.setReceta(receta);
        LOGGER.log(Level.INFO, "Termina proceso de creación del utensilio");
        UtensilioEntity newUtensilioEntity= persistence.create(utensilioEntity);
        return newUtensilioEntity;
    }
    
    public UtensilioEntity updateUtensilio(Long id,UtensilioEntity utensilio)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el utensilio con id = {0}", id);
        UtensilioEntity newUtensilioEntity = persistence.update(utensilio);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el utensilio con id = {0}", id);
        return newUtensilioEntity;
    }
    
       /**
     * Actualiza la información de una instancia de Utensilio.
     *
     * @param utensilioEntity Instancia de UtensilioEntity con los nuevos datos.
     * @param recetasId id del Receta el cual sera padre del Utensilio actualizado.
     * @return Instancia de UtensilioEntity con los datos actualizados.
     * ,new Object[]{utensilioEntity.getId(),recetasId}
     */
    public UtensilioEntity updateUtensilioReceta(Long recetasId, Long utensiliosId, UtensilioEntity utensilioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el utensilio con id = {0} de la receta con id = {1}" ,new Object[]{utensilioEntity.getId(),recetasId});
        RecetaEntity recetaEntity = recetaPersistence.find(recetasId);
        UtensilioEntity utensilioOriginal = persistence.find(utensiliosId);
        validarRNReceta(recetaEntity, utensilioEntity);        
        validarRN(utensilioEntity);
        utensilioOriginal.setReceta(recetaEntity);
        utensilioOriginal.setNombre(utensilioEntity.getNombre());
        utensilioOriginal.setPrecio(utensilioEntity.getPrecio());
        utensilioOriginal.setDescripcion(utensilioEntity.getDescripcion());
        
        UtensilioEntity newUtensilioEntity = persistence.update(utensilioOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el utensilio con id = {0} de la receta con id = {1}" ,new Object[]{utensilioEntity.getId(),recetasId});
        return newUtensilioEntity;
    }
    
    public List<UtensilioEntity> getUtensilios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los utensilios");
        List<UtensilioEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los utensilios");
        return lista;
    }
    
        /**
     * Obtiene la lista de los registros de Utensilio que pertenecen a un Receta.
     *
     * @param recetasId id del Receta el cual es padre de los Utensilios.
     * @return Colección de objetos de UtensilioEntity.
     */
    public List<UtensilioEntity> getUtensiliosReceta(Long recetasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los utensilios asociados al receta con id = {0}", recetasId);
        RecetaEntity recetaEntity = recetaPersistence.find(recetasId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los utensilios asociados al receta con id = {0}", recetasId);
        return recetaEntity.getUtensilios();
    }
    
    public UtensilioEntity getUtensilio(Long utensilioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el utensilio con id = {0}", utensilioId);
        UtensilioEntity utensilioEntity = persistence.find(utensilioId);
        if (utensilioEntity == null) {
            LOGGER.log(Level.SEVERE, "El utensilio con el id = {0} no existe", utensilioId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el utensilio con id = {0}", utensilioId);
        return utensilioEntity;
    }
    
    public UtensilioEntity getUtensilioByReceta(Long recetasId, Long utensiliosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el utensilio con id = {0} de la receta con id = {1}" ,new Object[]{utensiliosId,recetasId});
        return persistence.findByReceta(recetasId, utensiliosId);
    }
    
    public void deleteUtensilio(Long utensilioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el utensilio con id = {0}", utensilioId);
        persistence.delete(utensilioId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el utensilio con id = {0}", utensilioId);
    }
    
    /**
     * Elimina una instancia de Utensilio de la base de datos.
     *
     * @param utensiliosId Identificador de la instancia a eliminar.
     * @param recetasId id del Receta el cual es padre del Utensilio.
     * @throws BusinessLogicException Si la reseña no esta asociada al receta.
     *
     */
    public void deleteUtensilioReceta(Long recetasId, Long utensiliosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el utensilio con id = {0} de la receta con id = {1}" ,new Object[]{utensiliosId,recetasId});
        UtensilioEntity old = getUtensilioByReceta(recetasId, utensiliosId);
        if (old == null) {
            throw new BusinessLogicException("El utensilio con id = " + utensiliosId + " no esta asociado a la receta con id = " + recetasId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el utensilio con id = {0} de la receta con id = {1}" ,new Object[]{utensiliosId,recetasId});
    }

    public UtensilioEntity updateUtensilio(Long recetasId, Long utensiliosId, UtensilioEntity toEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
