/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Ingrith Barbosa
 */
@Entity
public class RecetaEntity extends BaseEntity implements Serializable
{
    private String nombre;
    private Integer cantIngredientes;
    private String descripcion;
    private Integer calorias;
    private String tiempoPrep;
    private String dificultad;
    private Boolean popular;
    
    @PodamExclude
    @OneToMany (mappedBy = "receta",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private List<IngredienteEntity> ingredientes = new ArrayList<>();
    
    @PodamExclude
    @OneToMany (mappedBy = "receta",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private List<UtensilioEntity> utensilios = new ArrayList<>();
    
    @PodamExclude
    @OneToMany (mappedBy = "receta",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private List<FotoEntity> fotos = new ArrayList<>();
    
    @PodamExclude
    @OneToMany (mappedBy = "receta",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private List<VideoEntity> videos = new ArrayList<>();
    
    @PodamExclude
    @OneToMany (mappedBy = "receta",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private List<ComentarioEntity> comentarios = new ArrayList<>();
    
//    @PodamExclude
//    @OneToMany
//    private List<RecetaEntity> variaciones = new ArrayList<>();
    
    @PodamExclude
    @ManyToOne
    private UsuarioEntity usuario;
    
    @PodamExclude
    @OneToMany (mappedBy = "receta",cascade = CascadeType.PERSIST,orphanRemoval = true )
    private List<CalificacionEntity> calificaciones = new ArrayList<>();

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
     * @return the ingredientes
     */
    public List<IngredienteEntity> getIngredientes() {
        return ingredientes;
    }

    /**
     * @param ingredientes the ingredientes to set
     */
    public void setIngredientes(List<IngredienteEntity> ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * @return the utensilios
     */
    public List<UtensilioEntity> getUtensilios() {
        return utensilios;
    }

    /**
     * @param utensilios the utensilios to set
     */
    public void setUtensilios(List<UtensilioEntity> utensilios) {
        this.utensilios = utensilios;
    }

    /**
     * @return the fotos
     */
    public List<FotoEntity> getFotos() {
        return fotos;
    }

    /**
     * @param fotos the fotos to set
     */
    public void setFotos(List<FotoEntity> fotos) {
        this.fotos = fotos;
    }

    /**
     * @return the videos
     */
    public List<VideoEntity> getVideos() {
        return videos;
    }

    /**
     * @param videos the videos to set
     */
    public void setVideos(List<VideoEntity> videos) {
        this.videos = videos;
    }

    /**
     * @return the comentarios
     */
    public List<ComentarioEntity> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the usuario
     */
    public UsuarioEntity getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the calificaciones
     */
    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }
    
    @Override
    public boolean equals(Object obj) {
    if (! super.equals(obj)) {
      return false;
    }
    RecetaEntity fobj = (RecetaEntity) obj;
    return nombre.equals(fobj.getNombre()) && cantIngredientes.equals(fobj.getCantIngredientes()) && descripcion.equals(fobj.getDescripcion())
            && calorias.equals(fobj.getCalorias()) && tiempoPrep.equals(fobj.getTiempoPrep()) && dificultad.equals(fobj.getDificultad())
            && popular.equals(fobj.getPopular());
  }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nombre);
        hash = 67 * hash + Objects.hashCode(this.cantIngredientes);
        hash = 67 * hash + Objects.hashCode(this.descripcion);
        hash = 67 * hash + Objects.hashCode(this.calorias);
        hash = 67 * hash + Objects.hashCode(this.tiempoPrep);
        hash = 67 * hash + Objects.hashCode(this.dificultad);
        hash = 67 * hash + Objects.hashCode(this.popular);
        return hash;
    }
}
