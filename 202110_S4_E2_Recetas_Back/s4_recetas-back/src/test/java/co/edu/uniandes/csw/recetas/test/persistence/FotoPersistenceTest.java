/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;

import co.edu.uniandes.csw.recetas.entities.FotoEntity;
import co.edu.uniandes.csw.recetas.persistence.FotoPersistence;
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
public class FotoPersistenceTest {
    
    @Inject
    FotoPersistence persistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FotoEntity.class.getPackage())
                .addPackage(FotoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void testCreate() {
        
        PodamFactory factory = new PodamFactoryImpl();
        FotoEntity foto = factory.manufacturePojo(FotoEntity.class);
        FotoEntity result = persistence.create(foto);
        Assert.assertNotNull(result);
        
        FotoEntity entity =
                em.find(FotoEntity.class, result.getId());
        
        Assert.assertEquals(foto.getFormato(), entity.getFormato());
    }
    
    @Test
    public void testFind()
    {
        
        PodamFactory factory =new PodamFactoryImpl();
        FotoEntity foto;
     
        foto= factory.manufacturePojo(FotoEntity.class);
        persistence.create(foto);
        
        foto=persistence.find(foto.getId());
        
     
        Assert.assertNotNull(foto);    
    }
    
    @Test
    public void testFindAll()
    {
        
        PodamFactory factory =new PodamFactoryImpl();
        List<FotoEntity> fotos;
        FotoEntity foto;

        foto= factory.manufacturePojo(FotoEntity.class);
        persistence.create(foto);

        fotos=persistence.findAll();

        Assert.assertFalse(fotos.isEmpty());
    }    
    
    @Test
    public void testUpdate()
    {
        
        PodamFactory factory =new PodamFactoryImpl();
        FotoEntity foto;
        FotoEntity fotoNuevo;

        foto= factory.manufacturePojo(FotoEntity.class);
        persistence.create(foto);
        foto.setFormato("Blablabla");

        fotoNuevo = persistence.update(foto);

        Assert.assertEquals("Blablabla", fotoNuevo.getFormato());
    }
    
    @Test
    public void testDelete() {
        
        PodamFactory factory = new PodamFactoryImpl();
        FotoEntity foto = factory.manufacturePojo(FotoEntity.class);
        FotoEntity result = persistence.create(foto);
        Assert.assertNotNull(result);
        
        FotoEntity entity = em.find(FotoEntity.class, result.getId());       
        Assert.assertEquals(foto.getFormato(), entity.getFormato());
        
        persistence.delete(entity.getId());
        FotoEntity eliminado = em.find(FotoEntity.class, result.getId());
        Assert.assertNull(eliminado);
    }
}
