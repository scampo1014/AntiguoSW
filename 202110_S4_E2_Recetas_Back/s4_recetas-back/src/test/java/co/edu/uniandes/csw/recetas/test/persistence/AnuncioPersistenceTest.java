/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;

import co.edu.uniandes.csw.recetas.entities.AnuncioEntity;
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
public class AnuncioPersistenceTest {
    @Inject
    AnuncioPersistence persistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba.
     */
    private List<AnuncioEntity> data = new ArrayList<AnuncioEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AnuncioEntity.class.getPackage())
                .addPackage(AnuncioPersistence.class.getPackage())
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
    private void clearData() {
        em.createQuery("delete from AnuncioEntity").executeUpdate();
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

            AnuncioEntity entity = factory.manufacturePojo(AnuncioEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    @Test
    public void testCreate() {

        PodamFactory factory = new PodamFactoryImpl();
        AnuncioEntity anuncio;

        anuncio = factory.manufacturePojo(AnuncioEntity.class);
        anuncio = persistence.create(anuncio);

        Assert.assertNotNull(anuncio);
        AnuncioEntity otroAnuncio = persistence.find(anuncio.getId());
        Assert.assertNotNull(otroAnuncio);
        Assert.assertEquals(anuncio.getCosto(), otroAnuncio.getCosto());
        Assert.assertEquals(anuncio.getNombre(), otroAnuncio.getNombre());
        Assert.assertEquals(anuncio.getDuracion(), otroAnuncio.getDuracion());
        Assert.assertEquals(anuncio.getContenido(), otroAnuncio.getContenido());
        Assert.assertEquals(anuncio.getMedioDePago(), otroAnuncio.getMedioDePago());
        Assert.assertEquals(anuncio.getTiempoDisponible(), otroAnuncio.getTiempoDisponible());
    }

    @Test
    public void testFind() {

        AnuncioEntity anuncio = data.get(0);
        AnuncioEntity otroAnuncio = persistence.find(anuncio.getId());
        Assert.assertNotNull(otroAnuncio);
        Assert.assertEquals(anuncio.getCosto(), otroAnuncio.getCosto());
        Assert.assertEquals(anuncio.getNombre(), otroAnuncio.getNombre());
        Assert.assertEquals(anuncio.getDuracion(), otroAnuncio.getDuracion());
        Assert.assertEquals(anuncio.getContenido(), otroAnuncio.getContenido());
        Assert.assertEquals(anuncio.getMedioDePago(), otroAnuncio.getMedioDePago());
        Assert.assertEquals(anuncio.getTiempoDisponible(), otroAnuncio.getTiempoDisponible());

    }

    @Test
    public void testFindAll() {

        List<AnuncioEntity> anuncio;

        anuncio = persistence.findAll();

        Assert.assertFalse(anuncio.isEmpty());
    }

    @Test
    public void testUpdate() {

        PodamFactory factory = new PodamFactoryImpl();
        AnuncioEntity anuncio;
        AnuncioEntity AnuncioAct;

        anuncio = factory.manufacturePojo(AnuncioEntity.class);
        persistence.create(anuncio);
        anuncio.setNombre("Nombre");

        AnuncioAct = persistence.update(anuncio);

        Assert.assertEquals("Nombre", AnuncioAct.getNombre());
    }

    @Test
    public void deleteTest() {
        AnuncioEntity entity = data.get(0);
        persistence.delete(entity.getId());
        AnuncioEntity borrado = em.find(AnuncioEntity.class,entity.getId());
        Assert.assertNull(borrado);
    }
}
