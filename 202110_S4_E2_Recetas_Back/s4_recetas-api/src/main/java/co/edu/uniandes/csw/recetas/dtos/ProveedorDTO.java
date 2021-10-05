/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.ProveedorEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class ProveedorDTO implements Serializable {

    private Long id;
    private String nombre;
    private String login;
    private String correo;
    private String contrasenia;

    public ProveedorDTO() {
    }

    public ProveedorDTO(ProveedorEntity proveedor) {
        if (proveedor != null) {
            this.contrasenia = proveedor.getContrasenia();
            this.correo = proveedor.getCorreo();
            this.login = proveedor.getLogin();
            this.nombre = proveedor.getNombre();
        }
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLogin() {
        return login;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ProveedorEntity toEntity() {
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setContrasenia(this.contrasenia);
        proveedor.setCorreo(this.correo);
        proveedor.setLogin(this.login);
        proveedor.setNombre(this.nombre);
        return proveedor;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
