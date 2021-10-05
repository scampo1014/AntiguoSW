/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
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
public class CalificacionPersistence {
    
    private static final String CID = "calificacionesId";
    
    @PersistenceContext (unitName= "recetasPU")
    protected EntityManager em;
    
    public CalificacionEntity create (CalificacionEntity calificacion)
    {
        em.persist(calificacion);
        return calificacion;
    }

    public List<CalificacionEntity> findAll() 
    {
        Query q = em.createQuery("select u from CalificacionEntity u");
        return q.getResultList();
    }
    
    public CalificacionEntity resultCheck(List<CalificacionEntity> results) {
        CalificacionEntity calificacion = null;
        if (results != null && !results.isEmpty()) {
            calificacion = results.get(0);
        } 
        return calificacion;
    }
    
    public CalificacionEntity findByUsuario(Long usuariosId, Long calificacionesId) {
        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.usuario.id = :usuarioid) and (p.id = :calificacionesId)", CalificacionEntity.class);
        q.setParameter("usuarioid", usuariosId);
        q.setParameter(CID, calificacionesId);
        List<CalificacionEntity> results = q.getResultList();
        return resultCheck(results);
    }
    
    public CalificacionEntity findByReceta(Long recetasId, Long calificacionesId) {
        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.receta.id = :recetaid) and (p.id = :calificacionesId)", CalificacionEntity.class);
        q.setParameter("recetaid", recetasId);
        q.setParameter(CID, calificacionesId);
        List<CalificacionEntity> results = q.getResultList();
        return resultCheck(results);
    }
    
    public CalificacionEntity findByProveedor(Long proveedoresId, Long calificacionesId) {
        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.proveedor.id = :proveedorid) and (p.id = :calificacionesId)", CalificacionEntity.class);
        q.setParameter("proveedorid", proveedoresId);
        q.setParameter(CID, calificacionesId);
        List<CalificacionEntity> results = q.getResultList();
        return resultCheck(results);
    }
    public CalificacionEntity findByComentario(Long comentariosId, Long calificacionesId) {
        TypedQuery<CalificacionEntity> q = em.createQuery("select c from ComentarioEntity p where (c.comentario.id = :comentarioid) and (c.id = :calificacionesId)", CalificacionEntity.class);
        q.setParameter("comentarioid", comentariosId);
        q.setParameter(CID, calificacionesId);
        List<CalificacionEntity> results = q.getResultList();
        return resultCheck(results);
    }
    public CalificacionEntity find(Long calificacionId) 
    {
        return em.find(CalificacionEntity.class, calificacionId);
    }
    
     public CalificacionEntity update(CalificacionEntity calificacionEntity) 
     {
        return em.merge(calificacionEntity);
    }
     
    public void delete(Long calificacionId) 
    {
        CalificacionEntity calificacionEntity = em.find(CalificacionEntity.class, calificacionId);
        em.remove(calificacionEntity);
    }
    
}
