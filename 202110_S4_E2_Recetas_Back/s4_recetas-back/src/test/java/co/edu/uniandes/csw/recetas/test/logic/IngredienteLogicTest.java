/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.logic;

import co.edu.uniandes.csw.recetas.ejb.IngredienteLogic;
import co.edu.uniandes.csw.recetas.entities.IngredienteEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.IngredientePersistence;
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
 * @author Maria Valentina Garcia
 */
@RunWith(Arquillian.class)
public class IngredienteLogicTest {
    
    @Inject
    private IngredienteLogic ingredienteLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<IngredienteEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(IngredienteEntity.class.getPackage())
                .addPackage(IngredientePersistence.class.getPackage())
                .addPackage(IngredienteLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
    
    /**
    * Limpia las tablas que están implicadas en la prueba.
    */
    private void clearData() {
        em.createQuery("delete from IngredienteEntity").executeUpdate();
    }

    /**
        * Inserta los datos iniciales para el correcto funcionamiento de las
        * pruebas.
    */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            IngredienteEntity entity = factory.manufacturePojo(IngredienteEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createIngredienteTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        IngredienteEntity newEntity = factory.manufacturePojo(IngredienteEntity.class);
        newEntity.setPrecio(10000);
        IngredienteEntity result = ingredienteLogic.createIngrediente(newEntity);
        Assert.assertNotNull(result);

        IngredienteEntity entity = em.find(IngredienteEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getPrecio(), newEntity.getPrecio());
        Assert.assertEquals(entity.getCantidad(), newEntity.getCantidad());
        
        //Una receta no puede tener dos ingredientes con el mismo nombre

        //El precio no puede ser negativo
        IngredienteEntity newEntity2 = factory.manufacturePojo(IngredienteEntity.class);
        newEntity2.setPrecio(-10000);    
        
        IngredienteEntity result2=null;
        try {
            result2 = ingredienteLogic.createIngrediente(newEntity2);
        } catch (Exception e) {
            Assert.assertEquals("El precio no puede ser negativo", e.getMessage());
        }
        Assert.assertNull(result2);
        
        //El precio no puede ser mayor a 50000
        IngredienteEntity newEntity3 = factory.manufacturePojo(IngredienteEntity.class);
        newEntity3.setPrecio(60000);
        IngredienteEntity result3=null;
        try {
            result3 = ingredienteLogic.createIngrediente(newEntity3);
        } catch (Exception e) {
            Assert.assertEquals("El precio del ingrediente es muy alto", e.getMessage());
        }
        Assert.assertNull(result3);
    }
    
    @Test
    public void getIngredientesTest() {
        List<IngredienteEntity> list = ingredienteLogic.getIngredientes();
        Assert.assertEquals(data.size(), list.size());
        for (IngredienteEntity entity : list) {
            boolean found = false;
            for (IngredienteEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getIngrediente(Long ingredienteId) {
        IngredienteEntity entity = data.get(0);
        IngredienteEntity resultEntity = ingredienteLogic.getIngrediente(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getPrecio(), resultEntity.getPrecio());
        Assert.assertEquals(entity.getCantidad(), resultEntity.getCantidad());
    }
    
    @Test
    public void updateIngredienteTest() {
        PodamFactory factory = new PodamFactoryImpl();
        IngredienteEntity entity = data.get(0);
        IngredienteEntity pojoEntity = factory.manufacturePojo(IngredienteEntity.class);

        pojoEntity.setId(entity.getId());

        ingredienteLogic.updateIngrediente(pojoEntity.getId(), pojoEntity);

        IngredienteEntity resp = em.find(IngredienteEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getPrecio(), resp.getPrecio());
        Assert.assertEquals(pojoEntity.getCantidad(), resp.getCantidad());
    }
    
    @Test
    public void deleteIngredienteTest() throws BusinessLogicException {
        IngredienteEntity entity = data.get(0);
        ingredienteLogic.deleteIngrediente(entity.getId());
        IngredienteEntity deleted = em.find(IngredienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
