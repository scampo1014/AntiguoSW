/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.logic;

import co.edu.uniandes.csw.recetas.ejb.RecetaLogic;
import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.entities.UtensilioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
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
public class RecetaLogicTest {
    
    @Inject
    private RecetaLogic recetaLogic;
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<RecetaEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecetaEntity.class.getPackage())
                .addPackage(RecetaLogic.class.getPackage())
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
    */
    private void clearData() {
        em.createQuery("delete from RecetaEntity").executeUpdate();
    }

    /**
        * Inserta los datos iniciales para el correcto funcionamiento de las
        * pruebas.
    */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            RecetaEntity entity = factory.manufacturePojo(RecetaEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    @Test
    public void createRecetaTest() throws BusinessLogicException
    {
        
        RecetaEntity newEntity = factory.manufacturePojo(RecetaEntity.class);
        newEntity.setCantIngredientes(10);
        newEntity.setCalorias(5);
        RecetaEntity result = recetaLogic.create(newEntity);
        
        Assert.assertNotNull(result);
        
        RecetaEntity entity = em.find(RecetaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getDificultad(), entity.getDificultad());
        Assert.assertEquals(newEntity.getTiempoPrep(), entity.getTiempoPrep());
        
        
    }
    @Test 
    public void createRecetaSinIngredientesTest()throws BusinessLogicException
    {
        RecetaEntity prueba= factory.manufacturePojo(RecetaEntity.class);
        prueba.setCantIngredientes(0);
        RecetaEntity receta=null;
        try{
            receta = recetaLogic.create(prueba);
        }
        catch(Exception e)
        {
            Assert.assertEquals("La receta debe tener mínimo un ingrediente.", e.getMessage());
        }
    }
    @Test
    public void createRecetaNombreRepetidoTest() throws BusinessLogicException
    {
        RecetaEntity prueba= factory.manufacturePojo(RecetaEntity.class);
        prueba.setCantIngredientes(10);
        prueba.setCalorias(4);
        RecetaEntity receta=recetaLogic.create(prueba);
        
        RecetaEntity prueba2= factory.manufacturePojo(RecetaEntity.class);
        prueba2.setNombre(receta.getNombre());
        prueba2.setCantIngredientes(10);
        prueba2.setCalorias(3);
        RecetaEntity receta2=null;
        try
        {
            receta2 = recetaLogic.create(prueba2);
        }
        catch(Exception e)
        {
            Assert.assertEquals("No puede haber dos recetas con el mismo nombre.", e.getMessage());
        }
    }
    @Test
    public void createRecetaCaloriasTest()
    {
        RecetaEntity prueba= factory.manufacturePojo(RecetaEntity.class);
        prueba.setCalorias(-3);
        prueba.setCantIngredientes(3);
        RecetaEntity receta=null;
        try{
            receta = recetaLogic.create(prueba);
        }
        catch(Exception e)
        {
            Assert.assertEquals("La cantidad de calorías debe ser un número mayor a 0.", e.getMessage());
        }
    }
    @Test
    public void getRecetaTest(Long recetaId) {
        RecetaEntity entity = data.get(0);
        RecetaEntity resultEntity = recetaLogic.getReceta(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getDificultad(), resultEntity.getDificultad());
    }
    @Test
    public void getRecetasTest() {
        List<RecetaEntity> list = recetaLogic.getRecetas();
        Assert.assertEquals(data.size(), list.size());
        for (RecetaEntity entity : list) {
            boolean found = false;
            for (RecetaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    @Test
    public void updateRecetaTest() throws BusinessLogicException  {
        
        RecetaEntity entity = data.get(0);
        RecetaEntity pojoEntity = factory.manufacturePojo(RecetaEntity.class);
        pojoEntity.setCantIngredientes(10);
        pojoEntity.setCalorias(3);
        pojoEntity.setId(entity.getId());
        recetaLogic.updateReceta(pojoEntity);
        RecetaEntity resp = em.find(RecetaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        
        //Una receta debe tener mínimo un ingrediente
        entity.setCantIngredientes(0);
        try{
            RecetaEntity receta = recetaLogic.updateReceta(entity);
        }
        catch(Exception e)
        {
            Assert.assertEquals("La receta debe tener mínimo un ingrediente.", e.getMessage());
        }
        //La cantidad de calorias debe ser mayor a 0
        entity.setCalorias(-3);
        entity.setCantIngredientes(4);
        try{
            RecetaEntity receta = recetaLogic.updateReceta(entity);
        }
        catch(Exception e)
        {
            Assert.assertEquals("La cantidad de calorías debe ser un número mayor a 0.", e.getMessage());
        }
        
    }
    @Test
    public void deleteRecetaTest() throws BusinessLogicException {
        RecetaEntity entity = data.get(0);
        recetaLogic.deleteReceta(entity.getId());
        RecetaEntity deleted = em.find(RecetaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
