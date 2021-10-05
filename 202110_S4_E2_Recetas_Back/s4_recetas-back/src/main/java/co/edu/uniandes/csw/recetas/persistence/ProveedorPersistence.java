/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
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
public class ProveedorPersistence {
    
    @PersistenceContext(unitName="recetasPU")
    protected EntityManager em;
    
    public ProveedorEntity create(ProveedorEntity proveedor ){
        em.persist(proveedor);
        return proveedor;
    } 
    
    public ProveedorEntity find(Long id ){
        ProveedorEntity proveedor= em.find(ProveedorEntity.class, id);
        return proveedor;
    }
    
    public ProveedorEntity resultCheck(List<ProveedorEntity> results) {
        ProveedorEntity proveedor = null;
        if (results != null && !results.isEmpty()) {
            proveedor = results.get(0);
        } 
        return proveedor;
    }
    
    public List<ProveedorEntity> findAll( ){
        Query query = em.createQuery("select u from ProveedorEntity u", ProveedorEntity.class);
        return query.getResultList();
    }
    
    public ProveedorEntity update( ProveedorEntity proveedor ){
        em.merge(proveedor);
        ProveedorEntity proveedorAct=em.find(ProveedorEntity.class, proveedor.getId());
        return proveedorAct;
    } 
    
    public void delete (Long id)
    {
        ProveedorEntity proveedor= em.find(ProveedorEntity.class, id);
        em.remove(proveedor);
    }
    public ProveedorEntity findByLogin(String login ){
        TypedQuery query = em.createQuery("Select e From ProveedorEntity e where e.login = :login", ProveedorEntity.class);
        // Se remplaza el placeholder ":isbn" con el valor del argumento 
        query = query.setParameter("login", login);
        // Se invoca el query se obtiene la lista resultado
        List<ProveedorEntity> sameLogin = query.getResultList();
        return resultCheck(sameLogin);
    }
    public ProveedorEntity findByCorreo(String correo ){
        TypedQuery query = em.createQuery("Select e From ProveedorEntity e where e.correo = :correo", ProveedorEntity.class);
        // Se remplaza el placeholder ":isbn" con el valor del argumento 
        query = query.setParameter("correo", correo);
        // Se invoca el query se obtiene la lista resultado
        List<ProveedorEntity> sameCorreo = query.getResultList();
        return resultCheck(sameCorreo);
    }
    public ProveedorEntity findByNombre(String nombre ){
        TypedQuery query = em.createQuery("Select e From ProveedorEntity e where e.nombre = :nombre", ProveedorEntity.class);
        // Se remplaza el placeholder ":isbn" con el valor del argumento 
        query = query.setParameter("nombre", nombre);
        // Se invoca el query se obtiene la lista resultado
        List<ProveedorEntity> sameNombre = query.getResultList();
        return resultCheck(sameNombre);
    }
}
