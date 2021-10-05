/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;

import co.edu.uniandes.csw.recetas.entities.IngredienteEntity;
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
public class IngredientePersistenceTest {
    
    @Inject
    IngredientePersistence persistence;
    
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
    private List<IngredienteEntity> data = new ArrayList<IngredienteEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(IngredienteEntity.class.getPackage())
                .addPackage(IngredientePersistence.class.getPackage())
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
        em.createQuery("delete from IngredienteEntity").executeUpdate();
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

            IngredienteEntity entity = factory.manufacturePojo(IngredienteEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void testCreate()
    {
        
     PodamFactory factory =new PodamFactoryImpl();
     IngredienteEntity ingrediente;
     
     ingrediente= factory.manufacturePojo(IngredienteEntity.class);
     ingrediente=persistence.create(ingrediente);
     
     Assert.assertNotNull(ingrediente);
     IngredienteEntity otroIngrediente = persistence.find(ingrediente.getId());
     Assert.assertNotNull(otroIngrediente);
     Assert.assertEquals(ingrediente.getNombre(), otroIngrediente.getNombre());
     Assert.assertEquals(ingrediente.getPrecio(), otroIngrediente.getPrecio());
     Assert.assertEquals(ingrediente.getCantidad(), otroIngrediente.getCantidad());
        
    }
    
    @Test
    public void testFind()
    {
        
     IngredienteEntity ingrediente = data.get(0);
     IngredienteEntity otroIngrediente = persistence.find(ingrediente.getId());
     Assert.assertNotNull(otroIngrediente);
     Assert.assertEquals(ingrediente.getNombre(), otroIngrediente.getNombre());
     Assert.assertEquals(ingrediente.getPrecio(), otroIngrediente.getPrecio());
     Assert.assertEquals(ingrediente.getCantidad(), otroIngrediente.getCantidad());
        
    }
    
    @Test
    public void testFindAll()
    {
     List<IngredienteEntity> ingredientes;

    ingredientes = persistence.findAll();

    Assert.assertFalse(ingredientes.isEmpty());
    }    
    
    @Test
    public void testUpdate()
    {
        
     PodamFactory factory =new PodamFactoryImpl();
     IngredienteEntity ingrediente;
     IngredienteEntity ingredienteActualizado;
     
     ingrediente= factory.manufacturePojo(IngredienteEntity.class);
     persistence.create(ingrediente);
     ingrediente.setNombre("otroNombre");
     
     ingredienteActualizado=persistence.update(ingrediente);
     
     Assert.assertEquals("otroNombre", ingredienteActualizado.getNombre());
     Assert.assertEquals(ingrediente.getPrecio(), ingredienteActualizado.getPrecio());
     Assert.assertEquals(ingrediente.getCantidad(), ingredienteActualizado.getCantidad());
     
    }   
    
    @Test
    public void deleteTest() {
        IngredienteEntity entity = data.get(0);
        persistence.delete(entity.getId());
        IngredienteEntity deleted = em.find(IngredienteEntity.class,entity.getId());
        Assert.assertNull(deleted);
    }
    
}
