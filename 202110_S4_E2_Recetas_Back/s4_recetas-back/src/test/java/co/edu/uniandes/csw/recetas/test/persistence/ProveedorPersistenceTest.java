/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;

import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import co.edu.uniandes.csw.recetas.persistence.ProveedorPersistence;
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
public class ProveedorPersistenceTest {
    @Inject
    ProveedorPersistence persistence;
    
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
    private List<ProveedorEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProveedorEntity.class.getPackage())
                .addPackage(ProveedorPersistence.class.getPackage())
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
        em.createQuery("delete from ProveedorEntity").executeUpdate();
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

            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    @Test
    public void testCreate() {

        PodamFactory factory = new PodamFactoryImpl();
        ProveedorEntity proveedor;

        proveedor = factory.manufacturePojo(ProveedorEntity.class);
        proveedor = persistence.create(proveedor);

        Assert.assertNotNull(proveedor);
        ProveedorEntity otroUsuario = persistence.find(proveedor.getId());
        Assert.assertNotNull(otroUsuario);
        Assert.assertEquals(proveedor.getLogin(), otroUsuario.getLogin());
        Assert.assertEquals(proveedor.getNombre(), otroUsuario.getNombre());
        Assert.assertEquals(proveedor.getCorreo(), otroUsuario.getCorreo());
        Assert.assertEquals(proveedor.getContrasenia(), otroUsuario.getContrasenia());
    }

    @Test
    public void testFind() {

        ProveedorEntity proveedor = data.get(0);
        ProveedorEntity otroProveedor = persistence.find(proveedor.getId());
        Assert.assertNotNull(otroProveedor);
        Assert.assertEquals(proveedor.getLogin(), otroProveedor.getLogin());
        Assert.assertEquals(proveedor.getNombre(), otroProveedor.getNombre());
        Assert.assertEquals(proveedor.getCorreo(), otroProveedor.getCorreo());
        Assert.assertEquals(proveedor.getContrasenia(), otroProveedor.getContrasenia());

    }

    @Test
    public void testFindAll() {

        List<ProveedorEntity> proveedor;

        proveedor = persistence.findAll();

        Assert.assertFalse(proveedor.isEmpty());
    }

    @Test
    public void testUpdate() {

        PodamFactory factory = new PodamFactoryImpl();
        ProveedorEntity proveedor;
        ProveedorEntity proveedorAct;

        proveedor = factory.manufacturePojo(ProveedorEntity.class);
        persistence.create(proveedor);
        proveedor.setLogin("otroLogin");

        proveedorAct = persistence.update(proveedor);

        Assert.assertEquals("otroLogin", proveedorAct.getLogin());
    }

    @Test
    public void deleteTest() {
        ProveedorEntity entity = data.get(0);
        persistence.delete(entity.getId());
        ProveedorEntity deleted = em.find(ProveedorEntity.class,entity.getId());
        Assert.assertNull(deleted);
    }
}
