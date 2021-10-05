/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
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
public class ComentarioPersistence {
    
    @PersistenceContext(unitName = "recetasPU")
    protected EntityManager em;
    public ComentarioEntity create(ComentarioEntity comentario) {
        em.persist(comentario);
        
        return comentario;
    }
    
    public ComentarioEntity find(Long id ){
        ComentarioEntity comentario= em.find(ComentarioEntity.class, id);
        
        return comentario;
    }
    
    public ComentarioEntity resultCheck(List<ComentarioEntity> results) {
        ComentarioEntity comentario = null;
        if (results != null && !results.isEmpty()) {
            comentario = results.get(0);
        } 
        return comentario;
    }
    
    public ComentarioEntity findByReceta(Long recetasId, Long comentariosId) {
        TypedQuery<ComentarioEntity> q = em.createQuery("select p from ComentarioEntity p where (p.receta.id = :recetaid) and (p.id = :comentariosId)", ComentarioEntity.class);
        q.setParameter("recetaid", recetasId);
        q.setParameter("comentariosId", comentariosId);
        List<ComentarioEntity> results = q.getResultList();
        ComentarioEntity comentario = null;
        return resultCheck(results);
    }
    
    public ComentarioEntity findByUsuario(Long usuariosId, Long comentariosId) {
        TypedQuery<ComentarioEntity> q = em.createQuery("select p from ComentarioEntity p where (p.usuario.id = :usuarioid) and (p.id = :comentariosId)", ComentarioEntity.class);
        q.setParameter("usuarioid", usuariosId);
        q.setParameter("comentariosId", comentariosId);
        List<ComentarioEntity> results = q.getResultList();
        return resultCheck(results);
    }
    
    public ComentarioEntity findByProveedor(Long proveedoresId, Long comentariosId) {
        TypedQuery<ComentarioEntity> q = em.createQuery("select p from ComentarioEntity p where (p.proveedor.id = :proveedorid) and (p.id = :comentariosId)", ComentarioEntity.class);
        q.setParameter("proveedorid", proveedoresId);
        q.setParameter("comentariosId", comentariosId);
        List<ComentarioEntity> results = q.getResultList();
        ComentarioEntity comentario = null;
        return resultCheck(results);
    }
    
    public List<ComentarioEntity> findAll( ){
        Query query = em.createQuery("SELECT u FROM ComentarioEntity u", ComentarioEntity.class);
        
        return query.getResultList();
    }
    
    public ComentarioEntity update( ComentarioEntity comentario ){
        em.merge(comentario);
        ComentarioEntity newComentario=em.find(ComentarioEntity.class, comentario.getId());
        return newComentario;
    } 
    
    public void delete (Long id)
    {
        ComentarioEntity comentario = em.find(ComentarioEntity.class, id);
        em.remove(comentario);
    }
}
