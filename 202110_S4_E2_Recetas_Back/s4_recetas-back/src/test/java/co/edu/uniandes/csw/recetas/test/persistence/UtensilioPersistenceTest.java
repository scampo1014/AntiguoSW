/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;

import co.edu.uniandes.csw.recetas.entities.UtensilioEntity;
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
public class UtensilioPersistenceTest {
    
    @Inject
    UtensilioPersistence persistence;
    
        /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;
    
     /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba.
     */
    private List<UtensilioEntity> data = new ArrayList<UtensilioEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UtensilioEntity.class.getPackage())
                .addPackage(UtensilioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
        /**
     * Configuración inicial de la prueba.
     */
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
        em.createQuery("delete from UtensilioEntity").executeUpdate();
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

            UtensilioEntity entity = factory.manufacturePojo(UtensilioEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void testCreate()
    {
        
     PodamFactory factory =new PodamFactoryImpl();
     UtensilioEntity utensilio;
     
     utensilio= factory.manufacturePojo(UtensilioEntity.class);
     utensilio=persistence.create(utensilio);
     
     Assert.assertNotNull(utensilio);
     UtensilioEntity otroUtensilio = persistence.find(utensilio.getId());
    Assert.assertNotNull(otroUtensilio);
    Assert.assertEquals(utensilio.getNombre(), otroUtensilio.getNombre());
    Assert.assertEquals(utensilio.getPrecio(), otroUtensilio.getPrecio());
    Assert.assertEquals(utensilio.getDescripcion(), otroUtensilio.getDescripcion());
        
    }
    
    @Test
    public void testFind()
    {
        
     UtensilioEntity utensilio = data.get(0);
     UtensilioEntity otroUtensilio = persistence.find(utensilio.getId());
     Assert.assertNotNull(otroUtensilio);
     Assert.assertEquals(utensilio.getNombre(), otroUtensilio.getNombre());
     Assert.assertEquals(utensilio.getPrecio(), otroUtensilio.getPrecio());
     Assert.assertEquals(utensilio.getDescripcion(), otroUtensilio.getDescripcion());
    }
    
    @Test
    public void testFindAll()
    {
        List<UtensilioEntity> utensilios;

        utensilios = persistence.findAll();

        Assert.assertFalse(utensilios.isEmpty());
    }    
    
    @Test
    public void testUpdate()
    {
        
     PodamFactory factory =new PodamFactoryImpl();
     UtensilioEntity utensilio;
     UtensilioEntity utensilioActualizado;
     
     utensilio= factory.manufacturePojo(UtensilioEntity.class);
     persistence.create(utensilio);
     utensilio.setNombre("otroNombre");
     
     utensilioActualizado=persistence.update(utensilio);
     
     Assert.assertEquals("otroNombre", utensilioActualizado.getNombre());
     Assert.assertEquals(utensilio.getPrecio(), utensilioActualizado.getPrecio());
     Assert.assertEquals(utensilio.getDescripcion(), utensilioActualizado.getDescripcion());
    }   
    
    @Test
    public void deleteTest() {
        UtensilioEntity entity = data.get(0);
        persistence.delete(entity.getId());
        UtensilioEntity deleted = em.find(UtensilioEntity.class,entity.getId());
        Assert.assertNull(deleted);
    }
    
}
