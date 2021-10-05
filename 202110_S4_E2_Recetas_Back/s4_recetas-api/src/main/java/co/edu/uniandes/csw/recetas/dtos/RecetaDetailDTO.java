/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.dtos;

import co.edu.uniandes.csw.recetas.entities.CalificacionEntity;
import co.edu.uniandes.csw.recetas.entities.ComentarioEntity;
import co.edu.uniandes.csw.recetas.entities.FotoEntity;
import co.edu.uniandes.csw.recetas.entities.IngredienteEntity;
import co.edu.uniandes.csw.recetas.entities.RecetaEntity;
import co.edu.uniandes.csw.recetas.entities.UtensilioEntity;
import co.edu.uniandes.csw.recetas.entities.VideoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ingrith Barbosa
 */
public class RecetaDetailDTO extends RecetaDTO implements Serializable{
    
    private List <ComentarioDTO> comentarios;
    private List <IngredienteDTO> ingredientes;
    private List <UtensilioDTO> utensilios;
    private List <FotoDTO> fotos;
    private List <VideoDTO> videos;
    private List <CalificacionDTO> calificaciones;
    
    /**
     * Constructor por defecto
     */
    public RecetaDetailDTO()
    {
        super();
    }
    public RecetaDetailDTO(RecetaEntity recetaEntity) {
        super(recetaEntity);
        if(recetaEntity!=null)
        {
            if(recetaEntity.getCalificaciones()!=null)
            {
                calificaciones = new ArrayList<>();
                for(CalificacionEntity calificacion: recetaEntity.getCalificaciones())
                {
                    calificaciones.add(new CalificacionDTO(calificacion));
                }
            }
            if(recetaEntity.getIngredientes()!=null)
            {
                ingredientes = new ArrayList<>();
                for(IngredienteEntity ingrediente: recetaEntity.getIngredientes())
                {
                    ingredientes.add(new IngredienteDTO(ingrediente));
                }
            }
            if(recetaEntity.getUtensilios()!=null)
            {
                utensilios = new ArrayList<>();
                for(UtensilioEntity utensilio: recetaEntity.getUtensilios())
                {
                    utensilios.add(new UtensilioDTO(utensilio));
                }
            }
            if(recetaEntity.getFotos()!=null)
            {
                fotos = new ArrayList<>();
                for(FotoEntity foto: recetaEntity.getFotos())
                {
                    fotos.add(new FotoDTO(foto));
                }
            }
            if(recetaEntity.getComentarios()!=null)
            {
                comentarios = new ArrayList<>();
                for(ComentarioEntity comentario: recetaEntity.getComentarios())
                {
                    comentarios.add(new ComentarioDTO(comentario));
                }
            }
            if(recetaEntity.getVideos()!=null)
            {
                videos = new ArrayList<>();
                for(VideoEntity video: recetaEntity.getVideos())
                {
                    videos.add(new VideoDTO(video));
                }
            }
        }
    }

    /**
     * @return the calificaciones
     */
    public List <CalificacionDTO> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(List <CalificacionDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return the ingredientes
     */
    public List <IngredienteDTO> getIngredientes() {
        return ingredientes;
    }

    /**
     * @param ingredientes the ingredientes to set
     */
    public void setIngredientes(List <IngredienteDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * @return the utensilios
     */
    public List <UtensilioDTO> getUtensilios() {
        return utensilios;
    }

    /**
     * @param utensilios the utensilios to set
     */
    public void setUtensilios(List <UtensilioDTO> utensilios) {
        this.utensilios = utensilios;
    }

    /**
     * @return the fotos
     */
    public List <FotoDTO> getFotos() {
        return fotos;
    }

    /**
     * @param fotos the fotos to set
     */
    public void setFotos(List <FotoDTO> fotos) {
        this.fotos = fotos;
    }
    /**
     * @return the comentarios
     */
    public List <ComentarioDTO> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List <ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @return the videos
     */
    public List <VideoDTO> getVideos() {
        return videos;
    }

    /**
     * @param videos the videos to set
     */
    public void setVideos(List <VideoDTO> videos) {
        this.videos = videos;
    }
    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa la receta.
     */
    @Override
    public RecetaEntity toEntity() {
        RecetaEntity receta= super.toEntity();
        if(ingredientes!=null)
        {
            List<IngredienteEntity> ingredientesEntity = new ArrayList<>();
            for(IngredienteDTO ingrediente: getIngredientes())
            {
                ingredientesEntity.add(ingrediente.toEntity());
            }
            receta.setIngredientes(ingredientesEntity);
        }
        if(utensilios!=null)
        {
            List<UtensilioEntity> utensiliosEntity = new ArrayList<>();
            for(UtensilioDTO utensilio: getUtensilios())
            {
                utensiliosEntity.add(utensilio.toEntity());
            }
            receta.setUtensilios(utensiliosEntity);
        }
        if(fotos!=null)
        {
            List<FotoEntity> fotosEntity = new ArrayList<>();
            for(FotoDTO foto: getFotos())
            {
                fotosEntity.add(foto.toEntity());
            }
            receta.setFotos(fotosEntity);
        }
        if(videos!=null)
        {
            List<VideoEntity> videosEntity = new ArrayList<>();
            for(VideoDTO video: getVideos())
            {
                videosEntity.add(video.toEntity());
            }
            receta.setVideos(videosEntity);
        }
        if(calificaciones!=null)
        {
            List<CalificacionEntity> calificacionesEntity = new ArrayList<>();
            for(CalificacionDTO calificacion: getCalificaciones())
            {
                calificacionesEntity.add(calificacion.toEntity());
            }
            receta.setCalificaciones(calificacionesEntity);
        }
        if(comentarios!=null)
        {
            List<ComentarioEntity> comentariosEntity = new ArrayList<>();
            for(ComentarioDTO comentario: getComentarios())
            {
                comentariosEntity.add(comentario.toEntity());
            }
            receta.setComentarios(comentariosEntity);
        }
        
        return receta;
        
    }

}
