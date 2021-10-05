/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;

import co.edu.uniandes.csw.recetas.entities.MetodoPagoEntity;
import co.edu.uniandes.csw.recetas.persistence.MetodoPagoPersistence;
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
 * @author juliantorres
 */
@RunWith(Arquillian.class)
public class MetodoPagoPersistenceTest {
    @Inject
    MetodoPagoPersistence persistence;
    
    
    @Inject 
    UserTransaction ut;
    
    
    @PersistenceContext
    private EntityManager em;
    
     private List<MetodoPagoEntity> data = new ArrayList<MetodoPagoEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MetodoPagoEntity.class.getPackage())
                .addPackage(MetodoPagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
    @Before
    public void configTest() {
        try {
            ut.begin();
            em.joinTransaction();
            clearData();
            insertData();
            ut.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ut.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
     private void clearData() {
        em.createQuery("delete from MetodoPagoEntity").executeUpdate();
    }
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            MetodoPagoEntity entity = factory.manufacturePojo(MetodoPagoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    @Test
    public void testCreate() {
        
        PodamFactory factory = new PodamFactoryImpl();
        MetodoPagoEntity metodoPago;
        
       metodoPago= factory.manufacturePojo(MetodoPagoEntity.class);
        metodoPago=persistence.create(metodoPago);
        
       Assert.assertNotNull(metodoPago);
       MetodoPagoEntity otroMetodo = persistence.find(metodoPago.getId());
   
       Assert.assertNotNull(otroMetodo);
       Assert.assertEquals(metodoPago.getMetodoPago(), otroMetodo.getMetodoPago());
        }
    
    @Test
    public void testFind()
    {
        
     MetodoPagoEntity metodoPago = data.get(0);
     MetodoPagoEntity otroMetodo = persistence.find(metodoPago.getId());
     Assert.assertNotNull(otroMetodo);
     Assert.assertEquals(metodoPago.getMetodoPago(), otroMetodo.getMetodoPago());   
    }
    
    @Test
    public void testFindAll()
      {
        List<MetodoPagoEntity> metodoPago;

        metodoPago = persistence.findAll();

        Assert.assertFalse(metodoPago.isEmpty());
    } 
    
    @Test
    public void testUpdate()
    {
        
        PodamFactory factory =new PodamFactoryImpl();
     MetodoPagoEntity metodoPago;
     MetodoPagoEntity metodoAct;
     
     metodoPago= factory.manufacturePojo(MetodoPagoEntity.class);
     persistence.create(metodoPago);
     metodoPago.setMetodoPago("otroMetodo");
     
     metodoAct=persistence.update(metodoPago);
     
     Assert.assertEquals("otroMetodo", metodoAct.getMetodoPago());
    }
    
    @Test
    public void testDelete() {
        
         MetodoPagoEntity entity = data.get(0);
        persistence.delete(entity.getId());
        MetodoPagoEntity deleted = em.find(MetodoPagoEntity.class,entity.getId());
        Assert.assertNull(deleted);
    }
}
