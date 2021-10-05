/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recetas.resources;

import co.edu.uniandes.csw.recetas.dtos.UsuarioDTO;
import co.edu.uniandes.csw.recetas.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.recetas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.recetas.entities.UsuarioEntity;
import co.edu.uniandes.csw.recetas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Maria Valentina Garcia
 */
@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    private static final String ELRECURSOR = "El recurso /usuarios/";
    private static final String NOEXISTE = " no existe.";

    @Inject
    private UsuarioLogic usuarioLogic;

    @POST
    public UsuarioDTO createUsuario(UsuarioDTO usuario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioResource createUsuario: input: {0}", usuario);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        UsuarioEntity usuarioEntity = usuario.toEntity();
        // Invoca la lógica para crear el usuario nuevo
        UsuarioEntity nuevoUsuarioEntity = usuarioLogic.createUsuario(usuarioEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO(nuevoUsuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource createUsuario: output: {0}", nuevoUsuarioDTO);
        return nuevoUsuarioDTO;
    }

    @GET
    public List<UsuarioDetailDTO> getUsuarios() {
        LOGGER.info("UsuarioResource getUsuarios: input: void");
        List<UsuarioDetailDTO> listaUsuarios = listEntity2DetailDTO(usuarioLogic.getUsuarios());
        LOGGER.log(Level.INFO, "UsuarioResource getUsuarios: output: {0}", listaUsuarios);
        return listaUsuarios;
    }

    /**
     * Busca la usuario con el id asociado recibido en la URL y la devuelve.
     *
     * @param usuariosId Identificador de la usuario que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link UsuarioDetailDTO} - La usuario buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la usuario.
     */
    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO getUsuario(@PathParam("usuariosId") Long usuariosId)  {
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: input: {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioLogic.getUsuario(usuariosId);
        if (usuarioEntity == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + NOEXISTE, 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la usuario con el id recibido en la URL con la informacion que
     * se recibe en el cuerpo de la petición.
     *
     * @param usuariosId Identificador de la usuario que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @param usuario {@link UsuarioDetailDTO} La usuario que se desea guardar.
     * @return JSON {@link UsuarioDetailDTO} - La usuario guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la usuario a
     * actualizar.
     */
    @PUT
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO updateUsuario(@PathParam("usuariosId") Long usuariosId, UsuarioDetailDTO usuario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: input: id:{0} , usuario: {1}", new Object[]{usuariosId, usuario});
        usuario.setId(usuariosId);
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + NOEXISTE, 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioLogic.updateUsuario(usuariosId, usuario.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la usuario con el id asociado recibido en la URL.
     *
     * @param usuariosId Identificador de la usuario que se desea borrar. Este
     * debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la usuario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la usuario.
     */
    @DELETE
    @Path("{usuariosId: \\d+}")
    public void deleteUsuario(@PathParam("usuariosId") Long usuariosId){
        LOGGER.log(Level.INFO, "UsuarioResource deleteUsuario: input: {0}", usuariosId);
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + NOEXISTE, 404);
        }
        usuarioLogic.deleteUsuario(usuariosId);
        LOGGER.info("UsuarioResource deleteUsuario: output: void");
    }

    /**
     * Conexión con el servicio de libros para una usuario.
     * {@link UsuarioBooksResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /books que
     * dependen de la usuario, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los libros de una usuario.
     *
     * @param usuariosId El ID de la usuario con respecto a la cual se accede al
     * servicio.
     * @return El servicio de libros para esta usuario en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la usuario.
     */
    @Path("{usuariosId: \\d+}/recetas")
    public Class<UsuarioRecetasResource> getUsuarioRecetasResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + NOEXISTE, 404);
        }
        return UsuarioRecetasResource.class;
    }

    @Path("{usuariosId: \\d+}/calificaciones")
    public Class<UsuarioCalificacionesResource> getUsuarioCalificacionesResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + NOEXISTE, 404);
        }
        return UsuarioCalificacionesResource.class;
    }

    @Path("{usuariosId: \\d+}/comentarios")
    public Class<UsuarioComentariosResource> getUsuarioComentariosResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException(ELRECURSOR + usuariosId + NOEXISTE, 404);
        }
        return UsuarioComentariosResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos UsuarioEntity a una lista de
     * objetos UsuarioDetailDTO (json)
     *
     * @param entityList corresponde a la lista de usuarioes de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de usuarioes en forma DTO (json)
     */
    private List<UsuarioDetailDTO> listEntity2DetailDTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }
}
