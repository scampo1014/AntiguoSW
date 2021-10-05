/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.persistence;

import co.edu.uniandes.csw.recetas.entities.AnuncioEntity;
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
public class AnuncioPersistence {

    @PersistenceContext(unitName = "recetasPU")
    protected EntityManager em;

    public AnuncioEntity create(AnuncioEntity anuncio) {
        em.persist(anuncio);
        return anuncio;
    }

    public AnuncioEntity find(Long id) {
        AnuncioEntity anuncio = em.find(AnuncioEntity.class, id);
        return anuncio;
    }
    
    public AnuncioEntity resultCheck(List<AnuncioEntity> results) {
        AnuncioEntity anuncio = null;
        if (results != null && !results.isEmpty()) {
            anuncio = results.get(0);
        } 
        return anuncio;
    }

    public AnuncioEntity findByProveedor(Long proveedoresId, Long anunciosId) {
        TypedQuery<AnuncioEntity> q = em.createQuery("select p from ProveedorEntity p where (p.proveedor.id = :proveedorid) and (p.id = :anunciosId)", AnuncioEntity.class);
        q.setParameter("proveedorid", proveedoresId);
        q.setParameter("anunciosId", anunciosId);
        List<AnuncioEntity> results = q.getResultList();
        return resultCheck(results);
    }

    public List<AnuncioEntity> findAll() {
        Query query = em.createQuery("select u from AnuncioEntity u", AnuncioEntity.class);
        return query.getResultList();
    }

    public AnuncioEntity update(AnuncioEntity anuncio) {
        em.merge(anuncio);
        AnuncioEntity anuncioAct = em.find(AnuncioEntity.class, anuncio.getId());
        return anuncioAct;
    }

    public void delete(Long id) {
        AnuncioEntity anuncio = em.find(AnuncioEntity.class, id);
        em.remove(anuncio);
    }
}
