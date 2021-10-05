/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.logic;

import co.edu.uniandes.csw.recetas.ejb.VideoLogic;
import co.edu.uniandes.csw.recetas.entities.VideoEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.VideoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Santiago Campo
 */
@RunWith(Arquillian.class)
public class VideoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private VideoLogic videoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<VideoEntity> data = new ArrayList<>();       
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VideoEntity.class.getPackage())
                .addPackage(VideoLogic.class.getPackage())
                .addPackage(VideoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    private void clearData() {
        em.createQuery("delete from VideoEntity").executeUpdate();
    }
    
    
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            VideoEntity entity = factory.manufacturePojo(VideoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
   
    @Test
    public void createVideoTest() throws BusinessLogicException {
        VideoEntity newEntity = factory.manufacturePojo(VideoEntity.class);
        newEntity.setDuracion(1);
        VideoEntity result = videoLogic.createVideo(newEntity);
        Assert.assertNotNull(result);
        VideoEntity entity = em.find(VideoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Reglas de Negocio 
     */
    
    
    @Test(expected = BusinessLogicException.class)
    public void createVideoConMismaDireccionTest() throws BusinessLogicException {
        VideoEntity newEntity = factory.manufacturePojo(VideoEntity.class);
        newEntity.setDireccion(data.get(0).getDireccion());
        videoLogic.createVideo(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createVideoConTamanioIncorrecto() throws BusinessLogicException {
        VideoEntity newEntity = factory.manufacturePojo(VideoEntity.class);
        newEntity.setTamanio(700000000.0);
        videoLogic.createVideo(newEntity);
        
        newEntity.setTamanio(-2.3);
        videoLogic.createVideo(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createVideoConDuracionIncorrecto() throws BusinessLogicException {
        VideoEntity newEntity = factory.manufacturePojo(VideoEntity.class);
        newEntity.setDuracion(700);
        videoLogic.createVideo(newEntity);
        
        newEntity.setDuracion(-3);
        videoLogic.createVideo(newEntity);
    }
    
    /**
     * ///////////////////////////////
     */
    
    @Test
    public void getVideosTest() {
        List<VideoEntity> list = videoLogic.getVideos();
        Assert.assertEquals(data.size(), list.size());
        for (VideoEntity entity : list) {
            boolean found = false;
            for (VideoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getVideoTest() {
        VideoEntity entity = data.get(0);
        VideoEntity resultEntity = videoLogic.getVideo(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }

    @Test
    public void updateVideoTest() {
        VideoEntity entity = data.get(0);
        VideoEntity pojoEntity = factory.manufacturePojo(VideoEntity.class);
        pojoEntity.setId(entity.getId());
        videoLogic.updateVideo(pojoEntity.getId(), pojoEntity);
        VideoEntity resp = em.find(VideoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    @Test
    public void deleteVideoTest() throws BusinessLogicException {
        VideoEntity entity = data.get(1);
        videoLogic.deleteVideo(entity.getId());
        VideoEntity deleted = em.find(VideoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
