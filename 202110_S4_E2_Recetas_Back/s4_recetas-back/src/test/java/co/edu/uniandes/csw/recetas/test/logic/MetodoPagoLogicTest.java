/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.logic;

import co.edu.uniandes.csw.recetas.ejb.MetodoPagoLogic;
import co.edu.uniandes.csw.recetas.entities.MetodoPagoEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
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
public class MetodoPagoLogicTest 
{
    @Inject
    private MetodoPagoLogic MetodoPagoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utt;
    
    private List<MetodoPagoEntity> data = new ArrayList<>();

    @Deployment    
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MetodoPagoEntity.class.getPackage())
                .addPackage(MetodoPagoPersistence.class.getPackage())
                .addPackage(MetodoPagoLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    @Before
    public void configTest() 
    {
        try {
            utt.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utt.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utt.rollback();
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
    public void createMetodoPagoTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        MetodoPagoEntity eentity = factory.manufacturePojo(MetodoPagoEntity.class);
       
        MetodoPagoEntity metodo = MetodoPagoLogic.createMetodoPagoEntity(eentity);
        Assert.assertNotNull(metodo);

        MetodoPagoEntity entity0 = em.find(MetodoPagoEntity.class, metodo.getId());
        
        Assert.assertEquals(entity0.getMetodoPago(), eentity.getMetodoPago());
        
        MetodoPagoEntity Entity1 = factory.manufacturePojo(MetodoPagoEntity.class);
        Entity1.setMetodoPago(entity0.getMetodoPago());
        MetodoPagoEntity result1=null;
        try {
            result1 = MetodoPagoLogic.createMetodoPagoEntity(Entity1);
        } catch (Exception e) {
            Assert.assertEquals("Ya existe un metodo de pago", e.getMessage());
        }
    }
    @Test
    public void getMetodoPagoTest() {
        List<MetodoPagoEntity> list = MetodoPagoLogic.getMetodos();
        Assert.assertEquals(data.size(), list.size());
        for (MetodoPagoEntity entity : list) {
            boolean found = false;
            for (MetodoPagoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getMetodoPago(Long id) {
        MetodoPagoEntity entity = data.get(0);
        MetodoPagoEntity resultEntity = MetodoPagoLogic.getMetodo(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getMetodoPago(), resultEntity.getMetodoPago()); 
    }
    
    @Test
    public void updateMetodoPagoTest()  {
        PodamFactory factory = new PodamFactoryImpl();
       MetodoPagoEntity entity = data.get(0);
       MetodoPagoEntity pEntity = factory.manufacturePojo(MetodoPagoEntity.class);

        pEntity.setId(entity.getId());

        MetodoPagoLogic.updateMetodoPago(pEntity.getId(), pEntity);

        MetodoPagoEntity resp = em.find(MetodoPagoEntity.class, entity.getId());

        Assert.assertEquals(pEntity.getId(), resp.getId());
        Assert.assertEquals(pEntity.getMetodoPago(), resp.getMetodoPago());
    }
    
    @Test
    public void deleteMetodoPagoTest() throws BusinessLogicException {
        MetodoPagoEntity entity = data.get(0);
        MetodoPagoLogic.deleteMetodoDePago(entity.getId());
        MetodoPagoEntity deleted = em.find(MetodoPagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
