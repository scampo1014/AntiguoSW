/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import co.edu.uniandes.csw.recetas.entities.AnuncioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.ProveedorPersistence;
import co.edu.uniandes.csw.recetas.persistence.AnuncioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Maria Valentina Garcia
 */
@Stateless
public class AnuncioLogic {

    private static final Logger LOGGER = Logger.getLogger(AnuncioLogic.class.getName());

    @Inject
    AnuncioPersistence persistence;

    @Inject
    private ProveedorPersistence proveedorPersistence;

    public AnuncioEntity crearAnuncio(AnuncioEntity anuncio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia la creacion del Anuncio");
        if (anuncio.getDuracion() < 0) {
            throw new BusinessLogicException("La duracion no puede ser menor a 0" + anuncio.getDuracion());
        }
        if (anuncio.getDuracion() > 100000) {
            throw new BusinessLogicException("La duracion es muy alta" + anuncio.getDuracion());
        }

        LOGGER.log(Level.INFO, "FIN");
        AnuncioEntity nAnuncioEntity = persistence.create(anuncio);
        return nAnuncioEntity;
    }

    public AnuncioEntity createAnuncioProveedor(Long proveedoresId, AnuncioEntity anuncioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear anuncio");
        ProveedorEntity proveedor = proveedorPersistence.find(proveedoresId);
        //validar reglas de negocio
        if (anuncioEntity.getDuracion() < 0) {
            throw new BusinessLogicException("La duracion no puede ser menor a 0" + anuncioEntity.getDuracion());
        }
        if (anuncioEntity.getDuracion() > 100000) {
            throw new BusinessLogicException("La duracion es muy alta" + anuncioEntity.getDuracion());
        }
        anuncioEntity.setProveedor(proveedor);
        LOGGER.log(Level.INFO, "Termina proceso de creación del anuncio");
        AnuncioEntity newAnuncioEntity = persistence.create(anuncioEntity);
        return newAnuncioEntity;
    }

    public AnuncioEntity updateAnuncio(Long id, AnuncioEntity anuncio) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el anuncio con id = {0}", id);
        AnuncioEntity newAnuncioEntity = persistence.update(anuncio);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el anuncio con id = {0}", id);
        return newAnuncioEntity;
    }

    /**
     * Actualiza la información de una instancia de Anuncio.
     *
     * @param anuncioEntity Instancia de AnuncioEntity con los nuevos datos.
     * @param proveedoresId id del Proveedor el cual sera padre del Anuncio
     * actualizado.
     * @return Instancia de AnuncioEntity con los datos actualizados.
     *
     */
    public AnuncioEntity updateAnuncioProveedor(Long proveedoresId, Long anunciosId, AnuncioEntity anuncioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el anuncio con id = {0}", anuncioEntity.getId());
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedoresId);
        AnuncioEntity anuncioOriginal = persistence.find(anunciosId);
        for (AnuncioEntity anuncio : proveedorEntity.getAnuncios()) {
            if (!anuncio.getNombre().equals(anuncioOriginal.getNombre()) && anuncio.getNombre().equals(anuncioEntity.getNombre())) {
                throw new BusinessLogicException("Una proveedor no puede tener dos anuncios con el mismo nombre");
            }
        }
        //validar reglas de negocio
        if (anuncioEntity.getDuracion() < 0) {
            throw new BusinessLogicException("La duracion no puede ser menor a 0" + anuncioEntity.getDuracion());
        }
        if (anuncioEntity.getDuracion() > 100000) {
            throw new BusinessLogicException("La duracion es muy alta" + anuncioEntity.getDuracion());
        }
        anuncioOriginal.setProveedor(proveedorEntity);
        anuncioOriginal.setNombre(anuncioEntity.getNombre());
        anuncioOriginal.setCosto(anuncioEntity.getCosto());
        anuncioOriginal.setDuracion(anuncioEntity.getDuracion());
        anuncioOriginal.setContenido(anuncioEntity.getContenido());
        anuncioOriginal.setMedioDePago(anuncioEntity.getMedioDePago());
        anuncioOriginal.setTiempoDisponible(anuncioEntity.getTiempoDisponible());

        AnuncioEntity newAnuncioEntity = persistence.update(anuncioOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el anuncio con id = {0}", proveedoresId);
        return newAnuncioEntity;
    }

    public List<AnuncioEntity> getAnuncios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los anuncios");
        List<AnuncioEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los anuncios");
        return lista;
    }

    /**
     * Obtiene la lista de los registros de Anuncio que pertenecen a un Proveedor.
     *
     * @param proveedoresId id del Proveedor el cual es padre de los Anuncios.
     * @return Colección de objetos de AnuncioEntity.
     */
    public List<AnuncioEntity> getAnunciosProveedor(Long proveedoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los anuncios asociados al proveedor con id = {0}", proveedoresId);
        ProveedorEntity proveedorEntity = proveedorPersistence.find(proveedoresId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los anuncios asociados al proveedor con id = {0}", proveedoresId);
        return proveedorEntity.getAnuncios();
    }

    public AnuncioEntity getAnuncio(Long anuncioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el anuncio con id = {0}", anuncioId);
        AnuncioEntity anuncioEntity = persistence.find(anuncioId);
        if (anuncioEntity == null) {
            LOGGER.log(Level.SEVERE, "El anuncio con el id = {0} no existe", anuncioId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el anuncio con id = {0}", anuncioId);
        return anuncioEntity;
    }

    public AnuncioEntity getAnuncioByProveedor(Long proveedoresId, Long anunciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el anuncio con id = {0}",anunciosId);
        return persistence.findByProveedor(proveedoresId, anunciosId);
    }

    public void deleteAnuncio(Long anuncioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el anuncio con id = {0}", anuncioId);
        persistence.delete(anuncioId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el anuncio con id = {0}", anuncioId);
    }

    /**
     * Elimina una instancia de Anuncio de la base de datos.
     *
     * @param anunciosId Identificador de la instancia a eliminar.
     * @param proveedoresId id del Proveedor el cual es padre del Anuncio.
     * @throws BusinessLogicException Si la reseña no esta asociada al proveedor.
     *
     */
    public void deleteAnuncioProveedor(Long proveedoresId, Long anunciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el anuncio con id = {0}" ,anunciosId);
        AnuncioEntity old = getAnuncioByProveedor(proveedoresId, anunciosId);
        if (old == null) {
            throw new BusinessLogicException("El anuncio con id = " + anunciosId + " no esta asociado a el proveedor con id = " + proveedoresId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el anuncio con id = {0}" , anunciosId);
    }

    public AnuncioEntity updateAnuncio(Long proveedoresId, Long anunciosId, AnuncioEntity toEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
