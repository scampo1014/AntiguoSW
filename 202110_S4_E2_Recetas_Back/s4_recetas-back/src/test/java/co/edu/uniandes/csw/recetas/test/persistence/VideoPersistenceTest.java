/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;

import co.edu.uniandes.csw.recetas.entities.VideoEntity;
import co.edu.uniandes.csw.recetas.persistence.VideoPersistence;
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
public class VideoPersistenceTest {
    
    @Inject
    VideoPersistence persistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VideoEntity.class.getPackage())
                .addPackage(VideoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void testCreate() {
        
        PodamFactory factory = new PodamFactoryImpl();
        VideoEntity video = factory.manufacturePojo(VideoEntity.class);
        VideoEntity result = persistence.create(video);
        Assert.assertNotNull(result);
        
        VideoEntity entity =
                em.find(VideoEntity.class, result.getId());
        
        Assert.assertEquals(video.getFormato(), entity.getFormato());
    }
    
    @Test
    public void testFind()
    {
        
        PodamFactory factory =new PodamFactoryImpl();
        VideoEntity video;
     
        video= factory.manufacturePojo(VideoEntity.class);
        persistence.create(video);
        
        video=persistence.find(video.getId());
        
     
        Assert.assertNotNull(video);    
    }
    
    @Test
    public void testFindAll()
    {
        
        PodamFactory factory =new PodamFactoryImpl();
        List<VideoEntity> videos;
        VideoEntity video;

        video= factory.manufacturePojo(VideoEntity.class);
        persistence.create(video);

        videos=persistence.findAll();

        Assert.assertFalse(videos.isEmpty());
    }    
    
    @Test
    public void testUpdate()
    {
        
        PodamFactory factory =new PodamFactoryImpl();
        VideoEntity video;
        VideoEntity videoNuevo;

        video= factory.manufacturePojo(VideoEntity.class);
        persistence.create(video);
        video.setFormato("Blablabla");

        videoNuevo = persistence.update(video);

        Assert.assertEquals("Blablabla", videoNuevo.getFormato());
    }
    
    @Test
    public void testDelete() {
        
        PodamFactory factory = new PodamFactoryImpl();
        VideoEntity video = factory.manufacturePojo(VideoEntity.class);
        VideoEntity result = persistence.create(video);
        Assert.assertNotNull(result);
        
        VideoEntity entity = em.find(VideoEntity.class, result.getId());       
        Assert.assertEquals(video.getFormato(), entity.getFormato());
        
        persistence.delete(entity.getId());
        VideoEntity eliminado = em.find(VideoEntity.class, result.getId());
        Assert.assertNull(eliminado);
    }
    
}
