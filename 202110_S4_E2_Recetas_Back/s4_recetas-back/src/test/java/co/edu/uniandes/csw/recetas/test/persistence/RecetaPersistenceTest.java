/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;


import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.persistence.RecetaPersistence;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Before;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Ingrith Barbosa
 */
@RunWith (Arquillian.class)
public class RecetaPersistenceTest {
    
    @Inject
    private RecetaPersistence persistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<RecetaEntity> data = new ArrayList<RecetaEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecetaEntity.class.getPackage())
                .addPackage(RecetaPersistence.class.getPackage())
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
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from RecetaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            RecetaEntity entity = factory.manufacturePojo(RecetaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    @Test
    public void createRecetaTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        
        RecetaEntity prueba= factory.manufacturePojo(RecetaEntity.class);
        RecetaEntity receta= persistence.create(prueba);
        
        Assert.assertNotNull(receta);
        
        RecetaEntity entity= persistence.find(prueba.getId());
        
        Assert.assertEquals(receta.getId(), entity.getId());
        Assert.assertEquals(receta.getNombre(), entity.getNombre());
        Assert.assertEquals(receta.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(receta.getUsuario(), entity.getUsuario());
        
    }
    @Test
    public void findAllTest() {
        List<RecetaEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (RecetaEntity ent : list) {
            boolean found = false;
            for (RecetaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    @Test
    public void findTest() {
        RecetaEntity entity = data.get(0);
        RecetaEntity newEntity = persistence.find(entity.getId());
        
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
    }
    @Test
    public void deleteTest() {
        RecetaEntity entity = data.get(0);
        persistence.delete(entity.getId());
        RecetaEntity deleted = em.find(RecetaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    @Test
    public void updateTest() {
        RecetaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        RecetaEntity newEntity = factory.manufacturePojo(RecetaEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        RecetaEntity resp = em.find(RecetaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }
    
    
}
