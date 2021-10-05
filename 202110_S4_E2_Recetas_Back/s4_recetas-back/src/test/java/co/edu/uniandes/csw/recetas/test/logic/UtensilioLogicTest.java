/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.logic;

import co.edu.uniandes.csw.recetas.ejb.UtensilioLogic;
import co.edu.uniandes.csw.recetas.entities.UtensilioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.UtensilioPersistence;
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
public class UtensilioLogicTest {
    
    @Inject
    private UtensilioLogic utensilioLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<UtensilioEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UtensilioEntity.class.getPackage())
                .addPackage(UtensilioPersistence.class.getPackage())
                .addPackage(UtensilioLogic.class.getPackage())
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
        em.createQuery("delete from UtensilioEntity").executeUpdate();
    }

    /**
        * Inserta los datos iniciales para el correcto funcionamiento de las
        * pruebas.
    */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            UtensilioEntity entity = factory.manufacturePojo(UtensilioEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createUtensilioTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        UtensilioEntity newEntity = factory.manufacturePojo(UtensilioEntity.class);
        newEntity.setPrecio(10000);
        UtensilioEntity result = utensilioLogic.createUtensilio(newEntity);
        Assert.assertNotNull(result);

        UtensilioEntity entity = em.find(UtensilioEntity.class, result.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getPrecio(), newEntity.getPrecio());
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        
        //Una receta no puede tener dos utensilios con el mismo nombre

        //El precio no puede ser negativo
        UtensilioEntity newEntity2 = factory.manufacturePojo(UtensilioEntity.class);
        newEntity2.setPrecio(-10000);    
        
        UtensilioEntity result2=null;
        try {
            result2 = utensilioLogic.createUtensilio(newEntity2);
        } catch (Exception e) {
            Assert.assertEquals("El precio no puede ser negativo", e.getMessage());
        }
        Assert.assertNull(result2);
        
        //El precio no puede ser mayor a 500000
        UtensilioEntity newEntity3 = factory.manufacturePojo(UtensilioEntity.class);
        newEntity3.setPrecio(600000);
        UtensilioEntity result3=null;
        try {
            result3 = utensilioLogic.createUtensilio(newEntity3);
        } catch (Exception e) {
            Assert.assertEquals("El precio del utensilio es muy alto", e.getMessage());
        }
        Assert.assertNull(result3);
    }
    
    @Test
    public void getUtensiliosTest() {
        List<UtensilioEntity> list = utensilioLogic.getUtensilios();
        Assert.assertEquals(data.size(), list.size());
        for (UtensilioEntity entity : list) {
            boolean found = false;
            for (UtensilioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getUtensilio(Long utensilioId) {
        UtensilioEntity entity = data.get(0);
        UtensilioEntity resultEntity = utensilioLogic.getUtensilio(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getPrecio(), resultEntity.getPrecio());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());

    }
    
    @Test
    public void updateUtensilioTest() {
        PodamFactory factory = new PodamFactoryImpl();
        UtensilioEntity entity = data.get(0);
        UtensilioEntity pojoEntity = factory.manufacturePojo(UtensilioEntity.class);

        pojoEntity.setId(entity.getId());

        utensilioLogic.updateUtensilio(pojoEntity.getId(), pojoEntity);

        UtensilioEntity resp = em.find(UtensilioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getPrecio(), resp.getPrecio());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
    }
    
    @Test
    public void deleteUtensilioTest() throws BusinessLogicException {
        UtensilioEntity entity = data.get(0);
        utensilioLogic.deleteUtensilio(entity.getId());
        UtensilioEntity deleted = em.find(UtensilioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    
}
