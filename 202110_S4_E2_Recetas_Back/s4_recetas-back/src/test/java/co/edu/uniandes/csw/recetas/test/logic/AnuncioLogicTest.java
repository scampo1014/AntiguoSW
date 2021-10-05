/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.logic;

import co.edu.uniandes.csw.recetas.ejb.AnuncioLogic;
import co.edu.uniandes.csw.recetas.entities.AnuncioEntity;;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.AnuncioPersistence;
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
public class AnuncioLogicTest {
     @Inject
    private AnuncioLogic AnuncioLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utt;
    
    private List<AnuncioEntity> data = new ArrayList<>();

    @Deployment    
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AnuncioEntity.class.getPackage())
                .addPackage(AnuncioPersistence.class.getPackage())
                .addPackage(AnuncioLogic.class.getPackage())
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
        em.createQuery("delete from AnuncioEntity").executeUpdate();
    }

   
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            AnuncioEntity entity = factory.manufacturePojo(AnuncioEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
     @Test
    public void crearAnuncioTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        AnuncioEntity eentity = factory.manufacturePojo(AnuncioEntity.class);
        eentity.setCosto(100000);
        eentity.setDuracion(100000);
        eentity.setTiempoDisponible(100000);
        AnuncioEntity resultado = AnuncioLogic.crearAnuncio(eentity);
        Assert.assertNotNull(resultado);

        AnuncioEntity entity0 = em.find(AnuncioEntity.class, resultado.getId());
        Assert.assertEquals(entity0.getContenido(), eentity.getContenido());
        Assert.assertEquals(entity0.getCosto(), eentity.getCosto());
        Assert.assertEquals(entity0.getDuracion(), eentity.getDuracion());
        Assert.assertEquals(entity0.getMedioDePago(), eentity.getMedioDePago());
        Assert.assertEquals(entity0.getNombre(), eentity.getNombre());
        Assert.assertEquals(entity0.getTiempoDisponible(), eentity.getTiempoDisponible());
        
       
        AnuncioEntity entity1= factory.manufacturePojo(AnuncioEntity.class);
        entity1.setDuracion(-123);
        AnuncioEntity resultado1=null;
        try
            {
                resultado1=AnuncioLogic.crearAnuncio(entity1);      
            }
        catch (Exception e)
        {
            Assert.assertEquals("La duracion no puede ser menor a 0"+ entity1.getDuracion(), e.getMessage());
        }
        Assert.assertNull(resultado1);
        }
    @Test
    public void getAnuncioTest() {
        List<AnuncioEntity> list = AnuncioLogic.getAnuncios();
        Assert.assertEquals(data.size(), list.size());
        for (AnuncioEntity entity : list) {
            boolean found = false;
            for (AnuncioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    @Test
    public void getAnuncio(Long id) {
        AnuncioEntity entity = data.get(0);
        AnuncioEntity resultEntity = AnuncioLogic.getAnuncio(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getContenido(), resultEntity.getContenido());
        Assert.assertEquals(entity.getCosto(), resultEntity.getCosto());
        Assert.assertEquals(entity.getDuracion(), resultEntity.getDuracion());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getMedioDePago(), resultEntity.getMedioDePago());
        Assert.assertEquals(entity.getTiempoDisponible(), resultEntity.getTiempoDisponible());
    }
     @Test
    public void updateAnuncioTest() {
        PodamFactory factory = new PodamFactoryImpl();
        AnuncioEntity entity = data.get(0);
        AnuncioEntity aEntity = factory.manufacturePojo(AnuncioEntity.class);

        aEntity.setId(entity.getId());

        AnuncioLogic.updateAnuncio(aEntity.getId(), aEntity);

        AnuncioEntity resp = em.find(AnuncioEntity.class, entity.getId());

        Assert.assertEquals(aEntity.getId(), resp.getId());
        Assert.assertEquals(aEntity.getContenido(), resp.getContenido());
        Assert.assertEquals(aEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(aEntity.getCosto(), resp.getCosto());
        Assert.assertEquals(aEntity.getDuracion(), resp.getDuracion());
        Assert.assertEquals(aEntity.getMedioDePago(), resp.getMedioDePago());
        Assert.assertEquals(aEntity.getTiempoDisponible(), resp.getTiempoDisponible());
    }
    @Test
    public void deleteAnuncioTest() throws BusinessLogicException {
        AnuncioEntity entity = data.get(0);
        AnuncioLogic.deleteAnuncio(entity.getId());
        AnuncioEntity deleted = em.find(AnuncioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
