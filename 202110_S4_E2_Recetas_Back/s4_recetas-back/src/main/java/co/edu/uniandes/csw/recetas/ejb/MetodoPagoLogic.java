/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.MetodoPagoEntity;
import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.MetodoPagoPersistence;
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
public class MetodoPagoLogic 
{
    private static final Logger LOGGER = Logger.getLogger(MetodoPagoLogic.class.getName());

    @Inject
    MetodoPagoPersistence persistence;
    @Inject 
    private ProveedorPersistence proveedorPersistence;
    
    public void validarRNProveedor(ProveedorEntity proveedor, MetodoPagoEntity metodoPagoEntity) throws BusinessLogicException
    {
        for (MetodoPagoEntity metodoPago: proveedor.getMetodosPago())
        {
            if(metodoPago.getMetodoPago().equals(metodoPagoEntity.getMetodoPago()))
            {
                throw new BusinessLogicException("Una receta no puede tener dos metodosPago con el mismo nombre");
            }
        }
    }
    
    public MetodoPagoEntity createMetodoPagoEntity(MetodoPagoEntity metodoPago) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "Inicia proceso de creación metodo de pago");
        if(persistence.findByMetodoDePago(metodoPago.getMetodoPago())!=null) 
        {
            throw new BusinessLogicException ("Ya existe un metodo de pago");
        }
          LOGGER.log(Level.INFO, "FIN");
        MetodoPagoEntity MetodoPagoEntity= persistence.create(metodoPago);
        return MetodoPagoEntity;
            
        }
     public MetodoPagoEntity createMetodoPagoProveedor(Long proveedoresId, MetodoPagoEntity metodoPagoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear metodoPago");
        ProveedorEntity proveedor = proveedorPersistence.find(proveedoresId);
        validarRNProveedor(proveedor, metodoPagoEntity);        
        
        metodoPagoEntity.setProveedor(proveedor);
        LOGGER.log(Level.INFO, "Termina proceso de creación del metodoPago");
        return persistence.create(metodoPagoEntity);
    }
    public MetodoPagoEntity updateMetodoPago(Long id,MetodoPagoEntity metodoPago)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el metodo con id = {0}", id);
        MetodoPagoEntity nMetodoPagoEntity = persistence.update(metodoPago);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el metodo con id = {0}", id);
        return nMetodoPagoEntity;
    }
        public MetodoPagoEntity updateMetodoPagoProveedor(Long proveedoresId, Long metodosPagoId, MetodoPagoEntity metodoPagoEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el metodoPago con id = {0} de la receta con id = {1}" ,new Object[]{metodoPagoEntity.getId(),proveedoresId});
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedoresId);
        MetodoPagoEntity metodo1 = persistence.find(metodosPagoId);
        validarRNProveedor(proveedorEntity, metodoPagoEntity);  
        metodo1.setProveedor(proveedorEntity);
        metodo1.setMetodoPago(metodoPagoEntity.getMetodoPago());
        
        MetodoPagoEntity nProveedorEntity = persistence.update(metodo1);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el metodoPago con id = {0} del libro con id = {1}" ,new Object[]{metodoPagoEntity.getId(),proveedoresId});
        return nProveedorEntity;
    }
        
        public void deleteMetodoDePago(Long metodoDePagoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el metodo de pago con id = {0}", metodoDePagoId);
        persistence.delete(metodoDePagoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el metodo de pago con id = {0}", metodoDePagoId);
    }
    public List<MetodoPagoEntity> getMetodos()
    {
        LOGGER.log(Level.INFO, "tratar los metodos");
         List<MetodoPagoEntity> list = persistence.findAll();
         LOGGER.log(Level.INFO, "Fin");
         return list;
    }
      public MetodoPagoEntity getMetodoPagoByProveedor(Long proveedoresId, Long metodosPagoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el metodoPago con id = {0} del libro con id = {1}" ,new Object[]{metodosPagoId,proveedoresId});
        return persistence.findByProveedor(proveedoresId, metodosPagoId);
    }
    public MetodoPagoEntity getMetodo(Long id)
    {
        LOGGER.log(Level.INFO, "Encontrar un metodo");
        MetodoPagoEntity metodoPagoEntity=persistence.find(id);
        LOGGER.log(Level.INFO, "Fin");
        return metodoPagoEntity;
    }
    public void deleteMetodoPagoProveedor(Long proveedoresId, Long metodosPagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el metodos con id = {0} del libro con id = {1}" ,new Object[]{metodosPagoId,proveedoresId});
        MetodoPagoEntity old = getMetodoPagoByProveedor(proveedoresId, metodosPagoId);
        if (old == null) {
            throw new BusinessLogicException("El metodos con id = " + metodosPagoId + " no esta asociado a el libro con id = " + proveedoresId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el metodos con id = {0} del libro con id = {1}" ,new Object[]{metodosPagoId,proveedoresId});
    }
    public List<MetodoPagoEntity> getMetodosPagoProveedor(Long proveedoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los metodos asociados al receta con id = {0}", proveedoresId);
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedoresId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los metodos asociados al receta con id = {0}", proveedoresId);
        return proveedorEntity.getMetodosPago();
    }
    
}
