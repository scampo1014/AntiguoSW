/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.ejb;

import co.edu.uniandes.csw.recetas.entities.UsuarioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recetas.persistence.UsuarioPersistence;
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
public class UsuarioLogic {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioLogic.class.getName());

    @Inject
    UsuarioPersistence persistence;

    public UsuarioEntity createUsuario(UsuarioEntity usuario) throws BusinessLogicException {
        //validar reglas de negocio

        LOGGER.log(Level.INFO, "Inicia proceso de creación del usuario");
        //No hay dos usuarios con el mismo login
        if(persistence.findByLogin(usuario.getLogin())!=null) 
        {
            throw new BusinessLogicException("Ya existe un usuario con el login: " + usuario.getLogin());
        }
        
        //No hay dos usuarios con el mismo correo
        if(persistence.findByCorreo(usuario.getCorreo())!=null) 
        {
            throw new BusinessLogicException("Ya existe un usuario con el correo: " + usuario.getCorreo());
        }
        
        //La constrasenia tiene minimo 8 caracteres
        if(usuario.getContrasenia().length()<8)
        {
            throw new BusinessLogicException("La contrasenia debe tener minimo 8 caracteres");
        }
        
        LOGGER.log(Level.INFO, "Termina proceso de creación del usuario");
        UsuarioEntity newUsuarioEntity= persistence.create(usuario);
        return newUsuarioEntity;
    }
    
    public UsuarioEntity updateUsuario(Long id,UsuarioEntity usuario) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el usuario con id = {0}", id);
        UsuarioEntity usuarioOriginal=persistence.find(id);
        if(!usuario.getLogin().equals(usuarioOriginal.getLogin()) && persistence.findByLogin(usuario.getLogin())!=null) 
        {
            throw new BusinessLogicException("Ya existe un usuario con el login: " + usuario.getLogin());
        }        
        if(!usuario.getCorreo().equals(usuarioOriginal.getCorreo()) && persistence.findByCorreo(usuario.getCorreo())!=null) 
        {
            throw new BusinessLogicException("Ya existe un usuario con el correo: " + usuario.getCorreo());
        }
        if(usuario.getContrasenia().length()<8)
        {
            throw new BusinessLogicException("La contrasenia debe tener minimo 8 caracteres");
        }
        usuarioOriginal.setNombre(usuario.getNombre());
        usuarioOriginal.setLogin(usuario.getLogin());
        usuarioOriginal.setCorreo(usuario.getCorreo());
        usuarioOriginal.setContrasenia(usuario.getContrasenia());
        usuarioOriginal.setFoto(usuario.getFoto());
        UsuarioEntity newUsuarioEntity = persistence.update(usuarioOriginal);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el usuario con id = {0}", id);
        return newUsuarioEntity;
    }
    
    public List<UsuarioEntity> getUsuarios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los usuarios");
        List<UsuarioEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los usuarios");
        return lista;
    }
    
    public UsuarioEntity getUsuario(Long usuarioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el usuario con id = {0}", usuarioId);
        UsuarioEntity usuarioEntity = persistence.find(usuarioId);
        if (usuarioEntity == null) {
            LOGGER.log(Level.SEVERE, "El usuario con el id = {0} no existe", usuarioId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el usuario con id = {0}", usuarioId);
        return usuarioEntity;
    }    
    
    public void deleteUsuario(Long usuarioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el usuario con id = {0}", usuarioId);
        persistence.delete(usuarioId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el usuario con id = {0}", usuarioId);
    }
    
    public UsuarioEntity getUsuarioByLogin(String usuarioLogin)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el usuario con login = {0}", usuarioLogin);
        UsuarioEntity usuarioEntity = persistence.findByLogin(usuarioLogin);
        if (usuarioEntity == null) {
            LOGGER.log(Level.SEVERE, "El usuario con el login = {0} no existe", usuarioLogin);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el usuario con login = {0}", usuarioLogin);
        return usuarioEntity;
    }

}
