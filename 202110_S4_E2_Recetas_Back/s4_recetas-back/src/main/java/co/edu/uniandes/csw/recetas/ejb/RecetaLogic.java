/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.RecetaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Ingrith Barbosa
 */
@Stateless
public class RecetaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(RecetaLogic.class.getName());
    
    @Inject
    private RecetaPersistence persistence;
    
    public RecetaEntity create(RecetaEntity newReceta) throws BusinessLogicException
    {
        if(newReceta.getCantIngredientes()<=0)
            throw new BusinessLogicException("La receta debe tener mínimo un ingrediente.");
        if(newReceta.getCalorias()<=0)
            throw new BusinessLogicException("La cantidad de calorías debe ser un número mayor a 0.");
        for (RecetaEntity r: persistence.findAll())
        {
            if(r.getNombre().equals(newReceta.getNombre()))
                throw new BusinessLogicException("No puede haber dos recetas con el mismo nombre.");
        }
        return persistence.create(newReceta);  
    }
    public List<RecetaEntity> getRecetas() {
        List<RecetaEntity> recetas = persistence.findAll();
        return recetas;
    }
    public RecetaEntity getReceta(Long recetaId) {
        RecetaEntity recetaEntity = persistence.find(recetaId);
        if (recetaEntity == null) 
        {
            LOGGER.log(Level.SEVERE, "La receta con el id = {0} no existe", recetaId);
        }
        return recetaEntity;
    }
    public RecetaEntity updateReceta(RecetaEntity receta) throws BusinessLogicException {
        if(receta.getCantIngredientes()<=0)
            throw new BusinessLogicException("La receta debe tener mínimo un ingrediente.");
        if(receta.getCalorias()<=0)
            throw new BusinessLogicException("La cantidad de calorías debe ser un número mayor a 0.");
        for (RecetaEntity r: persistence.findAll())
        {
            if(r.getNombre().equals(receta.getNombre()))
                throw new BusinessLogicException("No puede haber dos recetas con el mismo nombre.");
        }
        RecetaEntity newEntity = persistence.update(receta);
        return newEntity;
    }
    public void deleteReceta(Long recetasId) {
        
        persistence.delete(recetasId);
    }
}
