/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.test.persistence;

import co.edu.uniandes.csw.recetas.entities.UsuarioEntity;
import co.edu.uniandes.csw.recetas.persistence.UsuarioPersistence;
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
public class UsuarioPersistenceTest {

    @Inject
    UsuarioPersistence persistence;
    
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
    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
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

            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    @Test
    public void testCreate() {

        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity usuario;

        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario = persistence.create(usuario);

        Assert.assertNotNull(usuario);
        UsuarioEntity otroUsuario = persistence.find(usuario.getId());
        Assert.assertNotNull(otroUsuario);
        Assert.assertEquals(usuario.getLogin(), otroUsuario.getLogin());
        Assert.assertEquals(usuario.getNombre(), otroUsuario.getNombre());
        Assert.assertEquals(usuario.getCorreo(), otroUsuario.getCorreo());
        Assert.assertEquals(usuario.getContrasenia(), otroUsuario.getContrasenia());
        Assert.assertEquals(usuario.getFoto(), otroUsuario.getFoto());
        
    }

    @Test
    public void testFind() {

        UsuarioEntity usuario = data.get(0);
        UsuarioEntity otroUsuario = persistence.find(usuario.getId());
        Assert.assertNotNull(otroUsuario);
        Assert.assertEquals(usuario.getLogin(), otroUsuario.getLogin());
        Assert.assertEquals(usuario.getNombre(), otroUsuario.getNombre());
        Assert.assertEquals(usuario.getCorreo(), otroUsuario.getCorreo());
        Assert.assertEquals(usuario.getContrasenia(), otroUsuario.getContrasenia());
        Assert.assertEquals(usuario.getFoto(), otroUsuario.getFoto());
    }

    @Test
    public void testFindAll() {

        List<UsuarioEntity> usuarios;

        usuarios = persistence.findAll();

        Assert.assertFalse(usuarios.isEmpty());
    }

    @Test
    public void testUpdate() {

        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity usuario;
        UsuarioEntity usuarioActualizado;

        usuario = factory.manufacturePojo(UsuarioEntity.class);
        persistence.create(usuario);
        usuario.setLogin("otroLogin");

        usuarioActualizado = persistence.update(usuario);

        Assert.assertEquals("otroLogin", usuarioActualizado.getLogin());
        Assert.assertEquals(usuario.getNombre(), usuarioActualizado.getNombre());
        Assert.assertEquals(usuario.getCorreo(), usuarioActualizado.getCorreo());
        Assert.assertEquals(usuario.getContrasenia(), usuarioActualizado.getContrasenia());
        Assert.assertEquals(usuario.getFoto(), usuarioActualizado.getFoto());
    }

    @Test
    public void deleteTest() {
        UsuarioEntity entity = data.get(0);
        persistence.delete(entity.getId());
        UsuarioEntity deleted = em.find(UsuarioEntity.class,entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void testFindByLogin() {

        UsuarioEntity usuario = data.get(0);
        UsuarioEntity otroUsuario = persistence.findByLogin(usuario.getLogin());
        Assert.assertNotNull(otroUsuario);
        Assert.assertEquals(usuario.getLogin(), otroUsuario.getLogin());
        Assert.assertEquals(usuario.getNombre(), otroUsuario.getNombre());
        Assert.assertEquals(usuario.getCorreo(), otroUsuario.getCorreo());
        Assert.assertEquals(usuario.getContrasenia(), otroUsuario.getContrasenia());
        Assert.assertEquals(usuario.getFoto(), otroUsuario.getFoto());

    }
    
    @Test
    public void testFindByCorreo() {

        UsuarioEntity usuario = data.get(0);
        UsuarioEntity otroUsuario = persistence.findByCorreo(usuario.getCorreo());
        Assert.assertNotNull(otroUsuario);
        Assert.assertEquals(usuario.getLogin(), otroUsuario.getLogin());
        Assert.assertEquals(usuario.getNombre(), otroUsuario.getNombre());
        Assert.assertEquals(usuario.getCorreo(), otroUsuario.getCorreo());
        Assert.assertEquals(usuario.getContrasenia(), otroUsuario.getContrasenia());
        Assert.assertEquals(usuario.getFoto(), otroUsuario.getFoto());
    }
    
}
