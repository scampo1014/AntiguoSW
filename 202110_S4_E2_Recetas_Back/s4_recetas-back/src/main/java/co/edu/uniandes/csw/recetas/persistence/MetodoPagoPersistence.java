/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.MetodoPagoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author juliantorres
 */
@Stateless
public class MetodoPagoPersistence 
{
  @PersistenceContext(unitName="recetasPU")
    protected EntityManager em;
    
    public MetodoPagoEntity create(MetodoPagoEntity metodoPago ){
        em.persist(metodoPago);
        return metodoPago;
    } 
    
    public MetodoPagoEntity find(Long id ){
        MetodoPagoEntity metodoPago= em.find(MetodoPagoEntity.class, id);
        return metodoPago;
    }
    
    public MetodoPagoEntity resultCheck(List<MetodoPagoEntity> results) {
        MetodoPagoEntity metodoPago = null;
        if (results != null && !results.isEmpty()) {
            metodoPago = results.get(0);
        } 
        return metodoPago;
    }
    
    public List<MetodoPagoEntity> findAll( ){
        Query query;
      query = em.createQuery("select u from MetodoPagoEntity u", MetodoPagoEntity.class);
        return query.getResultList();
    }
    
    public MetodoPagoEntity update( MetodoPagoEntity metodoPago ){
        em.merge(metodoPago);
        MetodoPagoEntity metodoPagoAct=em.find(MetodoPagoEntity.class, metodoPago.getId());
        return metodoPagoAct;
    } 
    public MetodoPagoEntity findByProveedor(Long proveedoresId, Long metodosPagoId) {
        TypedQuery<MetodoPagoEntity> q = em.createQuery("select p from ProveedorEntity p where (p.proveedor.id = :proveedorid) and (p.id = :metodosPagoId)", MetodoPagoEntity.class);
        q.setParameter("proveedorid", proveedoresId);
        q.setParameter("metodosPagoId", metodosPagoId);
        List<MetodoPagoEntity> results = q.getResultList();
        return resultCheck(results);
    }
    
    public void delete (Long id)
    {
        MetodoPagoEntity metodoPago= em.find(MetodoPagoEntity.class, id);
        em.remove(metodoPago);
    }  
    public MetodoPagoEntity findByMetodoDePago(String metodoDePago ){
        TypedQuery query = em.createQuery("Select e From MetodoPagoEntity e where e.metodoPago = :metodoPago", MetodoPagoEntity.class);
        // Se remplaza el placeholder ":isbn" con el valor del argumento 
        query = query.setParameter("metodoPago", metodoDePago);
        // Se invoca el query se obtiene la lista resultado
        List<MetodoPagoEntity> sameMetodo = query.getResultList();
        return resultCheck(sameMetodo);
    }
}
