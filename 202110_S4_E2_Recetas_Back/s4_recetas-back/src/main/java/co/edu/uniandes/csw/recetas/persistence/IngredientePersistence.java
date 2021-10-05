/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.IngredienteEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Maria Valentina Garcia
 */
@Stateless
public class IngredientePersistence {
    
    @PersistenceContext(unitName="recetasPU")
    protected EntityManager em;
    
    public IngredienteEntity create(IngredienteEntity ingrediente ){
        em.persist(ingrediente);
        return ingrediente;
    } 
    
    public IngredienteEntity find(Long id ){
        IngredienteEntity ingrediente= em.find(IngredienteEntity.class, id);
        return ingrediente;
    }
    
    public IngredienteEntity resultCheck(List<IngredienteEntity> results) {
        IngredienteEntity ingrediente = null;
        if (results != null && !results.isEmpty()) {
            ingrediente = results.get(0);
        } 
        return ingrediente;
    }
    
   public IngredienteEntity findByReceta(Long recetasId, Long ingredientesId) {
        TypedQuery<IngredienteEntity> q = em.createQuery("select p from IngredienteEntity p where (p.receta.id = :recetaid) and (p.id = :ingredientesId)", IngredienteEntity.class);
        q.setParameter("recetaid", recetasId);
        q.setParameter("ingredientesId", ingredientesId);
        List<IngredienteEntity> results = q.getResultList();
        return resultCheck(results);
    }
    
    public List<IngredienteEntity> findAll( ){
        Query query = em.createQuery("select u from IngredienteEntity u", IngredienteEntity.class);
        return query.getResultList();
    }
    
    public IngredienteEntity update( IngredienteEntity ingrediente ){
        em.merge(ingrediente);
        IngredienteEntity ingredienteAtualizado=em.find(IngredienteEntity.class, ingrediente.getId());
        return em.merge(ingrediente);
    } 
    
    public void delete (Long id)
    {
        IngredienteEntity ingrediente= em.find(IngredienteEntity.class, id);
        em.remove(ingrediente);
    }
    
}
