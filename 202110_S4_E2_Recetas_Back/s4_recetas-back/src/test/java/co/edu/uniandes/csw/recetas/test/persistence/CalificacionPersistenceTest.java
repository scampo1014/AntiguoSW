/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.persistence.CalificacionPersistence;
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
 * @author Ingrith Barbosa
 */
@RunWith (Arquillian.class)
public class CalificacionPersistenceTest {
    
    @Inject
    private CalificacionPersistence persistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();
 
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
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

            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void createCalificacionTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        
        CalificacionEntity prueba= factory.manufacturePojo(CalificacionEntity.class);
        CalificacionEntity calificacion= persistence.create(prueba);
        
        Assert.assertNotNull(calificacion);
        
        CalificacionEntity entity= persistence.find(prueba.getId());
        
        Assert.assertEquals(calificacion.getId(), entity.getId());
        Assert.assertEquals(calificacion.getCalificador(), entity.getCalificador());
        Assert.assertEquals(calificacion.getPuntos(), entity.getPuntos());
        Assert.assertEquals(calificacion.getComentario(), entity.getComentario());
        Assert.assertEquals(calificacion.getUsuario(), entity.getUsuario());
        
    }
    @Test
    public void findAllTest() {
        List<CalificacionEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CalificacionEntity ent : list) {
            boolean found = false;
            for (CalificacionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    @Test
    public void findTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity newEntity = persistence.find(entity.getId());
        
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getCalificador(), newEntity.getCalificador());
        Assert.assertEquals(entity.getUsuario(), newEntity.getUsuario());
        Assert.assertEquals(entity.getComentario(), newEntity.getComentario());
        Assert.assertEquals(entity.getPuntos(), newEntity.getPuntos());
    }
    @Test
    public void deleteTest() {
        CalificacionEntity entity = data.get(0);
        persistence.delete(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    @Test
    public void updateTest() {
        CalificacionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getCalificador(), resp.getCalificador());
        Assert.assertEquals(newEntity.getPuntos(), resp.getPuntos());
        Assert.assertEquals(newEntity.getUsuario(), resp.getUsuario());
        Assert.assertEquals(newEntity.getComentario(), resp.getComentario());
    }
    
}
