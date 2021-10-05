/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.ProveedorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author juliantorres
 */
@Stateless
public class ProveedorLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ProveedorLogic.class.getName());
    @Inject
    ProveedorPersistence persistence;
    
    
    public ProveedorEntity createProveedor(ProveedorEntity c) throws BusinessLogicException{
      LOGGER.log(Level.INFO, "Crear proveedor");
        if(persistence.findByLogin(c.getLogin())!=null) 
        {
            throw  new BusinessLogicException ("Ya existe un proveedor con este login"+ c.getLogin());
        }
        
        
        if(persistence.findByCorreo(c.getCorreo())!=null) 
        {
            throw  new BusinessLogicException ("Ya existe un proveedor con este correo"+ c.getCorreo());
        }
        if(c.getContrasenia().length()<8)
        {
            throw  new BusinessLogicException ("No se puede utilizar esta contrasenia");
        }
        if(persistence.findByNombre(c.getNombre())!=null)
        {
            throw  new BusinessLogicException ("Ya existe un proveedor con este Nombre"+ c.getNombre());
        }
        LOGGER.log(Level.INFO, "FIN");
        ProveedorEntity ProveedorEntity= persistence.create(c);
        return ProveedorEntity;
    }

    /**
     *
     * @param id
     * @param c
     * @return
     */
    public ProveedorEntity updateProveedor(Long id, ProveedorEntity c) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el proveedor con id = {0}", id);
        ProveedorEntity proveedor1=persistence.find(id);
        if(!c.getLogin().equals(proveedor1.getLogin()) && persistence.findByLogin(c.getLogin())!=null) 
        {
            throw new BusinessLogicException("Ya existe un proveedor con el login: " + c.getLogin());
        }        
        if(!c.getCorreo().equals(proveedor1.getCorreo()) && persistence.findByCorreo(c.getCorreo())!=null) 
        {
            throw new BusinessLogicException("Ya existe un proveedor con el correo: " + c.getCorreo());
        }
        if(c.getContrasenia().length()<8)
        {
            throw new BusinessLogicException("La contrasenia debe tener minimo 8 caracteres");
        }
        proveedor1.setNombre(c.getNombre());
        proveedor1.setLogin(c.getLogin());
        proveedor1.setCorreo(c.getCorreo());
        proveedor1.setContrasenia(c.getContrasenia());
        ProveedorEntity newUsuarioEntity = persistence.update(proveedor1);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el proveedor con id = {0}", id);
        return newUsuarioEntity;
    }
    public void deleteProveedor(Long proveedorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar al proveedor con id = {0}", proveedorId);
        persistence.delete(proveedorId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar al proveedor con id = {0}", proveedorId);
    }
    public List<ProveedorEntity> getProveedores()
    {
        LOGGER.log(Level.INFO, "tratar los proveedores");
         List<ProveedorEntity> list = persistence.findAll();
         LOGGER.log(Level.INFO, "Fin");
         return list;
    }
    public ProveedorEntity getProveedor(Long id)
    {
        LOGGER.log(Level.INFO, "Encontrar un proveedor");
        ProveedorEntity proveedorEntity=persistence.find(id);
        LOGGER.log(Level.INFO, "Fin");
        return proveedorEntity;
    }
    
    
}
