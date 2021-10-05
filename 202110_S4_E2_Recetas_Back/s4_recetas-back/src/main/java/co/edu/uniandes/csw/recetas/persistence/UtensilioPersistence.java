/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.UtensilioEntity;
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
public class UtensilioPersistence {
    
    @PersistenceContext(unitName="recetasPU")
    protected EntityManager em;
    
    public UtensilioEntity create(UtensilioEntity utensilio ){
        em.persist(utensilio);
        return utensilio;
    } 
    
    public UtensilioEntity find(Long id ){
        UtensilioEntity utensilio= em.find(UtensilioEntity.class, id);
        return utensilio;
    }
    
    public UtensilioEntity resultCheck(List<UtensilioEntity> results) {
        UtensilioEntity utensilio = null;
        if (results != null && !results.isEmpty()) {
            utensilio = results.get(0);
        } 
        return utensilio;
    }
    
    public UtensilioEntity findByReceta(Long recetasId, Long utensiliosId) {
        TypedQuery<UtensilioEntity> q = em.createQuery("select p from UtensilioEntity p where (p.receta.id = :recetaid) and (p.id = :utensiliosId)", UtensilioEntity.class);
        q.setParameter("recetaid", recetasId);
        q.setParameter("utensiliosId", utensiliosId);
        List<UtensilioEntity> results = q.getResultList();
        return resultCheck(results);
    }
    
    public List<UtensilioEntity> findAll( ){
        Query query = em.createQuery("select u from UtensilioEntity u", UtensilioEntity.class);
        return query.getResultList();
    }
    
    public UtensilioEntity update( UtensilioEntity utensilio ){
        em.merge(utensilio);
        UtensilioEntity utensilioAtualizado=em.find(UtensilioEntity.class, utensilio.getId());
        return utensilioAtualizado;
    } 
    
    public void delete (Long id)
    {
        UtensilioEntity utensilio= em.find(UtensilioEntity.class, id);
        em.remove(utensilio);
    }
    
}
