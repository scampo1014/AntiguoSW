/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.UsuarioEntity;
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
public class UsuarioPersistence {
        
    @PersistenceContext(unitName="recetasPU")
    protected EntityManager em;
    
    public UsuarioEntity create(UsuarioEntity usuario ){
        em.persist(usuario);
        return usuario;
    } 
    
    public UsuarioEntity find(Long id ){
        UsuarioEntity usuario= em.find(UsuarioEntity.class, id);
        return usuario;
    }
    
    public UsuarioEntity resultCheck(List<UsuarioEntity> results) {
        UsuarioEntity usuario = null;
        if (results != null && !results.isEmpty()) {
            usuario = results.get(0);
        } 
        return usuario;
    }
    
    public List<UsuarioEntity> findAll( ){
        Query query = em.createQuery("select u from UsuarioEntity u", UsuarioEntity.class);
        return query.getResultList();
    }
    
    public UsuarioEntity update( UsuarioEntity usuario ){
        em.merge(usuario);
        UsuarioEntity usuarioAtualizado=em.find(UsuarioEntity.class, usuario.getId());
        return usuarioAtualizado;
    } 
    
    public void delete (Long id)
    {
        UsuarioEntity usuario= em.find(UsuarioEntity.class, id);
        em.remove(usuario);
    }
    
    public UsuarioEntity findByLogin(String login ){
        // Se crea un query para buscar usuarios con el login que recibe el método como argumento. ":login" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From UsuarioEntity e where e.login = :login", UsuarioEntity.class);
        // Se remplaza el placeholder ":isbn" con el valor del argumento 
        query = query.setParameter("login", login);
        // Se invoca el query se obtiene la lista resultado
        List<UsuarioEntity> sameLogin = query.getResultList();
        return resultCheck(sameLogin);
    }
    
    public UsuarioEntity findByCorreo(String correo ){
        // Se crea un query para buscar usuarios con el correo que recibe el método como argumento. ":correo" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From UsuarioEntity e where e.correo = :correo", UsuarioEntity.class);
        // Se remplaza el placeholder ":isbn" con el valor del argumento 
        query = query.setParameter("correo", correo);
        // Se invoca el query se obtiene la lista resultado
        List<UsuarioEntity> sameCorreo = query.getResultList();
        return resultCheck(sameCorreo);
    }
}

