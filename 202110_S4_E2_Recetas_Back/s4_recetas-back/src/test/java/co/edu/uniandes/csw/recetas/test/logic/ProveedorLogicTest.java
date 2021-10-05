/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.logic;
;


import co.edu.uniandes.csw.recetas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.ProveedorPersistence;
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
public class ProveedorLogicTest {
    
    @Inject
    private ProveedorLogic ProveedorLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utt;
    
    private List<ProveedorEntity> data = new ArrayList<>();

    @Deployment    
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProveedorEntity.class.getPackage())
                .addPackage(ProveedorPersistence.class.getPackage())
                .addPackage(ProveedorLogic.class.getPackage())
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
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }

   
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    
    
    
    
    @Test
    public void createProveedorTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        ProveedorEntity eentity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity proveedor = ProveedorLogic.createProveedor(eentity);
        Assert.assertNotNull(proveedor);

        ProveedorEntity entity0 = em.find(ProveedorEntity.class, proveedor.getId());
        
        Assert.assertEquals(entity0.getLogin(), eentity.getLogin());
        Assert.assertEquals(entity0.getCorreo(), eentity.getCorreo());
        Assert.assertEquals(entity0.getContrasenia(), eentity.getContrasenia());
        Assert.assertEquals(entity0.getNombre(), eentity.getNombre());
       
        ProveedorEntity Entity1 = factory.manufacturePojo(ProveedorEntity.class);
        Entity1.setLogin(entity0.getLogin());
        ProveedorEntity result1=null;
        try {
            result1 = ProveedorLogic.createProveedor(Entity1);
        } catch (Exception e) {
            Assert.assertEquals("Ya existe un proveedor con este login"+ Entity1.getLogin(), e.getMessage());
        }
        Assert.assertNull(result1);
        
        
         //correo
        ProveedorEntity Entity2 = factory.manufacturePojo(ProveedorEntity.class);
        Entity2.setCorreo(entity0.getCorreo());
        ProveedorEntity result2=null;
        try {
            result2 = ProveedorLogic.createProveedor(Entity2);
        } catch (Exception e) {
            Assert.assertEquals("Ya existe un proveedor con este correo"+ Entity2.getCorreo(), e.getMessage());
        }
        Assert.assertNull(result2);
        
        //contrasenia
        
        
        ProveedorEntity Entity3 = factory.manufacturePojo(ProveedorEntity.class);
        Entity3.setContrasenia(entity0.getContrasenia());
        Entity3.setContrasenia("123");
         ProveedorEntity result3=null;
        try {
            result3 = ProveedorLogic.createProveedor(Entity3);
        } catch (Exception e) {
            Assert.assertEquals("No se puede utilizar esta contrasenia", e.getMessage());
        }
        Assert.assertNull(result3);
        
        //nombre
        
        ProveedorEntity Entity4 = factory.manufacturePojo(ProveedorEntity.class);
        Entity4.setNombre(entity0.getNombre());
        ProveedorEntity result4=null;
        try {
            result4 = ProveedorLogic.createProveedor(Entity4);
        } catch (Exception e) {
            Assert.assertEquals("Ya existe un proveedor con este Nombre"+ Entity4.getNombre(), e.getMessage());
        }
        Assert.assertNull(result4);
    }
    @Test
    public void getProveedorTest() {
        List<ProveedorEntity> list = ProveedorLogic.getProveedores();
        Assert.assertEquals(data.size(), list.size());
        for (ProveedorEntity entity : list) {
            boolean found = false;
            for (ProveedorEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getProveedor(Long id) {
        ProveedorEntity entity = data.get(0);
        ProveedorEntity resultEntity = ProveedorLogic.getProveedor(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getLogin(), resultEntity.getLogin());
        Assert.assertEquals(entity.getCorreo(), resultEntity.getCorreo());
        Assert.assertEquals(entity.getContrasenia(), resultEntity.getContrasenia());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
    }
    
    @Test
    public void updateProveedorTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        ProveedorEntity entity = data.get(0);
        ProveedorEntity pEntity = factory.manufacturePojo(ProveedorEntity.class);

        pEntity.setId(entity.getId());

        ProveedorLogic.updateProveedor(pEntity.getId(), pEntity);

        ProveedorEntity resp = em.find(ProveedorEntity.class, entity.getId());

        Assert.assertEquals(pEntity.getId(), resp.getId());
        Assert.assertEquals(pEntity.getLogin(), resp.getLogin());
        Assert.assertEquals(pEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pEntity.getCorreo(), resp.getCorreo());
        Assert.assertEquals(pEntity.getContrasenia(), resp.getContrasenia());
    }
    
    @Test
    public void deleteProveedorTest() throws BusinessLogicException {
        ProveedorEntity entity = data.get(0);
        ProveedorLogic.deleteProveedor(entity.getId());
        ProveedorEntity deleted = em.find(ProveedorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
