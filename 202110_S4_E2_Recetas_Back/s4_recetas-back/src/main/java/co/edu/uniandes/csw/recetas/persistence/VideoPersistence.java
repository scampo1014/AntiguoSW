/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.VideoEntity;
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
public class VideoPersistence {
    
    @PersistenceContext(unitName = "recetasPU")
    protected EntityManager em;
    public VideoEntity create(VideoEntity video) {
        em.persist(video);
        
        return video;
    }
    
    public VideoEntity find(Long id ){
        VideoEntity video= em.find(VideoEntity.class, id);
        
        return video;
    }
    
    public VideoEntity resultCheck(List<VideoEntity> results) {
        VideoEntity video = null;
        if (results != null && !results.isEmpty()) {
            video = results.get(0);
        } 
        return video;
    }
    
    public VideoEntity findByReceta(Long recetasId, Long videosId) {
        TypedQuery<VideoEntity> q = em.createQuery("select p from VideoEntity p where (p.receta.id = :recetaid) and (p.id = :videosid)", VideoEntity.class);
        q.setParameter("recetaid", recetasId);
        q.setParameter("videosid", videosId);
        List<VideoEntity> results = q.getResultList();
        VideoEntity video = null;
        return resultCheck(results);
    }
    
    public List<VideoEntity> findAll( ){
        Query query = em.createQuery("SELECT u FROM VideoEntity u", VideoEntity.class);
        
        return query.getResultList();
    }
    
    public VideoEntity update( VideoEntity video ){
        em.merge(video);
        VideoEntity newVideo=em.find(VideoEntity.class, video.getId());
        return newVideo;
    } 
    
    public void delete (Long id)
    {
        VideoEntity video = em.find(VideoEntity.class, id);
        em.remove(video);
    }
    
    public VideoEntity findByDireccion(String direccion) {
        
        TypedQuery<VideoEntity> query = em.createQuery("Select e From VideoEntity e where e.direccion = :direccion", VideoEntity.class);
        
        query = query.setParameter("direccion", direccion);
        
        List<VideoEntity> sameDireccion = query.getResultList();
        return resultCheck(sameDireccion);
    }
}
