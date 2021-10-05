/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.FotoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Santiago Campo
 */
@Stateless
public class FotoPersistence {
    
    @PersistenceContext(unitName = "recetasPU")
    protected EntityManager em;
    public FotoEntity create(FotoEntity foto) {
        em.persist(foto);
        
        return foto;
    }
    
    public FotoEntity find(Long id ){
        FotoEntity foto= em.find(FotoEntity.class, id);
        
        return foto;
    }
    
    public FotoEntity resultCheck(List<FotoEntity> results) {
        FotoEntity foto = null;
        if (results != null && !results.isEmpty()) {
            foto = results.get(0);
        } 
        return foto;
    }
    
    public FotoEntity findByReceta(Long recetasId, Long fotosId) {
        TypedQuery<FotoEntity> q = em.createQuery("select p from FotoEntity p where (p.receta.id = :recetaid) and (p.id = :fotosid)", FotoEntity.class);
        q.setParameter("recetaid", recetasId);
        q.setParameter("fotosid", fotosId);
        List<FotoEntity> results = q.getResultList();
        return resultCheck(results);
    }
    
    public List<FotoEntity> findAll( ){
        Query query = em.createQuery("SELECT u FROM FotoEntity u", FotoEntity.class);
        
        return query.getResultList();
    }
    
    public FotoEntity update( FotoEntity foto ){
        em.merge(foto);
        FotoEntity newFoto=em.find(FotoEntity.class, foto.getId());
        return newFoto;
    } 
    
    public void delete (Long id)
    {
        FotoEntity foto = em.find(FotoEntity.class, id);
        em.remove(foto);
    }

    public FotoEntity findByDireccion(String direccion) {
        
        TypedQuery<FotoEntity> query = em.createQuery("Select e From FotoEntity e where e.direccion = :direccion", FotoEntity.class);
        
        query = query.setParameter("direccion", direccion);
        
        List<FotoEntity> sameDireccion = query.getResultList();
        
        return resultCheck(sameDireccion);
    }
    
}
