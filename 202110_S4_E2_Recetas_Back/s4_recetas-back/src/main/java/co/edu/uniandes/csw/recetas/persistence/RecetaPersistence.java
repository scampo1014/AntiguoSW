/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ingrith Barbosa
 */
@Stateless
public class RecetaPersistence {
    
    @PersistenceContext (unitName= "recetasPU")
    protected EntityManager em;
    
    public RecetaEntity create (RecetaEntity receta)
    {
        em.persist(receta);
        return receta;
    }

    public List<RecetaEntity> findAll() 
    {
        Query q = em.createQuery("select u from RecetaEntity u");
        return q.getResultList();
    }
    
    public RecetaEntity find(Long recetaId) 
    {
        return em.find(RecetaEntity.class, recetaId);
    }
    
    public RecetaEntity resultCheck(List<RecetaEntity> results) {
        RecetaEntity receta = null;
        if (results != null && !results.isEmpty()) {
            receta = results.get(0);
        } 
        return receta;
    }
    
    public RecetaEntity findByUsuario(Long usuariosId, Long recetasId) {
        TypedQuery<RecetaEntity> q = em.createQuery("select p from RecetaEntity p where (p.usuario.id = :usuarioid) and (p.id = :recetasId)", RecetaEntity.class);
        q.setParameter("usuarioid", usuariosId);
        q.setParameter("recetasId", recetasId);
        List<RecetaEntity> results = q.getResultList();
        return resultCheck(results);
    }
    
     public RecetaEntity update(RecetaEntity recetaEntity) 
     {
        return em.merge(recetaEntity);
    }
     
    public void delete(Long recetaId) 
    {
        RecetaEntity recetaEntity = em.find(RecetaEntity.class, recetaId);
        em.remove(recetaEntity);
    }
    
    
}
