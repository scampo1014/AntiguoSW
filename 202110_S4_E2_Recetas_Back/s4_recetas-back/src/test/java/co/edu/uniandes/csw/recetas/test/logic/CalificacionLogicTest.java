/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.logic;

import co.edu.uniandes.csw.recetas.ejb.CalificacionLogic;
import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
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
@RunWith(Arquillian.class)
public class CalificacionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<CalificacionEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Inject
    private CalificacionLogic calificacionLogic;
    
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }

    /**
        * Inserta los datos iniciales para el correcto funcionamiento de las
        * pruebas.
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
    public void createCalificacionTest() throws BusinessLogicException
    {
        
        CalificacionEntity prueba= factory.manufacturePojo(CalificacionEntity.class);
        prueba.setPuntos(3.0);
        CalificacionEntity calificacion=calificacionLogic.create(prueba);
        Assert.assertNotNull(calificacion);
        
        CalificacionEntity entity = em.find(CalificacionEntity.class, calificacion.getId());
        Assert.assertEquals(entity.getId(), prueba.getId());
        Assert.assertEquals(entity.getPuntos(), prueba.getPuntos());
        
        CalificacionEntity prueba2= factory.manufacturePojo(CalificacionEntity.class);
        prueba2.setPuntos(7.0);
        
        CalificacionEntity calificacion2=null;
        try{
            calificacion2= calificacionLogic.create(prueba2);
        }
        catch(Exception e)
        {
            Assert.assertEquals("La calificación debe estar entre 1.0 y 5.0", e.getMessage());
        }
        
        
    }
    @Test
    public void getCalificacionTest(Long calificacionId) {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity =calificacionLogic.getCalificacion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getPuntos(), resultEntity.getPuntos());
        Assert.assertEquals(entity.getCalificador(), resultEntity.getCalificador());
    }
    @Test
    public void getCalificacionesTest() {
        List<CalificacionEntity> list = calificacionLogic.getCalificaciones();
        Assert.assertEquals(data.size(), list.size());
        for (CalificacionEntity entity : list) {
            boolean found = false;
            for (CalificacionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    @Test
    public void updateCalificacionTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        pojoEntity.setPuntos(3.0);
        pojoEntity.setId(entity.getId());
        calificacionLogic.updateCalificacion(pojoEntity);
        
        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getPuntos(), resp.getPuntos());
        Assert.assertEquals(pojoEntity.getCalificador(), resp.getCalificador());
        
        CalificacionEntity calificacion = factory.manufacturePojo(CalificacionEntity.class);
        calificacion.setPuntos(7.0);
        try{
            calificacionLogic.updateCalificacion(calificacion);
        }
        catch(Exception e)
        {
            Assert.assertEquals("La calificación debe estar entre 1.0 y 5.0", e.getMessage());
        }
        
    }
    @Test
    public void deleteCalificacionTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        calificacionLogic.deleteCalificacion(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
