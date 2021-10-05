/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;

import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import co.edu.uniandes.csw.recetas.persistence.ComentarioPersistence;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Santiago Campo
 */
@RunWith(Arquillian.class)
public class ComentarioPersistenceTest {
    
    @Inject
    ComentarioPersistence persistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void testCreate() {
        
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        ComentarioEntity result = persistence.create(comentario);
        Assert.assertNotNull(result);
        
        ComentarioEntity entity =
                em.find(ComentarioEntity.class, result.getId());
        
        Assert.assertEquals(comentario.getComentario(), entity.getComentario());
    }
    
    @Test
    public void testFind()
    {
        
        PodamFactory factory =new PodamFactoryImpl();
        ComentarioEntity comentario;
     
        comentario= factory.manufacturePojo(ComentarioEntity.class);
        persistence.create(comentario);
        
        comentario=persistence.find(comentario.getId());
        
     
        Assert.assertNotNull(comentario);    
    }
    
    @Test
    public void testFindAll()
    {
        
        PodamFactory factory =new PodamFactoryImpl();
        List<ComentarioEntity> comentarios;
        ComentarioEntity comentario;

        comentario= factory.manufacturePojo(ComentarioEntity.class);
        persistence.create(comentario);

        comentarios=persistence.findAll();

        Assert.assertFalse(comentarios.isEmpty());
    }    
    
    @Test
    public void testUpdate()
    {
        
        PodamFactory factory =new PodamFactoryImpl();
        ComentarioEntity comentario;
        ComentarioEntity comentarioNuevo;

        comentario= factory.manufacturePojo(ComentarioEntity.class);
        persistence.create(comentario);
        comentario.setComentario("Blablabla");

        comentarioNuevo = persistence.update(comentario);

        Assert.assertEquals("Blablabla", comentarioNuevo.getComentario());
    }
    
    @Test
    public void testDelete() {
        
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        ComentarioEntity result = persistence.create(comentario);
        Assert.assertNotNull(result);
        
        ComentarioEntity entity = em.find(ComentarioEntity.class, result.getId());       
        Assert.assertEquals(comentario.getComentario(), entity.getComentario());
        
        persistence.delete(entity.getId());
        ComentarioEntity eliminado = em.find(ComentarioEntity.class, result.getId());
        Assert.assertNull(eliminado);
    }
}
