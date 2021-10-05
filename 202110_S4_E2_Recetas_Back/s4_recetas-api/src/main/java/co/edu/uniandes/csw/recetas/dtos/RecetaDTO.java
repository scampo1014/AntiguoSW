/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import java.io.Serializable;

/**
 *
 * @author Ingrith Barbosa
 */
public class RecetaDTO implements Serializable{
    
    private Long id;
    private String nombre;
    private Integer cantIngredientes;
    private String descripcion;
    private Integer calorias;
    private String tiempoPrep;
    private String dificultad;
    private Boolean popular;
    private UsuarioDTO usuario;

    /**
     * Constructor por defecto
     */
    public RecetaDTO()
    {
        
    }
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param recetaEntity: Es la entidad que se va a convertir a DTO
     */
    public RecetaDTO(RecetaEntity recetaEntity) {
        if (recetaEntity != null) {
            this.id=recetaEntity.getId();
            this.calorias = recetaEntity.getCalorias();
            this.cantIngredientes = recetaEntity.getCantIngredientes();
            this.descripcion = recetaEntity.getDescripcion();
            this.dificultad = recetaEntity.getDificultad();
            this.nombre = recetaEntity.getNombre();
            this.popular = recetaEntity.getPopular();
            this.tiempoPrep = recetaEntity.getTiempoPrep();
            if(recetaEntity.getUsuario()!=null)
                this.usuario= new UsuarioDTO(recetaEntity.getUsuario());
        }
    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the cantIngredientes
     */
    public Integer getCantIngredientes() {
        return cantIngredientes;
    }

    /**
     * @param cantIngredientes the cantIngredientes to set
     */
    public void setCantIngredientes(Integer cantIngredientes) {
        this.cantIngredientes = cantIngredientes;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the calorias
     */
    public Integer getCalorias() {
        return calorias;
    }

    /**
     * @param calorias the calorias to set
     */
    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    /**
     * @return the tiempoPrep
     */
    public String getTiempoPrep() {
        return tiempoPrep;
    }

    /**
     * @param tiempoPrep the tiempoPrep to set
     */
    public void setTiempoPrep(String tiempoPrep) {
        this.tiempoPrep = tiempoPrep;
    }

    /**
     * @return the dificultad
     */
    public String getDificultad() {
        return dificultad;
    }

    /**
     * @param dificultad the dificultad to set
     */
    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    /**
     * @return the popular
     */
    public Boolean getPopular() {
        return popular;
    }

    /**
     * @param popular the popular to set
     */
    public void setPopular(Boolean popular) {
        this.popular = popular;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the usuario
     */
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public RecetaEntity toEntity() {
        RecetaEntity recetaEntity = new RecetaEntity();
        recetaEntity.setId(this.getId());
        recetaEntity.setCalorias(this.getCalorias());
        recetaEntity.setCantIngredientes(this.getCantIngredientes());
        recetaEntity.setDescripcion(this.getDescripcion());
        recetaEntity.setDificultad(this.getDificultad());
        recetaEntity.setNombre(this.getNombre());
        recetaEntity.setPopular(this.getPopular());
        recetaEntity.setTiempoPrep(this.getTiempoPrep());
        if(this.getUsuario()!=null)
            recetaEntity.setUsuario(this.getUsuario().toEntity());
        return recetaEntity;
    }
}
